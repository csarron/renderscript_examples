#pragma version(1)
#pragma rs_fp_relaxed

#pragma rs java_package_name(net.hydex11.profilerexample)

#include "multipleKernelsTestFunctions.rsh"

rs_script_call_t kernelCallLimits;

void initializeCallLimits(rs_allocation inputAllocation){

    int elementsCount = rsAllocationGetDimX(inputAllocation);

    kernelCallLimits.xStart = 1;
    kernelCallLimits.xEnd = elementsCount - 1;
    kernelCallLimits.yStart = 0;
    kernelCallLimits.yEnd = 0;
    kernelCallLimits.zStart = 0;
    kernelCallLimits.zEnd = 0;

}

void invokeMultipleKernelsCall(rs_script scriptFirst, rs_script scriptSecond,
    rs_allocation inputAllocation, rs_allocation midAllocation, rs_allocation outputAllocation){

	rsForEach(scriptFirst, inputAllocation, midAllocation, NULL, 0, &kernelCallLimits);
	rsForEach(scriptSecond, midAllocation, outputAllocation, NULL, 0, &kernelCallLimits);

}

void invokeSingleKernelCall(rs_script scriptUnique, rs_allocation inputAllocation, rs_allocation outputAllocation){

	rsForEach(scriptUnique, inputAllocation, outputAllocation, NULL, 0, &kernelCallLimits);

}

