// Needed directive for RS to work
#pragma version(1)

// Change java_package_name directive to match your Activity's package path
#pragma rs java_package_name(net.hydex11.firstexample)

// Let's map our input allocation here
rs_allocation inputAllocation;

// This kernel function will just sum 2 to every input element
int __attribute__((kernel)) sum2(uint32_t x) {

    // Get current element from input allocation
    int inputElement = rsGetElementAt_int(inputAllocation, x);

    // Performs the sum
    return inputElement + 2;

}