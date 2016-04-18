LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := native
LOCAL_CFLAGS += -fopenmp -std=c++11
LOCAL_LDFLAGS += -fopenmp

LOCAL_SRC_FILES := jni.cpp fastlib/fast_9.cpp

include $(BUILD_SHARED_LIBRARY)