//
// Created by Alberto on 28/02/2016.
//

#include "main.h"

// Include RenderScript generated headers following .rs files' relative paths.
// Dots are replaced by underscores
// Ex. app/src/main/rs/main.rs -> rs/ScriptC_main.h
#include "rs/ScriptC_main.h"

using namespace android::RSC;

//--- Pre declare functions ---
extern "C" {
// See main.h for JNI_FUNCTION macro explanation.
// JNI function, to call RS context initialization
JNI_FUNCTION(void, initRenderScript, jstring
        cacheDirObj, jboolean
                     useDebugMode);
// JNI function, to start the example
JNI_FUNCTION(void, ndkExample);
}

// Function used to initialize RS context
void initRS(const char *cacheDir, bool useDebugMode);

// Main function, to run the example
void runNDKExample();

// Function to trigger on RS error
void rsErrorHandlerFunction(uint32_t errorNum, const char *errorText);

// Function used to print an Allocation content (debug)
void debugAllocationSimpleCopy(const char *tag, sp <Allocation> dAllocation);

//--- END ---

// RS context
sp <RS> mRS;

// Initialize RenderScript context
void initRS(const char *cacheDir, bool useDebugMode) {

    LOGD(useDebugMode ?
         "Creating new RS context using DEBUG context type" :
         "Creating new RS context using NORMAL context type"
    );

    mRS = new RS();

    // Set function to handle errors, when thrown
    LOGD("Setting RS error function");
    mRS->setErrorHandler(&rsErrorHandlerFunction);

    LOGD("Initializing new RS context with cache dir: %s", cacheDir);
    mRS->init(cacheDir, useDebugMode ? RS_CONTEXT_TYPE_DEBUG : RS_CONTEXT_TYPE_NORMAL);

}

void runNDKExample() {

    LOGD("Running NDK example");

    const int inputElementsCount = 16;

    int inputArray[inputElementsCount];

    // Fills input data with some values
    for (int i = 0; i < inputElementsCount; i++) {
        inputArray[i] = i;
    }
    // inputArray will now range from 0 to 9

    LOGD("Filled sample input data");

    // Instantiates an Allocation and copies in it sample data
    sp <Allocation> inputAllocationSimple = Allocation::createSized(mRS, Element::I32(mRS),
                                                                    inputElementsCount);
    inputAllocationSimple->copy1DFrom((void *) inputArray);

    // Debugs allocation contents
    debugAllocationSimpleCopy("inputAllocationSimple", inputAllocationSimple);

    // Output allocation where to store results
    sp <Allocation> outputAllocation = Allocation::createSized(mRS, Element::I32(mRS),
                                                               inputElementsCount);

    // Init custom script
    ScriptC_main *myScript = new ScriptC_main(mRS);

    // Execute kernel on simple allocation and debug it
    myScript->forEach_mulKernel(inputAllocationSimple, outputAllocation);
    mRS->finish();

    // outputAllocation will now contain input elements
    // multiplied by 10.
    debugAllocationSimpleCopy("inputAllocationSimple -> outputAllocation", outputAllocation);
}

// RenderScript error handler function
void rsErrorHandlerFunction(uint32_t errorNum, const char *errorText) {
    LOGE("RS error %d: %s", errorNum, errorText);
}

// Debug function that copies allocation contents and print it
void debugAllocationSimpleCopy(const char *tag, sp <Allocation> dAllocation) {

    // Retrieve elements count
    const int xSize = dAllocation->getType()->getX();

    // Copies allocation contents to local array
    int localCopy[xSize];
    dAllocation->copy1DTo((void *) localCopy);

    char debugString[255];

    // Print array
    char *pos = debugString;
    for (int i = 0; i != xSize; i++) {
        if (i) {
            pos += sprintf(pos, ", ");
        }
        pos += sprintf(pos, "%d", localCopy[i]);
    }

    LOGD("%s: %s\n", tag, debugString);
}

extern "C"
{

JNI_FUNCTION(void, initRenderScript, jstring
        cacheDirObj, jboolean
                     useDebugMode) {

    // Retrieves bytes from Java side to NDK one
    const char *cacheDir = env->GetStringUTFChars(cacheDirObj, NULL);

    // Initialize RS context
    initRS(cacheDir, useDebugMode);

    env->ReleaseStringUTFChars(cacheDirObj, cacheDir);
}

// Test function to invoke from Java
JNI_FUNCTION(void, ndkExample) {
    runNDKExample();
}
}
