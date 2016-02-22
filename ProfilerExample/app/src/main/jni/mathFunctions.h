float piTest(int piIterations) {
   float i;    // Number of iterations and control variable
  float s = 1;   //Signal for the next operation
  float pi = 3;

  for(i = 2; i <= piIterations*2; i += 2){
    pi = pi + s * (4 / (i * (i + 1) * (i + 2)));
    s = -s;
  }

  return pi;

}