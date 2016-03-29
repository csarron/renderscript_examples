// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.colornormalizationexample)

uchar4 __attribute__((kernel)) normalizeImage(uchar4 in, int x, int y){

// Converts uchars to floats. fIn values range from 0.0 to 1.0 inclusive.
float4 fIn = rsUnpackColor8888(in);

// Creates sum of color for current pixel
float sum = fIn.r + fIn.g + fIn.b;

float4 fOut;
fOut.a = 1.0f;

// Calculates weighted divisions of pixel channels
fOut.r = fIn.r / sum;
fOut.g = fIn.g / sum;
fOut.b = fIn.b / sum;

// Converts to uchar values. Multiplies each float by 255
uchar4 out = rsPackColorTo8888(fOut);

return out;

}