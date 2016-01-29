// RenderScript version

#pragma version(1)
#pragma rs_fp_relaxed

#pragma rs java_package_name(net.hydex11.profilerexample)

// Some credit goes to: http://stackoverflow.com/questions/13917106/where-is-the-filterscript-documentation-and-how-can-i-use-it

int blurRadius = 3;

rs_allocation inputAllocation;

uint32_t width;
uint32_t height;

uchar4 __attribute__((kernel)) root(uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;
    for (int yi = y-blurRadius; yi <= y+blurRadius; ++yi) {
        for (int xi = x-blurRadius; xi <= x+blurRadius; ++xi) {
            if (xi >= 0 && xi < width && yi >= 0 && yi < height) {
                sum += convert_uint4(rsGetElementAt_uchar4(inputAllocation, xi, yi));
                ++count;
            }
        }
    }
    return convert_uchar4(sum/count);
}


void pointerKernel(const uchar4 * v_in, uchar4 * v_out, uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;

    // We can use the image width as stride
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
    {
        for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
            sum += convert_uint4(v_in[xi + width * yi]);
            ++count;
        }
    }

    *v_out = convert_uchar4(sum/count);
}