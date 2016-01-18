// Needed directive for RS to work
#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(net.hydex11.surfacerenderexample)

rs_allocation renderAllocation;
rs_allocation particlesAllocation;

int renderAllocationWidth;
int renderAllocationHeight;

const float gy = 9.81f; // Constant value for vertical acceleration in pixels/second^2
// Angle is 0 on x-axis, ccw. Default is towards bottom, 270 deg.
float accY = 0;
float accX = 0;

const static uchar4 black = {0,0,0,255}; // Base pixel, used to clean the screen

// Creates a custom particle structure
typedef struct Particle {

    uchar4 color; // Color will be a random one

    float x; // Current particle position in screen
    float y;

    float vx, vy; // Speed in pixels/second

    int lastUpdateTime; // Last time the particle was checked by the code

    bool isValid; // If particle is not valid, it gets reinitialized

} Particle_t;

// Clears the screen and makes everything black
uchar4 __attribute__((kernel)) cleanRenderAllocation(uint32_t x, uint32_t y){
    return black;
}

// Marks all particles as not valid, so that all of them have to be reinitialized
Particle_t __attribute__((kernel)) initParticles(Particle_t in, uint32_t x) {
    in.isValid = false;
    return in;
}

// Random number function, faster than rsRand
// http://stackoverflow.com/questions/28115905/rsrand-significantly-slows-down-renderscript
uint32_t r0 = 0x6635e5ce, r1 = 0x13bf026f, r2 = 0x43225b59, r3 = 0x3b0314d0;
static float getRandomNumber(){
uint32_t t = r0 ^ (r0 << 11);
  r0 = r1; r1 = r2; r2 = r3;
  r3 = r3 ^ (r3 >> 19) ^ t ^ (t >> 8);
  return (float) r3 / 0xffffffff;
}

// Kernel function that updates particles and draws their positions
Particle_t __attribute__((kernel)) drawParticles(Particle_t in, uint32_t x) {

    // If particle is new, so not valid, creates it at random height and enters screen from left
    if(in.isValid == false)
    {
        in.x=0;
        in.y=getRandomNumber()*(float)renderAllocationHeight;

        // Calculates a random launch angle for the particle (horizontal axis),
        // min angle is -15, so pointing a bit downwards, max is 60, upwards
        float angle = radians(getRandomNumber()*75.0f-15.0f);

        // Calculate random launch speed, min 5, max 60
        float launchSpeed = getRandomNumber()*55.0f+5.0f;

        // Sets current horizontal speed at random value
        in.vx = launchSpeed * cos(angle);
        in.vy = -launchSpeed * sin(angle); // Negative, as vertical speed axis points downwards

        in.isValid = true;

        // Saves current time in milliseconds, so that on next call the other values can be calculated accordingly
        in.lastUpdateTime = rsUptimeMillis();

        // Calculates random particle color
        in.color.r = round(getRandomNumber())*255;
        in.color.g = round(getRandomNumber())*255;
        in.color.b = round(getRandomNumber())*255;
        in.color.a = 255;

    }
    else
    {
        int currentTime = rsUptimeMillis();

        // For sure this value can be cast to float (32bits) as, to cause an overflow, we'd have to run this app
        // for about 23 days.
        float elapsedTime = (float)(currentTime-in.lastUpdateTime)/1000.0f;

        // Updates current particle position based on speed
        in.x += in.vx * elapsedTime;
        in.y += in.vy * elapsedTime;

        // Updates vertical speed accordingly to acceleration
        // Y acceleration is positive towards bottom of screen
        in.vy += 2*accY*elapsedTime;
        in.vx += 2*accX*elapsedTime;

        in.lastUpdateTime = currentTime;
    }

    // Checks if x and y of particle are outside rendering bounds. If so, marks particle as not good
    if(in.x < 0 || in.y < 0 || in.x > renderAllocationWidth - 1 || in.y > renderAllocationHeight -1)
    {
        in.isValid = false;
    }
    else
    {
        // Draws particle
        rsSetElementAt_uchar4(renderAllocation, in.color, in.x, in.y);
    }

    return in;
}