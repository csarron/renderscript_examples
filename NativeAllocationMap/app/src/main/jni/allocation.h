#include <jni.h>
#include <stdint.h>

#ifndef JNI_ALLOCATION_H_H
#define JNI_ALLOCATION_H_H

typedef enum {
    RS_ALLOCATION_MIPMAP_NONE = 0, RS_ALLOCATION_MIPMAP_FULL = 1, RS_ALLOCATION_MIPMAP_ON_SYNC_TO_TEXTURE = 2
} rs_allocation_mipmap_control;

typedef struct Allocation {
    char __pad[36];
    // Padding is sum of contents of struct ObjectBase rs/rsObjectBase.cpp

    struct {
        void *drv;

        struct {
            const void *type;

            uint32_t usageFlags;
            rs_allocation_mipmap_control mipmapControl;

            // Cached fields from the Type and Element
            // to prevent pointer chasing in critical loops.
            uint32_t yuv;
            uint32_t elementSizeBytes;
            bool hasMipmaps;
            bool hasFaces;
            bool hasReferences;
            void *userProvidedPtr;
            int32_t surfaceTextureID;
            void * deprecated01;
            void * deprecated02;
        } state;

        char __pad2[8]; // ptr fix

        struct DrvState {
            struct LodState {
                void *mallocPtr;
                size_t stride;
                uint32_t dimX;
                uint32_t dimY;
                uint32_t dimZ;
            } lod[16];
            size_t faceOffset;
            uint32_t lodCount;
            uint32_t faceCount;
        } drvState;

    } mHal;
} Allocation_t;

#endif //JNI_ALLOCATION_H_H
