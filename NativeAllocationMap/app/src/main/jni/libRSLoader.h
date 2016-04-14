//
// Created by Alberto on 14/01/2016.
//

#ifndef NATIVEALLOCATIONMAP_SCRIPT_H
#define NATIVEALLOCATIONMAP_SCRIPT_H

#include "stddef.h"

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
typedef void (*rsScriptForEachPtr)(void *, void *, int, void *, void *, void *, int, void *, int);

// Declaration of RS finish function pointer. Its only argument is RS context pointer.
typedef void (*rsContextFinishPtr)(void *);

// Function to set a script's integer value.
// Actual declaration:
// https://android.googlesource.com/platform/frameworks/rs/+/marshmallow-mr1-release/rsScript.cpp#243
// void rsi_ScriptSetVarI(Context *rsc, RsScript vs, uint32_t slot, int value)
typedef void (*rsScriptSetVarIPtr)(void *, void *, int, int);

// Function to get an allocation pointer.
// Actual declaration:
// https://android.googlesource.com/platform/frameworks/rs/+/marshmallow-mr1-release/rsAllocation.cpp#844
// void *rsi_AllocationGetPointer(Context *rsc, RsAllocation valloc,
//      uint32_t lod, RsAllocationCubemapFace face,
//      uint32_t z, uint32_t array, size_t *stride, size_t strideLen)
typedef void * (*rsAllocationGetPointerPtr) (void * RsContext, void * RsAllocation, int lod,
                                  int RsAllocationCubemapFace, int z, int array,
                                  size_t *stride, size_t stride_len);

// Get functions table
typedef struct {
    rsScriptForEachPtr ScriptForEach;
    rsContextFinishPtr ContextFinish;
    rsScriptSetVarIPtr ScriptSetVarI;
    rsAllocationGetPointerPtr AllocationGetPointer;
} RSFnPointers;

// Functions to load RS functions from libRS.so library
RSFnPointers * loadLibRSForNativeKernel();
RSFnPointers * loadLibRSForAllocationGetPointer();

#endif //NATIVEALLOCATIONMAP_SCRIPT_H
