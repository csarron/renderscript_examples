#include "idefix.h"
#include <jni.h>
#include <string.h>
#include <android/log.h>

#include "allocation.h"
#include "libRSLoader.h"

extern "C" {

JNIEXPORT jint
JNICALL
Java_net_hydex11_nativeallocationmap_MainActivity_executeNativeExtraction(JNIEnv *env, jobject,
                                                                          jlong ContextID,
                                                                          jlong AllocationID) {

    // Gets allocation mallocPtr
    void *mallocPtr = getAllocationPointer(env, (void*) ContextID, (void*) AllocationID);

    // Reads values and packs them into an integer (just to transfer them back to Java with ease)
    unsigned char values[3];

    // Copies values using memcpy(dest, src, byteSize)
    memcpy(&values, mallocPtr, 3);

    // Packs the result (from 3 bytes into an integer) to easily return it
    int output = 0;
    output |= values[0];
    output |= values[1] << 8;
    output |= values[2] << 16;

    return output;
}

// Includes our custom loading function
#include "libRSLoader.h"

// Section to test the call to a kernel from NDK side
#define  LOG_TAG    "NativeKernel"
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

JNIEXPORT bool JNICALL
Java_net_hydex11_nativeallocationmap_MainActivity_executeNativeKernel(JNIEnv *env, jobject,
                                                                      jlong ContextID,
                                                                      jlong ScriptID,
                                                                      jlong AllocationInID,
                                                                      jlong AllocationOutID) {

    RSFnPointers * fnPointers;

    LOGI("loadLibRSForNativeKernel()");
    if((fnPointers = loadLibRSForNativeKernel()) == NULL)
        return false;

    // Invoke forEach for our kernel "sum2", that has slot 2 (as it is the second, non "root" named, declared function).
    int kernelSlot = 2;

    // We want to modify the ndkSumAmount variable content. It occupies slot 0, as it is the first
    // variable declared in the script.
    int variableSlot = 0;
    int newValue = 5;

    // All the following fields can be obtained using reflection:
    //
    // * ContextID is the pointer to a Java RenderScript.mContext private field.
    // * ScriptID is the pointer to a Java ScriptC.mID private field.
    // * AllocationInID is the pointer to a Java Allocation.mID private field.
    // * AllocationOutID is the pointer to a Java Allocation.mID private field.
    // * kernelSlot is the slot index of wanted kernel function. This slot can be
    //		seen inside a script's auto-generated code, like the row:
    //
    // 		private final static int mExportForEachIdx_myKernelName = 1;
    //
    // * variableSlot is the slot index of script's non-static variable:
    //
    //      private final static int mExportVarIdx_ndkSumAmount = 0;

    LOGI("rsScriptSetVarI()");
    fnPointers->ScriptSetVarI((void *) ContextID, (void *) ScriptID, variableSlot, newValue);

    LOGI("rsScriptForEach()");
    fnPointers->ScriptForEach((void *) ContextID, (void *) ScriptID, kernelSlot, (void *) AllocationInID,
                    (void *) AllocationOutID, 0, 0, 0, 0);

    // Wait for the kernel to end its operations
    LOGI("rsContextFinish()");
    fnPointers->ContextFinish((void *) ContextID);

    return true;
}

}

#undef LOG_TAG
#undef LOGE
