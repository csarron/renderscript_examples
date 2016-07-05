// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};

uchar __attribute__((kernel)) rgbaToGray(uchar4 in, uint32_t x, uint32_t y) {

    uchar out = (uchar) ((float)in.r*grayMultipliers.r) +
            ((float)in.g*grayMultipliers.g) +
          ((float)in.b*grayMultipliers.b);

    return out;
}

const uchar4 red = {255,0,0,255};
const uchar4 transparent = {0,0,0,0};
uchar4 __attribute__((kernel)) showFastKeypoints(uchar in, uint32_t x, uint32_t y)
{
    // If keypoint was found, displays a red point. Otherwise, transparent pixel.
   if(in > 0) return red;
   return transparent;
}

// Converts a 1-channel image to a 4-channel one
uchar4 __attribute__((kernel)) grayToRGBA(uchar in, uint32_t x, uint32_t y)
{
   uchar4 out;

   out.a = 255;
   out.r = in;
   out.g = in;
   out.b = in;

   return out;
}