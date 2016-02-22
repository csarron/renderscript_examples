// FilterScript script

#pragma version(1)

#pragma rs java_package_name(net.hydex11.profilerexample)

// Some redit goes to: http://stackoverflow.com/questions/13917106/where-is-the-filterscript-documentation-and-how-can-i-use-it

int blurRadius = 3;

rs_allocation inputAllocation;
rs_allocation outputAllocation;

uint32_t width = 0;
uint32_t height = 0;

uchar4 __attribute__((kernel)) blurSimpleKernelFS(uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;
    for (int yi = -blurRadius; yi <= blurRadius; ++yi) {
        for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
                sum += convert_uint4(rsGetElementAt_uchar4(inputAllocation, x+xi, y+yi));
                ++count;
        }
    }
    return convert_uchar4(sum/count);
}

// Test an allocation directly loaded inside a script
const int pngWidth = 500;
const int pngHeight = 286;
uchar4 pngData[pngWidth * pngHeight];

// Kernel function used to load image data
void __attribute__((kernel)) fillPngData(uchar4 in, uint32_t x, uint32_t y){
    pngData[x + y * pngWidth] = in;
}

uchar4 __attribute__((kernel)) blurSimpleKernelFSGetFromScriptVariable(uint32_t x, uint32_t y) {
    uint4 sum = 0;
    uint count = 0;

    // We can use the image width as stride
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
    {
        for (int xi = -blurRadius; xi <= blurRadius; ++xi) {

            int idx = x+xi + (y+yi) * pngWidth;
            sum += convert_uint4(pngData[idx]);
            ++count;
        }
    }

    return convert_uchar4(sum/count);
}

// Set tons of values, using radius blur
void __attribute__((kernel)) setValuesSimpleKernelFS(uchar4 in, int x, int y){
    for(int yi = -blurRadius; yi <= blurRadius; ++yi)
        {
            for (int xi = -blurRadius; xi <= blurRadius; ++xi) {
                rsSetElementAt_uchar4(outputAllocation, in, x+xi, y+yi);
            }
        }
}

const static float3 grayMultipliers = {0.299f, 0.587f, 0.114f};

// The following function presents a standard way to peroform in/out
// operations on Allocation having different Types.
uchar __attribute__((kernel)) rgbaToGraySimpleKernelFS(uchar4 in, uint32_t x, uint32_t y) {

    return (uchar) ((float)in.r*grayMultipliers.r +
                      (float)in.g*grayMultipliers.g +
                    (float)in.b*grayMultipliers.b);
}

// PI test
int piIterations = 30;
// Test for pure calculation
// Reference: http://www.codeproject.com/Articles/813185/Calculating-the-Number-PI-Through-Infinite-Sequenc
static float piTest() {
   float i;    // Number of iterations and control variable
  float s = 1;   //Signal for the next operation
  float pi = 3;

  for(i = 2; i <= piIterations*2; i += 2){
    pi = pi + s * (4 / (i * (i + 1) * (i + 2)));
    s = -s;
  }

  return pi;

}

float __attribute__((kernel)) PITestSimpleKernel(){

    return piTest();

}