//
// Created by Alberto on 14/01/2016.
//

#ifndef NATIVEALLOCATIONMAP_SCRIPT_H
#define NATIVEALLOCATIONMAP_SCRIPT_H

#include <dlfcn.h>
#include <android/log.h>

#define  LOG_TAG    "jniDLSym"
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

// Handle to libRS.so library
void *libRShandle;

// Function to load libRS.so shared library from device
inline bool loadLibRS() {
    LOGI("Loading libRS.so");

    // Let's just load it once
    if (libRShandle == NULL) {
        // Loads libRS.so headers to find function address.
        // More info can be found here: http://linux.die.net/man/3/dlopen
        libRShandle = dlopen("libRS.so", RTLD_LOCAL | RTLD_LAZY);
        if (libRShandle == NULL) {
            LOGE("libRS.so not available");
            return false;
        }
    }
}

// Declaration of RS forEach function pointer. Required args can be seen in its definition inside
// rsScript.cpp file:
//
// void rsi_ScriptForEach(Context *rsc, RsScript vs, uint32_t slot,
//                       RsAllocation vain, RsAllocation vaout,
//                       const void *params, size_t paramLen,
//                       const RsScriptCall *sc, size_t scLen)
//
// Reference:
// https://android.googlesource.com/platform/frameworks/rs/+/marshmallow-mr1-release/rsScript.cpp#210
//
// Types reference:
// https://android.googlesource.com/platform/frameworks/rs/+/marshmallow-mr1-release/rsDefines.h#43
//
// * rsc is the pointer to a RS context
// * vs is the pointer to a RS script
// * slot is the integer representing the kernel index that has to be called.
// * vain and vaout are pointers to input and output allocations
// * params is custom user data struct pointer.
// * paramLen is user data struct size.
// * sc is a LaunchOptions pointer, which restrains the kernel
//		usage, for example limiting initial X or Y.
// * scLen is the actual size of the LaunchOptions struct.
int (*rsScriptForEach)(void *, void *, int, void *, void *, void *, int, void *, int);

// Declaration of RS finish function pointer. Its only argument is RS context pointer.
int (*rsContextFinish)(void *);

// Function to set a script's integer value.
// Actual declaration:
// https://android.googlesource.com/platform/frameworks/rs/+/marshmallow-mr1-release/rsScript.cpp#243
// void rsi_ScriptSetVarI(Context *rsc, RsScript vs, uint32_t slot, int value)
int (*rsScriptSetVarI)(void *, void *, int, int);

inline bool loadLibRSForNativeKernel() {

    if (!loadLibRS()) { return false; }

    // Loads functions into our variable
    void *tmpHandle;

    LOGI("Loading rsScriptForEach");
    tmpHandle = dlsym(libRShandle, "rsScriptForEach");
    if (tmpHandle == NULL) {
        LOGE("rsScriptForEach not available");
        return false;
    } else {
        *(void **) (&rsScriptForEach) = tmpHandle;
    }

    LOGI("Loading rsContextFinish");
    tmpHandle = dlsym(libRShandle, "rsContextFinish");
    if (tmpHandle == NULL) {
        LOGE("rsContextFinish not available");
        return false;
    } else {
        *(void **) (&rsContextFinish) = tmpHandle;
    }

    LOGI("Loading rsScriptSetVarI");
    tmpHandle = dlsym(libRShandle, "rsScriptSetVarI");
    if (tmpHandle == NULL) {
        LOGE("rsScriptSetVarI not available");
        return false;
    } else {
        *(void **) (&rsScriptSetVarI) = tmpHandle;
    }

    return true;
}


// Function to get an allocation pointer.
// Actual declaration:
// https://android.googlesource.com/platform/frameworks/rs/+/marshmallow-mr1-release/rsAllocation.cpp#844
// void *rsi_AllocationGetPointer(Context *rsc, RsAllocation valloc,
//      uint32_t lod, RsAllocationCubemapFace face,
//      uint32_t z, uint32_t array, size_t *stride, size_t strideLen)
void * (*rsAllocationGetPointer) (void * RsContext, void * RsAllocation, int lod,
                                int RsAllocationCubemapFace, int z, int array,
                                size_t *stride, size_t stride_len);
inline bool loadLibRSForAllocationGetPointer() {

    if (!loadLibRS()) { return false; }

    // Loads functions into our variable
    void *tmpHandle;

    LOGI("Loading rsAllocationGetPointer");
    tmpHandle = dlsym(libRShandle, "rsAllocationGetPointer");
    if (tmpHandle == NULL) {
        LOGE("rsAllocationGetPointer not available");
        return false;
    } else {
        *(void **) (&rsAllocationGetPointer) = tmpHandle;
    }

    return true;
}

#undef LOG_TAG
#undef LOGE

#endif //NATIVEALLOCATIONMAP_SCRIPT_H
