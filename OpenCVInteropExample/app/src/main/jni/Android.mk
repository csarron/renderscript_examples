LOCAL_PATH := $(call my-dir)

# Include OpenCV runtime libraries
include $(CLEAR_VARS)

OPENCV_INSTALL_MODULES:=on
OPENCV_CAMERA_MODULES:=off
OPENCV_LIB_TYPE:=SHARED

include $(LOCAL_PATH)/../../../../openCVLibrary310/native/jni/OpenCV.mk

