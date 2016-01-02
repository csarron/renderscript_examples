#pragma version(1)

#pragma rs java_package_name(net.hydex11.profilerexample)

int __attribute__((kernel)) root1(int in, int x) {

return (in*2) % 123456;

}

int __attribute__((kernel)) root2(int in, int x) {

return in + (int)(cos((float)in)*10.0f);

}


int __attribute__((kernel)) root3(int in, int x) {

return in + (int)((float)(tan((float)in))*10.0f);

}