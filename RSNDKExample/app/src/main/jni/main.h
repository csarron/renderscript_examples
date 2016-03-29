#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <stdlib.h>

#include <RenderScript.h>

#ifndef RSNDKEXAMPLE_MAIN_H
#define RSNDKEXAMPLE_MAIN_H

// Macro to declare a general JNI function
#define _JNI_FUNCTION(packageName, className, returnType, functionName, ...) \
    JNIEXPORT returnType \
    Java_##packageName##_##className##_##functionName \
    (JNIEnv* env, jobject jObj, ##__VA_ARGS__)

// Macro to declare a specialized JNI function
// JNI_FUNCTION(returnType, functionName, arguments)
// Arguments "JNIEnv* env" and "jobject jObj" are already defined.
//
// Bounds JNI functions to package net.hydex11.rsndkexample
// and class MainActivity
#define JNI_FUNCTION(returnType, functionName, ...) \
    _JNI_FUNCTION( \
        net_hydex11_rsndkexample, \
        MainActivity, \
        returnType, functionName, ##__VA_ARGS__)

// Log functions
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "RSNDK", __VA_ARGS__))
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "RSNDK", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "RSNDK", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "RSNDK", __VA_ARGS__))

#endif //RSNDKEXAMPLE_MAIN_H
