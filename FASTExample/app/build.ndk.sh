#!/bin/bash

/opt/android/ndk/android-ndk-r11/ndk-build NDK_PROJECT_PATH=build/intermediates/ndk NDK_LIBS_OUT=src/main/libs APP_BUILD_SCRIPT=src/main/jni/Android.mk NDK_APPLICATION_MK=src/main/jni/Application.mk V=1
