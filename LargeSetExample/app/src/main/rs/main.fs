#pragma version(1)

#pragma rs java_package_name(net.hydex11.largesetexample)

// Fill allocations with random data
float __attribute__((kernel)) fillWithRandomData(){
    return rsRand(1.0f, 100000.0f);
}

// Computes operations on data
float __attribute__((kernel)) compute(float in){
    return log(in) * rsRand(1.0f, 100000.0f) + 1 + sin(in);
}