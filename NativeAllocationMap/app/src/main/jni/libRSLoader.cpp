//
// Created by cmaster11 on 14/04/16.
//

#include "libRSLoader.h"
#include <android/log.h>
#include <dlfcn.h>
#include <malloc.h>

#define  LOG_TAG    "jniDLSym"
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

// Handle to libRS.so library
void *libRShandle;
RSFnPointers * rsFnPointers;

// Function to load libRS.so shared library from device
bool loadLibRS() {
    LOGI("Loading libRS.so");

    // Let's just load it once
    if (libRShandle == NULL) {

        // Initialize pointers structure
        rsFnPointers = (RSFnPointers*) malloc(sizeof(RSFnPointers));

        // Loads libRS.so headers to find function address.
        // More info can be found here: http://linux.die.net/man/3/dlopen
        libRShandle = dlopen("libRS.so", RTLD_LOCAL | RTLD_LAZY);
        if (libRShandle == NULL) {
            LOGE("libRS.so not available");
            return false;
        }
    }

    return true;
}

RSFnPointers * loadLibRSForNativeKernel() {

    if (!loadLibRS()) { return NULL; }

    // Loads functions into our variable
    void *tmpHandle;

    LOGI("Loading rsScriptForEach");
    tmpHandle = dlsym(libRShandle, "rsScriptForEach");
    if (tmpHandle == NULL) {
        LOGE("rsScriptForEach not available");
        return NULL;
    } else {
        rsFnPointers->ScriptForEach = (rsScriptForEachPtr) tmpHandle;
    }

    LOGI("Loading rsContextFinish");
    tmpHandle = dlsym(libRShandle, "rsContextFinish");
    if (tmpHandle == NULL) {
        LOGE("rsContextFinish not available");
        return NULL;
    } else {
        rsFnPointers->ContextFinish = (rsContextFinishPtr) tmpHandle;
    }

    LOGI("Loading rsScriptSetVarI");
    tmpHandle = dlsym(libRShandle, "rsScriptSetVarI");
    if (tmpHandle == NULL) {
        LOGE("rsScriptSetVarI not available");
        return NULL;
    } else {
        rsFnPointers->ScriptSetVarI = (rsScriptSetVarIPtr) tmpHandle;
    }

    return rsFnPointers;
}

RSFnPointers * loadLibRSForAllocationGetPointer() {

    if (!loadLibRS()) { return NULL; }

    // Loads functions into our variable
    void *tmpHandle;

    LOGI("Loading rsAllocationGetPointer");
    tmpHandle = dlsym(libRShandle, "rsAllocationGetPointer");
    if (tmpHandle == NULL) {
        LOGE("rsAllocationGetPointer not available");
        return NULL;
    } else {
        rsFnPointers->AllocationGetPointer = (rsAllocationGetPointerPtr) tmpHandle;
    }

    return rsFnPointers;
}

#undef LOG_TAG
#undef LOGE
