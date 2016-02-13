// Needed directive for RS to work
#pragma version(1)
#pragma rs_fp_relaxed

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

#include "harris.rsh"

// FAST threshold
const uchar fastThreshold = 20;

// Define minimum and maximum threshold to evaluate
const uchar minValue = fastThreshold;
const uchar maxValue = 255 - fastThreshold;

// Number of contiguous points that need to be OR all brighter, OR all darker
// than the central pixel
const int fastContiguousPointsCount = 9;

// Input gray image allocation
rs_allocation grayAllocation;

uchar __attribute__((kernel)) fastNoOptimized(uchar in, uint32_t x, uint32_t y)
{

    // Central pixel is contained inside variable "in"
    uchar p0 = in;

    // Get circle patch pixels
    uchar p[16];
    p[0] = rsGetElementAt_uchar(grayAllocation,x , y - 3);
    p[1] = rsGetElementAt_uchar(grayAllocation,x + 1 , y - 3);
    p[2] = rsGetElementAt_uchar(grayAllocation,x + 2 , y - 2);
    p[3] = rsGetElementAt_uchar(grayAllocation,x + 3 , y - 1);
    p[4] = rsGetElementAt_uchar(grayAllocation,x + 3 , y);
    p[5] = rsGetElementAt_uchar(grayAllocation,x + 3 , y + 1);
    p[6] = rsGetElementAt_uchar(grayAllocation,x + 2 , y + 2);
    p[7] = rsGetElementAt_uchar(grayAllocation,x + 1 , y + 3);
    p[8] = rsGetElementAt_uchar(grayAllocation,x , y + 3);
    p[9] = rsGetElementAt_uchar(grayAllocation,x - 1 , y + 3);
    p[10] = rsGetElementAt_uchar(grayAllocation,x - 2 , y + 2);
    p[11] = rsGetElementAt_uchar(grayAllocation,x - 3 , y + 1);
    p[12] = rsGetElementAt_uchar(grayAllocation,x - 3 , y);
    p[13] = rsGetElementAt_uchar(grayAllocation,x - 3 , y - 1);
    p[14] = rsGetElementAt_uchar(grayAllocation,x - 2 , y - 2);
    p[15] = rsGetElementAt_uchar(grayAllocation,x - 1 , y - 3);

    uchar count = 0;
    bool isThresholdHigh = false;
    uchar referenceThreshold = 0;

    // Define which thresholds to use to compare patch pixels with central point.
    // As we are using uchars, their value cannot be lower than 0 or higher than 255.
    uchar highThreshold = (in < maxValue ? in + fastThreshold : 255);
    uchar lowThreshold = (in > minValue ? in - fastThreshold : 0);

    // As we operate on a circular patch, using indexes that range from 0 to 15,
    // and we have to check for contiguous pixels even on 15th pixel, we need to fix indexes
    // like 16, 17, 18 etc.. to become 0, 1, 2 etc..
    uchar fixedIndex = 0;

    // Cycle on every patch point
    for(uchar i = 0; i < 16; i++) {

        // Circular scan of patch
        count = 0;

        // This cycle checks that points following the current one are all OR brighter than
        // the reference one, OR all darker than it.
        for(uchar j = i; j < i + fastContiguousPointsCount; j++) {
            fixedIndex = j > 15 ? j-16 : j;

            if(j==i) {
                // At first round, check if check threshold has to be high or low.
                // If first point is much darker than the central one, all the following has to be.
                // The same concept applies if the first point is much lighter.
                if(p[j] > highThreshold)
                    isThresholdHigh = true;
                else if(p[j] < lowThreshold)
                    isThresholdHigh = false;
                else
                    break;

                referenceThreshold = (isThresholdHigh ? highThreshold : lowThreshold);
                count++;

                continue;
            }

            // If the first round determined that the check threshold is high, then the following
            // points have to be darker than the central one. Otherwise, proceed to next patch
            // point and restart the process.
            if(isThresholdHigh) {

                if(p[fixedIndex] > highThreshold) {
                    count++;
                } else break;

            } else {
                if(p[fixedIndex] < lowThreshold) {
                    count++;
                } else break;
            }

        }

        if(count == fastContiguousPointsCount) {
            // Found a keypoint!
            return 1;

            // Here harris score is not used as this is just an example to explain how FAST
            // extraction works.
        }

    }

    return 0;

}
