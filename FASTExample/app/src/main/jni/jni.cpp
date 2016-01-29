#include <jni.h>
#include "fastlib/fast.h"

extern "C" {

int imageWidth;
int imageHeight;

JNIEXPORT void JNICALL
Java_net_hydex11_fastexample_MainActivity_setImageSize(JNIEnv *env, jobject, jint width,
                                                       jint height) {
    imageWidth = width;
    imageHeight = height;
}

JNIEXPORT int JNICALL
Java_net_hydex11_fastexample_MainActivity_fastLibExtraction(JNIEnv *env, jobject, jbyteArray grayDataArray) {

    // False because we do not want to copy it
    jboolean isCopy = false;
    byte *grayDataPointer = (byte *) env->GetByteArrayElements(grayDataArray, &isCopy);

    // Perform extraction
    // xy* fast9_detect(const byte* im, int xsize, int ysize, int stride, int b, int* ret_num_corners);
    // Implementation of optimized FAST lib extractor.
    // Reference: http://www.edwardrosten.com/work/fast.html
    int cornersCount = 0;
    fast9_detect(grayDataPointer, imageWidth, imageHeight, imageWidth, 20, &cornersCount);

    // Release env
    env->ReleaseByteArrayElements(grayDataArray, (jbyte *) grayDataPointer, JNI_ABORT);

    return cornersCount;
}

}