LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := RSNDK
LOCAL_LDLIBS += -llog -landroid -lc -lz -lm

# RS setup
ndkLibDir := $(NDK_DIR)/platforms/android-19/arch-arm/usr/lib/rs
ndkIncludeDir := $(NDK_DIR)/platforms/android-19/arch-arm/usr/include

#LOCAL_C_FLAGS += -frtti
LOCAL_C_INCLUDES += $(ndkIncludeDir)/rs $(ndkIncludeDir)/rs/cpp $(ndkIncludeDir)/rs/scriptc
#LOCAL_STATIC_LIBRARIES += rs/libRScpp_static.a

LOCAL_LDLIBS += $(ndkLibDir)/libRScpp_static.a

# Should be changed to release when necessary
GENERATED_DIR:=build/generated/source/rs/debug

# Relative dir, as local src are relative to Android.mk folder
GENERATED_DIR_RELATIVE := ../../../$(GENERATED_DIR)

LOCAL_C_INCLUDES += $(GENERATED_DIR)

$(info ANDROID_NDK: $(NDK_DIR))
$(info GENERATED_DIR: $(GENERATED_DIR))
$(info GENERATED_DIR_RELATIVE: $(GENERATED_DIR_RELATIVE))

LOCAL_SRC_FILES := main.cpp $(GENERATED_DIR_RELATIVE)/ScriptC_main.cpp

include $(BUILD_SHARED_LIBRARY)