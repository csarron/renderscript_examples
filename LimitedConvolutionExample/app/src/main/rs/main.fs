// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.limitedconvolutionexample)


// Black color
static const uchar4 BLACK = {0,0,0,255};
uchar4 __attribute__((kernel)) fillWithBlack(int x, int y) {
    return BLACK;
}

// Store the input allocation
rs_allocation inputAllocation;

int outIndexX;
int outIndexY;

uchar4 __attribute__((kernel)) copyAllocation(int x, int y) {

    return rsGetElementAt_uchar4(inputAllocation, x + outIndexX, y + outIndexY);

}
