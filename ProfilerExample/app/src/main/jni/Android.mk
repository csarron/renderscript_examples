LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := native
LOCAL_CFLAGS += -fopenmp
LOCAL_LDFLAGS += -ljnigraphics -llog -fopenmp

LOCAL_SRC_FILES := jni.cpp

include $(BUILD_SHARED_LIBRARY)
