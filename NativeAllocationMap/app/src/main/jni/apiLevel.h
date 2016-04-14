//
// Created by Alberto on 13/04/2016.
//

#ifndef NATIVEALLOCATIONMAP_APILEVEL_H
#define NATIVEALLOCATIONMAP_APILEVEL_H

#include <jni.h>

// Do not need to check it too many times! :)
static int cachedApiLevel = 0;

// Reference http://stackoverflow.com/questions/10196361/how-to-check-the-device-running-api-level-using-c-code-via-ndk
inline int getAPILevel(JNIEnv *env) {
    if(cachedApiLevel) return cachedApiLevel;

    bool success = true;

    // VERSION is a nested class within android.os.Build (hence "$" rather than "/")
    jclass versionClass = env->FindClass("android/os/Build$VERSION");
    if (NULL == versionClass)
        throw ("Could not retrieve android/os/Build$VERSION class");

    jfieldID sdkIntFieldID = env->GetStaticFieldID(versionClass, "SDK_INT", "I");
    if (NULL == sdkIntFieldID)
        throw ("Could not retrieve SDK_INT field");

    cachedApiLevel = env->GetStaticIntField(versionClass, sdkIntFieldID);
    return cachedApiLevel;
}

#endif //NATIVEALLOCATIONMAP_APILEVEL_H
