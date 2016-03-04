LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := RSNDK
LOCAL_LDLIBS += -llog -landroid -lc -lz -lm

# Should be changed to release when necessary
GENERATED_DIR:=build/generated/source/rs/debug

# RS setup
ndkLibDir := $(NDK_DIR)/platforms/$(TARGET_PLATFORM)/arch-arm/usr/lib/rs
ndkIncludeDir := $(NDK_DIR)/platforms/$(TARGET_PLATFORM)/arch-arm/usr/include

LOCAL_C_INCLUDES += $(ndkIncludeDir)/rs $(ndkIncludeDir)/rs/cpp
LOCAL_C_INCLUDES += $(GENERATED_DIR)

LOCAL_LDFLAGS := -L$(ndkLibDir)
LOCAL_LDLIBS += -lRScpp_static

# Relative dir, as local src are relative to Android.mk folder
GENERATED_DIR_RELATIVE := ../../../$(GENERATED_DIR)

$(info TARGET_PLATFORM: $(TARGET_PLATFORM))
$(info ANDROID_NDK: $(NDK_DIR))
$(info GENERATED_DIR: $(GENERATED_DIR))
$(info GENERATED_DIR_RELATIVE: $(GENERATED_DIR_RELATIVE))

LOCAL_SRC_FILES := main.cpp $(GENERATED_DIR_RELATIVE)/ScriptC_main.cpp

# Add error catcher: https://github.com/xroche/coffeecatch
LOCAL_C_FLAGS += -funwind-tables  -Wl,--no-merge-exidx-entries
LOCAL_SRC_FILES += catcher/coffeecatch.c catcher/coffeejni.c

include $(BUILD_SHARED_LIBRARY)