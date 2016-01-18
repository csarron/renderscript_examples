//
// Created by Alberto on 14/01/2016.
//

#ifndef NATIVEALLOCATIONMAP_SCRIPT_H
#define NATIVEALLOCATIONMAP_SCRIPT_H

#include <dlfcn.h>

// Handle to libRS.so library
void *libRShandle;

// Declaration of RS forEach function pointer. Required args can be deduced by its usage inside
// Script.cpp file:
//
// - rsScriptForEach(mRS->getContext(), getID(), slot, in_id, out_id, usr, usrLen, NULL, 0);
//
// * Context, IDs are pointers ("in_id" is input Allocation pointer).
// * slot is an integer, as is a kernel index in a script.
// * usr is custom user data struct pointer.
// * usrLen is usr struct size
// * NULL is occuping the place of LaunchOptions pointer, that would restrain the kernel
//		usage, for example limiting initial X or Y.
// * 0 is the actual size of the LaunchOptions struct.
int (*rsScriptForEach)(void *, void *, int, void *, void *, void *, int, void *, int);

// Declaration of RS finish function pointer. Its only argument is RS context pointer.
int (*rsContextFinish)(void *);

// Function to set a script's integer value.
// Actual declaration: https://android.googlesource.com/platform/frameworks/rs/+/jb-mr2-release/rsScript.cpp#211
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
