// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.fastexample)

int fastThreshold = 20;
rs_allocation grayAllocation;

#include "harrisFS.rsh"
#include "fast_util.rsh"

// The following code is a direct porting of the original FAST library
// (http://www.edwardrosten.com/work/fast.html), which is highly optimized, auto
// generated code.
uchar __attribute__((kernel)) fastOptimized(uchar in, uint x, uint y)
{

    int cb = in + fastThreshold;
    int c_b = in - fastThreshold;

if (getFASTPixel(0,x,y) > cb) if (getFASTPixel(1,x,y) > cb) if (getFASTPixel(2,x,y) > cb) if (getFASTPixel(3,x,y) >
                                                                                  cb) if (
                    getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                      cb) if (
                    getFASTPixel(8,x,y) > cb) { }
            else if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (
                    getFASTPixel(10,x,y) <
                    c_b) if (
                    getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(15,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) { }
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (
                    getFASTPixel(10,x,y) <
                    c_b) if (
                    getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) if (
                    getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) if (
                    getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(6,x,y) < c_b) { }
            else if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(15,x,y) >
                    cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) if (
                    getFASTPixel(6,x,y) < c_b) { }
            else if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (
                    getFASTPixel(12,x,y) >
                    cb) if (
                    getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) if (
                    getFASTPixel(10,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) if (
                    getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) if (
                    getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (
                    getFASTPixel(12,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(5,x,y) < c_b) { }
            else if (getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) if (
                    getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (
                    getFASTPixel(12,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) if (
                    getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) if (
                    getFASTPixel(10,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) if (
                    getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(5,x,y) < c_b) { }
            else if (getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (
                    getFASTPixel(12,x,y) >
                    cb) if (
                    getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) if (
                    getFASTPixel(9,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                       cb) if (
                    getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(11,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (
                    getFASTPixel(4,x,y) <
                    c_b) { }
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) if (
                    getFASTPixel(9,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                       cb) if (
                    getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(5,x,y) <
                    c_b) if (
                    getFASTPixel(4,x,y) < c_b) { }
            else if (getFASTPixel(13,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(2,x,y) < c_b) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (
                    getFASTPixel(11,x,y) >
                    cb) if (
                    getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(15,x,y) >
                    cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                       cb) if (
                    getFASTPixel(8,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) >
                                                                                       cb) if (
                    getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(10,x,y) <
                    c_b) if (
                    getFASTPixel(6,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (
                    getFASTPixel(3,x,y) <
                    c_b) { }
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) if (
                    getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (
                    getFASTPixel(12,x,y) >
                    cb) if (
                    getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                       cb) if (
                    getFASTPixel(8,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) >
                                                                                       cb) if (
                    getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(10,x,y) <
                    c_b) if (
                    getFASTPixel(11,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (
                    getFASTPixel(4,x,y) <
                    c_b) if (
                    getFASTPixel(3,x,y) < c_b) { }
            else if (getFASTPixel(12,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(1,x,y) < c_b) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (
                    getFASTPixel(10,x,y) >
                    cb) if (
                    getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) if (
                    getFASTPixel(15,x,y) > cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                       cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) >
                                                                                       cb) if (
                    getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(2,x,y) > cb) if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) >
                                                                                       cb) if (
                    getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(5,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(3,x,y) < c_b) if (
                    getFASTPixel(2,x,y) <
                    c_b) { }
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (
                    getFASTPixel(11,x,y) >
                    cb) if (
                    getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(15,x,y) >
                    cb) { }
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) >
                                                                                       cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) >
                                                                                       cb) if (
                    getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(2,x,y) > cb) if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(5,x,y) >
                                                                                       cb) if (
                    getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (
                    getFASTPixel(10,x,y) <
                    c_b) if (
                    getFASTPixel(6,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (
                    getFASTPixel(3,x,y) <
                    c_b) if (
                    getFASTPixel(2,x,y) < c_b) { }
            else if (getFASTPixel(11,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) if (
                    getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(0,x,y) < c_b) if (getFASTPixel(1,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (
                    getFASTPixel(7,x,y) >
                    cb) if (getFASTPixel(9,x,y) >
                            cb) if (
                    getFASTPixel(6,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(3,x,y) >
                                                                                      cb) if (
                    getFASTPixel(2,x,y) > cb) { }
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (
                    getFASTPixel(11,x,y) <
                    c_b) if (
                    getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(2,x,y) < c_b) if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (
                    getFASTPixel(5,x,y) <
                    c_b) if (
                    getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(1,x,y) < c_b) if (getFASTPixel(2,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (
                    getFASTPixel(7,x,y) >
                    cb) if (getFASTPixel(8,x,y) >
                            cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(4,x,y) >
                                                                                       cb) if (
                    getFASTPixel(3,x,y) > cb) { }
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) if (
                    getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (
                    getFASTPixel(12,x,y) <
                    c_b) if (
                    getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) if (
                    getFASTPixel(8,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(2,x,y) < c_b) if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (
                    getFASTPixel(7,x,y) >
                    cb) if (
                    getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(6,x,y) >
                                                                                       cb) if (
                    getFASTPixel(5,x,y) > cb) if (getFASTPixel(4,x,y) > cb) { }
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(15,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) if (
                    getFASTPixel(9,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) if (
                    getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(7,x,y) >
                    cb) if (
                    getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (
                    getFASTPixel(11,x,y) >
                    cb) if (getFASTPixel(12,x,y) >
                            cb) if (
                    getFASTPixel(6,x,y) > cb) if (getFASTPixel(5,x,y) > cb) { }
            else if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (
                    getFASTPixel(6,x,y) >
                    cb) if (
                    getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) >
                                                                                      cb) if (
                    getFASTPixel(12,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) if (
                    getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) >
                                                                                       cb) if (
                    getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (
                    getFASTPixel(12,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(7,x,y) >
                    cb) if (
                    getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (
                    getFASTPixel(11,x,y) >
                    cb) if (getFASTPixel(12,x,y) >
                            cb) if (
                    getFASTPixel(13,x,y) > cb) if (getFASTPixel(6,x,y) > cb) { }
            else if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (
                    getFASTPixel(7,x,y) >
                    cb) if (
                    getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (
                    getFASTPixel(11,x,y) >
                    cb) if (getFASTPixel(13,x,y) >
                            cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) >
                                                                                       cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) > cb) if (getFASTPixel(15,x,y) < c_b) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) >
                                                                                      cb) if (
                    getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (
                    getFASTPixel(10,x,y) >
                    cb) if (getFASTPixel(11,x,y) >
                            cb) if (
                    getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(8,x,y) >
                    cb) if (
                    getFASTPixel(9,x,y) > cb) if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (
                    getFASTPixel(12,x,y) >
                    cb) if (
                    getFASTPixel(13,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) { }
            else if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (
                    getFASTPixel(9,x,y) >
                    cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) if (
                    getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (
                    getFASTPixel(9,x,y) >
                    cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) if (
                    getFASTPixel(6,x,y) > cb) { }
            else if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (
                    getFASTPixel(9,x,y) >
                    cb) if (
                    getFASTPixel(10,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(6,x,y) >
                    cb) if (
                    getFASTPixel(5,x,y) > cb) { }
            else if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (
                    getFASTPixel(14,x,y) <
                    c_b) if (
                    getFASTPixel(15,x,y) < c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) if (
                    getFASTPixel(10,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) if (
                    getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (
                    getFASTPixel(9,x,y) >
                    cb) if (
                    getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(6,x,y) > cb) if (
                    getFASTPixel(5,x,y) >
                    cb) if (getFASTPixel(4,x,y) >
                            cb) { }
            else if (getFASTPixel(13,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (
                    getFASTPixel(9,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) if (
                    getFASTPixel(9,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) if (
                    getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (
                    getFASTPixel(10,x,y) >
                    cb) if (getFASTPixel(11,x,y) >
                            cb) if (
                    getFASTPixel(6,x,y) > cb) if (getFASTPixel(5,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(3,x,y) >
                                                                                      cb) { }
            else if (getFASTPixel(12,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (getFASTPixel(14,x,y) > cb) if (
                    getFASTPixel(15,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (
                    getFASTPixel(12,x,y) <
                    c_b) if (
                    getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) if (
                    getFASTPixel(8,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) if (
                    getFASTPixel(8,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (
                    getFASTPixel(10,x,y) >
                    cb) if (getFASTPixel(6,x,y) >
                            cb) if (
                    getFASTPixel(5,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(2,x,y) >
                                                                                      cb) { }
            else if (getFASTPixel(11,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (getFASTPixel(13,x,y) > cb) if (
                    getFASTPixel(14,x,y) >
                    cb) if (
                    getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (getFASTPixel(10,x,y) < c_b) if (
                    getFASTPixel(11,x,y) <
                    c_b) if (
                    getFASTPixel(12,x,y) < c_b) if (getFASTPixel(13,x,y) < c_b) if (getFASTPixel(14,x,y) < c_b) if (
                    getFASTPixel(15,x,y) <
                    c_b) { }
            else if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (getFASTPixel(6,x,y) < c_b) if (
                    getFASTPixel(7,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(5,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(2,x,y) < c_b) if (getFASTPixel(3,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (
                    getFASTPixel(5,x,y) <
                    c_b) if (
                    getFASTPixel(6,x,y) < c_b) if (getFASTPixel(7,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(7,x,y) > cb) if (getFASTPixel(8,x,y) > cb) if (getFASTPixel(9,x,y) > cb) if (getFASTPixel(6,x,y) >
                                                                                       cb) if (
                    getFASTPixel(5,x,y) > cb) if (getFASTPixel(4,x,y) > cb) if (getFASTPixel(3,x,y) > cb) if (getFASTPixel(2,x,y) >
                                                                                      cb) if (
                    getFASTPixel(1,x,y) > cb) { }
            else if (getFASTPixel(10,x,y) > cb) { }
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(14,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) > cb) if (getFASTPixel(11,x,y) > cb) if (getFASTPixel(12,x,y) > cb) if (
                    getFASTPixel(13,x,y) >
                    cb) if (
                    getFASTPixel(14,x,y) > cb) if (getFASTPixel(15,x,y) > cb) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(7,x,y) < c_b) if (getFASTPixel(8,x,y) < c_b) if (getFASTPixel(9,x,y) < c_b) if (
                    getFASTPixel(6,x,y) <
                    c_b) if (
                    getFASTPixel(5,x,y) < c_b) if (getFASTPixel(4,x,y) < c_b) if (getFASTPixel(3,x,y) < c_b) if (
                    getFASTPixel(2,x,y) <
                    c_b) if (
                    getFASTPixel(1,x,y) < c_b) { }
            else if (getFASTPixel(10,x,y) < c_b) { }
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(14,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else if (getFASTPixel(10,x,y) < c_b) if (getFASTPixel(11,x,y) < c_b) if (getFASTPixel(12,x,y) < c_b) if (
                    getFASTPixel(13,x,y) <
                    c_b) if (
                    getFASTPixel(14,x,y) < c_b) if (getFASTPixel(15,x,y) < c_b) { }
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;
            else
                return 0;

    return calculateHarrisScore(x,y);
}
