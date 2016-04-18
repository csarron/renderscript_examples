#include <jni.h>
#include <stdlib.h>
#include <omp.h>
#include <algorithm>

#include "fastlib/fast.h"

int imageWidth;
int imageHeight;
int totalGrayPixels;
byte* grayImagePointer;

float clamp(float n, float lower, float upper)
{
    return std::max(lower, std::min(n, upper));
}

void yuvToGray(byte* input, byte* output)
{
    
#pragma omp parallel for default(shared)
    for(int y = 0; y < imageHeight; y++) {
        for(int x = 0; x < imageWidth; x++) {

            int baseIdx = x + y * imageWidth;
            int baseUYIndex = totalGrayPixels + (y >> 1) * imageWidth + (x & 0xfffffe);

            byte _y = input[baseIdx];
            byte _u = input[baseUYIndex];
            byte _v = input[baseUYIndex + 1];
            _y = _y < 16 ? 16 : _y;

            short Y = ((short)_y) - 16;
            short U = ((short)_u) - 128;
            short V = ((short)_v) - 128;

            // const static float3 gMonoMult = {0.299f, 0.587f, 0.114f};

            short p = 0;
            p += (short)((float)clamp((float)((Y * 298 + V * 409 + 128) >> 8), 0, 255) * 0.299f);           // R
            p += (short)((float)clamp((float)((Y * 298 - U * 100 - V * 208 + 128) >> 8), 0, 255) * 0.587f); // G
            p += (short)((float)clamp((float)((Y * 298 + U * 516 + 128) >> 8), 0, 255) * 0.114f);           // B

            output[baseIdx] = p;
        }
    }
}

extern "C" {

JNIEXPORT void JNICALL Java_net_hydex11_fastexample_MainActivity_setImageSize(JNIEnv* env,
    jobject,
    jint width,
    jint height)
{
    imageWidth = width;
    imageHeight = height;
    totalGrayPixels = imageWidth * imageHeight;

    // Pre allocates the gray image memory
    grayImagePointer = (byte*)malloc(totalGrayPixels);
    if(grayImagePointer == NULL)
        exit(1); // An error has occurred!
}

JNIEXPORT int JNICALL Java_net_hydex11_fastexample_MainActivity_yuvToGray(JNIEnv* env,
    jobject,
    jbyteArray yuvDataArray)
{
    // False because we do not want to copy the input data, just refer to it.
    jboolean isCopy = false;
    byte* yuvDataPointer = (byte*)env->GetByteArrayElements(yuvDataArray, &isCopy);

    // Receives a YUV image as input. Must be converted to a grayscale one.
    yuvToGray(yuvDataPointer, grayImagePointer);

    // Release env
    env->ReleaseByteArrayElements(yuvDataArray, (jbyte*)yuvDataPointer, JNI_ABORT);
}

JNIEXPORT int JNICALL Java_net_hydex11_fastexample_MainActivity_fastLibExtraction(JNIEnv* env,
    jobject)
{
    // Perform extraction
    // xy* fast9_detect(const byte* im, int xsize, int ysize, int stride, int b, int* ret_num_corners);
    // Implementation of optimized FAST lib extractor.
    // Reference: http://www.edwardrosten.com/work/fast.html
    int cornersCount = 0;
    fast9_detect(grayImagePointer, imageWidth, imageHeight, imageWidth, 20, &cornersCount);

    return cornersCount;
}
}
