LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := RSNDK
LOCAL_LDLIBS += -llog -landroid -lc -lz -lm

# RS setup
ndkLibDir := $(ANDROID_NDK)/platforms/android-19/arch-arm/usr/lib/rs
ndkIncludeDir := $(ANDROID_NDK)/platforms/android-19/arch-arm/usr/include

LOCAL_C_INCLUDES += $(ndkIncludeDir)/rs $(ndkIncludeDir)/rs/cpp $(ndkIncludeDir)/rs/scriptc
LOCAL_C_INCLUDES += ../../../build//rs/scriptc
LOCAL_LDLIBS += $(ndkLibDir)/libRScpp_static.a


RS_SOURCES := $(patsubst ./%,%, \
$(shell cd $(LOCAL_PATH) ; \
          find -L ../rs \( -name "*.rs" -or -name "*.fs" \) -and -not -name ".*") \
)

$(info LOCAL_PATH: $(LOCAL_PATH))
$(info RS_SOURCES: $(RS_SOURCES))

LOCAL_SRC_FILES := main.cpp $(RS_SOURCES)

include $(BUILD_SHARED_LIBRARY)