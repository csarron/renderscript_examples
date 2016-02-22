#pragma version(1)
#pragma rs java_package_name(net.hydex11.profilerexample)

#include "multipleKernelsTestFunctions.rsh"

rs_allocation inputAllocation;

float __attribute__((kernel)) root(float in, uint32_t x){

    float element1 = rsGetElementAt_float(inputAllocation, x-1); // Previous element
    float element2 = rsGetElementAt_float(inputAllocation, x+1); // Next element

    return calculateSecond(calculateFirst(in, element1, element2));

}