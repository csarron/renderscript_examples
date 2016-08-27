// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.limitedconvolutionexample)

// Black color
static const uchar4 BLACK = {0,0,0,255};
uchar4 __attribute__((kernel)) fillWithBlack(int x, int y) {
    return BLACK;
}

// --- Copies an allocation content to another one.

// This kernel gets executed on the output allocation, so the x and y are directly
// related to the output one (e.g. their max size will be defined by the example's outSizeX
// and outSizeY).
// To copy the contents of the first one, the example just takes directly the data from it
// using the rsGetElementAt_uchar4 function.

// Store the input allocation
rs_allocation inputAllocation;

// Offset indices, which define the start point for the copy in the input allocation.
int inputOffsetX;
int inputOffsetY;

uchar4 __attribute__((kernel)) copyAllocation(int x, int y) {

    return rsGetElementAt_uchar4(inputAllocation, x + inputOffsetX, y + inputOffsetY);

}
