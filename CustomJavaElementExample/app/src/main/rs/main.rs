// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.customjavaelementexample)

// Defines input Allocation pointer
rs_allocation aIn;

// Kernel that fills Allocation with some data
// Note that this kernel only has an input but not an output, as we are returning void
int __attribute__((kernel)) initializeMyElements(uint32_t x) {

    // This kernel uses an iteration hack, so that we can have it iterate through an int array
    // whose contents are the actual indexes of our custom Element allocation.

    // Gets a pointer to the current element
    void * el = rsGetElementAt(aIn,x);

    int * el_x = (int*) (el + 0); // Gets a pointer to x
    int * el_y = (int*) (el + 4); // Gets a pointer to y
    float * el_fx = (int*) (el + 8); // Gets a pointer to fx
    float * el_fy = (int*) (el + 12); // Gets a pointer to fy

    // Sets some data into our custom Element Allocation
    *el_x = x;
    *el_y = x * 3;
    *el_fx = (float) x * 1.2f;
    *el_fy = (float) x * 3.2f;

    // Returns current index, so that RS works the right way
    return x;
}

// Kernel that debugs Allocation
int __attribute__((kernel)) debugAllocation(uint32_t x) {
    // Gets a pointer to the current element
    void * el = rsGetElementAt(aIn,x);

    int * el_x = (int*) (el + 0); // Gets a pointer to x
    int * el_y = (int*) (el + 4); // Gets a pointer to y
    float * el_fx = (int*) (el + 8); // Gets a pointer to fx
    float * el_fy = (int*) (el + 12); // Gets a pointer to fy

    rsDebug("My custom Element", *el_x, *el_y, *el_fx, *el_fy);

    return x;
}