#include "apiLevel.h"
#include "libRSLoader.h"


inline void * getAllocationPointer(JNIEnv *env, void *contextPtr, void *allocationPtr) {

    void * mallocPtr;
    RSFnPointers * fnPointers;

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
            if((fnPointers = loadLibRSForAllocationGetPointer()) == NULL)
                throw("rsAllocationGetPointer loading failed");

            mallocPtr = fnPointers->AllocationGetPointer(contextPtr,allocationPtr,
                                                         0,0,0,0,0,sizeof(size_t));

            break;
        default:
            throw ("API level not supported");
    }

    return mallocPtr;

}