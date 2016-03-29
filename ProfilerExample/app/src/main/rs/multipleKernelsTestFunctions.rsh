// First kernel computes
static float calculateFirst(float element1, float element2, float element3){

    return atan2(element1, element2) * 100;
}

static float calculateSecond(float element1){

    return log(element1) * 1000;

}