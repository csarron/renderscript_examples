// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};

rs_allocation rgbAllocation;
rs_allocation fastKpAllocation;

uchar __attribute__((kernel)) rgbToGray(uint32_t x, uint32_t y)
{

    uchar4 rgbIn = rsGetElementAt_uchar4(rgbAllocation, x, y);
    uchar out;

    // As we are using a ScriptGroup, we have to use the same Element as input and output.
    out = (uchar) clamp((float)rgbIn.r*grayMultipliers.r, 0.0f, 255.0f) +
          (uchar) clamp((float)rgbIn.g*grayMultipliers.g, 0.0f, 255.0f) +
          (uchar) clamp((float)rgbIn.b*grayMultipliers.b, 0.0f, 255.0f);

    return out;
}

uchar4 __attribute__((kernel)) showFastKeypoints(uint32_t x, uint32_t y)
{

    uchar4 out;
    uchar in = rsGetElementAt_uchar(fastKpAllocation,x,y);

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