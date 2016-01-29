// Needed directive for RS to work
#pragma version(1)
#pragma rs_fp_relaxed

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

// FAST threshold
const uchar fastThreshold = 20;
const uchar minValue = fastThreshold;
const uchar maxValue = 255 - fastThreshold;

const int fastContiguousPointsCount = 9;

rs_allocation grayAllocation;

uchar __attribute__((kernel)) fastNoOptimized(uchar in, uint32_t x, uint32_t y)
{

    // Central pixel is contained inside variable "in"
    // Get circle patch pixels

    uchar p[16];

    uchar p0 = in;
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

    uchar highThreshold = (in < maxValue ? in + fastThreshold : 255);
    uchar lowThreshold = (in > minValue ? in - fastThreshold : 0);

    uchar fixedIndex = 0;

    for(uchar i = 0; i < 16; i++) {
        // Circular scan of patch
        count = 0;

        for(uchar j = i; j < i + fastContiguousPointsCount; j++) {
            fixedIndex = j > 15 ? j-16 : j;

            if(j==i) {
                // At first round, check if threshold has to be high or low
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
        }


    }

    return 0;


}
