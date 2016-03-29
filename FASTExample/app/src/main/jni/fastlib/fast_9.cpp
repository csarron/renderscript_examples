/*This is mechanically generated code*/
#include "fast.h"
#include <omp.h>

static void make_offsets(int pixel[], int row_stride) {
    pixel[0] = 0 + row_stride * 3;
    pixel[1] = 1 + row_stride * 3;
    pixel[2] = 2 + row_stride * 2;
    pixel[3] = 3 + row_stride * 1;
    pixel[4] = 3 + row_stride * 0;
    pixel[5] = 3 + row_stride * -1;
    pixel[6] = 2 + row_stride * -2;
    pixel[7] = 1 + row_stride * -3;
    pixel[8] = 0 + row_stride * -3;
    pixel[9] = -1 + row_stride * -3;
    pixel[10] = -2 + row_stride * -2;
    pixel[11] = -3 + row_stride * -1;
    pixel[12] = -3 + row_stride * 0;
    pixel[13] = -3 + row_stride * 1;
    pixel[14] = -2 + row_stride * 2;
    pixel[15] = -1 + row_stride * 3;
}

xy *fast9_detect(byte *im, int xsize, int ysize, int stride, int b, int *ret_num_corners) {
    int num_corners = 0;
    xy *ret_corners;
    int rsize = 512;
    int pixel[16];
    int x, y;

    make_offsets(pixel, stride);

#pragma omp parallel for
    for (y = 4; y < ysize - 5; y++)
        for (x = 4; x < xsize - 5; x++) {
            byte *p = im + x + y * stride;

            int cb = *p + b;
            int c_b = *p - b;
            if (p[pixel[0]] > cb) if (p[pixel[1]] > cb) if (p[pixel[2]] > cb) if (p[pixel[3]] >
                                                                                  cb) if (
                    p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                      cb) if (
                    p[pixel[8]] > cb) { }
            else if (p[pixel[15]] > cb) { }
            else
                continue;
            else if (p[pixel[7]] < c_b) if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) if (
                    p[pixel[10]] <
                    c_b) if (
                    p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[6]] < c_b) if (p[pixel[15]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) { }
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[14]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) if (
                    p[pixel[10]] <
                    c_b) if (
                    p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[14]] <
                    c_b) if (
                    p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[14]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) if (
                    p[pixel[10]] > cb) if (p[pixel[11]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) if (
                    p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (
                    p[pixel[13]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[6]] < c_b) { }
            else if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[15]] >
                    cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) if (
                    p[pixel[10]] > cb) if (p[pixel[11]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) if (
                    p[pixel[6]] < c_b) { }
            else if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[13]] > cb) if (p[pixel[11]] > cb) if (
                    p[pixel[12]] >
                    cb) if (
                    p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) if (
                    p[pixel[10]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) if (
                    p[pixel[9]] > cb) if (p[pixel[10]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) if (
                    p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (
                    p[pixel[12]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[5]] < c_b) { }
            else if (p[pixel[14]] < c_b) { }
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) if (
                    p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (
                    p[pixel[12]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) if (
                    p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) if (
                    p[pixel[10]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) if (
                    p[pixel[9]] > cb) if (p[pixel[10]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[5]] < c_b) { }
            else if (p[pixel[14]] < c_b) { }
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] < c_b) if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (
                    p[pixel[12]] >
                    cb) if (
                    p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) if (
                    p[pixel[9]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                       cb) if (
                    p[pixel[8]] > cb) if (p[pixel[9]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[11]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[5]] < c_b) if (
                    p[pixel[4]] <
                    c_b) { }
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) if (
                    p[pixel[9]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                       cb) if (
                    p[pixel[8]] > cb) if (p[pixel[9]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[5]] <
                    c_b) if (
                    p[pixel[4]] < c_b) { }
            else if (p[pixel[13]] < c_b) { }
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[2]] < c_b) if (p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (
                    p[pixel[11]] >
                    cb) if (
                    p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[15]] >
                    cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                       cb) if (
                    p[pixel[8]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] > cb) if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] >
                                                                                       cb) if (
                    p[pixel[7]] > cb) if (p[pixel[8]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[9]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[10]] <
                    c_b) if (
                    p[pixel[6]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[4]] < c_b) if (
                    p[pixel[3]] <
                    c_b) { }
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) if (
                    p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (
                    p[pixel[12]] >
                    cb) if (
                    p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                       cb) if (
                    p[pixel[8]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] > cb) if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] >
                                                                                       cb) if (
                    p[pixel[7]] > cb) if (p[pixel[8]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[9]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[10]] <
                    c_b) if (
                    p[pixel[11]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[5]] < c_b) if (
                    p[pixel[4]] <
                    c_b) if (
                    p[pixel[3]] < c_b) { }
            else if (p[pixel[12]] < c_b) { }
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[1]] < c_b) if (p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (
                    p[pixel[10]] >
                    cb) if (
                    p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) if (
                    p[pixel[15]] > cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                       cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] > cb) if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] >
                                                                                       cb) if (
                    p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[2]] > cb) if (p[pixel[3]] > cb) if (p[pixel[4]] > cb) if (p[pixel[5]] >
                                                                                       cb) if (
                    p[pixel[6]] > cb) if (p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[8]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[9]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[5]] < c_b) if (p[pixel[4]] < c_b) if (p[pixel[3]] < c_b) if (
                    p[pixel[2]] <
                    c_b) { }
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[14]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (
                    p[pixel[11]] >
                    cb) if (
                    p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[15]] >
                    cb) { }
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] >
                                                                                       cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] > cb) if (p[pixel[4]] > cb) if (p[pixel[5]] > cb) if (p[pixel[6]] >
                                                                                       cb) if (
                    p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[2]] > cb) if (p[pixel[3]] > cb) if (p[pixel[4]] > cb) if (p[pixel[5]] >
                                                                                       cb) if (
                    p[pixel[6]] > cb) if (p[pixel[7]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[8]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[9]] < c_b) if (
                    p[pixel[10]] <
                    c_b) if (
                    p[pixel[6]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[4]] < c_b) if (
                    p[pixel[3]] <
                    c_b) if (
                    p[pixel[2]] < c_b) { }
            else if (p[pixel[11]] < c_b) { }
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) if (
                    p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[0]] < c_b) if (p[pixel[1]] > cb) if (p[pixel[8]] > cb) if (
                    p[pixel[7]] >
                    cb) if (p[pixel[9]] >
                            cb) if (
                    p[pixel[6]] > cb) if (p[pixel[5]] > cb) if (p[pixel[4]] > cb) if (p[pixel[3]] >
                                                                                      cb) if (
                    p[pixel[2]] > cb) { }
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (
                    p[pixel[11]] <
                    c_b) if (
                    p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] < c_b) if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[2]] < c_b) if (p[pixel[3]] < c_b) if (p[pixel[4]] < c_b) if (
                    p[pixel[5]] <
                    c_b) if (
                    p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[1]] < c_b) if (p[pixel[2]] > cb) if (p[pixel[9]] > cb) if (
                    p[pixel[7]] >
                    cb) if (p[pixel[8]] >
                            cb) if (
                    p[pixel[10]] > cb) if (p[pixel[6]] > cb) if (p[pixel[5]] > cb) if (p[pixel[4]] >
                                                                                       cb) if (
                    p[pixel[3]] > cb) { }
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) if (
                    p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (
                    p[pixel[12]] <
                    c_b) if (
                    p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) if (
                    p[pixel[8]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] < c_b) if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[2]] < c_b) if (p[pixel[3]] > cb) if (p[pixel[10]] > cb) if (
                    p[pixel[7]] >
                    cb) if (
                    p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[11]] > cb) if (p[pixel[6]] >
                                                                                       cb) if (
                    p[pixel[5]] > cb) if (p[pixel[4]] > cb) { }
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[15]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) if (
                    p[pixel[9]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) if (
                    p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] < c_b) if (p[pixel[4]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[7]] >
                    cb) if (
                    p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (
                    p[pixel[11]] >
                    cb) if (p[pixel[12]] >
                            cb) if (
                    p[pixel[6]] > cb) if (p[pixel[5]] > cb) { }
            else if (p[pixel[14]] > cb) { }
            else
                continue;
            else if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[11]] > cb) if (p[pixel[5]] > cb) if (
                    p[pixel[6]] >
                    cb) if (
                    p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[10]] >
                                                                                      cb) if (
                    p[pixel[12]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[14]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) if (
                    p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] > cb) if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] >
                                                                                       cb) if (
                    p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (
                    p[pixel[12]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[7]] >
                    cb) if (
                    p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (
                    p[pixel[11]] >
                    cb) if (p[pixel[12]] >
                            cb) if (
                    p[pixel[13]] > cb) if (p[pixel[6]] > cb) { }
            else if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[12]] > cb) if (p[pixel[6]] > cb) if (
                    p[pixel[7]] >
                    cb) if (
                    p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (
                    p[pixel[11]] >
                    cb) if (p[pixel[13]] >
                            cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[15]] < c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[6]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] >
                                                                                       cb) if (
                    p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] > cb) if (p[pixel[15]] < c_b) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[10]] >
                                                                                      cb) if (
                    p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (
                    p[pixel[10]] >
                    cb) if (p[pixel[11]] >
                            cb) if (
                    p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[8]] >
                    cb) if (
                    p[pixel[9]] > cb) if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (
                    p[pixel[12]] >
                    cb) if (
                    p[pixel[13]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) { }
            else if (p[pixel[15]] < c_b) { }
            else
                continue;
            else if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (
                    p[pixel[9]] >
                    cb) if (
                    p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[14]] >
                    cb) if (
                    p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (
                    p[pixel[9]] >
                    cb) if (
                    p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) if (
                    p[pixel[6]] > cb) { }
            else if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (
                    p[pixel[9]] >
                    cb) if (
                    p[pixel[10]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[6]] >
                    cb) if (
                    p[pixel[5]] > cb) { }
            else if (p[pixel[14]] > cb) { }
            else
                continue;
            else if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (
                    p[pixel[14]] <
                    c_b) if (
                    p[pixel[15]] < c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) if (
                    p[pixel[10]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) if (
                    p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (
                    p[pixel[9]] >
                    cb) if (
                    p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[6]] > cb) if (
                    p[pixel[5]] >
                    cb) if (p[pixel[4]] >
                            cb) { }
            else if (p[pixel[13]] > cb) { }
            else
                continue;
            else if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (
                    p[pixel[9]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) if (
                    p[pixel[9]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) if (
                    p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[9]] > cb) if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (
                    p[pixel[10]] >
                    cb) if (p[pixel[11]] >
                            cb) if (
                    p[pixel[6]] > cb) if (p[pixel[5]] > cb) if (p[pixel[4]] > cb) if (p[pixel[3]] >
                                                                                      cb) { }
            else if (p[pixel[12]] > cb) { }
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (p[pixel[14]] > cb) if (
                    p[pixel[15]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (
                    p[pixel[12]] <
                    c_b) if (
                    p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) if (
                    p[pixel[8]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) if (
                    p[pixel[8]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] < c_b) if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[8]] > cb) if (p[pixel[7]] > cb) if (p[pixel[9]] > cb) if (
                    p[pixel[10]] >
                    cb) if (p[pixel[6]] >
                            cb) if (
                    p[pixel[5]] > cb) if (p[pixel[4]] > cb) if (p[pixel[3]] > cb) if (p[pixel[2]] >
                                                                                      cb) { }
            else if (p[pixel[11]] > cb) { }
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (p[pixel[13]] > cb) if (
                    p[pixel[14]] >
                    cb) if (
                    p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) if (p[pixel[10]] < c_b) if (
                    p[pixel[11]] <
                    c_b) if (
                    p[pixel[12]] < c_b) if (p[pixel[13]] < c_b) if (p[pixel[14]] < c_b) if (
                    p[pixel[15]] <
                    c_b) { }
            else if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (p[pixel[6]] < c_b) if (
                    p[pixel[7]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[3]] < c_b) if (p[pixel[4]] < c_b) if (p[pixel[5]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[2]] < c_b) if (p[pixel[3]] < c_b) if (p[pixel[4]] < c_b) if (
                    p[pixel[5]] <
                    c_b) if (
                    p[pixel[6]] < c_b) if (p[pixel[7]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[7]] > cb) if (p[pixel[8]] > cb) if (p[pixel[9]] > cb) if (p[pixel[6]] >
                                                                                       cb) if (
                    p[pixel[5]] > cb) if (p[pixel[4]] > cb) if (p[pixel[3]] > cb) if (p[pixel[2]] >
                                                                                      cb) if (
                    p[pixel[1]] > cb) { }
            else if (p[pixel[10]] > cb) { }
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[14]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] > cb) if (p[pixel[11]] > cb) if (p[pixel[12]] > cb) if (
                    p[pixel[13]] >
                    cb) if (
                    p[pixel[14]] > cb) if (p[pixel[15]] > cb) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[7]] < c_b) if (p[pixel[8]] < c_b) if (p[pixel[9]] < c_b) if (
                    p[pixel[6]] <
                    c_b) if (
                    p[pixel[5]] < c_b) if (p[pixel[4]] < c_b) if (p[pixel[3]] < c_b) if (
                    p[pixel[2]] <
                    c_b) if (
                    p[pixel[1]] < c_b) { }
            else if (p[pixel[10]] < c_b) { }
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) { }
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[14]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else if (p[pixel[10]] < c_b) if (p[pixel[11]] < c_b) if (p[pixel[12]] < c_b) if (
                    p[pixel[13]] <
                    c_b) if (
                    p[pixel[14]] < c_b) if (p[pixel[15]] < c_b) { }
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;
            else
                continue;

            num_corners++;

        }

    *ret_num_corners = num_corners;
    return ret_corners;

}
