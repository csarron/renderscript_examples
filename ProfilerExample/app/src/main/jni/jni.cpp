#include <jni.h>
#include <omp.h>
#include <stdlib.h>
#include <android/log.h>
#include <android/bitmap.h>

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "ProfilerExampleNDK", __VA_ARGS__))

#include "mathFunctions.h"

typedef unsigned char byte;

int imageWidth;
int imageHeight;
int totalGrayPixels, totalRGBAPixels;
byte* grayImagePointer, *rgbaImagePointer, *rgbaOutputImagePointer;

extern "C" {

// Load input image
JNIEXPORT void JNICALL
    Java_net_hydex11_profilerexample_MainActivity_setImageSize(JNIEnv* env, jobject, jint width, jint height)
{
    imageWidth = width;
    imageHeight = height;
    totalGrayPixels = imageWidth * imageHeight;
    totalRGBAPixels = imageWidth * imageHeight * 4;

    // Pre allocates the gray image memory
    grayImagePointer = (byte*)malloc(totalGrayPixels);
    if(grayImagePointer == NULL) {
        LOGD("Could not allocate NDK gray memory");
        exit(1); // An error has occurred!
    }

    rgbaImagePointer = (byte*)malloc(totalRGBAPixels);
    if(rgbaImagePointer == NULL) {
        LOGD("Could not allocate NDK rgba memory");
        exit(1); // An error has occurred!
    }
    rgbaOutputImagePointer = (byte*)malloc(totalRGBAPixels);
    if(rgbaOutputImagePointer == NULL) {
        LOGD("Could not allocate NDK rgba output memory");
        exit(1); // An error has occurred!
    }
}

JNIEXPORT void JNICALL
    Java_net_hydex11_profilerexample_MainActivity_loadInputImage(JNIEnv* env, jobject, jobject bitmap)
{

    int ret = 0;
    void* bitmapPixels;
    if((ret = AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels)) < 0) {
        LOGD("AndroidBitmap_lockPixels() failed ! error=%d", ret);
        exit(1); // An error has occurred!
    }

    // Copies input image locally.
    memcpy(rgbaImagePointer, bitmapPixels, totalRGBAPixels);

    AndroidBitmap_unlockPixels(env, bitmap);
}

// Blur test
int blurRadius;
int blurPadding;
int blurTotalCount;
JNIEXPORT void JNICALL Java_net_hydex11_profilerexample_MainActivity_ndkSetBlurData(JNIEnv* env, jobject, jint radius)
{
    blurRadius = radius;
    // Runtime padding, to prevent accessing wrong memory
   blurPadding = imageWidth * blurRadius + blurRadius;
    blurTotalCount = (blurRadius * 2 + 1);
    blurTotalCount *= blurTotalCount;
}

JNIEXPORT void JNICALL Java_net_hydex11_profilerexample_MainActivity_ndkBlur(JNIEnv* env, jobject)
{
// Here totalGrayPixels is used because it represents each pixel package
    #pragma omp parallel for shared(blurRadius, blurPadding, blurTotalCount)
    for(int i = blurPadding; i < totalGrayPixels - blurPadding - 1; i++) {

        int sum[] = { 0, 0, 0};
        int currentRGBAIndex = i * 4;
        for(int yi = -blurRadius; yi <= blurRadius; ++yi) {
            for(int xi = -blurRadius; xi <= blurRadius; ++xi) {
                
                int pixelIndex = currentRGBAIndex + (xi + yi*imageWidth)*4;

                sum[0] += rgbaImagePointer[pixelIndex];
                sum[1] += rgbaImagePointer[pixelIndex + 1];
                sum[2] += rgbaImagePointer[pixelIndex + 2];
            }
        }

        rgbaOutputImagePointer[currentRGBAIndex] = sum[0] / blurTotalCount;
        rgbaOutputImagePointer[currentRGBAIndex + 1] = sum[1] / blurTotalCount;
        rgbaOutputImagePointer[currentRGBAIndex + 2] = sum[2] / blurTotalCount;
    }
}

JNIEXPORT void JNICALL Java_net_hydex11_profilerexample_MainActivity_ndkSetValues(JNIEnv* env, jobject)
{
// Here totalGrayPixels is used because it represents each pixel package
    #pragma omp parallel for shared(blurRadius, blurPadding, blurTotalCount)
    for(int i = blurPadding; i < totalGrayPixels - blurPadding - 1; i++) {

        int currentRGBAIndex = i * 4;
        byte currentElement[3];
        
        currentElement[0] = rgbaImagePointer[currentRGBAIndex];
        currentElement[1] = rgbaImagePointer[currentRGBAIndex+1];
        currentElement[2] = rgbaImagePointer[currentRGBAIndex+2];
        
        for(int yi = -blurRadius; yi <= blurRadius; ++yi) {
            for(int xi = -blurRadius; xi <= blurRadius; ++xi) {
                
                int pixelIndex = currentRGBAIndex + (xi + yi*imageWidth)*4;
                
                rgbaOutputImagePointer[pixelIndex]  = currentElement[0];
                rgbaOutputImagePointer[pixelIndex+1]  = currentElement[1];
                rgbaOutputImagePointer[pixelIndex+2]  = currentElement[2];
                rgbaOutputImagePointer[pixelIndex+3]  = 255;
            }
        }
    }
}

// RGBAToGRAY test
const float grayMultipliers[] = { 0.299f, 0.587f, 0.114f };

JNIEXPORT void JNICALL Java_net_hydex11_profilerexample_MainActivity_rgbaToGray(JNIEnv* env, jobject)
{

#pragma omp parallel for
    for(int i = 0; i < totalGrayPixels; i++) {
        int currentRGBAIndex = i * 4;
        grayImagePointer[i] = (byte)((float)rgbaImagePointer[currentRGBAIndex] * grayMultipliers[0] +
                                     (float)rgbaImagePointer[currentRGBAIndex + 1] * grayMultipliers[1] +
                                     (float)rgbaImagePointer[currentRGBAIndex + 2] * grayMultipliers[2]);
    }
}

// PI test
JNIEXPORT void JNICALL Java_net_hydex11_profilerexample_MainActivity_calculatePI(JNIEnv* env,
                                                                                 jobject,
                                                                                 jint parallelExecutions,
                                                                                 jint piIterations)
{
#pragma omp parallel for
    for(int i = 0; i < parallelExecutions; i++) {
        piTest(piIterations);
    }
}

// Check OpenMP enabled
JNIEXPORT void JNICALL Java_net_hydex11_profilerexample_MainActivity_checkOpenMPEnabled(JNIEnv* env, jobject)
{

    int threadsNumber = 0;

#pragma omp parallel for
    for(int i = 0; i < 100; i++) {
        int currentNum = omp_get_num_threads();
        if(currentNum > threadsNumber)
            threadsNumber = currentNum;
    }

    LOGD("OpenMP current threads count: %d", threadsNumber);
    LOGD("OpenMP max threads count: %d", omp_get_max_threads());
}
}