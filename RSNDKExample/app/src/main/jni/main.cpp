//
// Created by Alberto on 28/02/2016.
//

#include "main.h"

#include "ScriptC_main.h"

#include "catcher/coffeecatch.h"
#include "catcher/coffeejni.h"

using namespace android::RSC;

sp<RS> mRS;

// RenderScript error handler function
void rsErrorHandlerFunction(uint32_t errorNum, const char* errorText)
{
    LOGE("RS error: %s", errorText);
}

int getElement(long ptr, int size){
    
    int dst = 0;
    long dstPtr = (long) &dst;
    memcpy((void*)(dstPtr), (void*)(ptr), size);
    
    return dst;
    
}

void checkMemory(){
	// Need to check that mRS contents behaves as expected
	
	int idx = 0;
	int offset = 0;
	int sizes[255];
	long ptrRS = (long) mRS.get();
	
	idx++; sizes[idx] = sizeof(android::RSC::LightRefBase<RS>); LOGD("sz android::RSC::LightRefBase<RS> %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx])); offset += sizes[idx]; 
	idx++; sizes[idx] = 4; offset += sizes[idx]; LOGD("sz virtual %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(pthread_t);  LOGD("sz pthread_t %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(pid_t);LOGD("sz pid_t %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(bool);  LOGD("sz bool %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(RsDevice); LOGD("sz RsDevice %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(RsContext); LOGD("sz RsContext %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(RsError);  LOGD("sz RsError %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(ErrorHandlerFunc_t);  LOGD("sz ErrorHandlerFunc_t %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(MessageHandlerFunc_t); LOGD("sz MessageHandlerFunc_t %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(bool); LOGD("sz bool %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));offset += sizes[idx]; 
	idx++; sizes[idx] = sizeof(std::string); LOGD("sz string %d = %lu", sizes[idx], getElement(ptrRS+offset,sizes[idx]));   


    long ptrstr = getElement(ptrRS+offset,sizes[idx]);
    
    char * chars = (char*)ptrstr;
    
    LOGD("chars1 %c", chars[0]);
    LOGD("chars2 %c", chars[1]);
    LOGD("chars3 %c", chars[2]);
    LOGD("chars4 %c", chars[3]);
    LOGD("chars5 %c", chars[4]);
    LOGD("chars6 %c", chars[5]);

   offset += sizes[idx]; 
    
}

// Initialize RenderScript context
void initRS(const char* cacheDir) //, const int cacheDirStringLength)
{

    LOGD("Creating new RS context");
    mRS = new RS();

    LOGD("Setting RS error function");
    // Set function to handle errors, when thrown
    mRS->setErrorHandler(&rsErrorHandlerFunction);

    LOGD("Initializing new RS context with cache dir: %s", cacheDir);

	//checkMemory();

    mRS->init(cacheDir); //, RS_CONTEXT_TYPE_DEBUG);
}

// Debug function that copies allocation contents and print it
void debugAllocationSimpleCopy(char* tag, sp<Allocation> dAllocation)
{

    // Retrieve elements count
    const int xSize = dAllocation->getType()->getX();

    // Copies allocation contents to local array
    int localCopy[xSize];
    dAllocation->copy1DTo((void*)localCopy);

    char debugString[255];

    // Print array
    char* pos = debugString;
    for(int i = 0; i != xSize; i++) {
        if(i) {
            pos += sprintf(pos, ", ");
        }
        pos += sprintf(pos, "%d", localCopy[i]);
    }

    LOGD("%s: %s\n", tag, debugString);
}

void runNDKExample()
{

    LOGD("Running NDK example");

    const int inputElementsCount = 10;

    int inputArray[inputElementsCount];

    // Fills input data with some values
    for(int i = 0; i < inputElementsCount; i++) {
        inputArray[i] = i;
    }

    LOGD("Filled sample input data");

    // Instantiates an Allocation and copies in it
    sp<Allocation> inputAllocationSimple = Allocation::createSized(mRS, Element::I32(mRS), inputElementsCount);
    inputAllocationSimple->copy1DFrom((void*)inputArray);

    debugAllocationSimpleCopy("inputAllocationSimple", inputAllocationSimple);

    // Instantiates an Allocation that will have RS_ALLOCATION_USAGE_SHARED flag, passing directly
    // custom data
    sp<const Type> inputType = Type::create(mRS, Element::I32(mRS), inputElementsCount, 0, 0);
    sp<Allocation> inputAllocationPointer =
        Allocation::createTyped(mRS,
                                inputType,
                                RS_ALLOCATION_MIPMAP_NONE,
                                RS_ALLOCATION_USAGE_SCRIPT | RS_ALLOCATION_USAGE_SHARED,
                                (void*)inputArray);

    debugAllocationSimpleCopy("inputAllocationPointer", inputAllocationPointer);

    // Output allocation where to store results
    sp<Allocation> outputAllocation = Allocation::createSized(mRS, Element::I32(mRS), inputElementsCount);

    // Init custom script
    ScriptC_main* myScript = new ScriptC_main(mRS);

    // Execute kernel on simple allocation and debug it
    myScript->forEach_mulKernel(inputAllocationSimple, outputAllocation);
    mRS->finish();

    debugAllocationSimpleCopy("inputAllocationSimple -> outputAllocation", outputAllocation);

    // Execute kernel on pointer allocation and debug it
    myScript->forEach_mulKernel(inputAllocationSimple, outputAllocation);
    mRS->finish();

    debugAllocationSimpleCopy("inputAllocationPointer -> outputAllocation", outputAllocation);
}

extern "C" {

JNI_FUNCTION(void, initRenderScript, jstring cacheDirObj) //, jint cacheDirStringLength)
{
    const char* tmpCacheDir = env->GetStringUTFChars(cacheDirObj, NULL);
    
    char cacheDir[255];
    
    int cacheDirStringLength = strlen(tmpCacheDir);
    memcpy((void*) cacheDir, (void*)tmpCacheDir, cacheDirStringLength);
    env->ReleaseStringUTFChars(cacheDirObj, tmpCacheDir);
    
    cacheDir[cacheDirStringLength] = 0;

    // Initialize RS context
    initRS(cacheDir); //, cacheDirStringLength);
    //COFFEE_TRY_JNI(env, initRS(cacheDir)); //, cacheDirStringLength);
}

// Test function to invoke from Java
JNI_FUNCTION(void, ndkExample)
{
    runNDKExample();
}
}
