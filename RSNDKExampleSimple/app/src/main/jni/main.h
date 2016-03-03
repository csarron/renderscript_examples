#include <jni.h>
#include <android/log.h>

#ifndef RSNDKEXAMPLE_MAIN_H
#define RSNDKEXAMPLE_MAIN_H

#define JNI_FUNCTION(returnType, functionName, ...) JNIEXPORT returnType Java_net_hydex11_rsndkexample_MainActivity_##functionName(JNIEnv* env, jobject, ##__VA_ARGS__)

//#ifdef NDEBUG

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "slam_cv", __VA_ARGS__))
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "slam_cv", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "slam_cv", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "slam_cv", __VA_ARGS__))

#endif //RSNDKEXAMPLE_MAIN_H
