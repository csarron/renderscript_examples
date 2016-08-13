// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.imagezoomexample)

// Store the input allocation
rs_allocation inputAllocation;

// Magnifying
// TODO: here, some checks should be performed to prevent atX and atY to be < 0, as well
//   as them to not be greater than width and height
int atX;
int atY;
float radius;
float scale; // The scale is >= 1

// Magnifier border size, to distinguish between magnified image and original one
static float borderSize = 8.0f;
// Border color, defaults to red
static uchar4 border = {255.0f, 0.0f, 0.0f, 255.0f};

uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {

    // Calculates the distance between the touched point and the current kernel
    // iteration pixel coordinated
    // Reference: http://math.stackexchange.com/a/198769
    float pointDistanceFromCircleCenter = sqrt(pow((float)(x - atX),2) + pow((float)(y - atY),2));

    // Notice: NOT OPTIMIZED, is good to understand the process
    float distancePow = pow(pointDistanceFromCircleCenter,2);
    float radiusPow = pow(radius, 2);

    // These distances show two different radiuses, which belong to the border
    // of the magnifier
    float borderInnerRadius = radiusPow - pow(borderSize,2);
    float borderOuterRadius = radiusPow + pow(borderSize,2);

    // Is this pixel outside the magnify radius?
    if(distancePow > borderOuterRadius)
    {
        // In this case, just copy the original image
        return in;
    }
    // Is the current pixel inside the magnifier border
    else if (distancePow >= borderInnerRadius && distancePow <= borderOuterRadius)
    {
        // Draw border
        return border;
    }

    // If the point is inside the magnifying inner radius, draw the magnified image

    // Calculates the current distance from the chosen magnifying center
    float diffX = x - atX;
    float diffY = y - atY;

    // Scales down the distance accordingly to scale and returns the original coordinates
    int originalX = atX + round(diffX / scale);
    int originalY = atY + round(diffY / scale);

    // Return the original image pixel at the calculated coordinates
    return rsGetElementAt_uchar4(inputAllocation, originalX, originalY);
}
