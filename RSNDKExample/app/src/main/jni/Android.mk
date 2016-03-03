LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := RSNDK
LOCAL_LDLIBS += -llog -landroid -lc -lz -lm

# RS setup
ndkLibDir := $(NDK_DIR)/platforms/android-19/arch-arm/usr/lib/rs
ndkIncludeDir := $(NDK_DIR)/platforms/android-19/arch-arm/usr/include

LOCAL_C_INCLUDES += $(ndkIncludeDir)/rs $(ndkIncludeDir)/rs/cpp $(ndkIncludeDir)/rs/scriptc
LOCAL_C_INCLUDES += build/generated/source/rs/debug
LOCAL_LDLIBS += $(ndkLibDir)/libRScpp_static.a

$(info ANDROID_NDK: $(NDK_DIR))

LOCAL_SRC_FILES := main.cpp

include $(BUILD_SHARED_LIBRARY)