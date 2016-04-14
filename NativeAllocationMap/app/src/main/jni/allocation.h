#include "apiLevel.h"
#include "libRSLoader.h"

inline void * getAllocationPointer(JNIEnv *env, void *contextPtr, void *allocationPtr) {

    void * mallocPtr;

    switch (getAPILevel(env)) {

        case 18:
            mallocPtr = * (void **) (allocationPtr + 76);
            break;
        case 19:
            mallocPtr = * (void **) (allocationPtr + 88);
            break;
        case 21:
        case 22:
        case 23:
        case 24:
            if(!loadLibRSForAllocationGetPointer())
                throw("rsAllocationGetPointer loading failed");

            mallocPtr = rsAllocationGetPointer(contextPtr,allocationPtr,0,0,0,0,0,0);

        default:
            throw ("API level not supported");
    }

    return mallocPtr;

}