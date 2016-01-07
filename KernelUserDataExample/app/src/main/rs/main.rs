// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.kerneluserdataexample)

// Custom user data structure (will not be reflected onto Java side,
// as we did not use typedef keyword to define it
struct MyUserData {
    int addValue;
    int mulValue;
};

// We are using output allocation here to show that it is not mandatory to use it inside root kernel
rs_allocation allocationOutput;

// This can be done only using RenderScript, NOT using FilterScript, as it uses pointers.
//
// Version with input and output allocations:
//      void root(const int32_t *v_in, int32_t *v_out, const void *usrData, uint32_t x, uint32_t y)
// Version with only input allocation:
//      void root(const int32_t *v_in, const void *usrData, uint32_t x, uint32_t y)
void root(const int * in, const void * usrData, uint32_t x) {

    // Retrieves user data content
    struct MyUserData userData = *(struct MyUserData*) usrData;

    // Perform calculation
    int output = *(in) * userData.mulValue + userData.addValue;

    // Sets result in output allocation
    rsSetElementAt_int(allocationOutput, output, x);

}

// Invoking function.
void invokeCalculation(rs_script myScript, rs_allocation inputAllocation, int addValue, int mulValue){

    // Custom user data structures
    struct MyUserData usrData;
    usrData.addValue = addValue;
    usrData.mulValue = mulValue;

    // rsForEach(rs_script script, rs_allocation input, rs_allocation output,
    //      const void * usrData, size_t usrDataLen, const rs_script_call_t *);

    // A stub must be used for successful compiling, as we are using a custom output, which is
    // not bound to root kernel function
    rs_allocation stubOutputAllocation;
    rsForEach(myScript, inputAllocation, stubOutputAllocation, &usrData, sizeof(usrData));

}