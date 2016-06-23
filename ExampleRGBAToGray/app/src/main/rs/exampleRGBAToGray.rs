// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.examplergbatogray)

// Local variable that will be used to access the input allocation.
// This variable behaves internally like a pointer
rs_allocation inAllocation;

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};
uchar4 __attribute__((kernel)) convertRGBAToGray(uint32_t x, uint32_t y) {

    uchar4 in = rsGetElementAt_uchar4(inAllocation, x, y);

    // Multiplies each input's pixel RGB values by their respective gray multipliers
    uchar grayValue = (uchar) ((float) in.r * grayMultipliers.r +
                      (float) in.g * grayMultipliers.g +
                      (float) in.b * grayMultipliers.b);

    uchar4 pixelOut;
    pixelOut.r = grayValue;
    pixelOut.g = grayValue;
    pixelOut.b = grayValue;
    pixelOut.a = in.a; // Preserve alpha

    return pixelOut;
}