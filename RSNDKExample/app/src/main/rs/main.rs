#pragma version(1)
#pragma rs_fp_relaxed

#pragma rs java_package_name(net.hydex11.rsndkexample)

void testKernel(const int *v_in, int *v_out){
    
    *v_out = *v_in*10;
    
}