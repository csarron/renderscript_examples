// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.imagezoomexample)

// Store the input allocation
rs_allocation inputAllocation;

// Util variables
int inWidth;
int inHeight;

// Magnifying
// TODO: here, some checks should be performed to prevent atX and atY to be < 0, as well
//   as them to not be greater than width and height
int atX;
int atY;
float radius;
float scale; // The scale is >= 1

static float borderSize = 8.0f;
static uchar4 border = {255.0f, 0.0f, 0.0f, 255.0f};

uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {

    float pointDistanceFromCircleCenter = sqrt(pow((float)(x - atX),2) + pow((float)(y - atY),2));

    // Notice: NOT OPTIMIZED, is good to understand the process
    float distancePow = pow(pointDistanceFromCircleCenter,2);
    float radiusPow = pow(radius, 2);
    float borderInnerRadius = radiusPow - pow(borderSize,2);
    float borderOuterRadius = radiusPow + pow(borderSize,2);

    // 1. Is this pixel outside the magnify radius?
    if(distancePow > borderOuterRadius)
    {
        // In this case, just copy the original image
        return in;
    }
    else if (distancePow >= borderInnerRadius && distancePow <= borderOuterRadius)
    {
        // Draw border
        return border;
    }

    // If the point is inside the magnifying radius, draw the magnified image
    float diffX = x - atX;
    float diffY = y - atY;

    int originalX = atX + round(diffX / scale);
    int originalY = atY + round(diffY / scale);

    return rsGetElementAt_uchar4(inputAllocation, originalX, originalY);
}
