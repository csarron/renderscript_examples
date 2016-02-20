#include "fast_opencv_const.rsh"

static uchar __popc_sub1(const int val, const int mul) {

    if ((val & mul) > 0) return 1; else return 0;

}

// Simple implementation of __pop c function
static uchar __popc(const int val) {

    uchar ret = 0;

    ret += __popc_sub1(val, 1);
    ret += __popc_sub1(val, 2);
    ret += __popc_sub1(val, 4);
    ret += __popc_sub1(val, 8);
    ret += __popc_sub1(val, 16);
    ret += __popc_sub1(val, 32);
    ret += __popc_sub1(val, 64);
    ret += __popc_sub1(val, 128);
    ret += __popc_sub1(val, 256);
    ret += __popc_sub1(val, 512);
    ret += __popc_sub1(val, 1024);
    ret += __popc_sub1(val, 2048);
    ret += __popc_sub1(val, 4096);
    ret += __popc_sub1(val, 8192);
    ret += __popc_sub1(val, 16384);
    ret += __popc_sub1(val, 32768);
    ret += __popc_sub1(val, 65536);
    ret += __popc_sub1(val, 131072);
    ret += __popc_sub1(val, 262144);
    ret += __popc_sub1(val, 524288);
    ret += __popc_sub1(val, 1048576);
    ret += __popc_sub1(val, 2097152);
    ret += __popc_sub1(val, 4194304);
    ret += __popc_sub1(val, 8388608);
    ret += __popc_sub1(val, 16777216);
    ret += __popc_sub1(val, 33554432);
    ret += __popc_sub1(val, 67108864);
    ret += __popc_sub1(val, 134217728);
    ret += __popc_sub1(val, 268435456);
    ret += __popc_sub1(val, 536870912);
    ret += __popc_sub1(val, 1073741824);

    return ret;
}

static bool isKeyPoint(int2 mask) {

    return (__popc(mask.x) > 8 && (c_table[(mask.x >> 3) - 63] & (1 << (mask.x & 7)))) ||
           (__popc(mask.y) > 8 && (c_table[(mask.y >> 3) - 63] & (1 << (mask.y & 7))));
}

static int diffType(const int v, const int x, const int th) {
    const int diff = x - v;

    return (int) (diff < -th) + ((int) (diff > th) << 1);
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

static uchar cornerScore(const uint4 C, const int v, const int threshold) {
    // binary search in [threshold + 1, 255]

    int min = threshold + 1;
    int max = 255;

    while (min <= max) {
        const int mid = (min + max) >> 1;


        int2 mask = calcMask(C, v, mid);

        int isKp = (isKeyPoint(mask)) ? 1 : 0;

        min = isKp * (mid + 1) + (isKp ^ 1) * min;
        max = (isKp ^ 1) * (mid - 1) + isKp * max;
    }

    return (uchar)(min - 1);
}

