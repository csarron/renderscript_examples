// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

int fastThreshold = 20;
rs_allocation grayAllocation;

#include "fast_opencv_util.rsh"

// Follows FAST features detection directly ported from OpenCV code
// https://github.com/Itseez/opencv/blob/master/modules/cudafeatures2d/src/cuda/fast.cu

uchar __attribute__((kernel)) fastOpenCV(uchar in, uint x, uint y){
    int v;
    uint4 C = {0,0,0,0};

    C.z |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 1, y - 3)) << 8;
    C.z |= (uint)(rsGetElementAt_uchar(grayAllocation, x, y - 3));
    C.y |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 1, y - 3)) << (3 * 8);

    C.z |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 2, y - 2)) << (2 * 8);
    C.y |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 2, y - 2)) << (2 * 8);

    C.z |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 3, y - 1)) << (3 * 8);
    C.y |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 3, y - 1)) << 8;

    C.w |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 3, y));
    v = (int) (rsGetElementAt_uchar(grayAllocation, x, y));
    C.y |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 3, y));

    int d1 = diffType(v, C.y & 0xff, fastThreshold);
    int d2 = diffType(v, C.w & 0xff, fastThreshold);

    if ((d1 | d2) == 0){
        return 0;
        }

    C.w |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 3, y + 1)) << 8;
    C.x |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 3, y + 1)) << (3 * 8);

    C.w |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 2, y + 2)) << (2 * 8);
    C.x |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 2, y + 2)) << (2 * 8);

    C.w |= (uint)(rsGetElementAt_uchar(grayAllocation, x - 1, y + 3)) << (3 * 8);
    C.x |= (uint)(rsGetElementAt_uchar(grayAllocation, x, y + 3));
    C.x |= (uint)(rsGetElementAt_uchar(grayAllocation, x + 1, y + 3)) << 8;

    int2 mask = calcMask(C, v, fastThreshold);

    if (isKeyPoint(mask)) {

        // Point is a FAST keypoint
        return 1;

    }

     return 0;
}