// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

rs_allocation inputAllocation;

int wIn, hIn;
int numTotalPixels;

void setInputImageSize(int _w,int _h)
{
    wIn = _w;
    hIn = _h;
    numTotalPixels = wIn * hIn;
}

const static float3 gMonoMult = {0.299f, 0.587f, 0.114f};
uchar __attribute__((kernel)) convert(uint32_t x, uint32_t y)
{

    // YUV 4:2:0 planar image, with 8 bit Y samples, followed by interleaved V/U plane with 8bit 2x2 subsampled chroma samples
    int baseIdx = x + y * wIn;
    int baseUYIndex = numTotalPixels + (y >> 1) * wIn + (x & 0xfffffe);

    uchar _y = rsGetElementAt_uchar(inputAllocation, baseIdx);
    uchar _u = rsGetElementAt_uchar(inputAllocation, baseUYIndex);
    uchar _v = rsGetElementAt_uchar(inputAllocation, baseUYIndex + 1);
    _y = _y < 16 ? 16 : _y;

    short Y = ((short)_y) - 16;
    short U = ((short)_u) - 128;
    short V = ((short)_v) - 128;

    float r = ((Y * 298 + V * 409 + 128) >> 8);
    float g = ((Y * 298 - U * 100 - V * 208 + 128) >> 8);
    float b = ((Y * 298 + U * 516 + 128) >> 8);

    return (uchar) clamp(r*gMonoMult.r + g*gMonoMult.g + b*gMonoMult.b, 0.0f, 255.0f);
}
