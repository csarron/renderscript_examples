APP_OPTIM:=release

APP_STL := gnustl_shared # This is needed because RS libs require RTTI support
APP_CPPFLAGS :=-fexceptions -std=c++11
APP_ABI := armeabi-v7a
APP_PLATFORM := android-19