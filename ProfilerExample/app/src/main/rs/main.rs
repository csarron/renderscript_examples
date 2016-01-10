// RenderScript version

#pragma version(1)

#pragma rs java_package_name(net.hydex11.profilerexample)

// Credit goes to: http://stackoverflow.com/questions/13917106/where-is-the-filterscript-documentation-and-how-can-i-use-it

rs_allocation in;

uint32_t width;
uint32_t height;

uchar4 __attribute__((kernel)) root(uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;
    for (int yi = y-1; yi <= y+1; ++yi) {
        for (int xi = x-1; xi <= x+1; ++xi) {
            if (xi >= 0 && xi < width && yi >= 0 && yi < height) {
                sum += convert_uint4(rsGetElementAt_uchar4(in, xi, yi));
                ++count;
            }
        }
    }
    return convert_uchar4(sum/count);
}