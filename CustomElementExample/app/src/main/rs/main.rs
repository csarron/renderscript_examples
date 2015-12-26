// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.customelementexample)

// Creates a custom structure

typedef struct MyElement {

    int x;
    int y;
    bool isValid;

} MyElement_t;

// Kernel that fills Allocation with some data
MyElement_t __attribute__((kernel)) initializeMyElements(uint32_t x) {
    MyElement_t el;

    el.x = x;
    el.y = x + 2;
    el.isValid = x % 2 == 0;

    return el;
}

// Kernel that debugs Allocation
void __attribute__((kernel)) debugAllocation(MyElement_t in) {

rsDebug("My custom Element", in.x, in.y, in.isValid);

}