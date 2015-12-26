// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.examplergbatogray)

uchar __attribute__((kernel)) sum1(uchar in, uint32_t x) {
    return in + 1;
}