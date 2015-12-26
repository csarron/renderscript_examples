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

}