// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.cameracaptureexample)

int2 sizeIn;
float scaleInv;
rs_allocation aIn;

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};

uchar4 __attribute__((kernel)) root(uint32_t x, uint32_t y) {

    /*
    This kernel uses nearest resizing:

    Input x and y belong to the output allocation, which is resized and downscaled by factor 2,
    so camera preview size is 1280x720 and output allocation has a size of 640x360.

    To get full size corresponding pixels, we use nearest resize process, to just scale pixels
    coordinates back using inverse scale and gets corresponding one.

    ooooooooo <- full size
    ^ ^ ^ ^ ^
    | | | | |
    o o o o o <- scaled size
    */

    int fX = rsClamp(round((float)x * scaleInv), 0,sizeIn.x);
    int fY = rsClamp(round((float)y * scaleInv), 0,sizeIn.y);

    // Our script will make every pixel enough "bright" to appear red!
    // We use gray conversion constants to get brightness value.

    uchar4 in = rsGetElementAt_uchar4(aIn, fX, fY);

    uchar grayValue = (uchar) ((float) in.r * grayMultipliers.r +
    	                      (float) in.g * grayMultipliers.g +
    	                      (float) in.b * grayMultipliers.b);

    // Pixels brighter than this threshold will appear red!
    int threshold = 200;
        uchar4 out;

    if(grayValue > threshold){
        out.r = 255;
        out.g = 0;
        out.b = 0;
        out.a = 255;
    }
    else
    {
        // If not higer than threshold, displays a transparent pixel, so camera preview,
        // which is on the bottom of the window, will be seen.
        out.r = 0;
        out.g = 0;
        out.b = 0;
        out.a = 0;
    }

    return out;

}