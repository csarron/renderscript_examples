#pragma version(1)
#pragma rs_fp_relaxed

#pragma rs java_package_name(net.hydex11.rsndkexample)

// Custom structures, like following one, cannot be declared, because following error
// appears: slangAssert failed at frameworks/compile/slang/slang_rs_export_type.cpp:1594
// '!"RSExportType::ExportClassRecord not implemented"'
/*
typedef struct Particle {

    int x, y, vx, vy, ax, ay;

} Particle_t;

Particle_t * myParticle;
*/

void testKernel(const int *v_in, int *v_out){
    
    *v_out = *v_in*10;
    
}