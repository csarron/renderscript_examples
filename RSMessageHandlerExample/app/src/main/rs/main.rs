// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.rsmessagehandlerexample)

const int MESSAGE_OK = 1;

void callMe(){

    // Creates simple struct
    struct {
        int x;
        int y;
        bool myBool;
        float f;
    } data;

    data.x = 5;
    data.y = 25;
    data.myBool = true;
    data.f = 1.3f;

    // Sends struct data to Java side
    rsSendToClientBlocking(MESSAGE_OK, (void*)&data, sizeof(data));

    data.x = 10;
    rsSendToClient(MESSAGE_OK, (void*)&data, sizeof(data));

}