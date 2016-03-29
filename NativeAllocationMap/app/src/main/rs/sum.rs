// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.nativeallocationmap)

// Kernel that will be called from Java
uchar __attribute__((kernel)) sum1(uchar in, uint32_t x) {
    return in + 1;
}

int ndkSumAmount = 0;

// Kernel that will be called directly from NDK side
uchar __attribute__((kernel)) sum2(uchar in, uint32_t x) {
    return in + ndkSumAmount;
}