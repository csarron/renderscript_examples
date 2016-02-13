/*
To understand the following math, please refer to:
http://docs.opencv.org/2.4/doc/tutorials/features2d/trackingmotion/harris_detector/harris_detector.html
*/

// We use a static scoring normalization process because the score can range from
// small values (even < 0) to extremely huge ones (like 50,000,000). As we want a
// normalized output (that can range from 0 to 255), without applying big agorithms
// we'll just use the log function and apply a minimum threshold (MIN_HARRIS_SCORE),
// calculated empirically during our experiments.
static const float MIN_HARRIS_SCORE = 16.118095651f;
static const float MAX_HARRIS_SCORE = 35.4938722213f;
static const float HARRIS_SCORE_DIV = 13.1607628254f;

// The following code is a reinterpretation of OpenCV harris score calculator,
// used with ORB features.
// Reference: https://github.com/Itseez/opencv/blob/master/modules/features2d/src/orb.cpp#L131
static const int BLOCK_SIZE = 7;
static const float HARRIS_K = 0.04f;

static uchar calculateHarrisScore(uchar * v_in, int row_stride) {

    int a = 0, b = 0, c = 0;
    
    uchar * currentPoint;

    int e0,e1,e2,e3,e4,e5,e6,e7,e8;

    int Ix;
    int Iy;

    // For each analyzed block we calculate the gradients
    for (int row = -3; row < 4; row++) {
        for (int col = -3; col < 4; col++) {
        
            currentPoint = v_in + col + row_stride * row;

            e0 = (int) currentPoint[-1 + row_stride * -1];
            e1 = (int) currentPoint[0 + row_stride * -1];
            e2 = (int) currentPoint[1 + row_stride * -1];
            e3 = (int) currentPoint[-1];
            //e4 = (int) currentPoint[0]; // Not used
            e5 = (int) currentPoint[1];
            e6 = (int) currentPoint[-1 + row_stride * 1];
            e7 = (int) currentPoint[0 + row_stride * 1];
            e8 = (int) currentPoint[1 + row_stride * 1];

            Ix = (e5 - e3) * 2 + (e2 - e0) + (e8 - e6);
            Iy = (e7 - e1) * 2 + (e6 - e0) + (e8 - e2);

            a += Ix * Ix;
            b += Iy * Iy;
            c += Ix * Iy;

        }
    }

    // Here we calculate the Harris score, and its log
    float ret = log((float) a * b - (float) c * c - HARRIS_K * ((float) a + b) * ((float) a + b));

    // If lower than our wanted score, this point is not a valid corner.
    if(ret < MIN_HARRIS_SCORE) {
        ret = 0;
    }
    else
    {
        // If score is higher than the maximum we can tolerate, we just adjust it.
        if(ret > MAX_HARRIS_SCORE)
            ret = MAX_HARRIS_SCORE;

        ret -= MIN_HARRIS_SCORE;
        ret *= HARRIS_SCORE_DIV;
    }

    return (uchar) ret;

}
