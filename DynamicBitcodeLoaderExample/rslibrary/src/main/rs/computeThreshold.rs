/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 - Alberto Marchetti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// This script parses an image and, if a pixel's brightness is higher than a certain threshold,
// it't turned to red.

// Sets property of this script, that tells our runtime environment which kernel to call.
// Slot is calculated from 1 onwards, as 0 is reserved for a function named "root". If you use it,
// set 0 here.
// Also, non-static variable indexes start from 0.
// Properties get read if line starts with //D

//D kernelSlot=1

#pragma version(1)

// We use a common package name for reflection
#pragma rs java_package_name(net.hydex11.rslibrary)

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};
uchar threshold = 200;

uchar4 __attribute__((kernel)) applyThreshold(uchar4 in) {

    uchar grayValue = (uchar) ((float) in.r * grayMultipliers.r +
                              (float) in.g * grayMultipliers.g +
                              (float) in.b * grayMultipliers.b);

    if(grayValue > threshold){
        in.r = 255;
        in.g = 0;
        in.b = 0;
    }

    return in;
}