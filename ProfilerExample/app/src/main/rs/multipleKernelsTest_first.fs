#pragma version(1)
#pragma rs java_package_name(net.hydex11.profilerexample)

#include "multipleKernelsTestFunctions.rsh"

// Fills allocation with random data
float __attribute__((kernel)) preFillAllocation(uint32_t x){

    return rsRand(0.0f,100000.0f);

}

rs_allocation inputAllocation;

// First kernel computes
float __attribute__((kernel)) root(float in, uint32_t x){

    float element1 = rsGetElementAt_float(inputAllocation, x-1); // Previous element
    float element2 = rsGetElementAt_float(inputAllocation, x+1); // Next element

    return calculateFirst(in, element1, element2);

}
