// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.imageresizeexample)

rs_allocation inputAllocation;

// Sets image sizes and calculates the scale factor
static float scaleInv;
static int inputWidth, inputHeight, outputWidth, outputHeight;

void setInformation(int _inputWidth, int _inputHeight,
    int _outputWidth, int _outputHeight){

    inputWidth = _inputWidth;
    inputHeight = _inputHeight;
    outputWidth = _outputWidth;
    outputHeight = _outputHeight;

    // Calculates inverse scale factor, by which
    // to round coordinates.
    //
    // Ex:
    // Input size is 100
    // Output desired size is 25
    //
    // Scale factor is 25 / 100 = 0.25
    // Inverse scale factor is 1 / 0.25 = 4
    //
    // When iterating directly on the output
    // allocation, to get input element it is needed
    // to use the inverse scale factor.
    //
    // Current output element index is 20
    // Respective input element index is 20 * 4 = 80
    //
    scaleInv = (float)inputWidth/(float)outputWidth;
}

// Standard bicubic resize process
static float4 cubicInterpolate (float4 p0,float4 p1,float4 p2,float4 p3 , float4 x) {
    return p1 + 0.5f * x * (p2 - p0 + x * (2.f * p0 - 5.f * p1 + 4.f * p2 - p3
                                           + x * (3.f * (p1 - p2) + p3 - p0)));
}

static uchar4 bicubic(float xf, float yf) {
    int startx = (int) floor(xf - 1);
    int starty = (int) floor(yf - 1);
    xf = xf - floor(xf);
    yf = yf - floor(yf);
    int maxx = inputWidth - 1;
    int maxy = inputHeight - 1;

    uint32_t xs0 = (uint32_t) max(0, startx + 0);
    uint32_t xs1 = (uint32_t) max(0, startx + 1);
    uint32_t xs2 = (uint32_t) min(maxx, startx + 2);
    uint32_t xs3 = (uint32_t) min(maxx, startx + 3);

    uint32_t ys0 = (uint32_t) max(0, starty + 0);
    uint32_t ys1 = (uint32_t) max(0, starty + 1);
    uint32_t ys2 = (uint32_t) min(maxy, starty + 2);
    uint32_t ys3 = (uint32_t) min(maxy, starty + 3);

    float4 p00 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs0, ys0));
    float4 p01 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs1, ys0));
    float4 p02 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs2, ys0));
    float4 p03 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs3, ys0));
    float4 p0  = cubicInterpolate(p00, p01, p02, p03, xf);

    float4 p10 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs0, ys1));
    float4 p11 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs1, ys1));
    float4 p12 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs2, ys1));
    float4 p13 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs3, ys1));
    float4 p1  = cubicInterpolate(p10, p11, p12, p13, xf);

    float4 p20 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs0, ys2));
    float4 p21 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs1, ys2));
    float4 p22 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs2, ys2));
    float4 p23 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs3, ys2));
    float4 p2  = cubicInterpolate(p20, p21, p22, p23, xf);

    float4 p30 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs0, ys3));
    float4 p31 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs1, ys3));
    float4 p32 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs2, ys3));
    float4 p33 = convert_float4(rsGetElementAt_uchar4(inputAllocation, xs3, ys3));
    float4 p3  = cubicInterpolate(p30, p31, p32, p33, xf);

    float4 p  = cubicInterpolate(p0, p1, p2, p3, yf);
    p = clamp(p + 0.5f, 0.f, 255.f);
    return convert_uchar4(p);
}

uchar4 __attribute__((kernel)) resizeNearest(uint32_t x, uint32_t y) {

    float fX = clamp((float)x*scaleInv, 0.0f, inputWidth);
    float fY = clamp((float)y*scaleInv, 0.0f, inputHeight);

    return rsGetElementAt_uchar4(inputAllocation, fX, fY);
}
uchar4 __attribute__((kernel)) resizeBicubic(uint32_t x, uint32_t y) {

    float fX = clamp((float)x*scaleInv, 0.0f, inputWidth);
    float fY = clamp((float)y*scaleInv, 0.0f, inputHeight);

    return bicubic(fX, fY);
}
