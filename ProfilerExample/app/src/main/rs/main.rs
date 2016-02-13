// RenderScript version

#pragma version(1)
#pragma rs_fp_relaxed

#pragma rs java_package_name(net.hydex11.profilerexample)

// Some credit goes to: http://stackoverflow.com/questions/13917106/where-is-the-filterscript-documentation-and-how-can-i-use-it

int blurRadius = 3;

rs_allocation inputAllocation;
rs_allocation outputAllocation;
rs_allocation grayAllocation;

uint32_t width;
uint32_t height;

// Blur function
uchar4 __attribute__((kernel)) blurSimpleKernel(uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;
    for (int yi = y-blurRadius; yi <= y+blurRadius; ++yi) {
        for (int xi = x-blurRadius; xi <= x+blurRadius; ++xi) {
                sum += convert_uint4(rsGetElementAt_uchar4(inputAllocation, x+xi, y+yi));
                ++count;
            }
    }
    return convert_uchar4(sum/count);
}

void blurPointerKernel(const uchar4 * v_in, uchar4 * v_out, uint32_t x, uint32_t y) {
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

void blurPointerKernelSet(const uchar4 * v_in, uint32_t x, uint32_t y) {
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

    rsSetElementAt_uchar4(outputAllocation, convert_uchar4(sum/count), x, y);
}


void blurPointerKernelGet(uchar4 * v_out, uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;

    // We can use the image width as stride
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
    {
        for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
            sum += convert_uint4(rsGetElementAt_uchar4(inputAllocation, x+xi, y+yi));
            ++count;
        }
    }

    *v_out = convert_uchar4(sum/count);
}

// RGBA to GRAY conversion
// The following functions are used to compare performance, in the case
// where input and output Allocations have different Type.
// Following is a comparison of three standard ways to perform the in/out
// operations.

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};

// The following function presents a standard way to peroform in/out
// operations on Allocation having different Types.
uchar __attribute__((kernel)) rgbaToGrayNoPointer(uchar4 in, uint32_t x, uint32_t y) {

    uchar out;

    out = (uchar) ((float)in.r*grayMultipliers.r) +
            ((float)in.g*grayMultipliers.g) +
          ((float)in.b*grayMultipliers.b);

   return out;
}

// Input: pointer
// Output: rsSetElementAt
void rgbaToGrayPointerAndSet(const uchar4 * v_in, uint32_t x, uint32_t y)
{
    uchar out;

    out = (uchar) ((float)v_in->r*grayMultipliers.r) +
            ((float)v_in->g*grayMultipliers.g) +
          ((float)v_in->b*grayMultipliers.b);

   rsSetElementAt_uchar(grayAllocation,out,x,y);
}

// Input: rsGetElementAt
// Output: pointer
void rgbaToGrayPointerAndGet(uchar * v_out, uint32_t x, uint32_t y)
{
    uchar out;

    uchar4 in = rsGetElementAt_uchar4(inputAllocation, x, y);

    out = (uchar) ((float)in.r*grayMultipliers.r) +
            ((float)in.g*grayMultipliers.g) +
          ((float)in.b*grayMultipliers.b);

   *v_out = out;
}

// Input: pointer
// Output: pointer
void rgbaToGrayPointerAndOut(const uchar4 * v_in, uchar * v_out, uint32_t x, uint32_t y)
{
    uchar out;

    out = (uchar) ((float)v_in->r*grayMultipliers.r) +
            ((float)v_in->g*grayMultipliers.g) +
          ((float)v_in->b*grayMultipliers.b);

   *v_out = out;
}

// Square set values
// Following functions set a predefined count of values in the output allocation,
// using the input as element to set.

// Uses blur radius as input number of values to set, to resemble (more or less) its
// memory access cost.

void __attribute__((kernel)) setValuesSimpleKernel(uchar4 in, int x, int y){
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
        {
            for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
                rsSetElementAt_uchar4(outputAllocation, in, x+xi, y+yi);
            }
        }
}

void setValuesPointerKernel(const uchar4 * v_in, uchar4 * v_out, int x, int y){
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
        {
            for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
                v_out[xi + yi*width] = *v_in;
            }
        }
}
void setValuesPointerKernelSet(const uchar4 * v_in, int x, int y){
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
        {
            for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
                rsSetElementAt_uchar4(outputAllocation, *v_in, x+xi, y+yi);
            }
        }
}