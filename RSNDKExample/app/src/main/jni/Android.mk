LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := RSNDK # Module name, produces libRSNDK.so
LOCAL_LDLIBS += -llog -landroid -lc -lz -lm

### RS setup
# Include all RS C++ headers and generated sources
LOCAL_C_INCLUDES += $(TARGET_C_INCLUDES)/rs/cpp \
	$(TARGET_C_INCLUDES)/rs

# Links required RS library
LOCAL_LDFLAGS += -L$(call host-path,$(TARGET_C_INCLUDES)/../lib/rs)
LOCAL_LDLIBS += -lRScpp_static

# Automatically includes all .rs and .fs files that reside in the
# app/src/main/rs folder
# Reference: http://stackoverflow.com/a/8980441/3671330
RS_FOLDER := $(LOCAL_PATH)/../rs
RS_SRC_FILES := $(wildcard $(RS_FOLDER)/*.rs)
RS_SRC_FILES += $(wildcard $(RS_FOLDER)/*.fs)

# Here, __ folder is included. It contains RS generated headers.
# It is named this way because dots were replaced by underscores.
# ../rs/main.rs -> __/rs/ScriptC_main.h
LOCAL_C_INCLUDES += $(TARGET_OBJS)/$(LOCAL_MODULE)/__

# FYI:
# TARGET_OBJS folder can be:
# 	app/build/intermediates/ndk/obj/local/armeabi-v7a/objs
# LOCAL_MODULE is: RSNDK

# Removes first part of path to make it acceptable
# by LOCAL_SRC_FILES
RS_SRC_FILES := $(RS_SRC_FILES:$(LOCAL_PATH)/%=%)

# C++ sources
LOCAL_SRC_FILES := main.cpp $(RS_SRC_FILES)

include $(BUILD_SHARED_LIBRARY)