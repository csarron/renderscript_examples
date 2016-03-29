// Needed directive for RS to work
#pragma version(1)
#pragma rs_fp_relaxes

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.customstructelementcopytojava)

// Intial, non-ordered, struct
typedef struct GrayPoint {
    short a;
    int x;
    int y;
    char b;
} GrayPoint_t;
GrayPoint_t stubPoint1; // Used to force generation or reflected code

// Ordered struct
typedef struct GrayPointOrdered {
    int x;
    int y;
    short a;
    char b;
} GrayPointOrdered_t;
GrayPointOrdered_t stubPoint2; // Used to force generation or reflected code

// Packed struct
typedef struct __attribute__((packed)) GrayPointPacked {
    short a;
    int x;
    int y;
    char b;
} GrayPointPacked_t;
GrayPointPacked_t stubPoint3; // Used to force generation or reflected code

GrayPointOrdered_t __attribute__((kernel)) fillRandom(int x, int y) {

    GrayPointOrdered_t point;

    point.x = x;
    point.y = y;
    point.a = (short)rsRand(0,32767);
    point.b = (char)rsRand(0,127);

    return point;

}

// Util function to debug elements
void debugElements(rs_allocation inputAllocation, int sizeX, int sizeY){
for (int x = 0; x < sizeX; x++) {
    for (int y = 0; y < sizeY; y++) {

        GrayPointOrdered_t item = * (GrayPointOrdered_t *) rsGetElementAt(inputAllocation, x, y);

        rsDebug("Element", x, y, item.a, item.b);
        }
    }
}