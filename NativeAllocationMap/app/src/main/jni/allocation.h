#include "apiLevel.h"
#include "libRSLoader.h"

// Function to get the allocation data pointer
inline void *getAllocationPointer(JNIEnv *env, void *contextPtr, void *allocationPtr) {

    void *mallocPtr;
    // Struct that contains RS lib function pointers
    RSFnPointers *fnPointers;

    switch (getAPILevel(env)) {
        // API 18 and 19 require direct memory access because
        // they do not provide a "getPointer" function
        case 18:
            mallocPtr = *(void **) (allocationPtr + 76);
            break;
        case 19:
            mallocPtr = *(void **) (allocationPtr + 88);
            break;
            // For API >= 21, AllocationGetPointer function is available
        case 21:
        case 22:
        case 23:
        case 24:
            if ((fnPointers = loadLibRSForAllocationGetPointer()) == NULL)
                throw ("rsAllocationGetPointer loading failed");

            // void *rsi_AllocationGetPointer(Context *rsc, RsAllocation valloc,
            //      uint32_t lod, RsAllocationCubemapFace face,
            //      uint32_t z, uint32_t array, size_t *stride, size_t strideLen)
            mallocPtr = fnPointers->AllocationGetPointer(contextPtr, allocationPtr, 0, 0, 0, 0, 0,
                                                         sizeof(size_t));

            break;
        default:
            throw ("API level not supported");
    }

    return mallocPtr;

}