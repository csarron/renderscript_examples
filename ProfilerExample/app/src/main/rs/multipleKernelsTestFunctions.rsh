// First kernel computes
static float calculateFirst(float element1, float element2){

    float atanCalculation = atan2(element1, element2);
    float deg = degrees(atanCalculation);

    return deg;
}

static float calculateSecond(float element1){

    float logCalculation = log(element1);
    float result = logCalculation * 1000 * sin(sinh(element1));

    return result;

}