//
// Created by Alberto on 14/01/2016.
//

#ifndef NATIVEALLOCATIONMAP_SCRIPT_H
#define NATIVEALLOCATIONMAP_SCRIPT_H

#include <dlfcn.h>

// Handle to libRS.so library
void *libRShandle;

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

void loadLibRS() {

    // Loads libRS.so headers to find function address.
    // More info can be found here: http://linux.die.net/man/3/dlopen
    libRShandle = dlopen("libRS.so", RTLD_LOCAL | RTLD_LAZY);

    // Loads functions into our variable
    *(void **) (&rsScriptForEach) = dlsym(libRShandle, "rsScriptForEach");
    *(void **) (&rsContextFinish) = dlsym(libRShandle, "rsContextFinish");
    *(void **) (&rsScriptSetVarI) = dlsym(libRShandle, "rsScriptSetVarI");
}

#endif //NATIVEALLOCATIONMAP_SCRIPT_H
