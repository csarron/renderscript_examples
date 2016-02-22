#pragma version(1)
#pragma rs java_package_name(net.hydex11.profilerexample)

#include "multipleKernelsTestFunctions.rsh"

// First kernel computes
float __attribute__((kernel)) root(float in, uint32_t x){

    return calculateSecond(in);

}