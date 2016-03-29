#include "fast_opencv_const.rsh"

// Reference: http://stackoverflow.com/questions/109023/how-to-count-the-number-of-set-bits-in-a-32-bit-integer/109025#109025
static uint32_t __popc(uint32_t i){
     i = i - ((i >> 1) & 0x55555555);
     i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
     return (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
}

static bool isKeyPoint(int2 mask) {
    return (__popc(mask.x) > 8 && (c_table[(mask.x >> 3) - 63] & (1 << (mask.x & 7)))) ||
           (__popc(mask.y) > 8 && (c_table[(mask.y >> 3) - 63] & (1 << (mask.y & 7))));
}

static int diffType(const int v, const int x, const int th) {
    const int diff = x - v;
    return (diff < -th ? 1 : 0) + ((diff > th ? 1 : 0) << 1);
}

static int2 calcMask(const uint4 C, const int v, const int th) {
    int2 mask;

    mask.x = 0;
    mask.y = 0;

    int d1, d2;

    d1 = diffType(v, C.x & 0xff, th);
    d2 = diffType(v, C.z & 0xff, th);

    if ((d1 | d2) == 0)
        return mask;

    mask.x |= (d1 & 1) << 0;
    mask.y |= ((d1 & 2) >> 1) << 0;

    mask.x |= (d2 & 1) << 8;
    mask.y |= ((d2 & 2) >> 1) << 8;


    d1 = diffType(v, C.y & 0xff, th);
    d2 = diffType(v, C.w & 0xff, th);

    if ((d1 | d2) == 0)
        return mask;

    mask.x |= (d1 & 1) << 4;
    mask.y |= ((d1 & 2) >> 1) << 4;

    mask.x |= (d2 & 1) << 12;
    mask.y |= ((d2 & 2) >> 1) << 12;


    d1 = diffType(v, (C.x >> (2 * 8)) & 0xff, th);
    d2 = diffType(v, (C.z >> (2 * 8)) & 0xff, th);

    if ((d1 | d2) == 0)
        return mask;

    mask.x |= (d1 & 1) << 2;
    mask.y |= ((d1 & 2) >> 1) << 2;

    mask.x |= (d2 & 1) << 10;
    mask.y |= ((d2 & 2) >> 1) << 10;


    d1 = diffType(v, (C.y >> (2 * 8)) & 0xff, th);
    d2 = diffType(v, (C.w >> (2 * 8)) & 0xff, th);

    if ((d1 | d2) == 0)
        return mask;

    mask.x |= (d1 & 1) << 6;
    mask.y |= ((d1 & 2) >> 1) << 6;

    mask.x |= (d2 & 1) << 14;
    mask.y |= ((d2 & 2) >> 1) << 14;


    d1 = diffType(v, (C.x >> (1 * 8)) & 0xff, th);
    d2 = diffType(v, (C.z >> (1 * 8)) & 0xff, th);

    /*if ((d1 | d2) == 0)
        return;*/

    mask.x |= (d1 & 1) << 1;
    mask.y |= ((d1 & 2) >> 1) << 1;

    mask.x |= (d2 & 1) << 9;
    mask.y |= ((d2 & 2) >> 1) << 9;


    d1 = diffType(v, (C.x >> (3 * 8)) & 0xff, th);
    d2 = diffType(v, (C.z >> (3 * 8)) & 0xff, th);

    /*if ((d1 | d2) == 0)
        return;*/

    mask.x |= (d1 & 1) << 3;
    mask.y |= ((d1 & 2) >> 1) << 3;

    mask.x |= (d2 & 1) << 11;
    mask.y |= ((d2 & 2) >> 1) << 11;


    d1 = diffType(v, (C.y >> (1 * 8)) & 0xff, th);
    d2 = diffType(v, (C.w >> (1 * 8)) & 0xff, th);

    /*if ((d1 | d2) == 0)
        return;*/

    mask.x |= (d1 & 1) << 5;
    mask.y |= ((d1 & 2) >> 1) << 5;

    mask.x |= (d2 & 1) << 13;
    mask.y |= ((d2 & 2) >> 1) << 13;


    d1 = diffType(v, (C.y >> (3 * 8)) & 0xff, th);
    d2 = diffType(v, (C.w >> (3 * 8)) & 0xff, th);

    mask.x |= (d1 & 1) << 7;
    mask.y |= ((d1 & 2) >> 1) << 7;

    mask.x |= (d2 & 1) << 15;
    mask.y |= ((d2 & 2) >> 1) << 15;

    return mask;
}