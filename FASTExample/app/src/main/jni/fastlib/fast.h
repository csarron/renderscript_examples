//
// Created by Alberto on 28/01/2016.
//

#ifndef FASTEXAMPLE_FAST_H
#define FASTEXAMPLE_FAST_H

typedef struct {
    int x, y;
} xy;
typedef unsigned char byte;

xy *fast9_detect(const byte *im, int xsize, int ysize, int stride, int b, int *ret_num_corners);

#endif //FASTEXAMPLE_FAST_H
