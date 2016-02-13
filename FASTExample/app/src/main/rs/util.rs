// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};

uchar __attribute__((kernel)) rgbaToGray(uchar4 in, uint32_t x, uint32_t y) {

    uchar out;

    out = (uchar) ((float)in.r*grayMultipliers.r) +
            ((float)in.g*grayMultipliers.g) +
          ((float)in.b*grayMultipliers.b);

    return out;
}

uchar4 __attribute__((kernel)) showFastKeypoints(uchar in, uint32_t x, uint32_t y)
{
    // If keypoint was found, displays a red point. Otherwise, transparent pixel.
    uchar4 out;

    if(in > 0) {
        out.r = 255;
        out.g = 0;
        out.b = 0;
        out.a = 255;
    } else {
        out.a = 0;
    }

    return out;

}