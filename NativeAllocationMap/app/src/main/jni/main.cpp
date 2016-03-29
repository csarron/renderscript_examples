#include <jni.h>
#include <string.h>

#include "allocation.h"

extern "C" {

JNIEXPORT jint JNICALL
Java_net_hydex11_nativeallocationmap_MainActivity_executeNativeExtraction(JNIEnv *env, jobject,
                                                                          jlong AllocationID) {
    // Casts pointer address void *
    void *pointerAddress = (void *) AllocationID;

    // Maps memory to stub allocation struct
    Allocation_t *mAllocation = (Allocation *) pointerAddress;

    // Retrieves memory pointer
    void *memoryPointer = mAllocation->mHal.drvState.lod[0].mallocPtr;

    // Reads values and packs them into an integer (just to transfer them back to Java with ease)
    unsigned char values[3];

    // Copies values using memcpy(dest, src, byteSize)
    memcpy(&values, memoryPointer, 3);

    // Packs the result (from 3 bytes into an integer) to easily return it
    int output = 0;
    output |= values[0];
    output |= values[1] << 8;
    output |= values[2] << 16;

    return output;
}

// Section to test the call to a kernel from NDK side

// Includes our custom loading function
#include "script.h"

JNIEXPORT void JNICALL
Java_net_hydex11_nativeallocationmap_MainActivity_executeNativeKernel(JNIEnv *env, jobject,
                                                                      jlong ContextID,
                                                                      jlong ScriptID,
                                                                      jlong AllocationInID,
                                                                      jlong AllocationOutID) {

    // Loads libRS.so library and finds rsScriptForEach function address
    loadLibRS();

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

    rsScriptSetVarI((void *) ContextID, (void *) ScriptID, variableSlot, newValue);

    rsScriptForEach((void *) ContextID, (void *) ScriptID, kernelSlot, (void *) AllocationInID,
                    (void *) AllocationOutID, 0, 0, 0, 0);

    // Wait for the kernel to end its operations
    rsContextFinish((void *) ContextID);
}

}