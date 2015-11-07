/*
  DESCRIPTION:
  formLerp works just like lerp(), except that it takes a parameter to map the outcome.
  Response can be linear, just like lerp(), but also an ease-in-out, sinus, square and many others.
  
  FUNCTION:
  formlerp(float start, float stop, float amount (normalized), String method, [optional] float strength);
  
  METHODS:
  "Default", "power-of", "ease-in-out", "sinus", "half-sinus", "square", "circular"
*/

import Form.*;
Form f;

String[] lerpTypes = { "Default", "power-of", "ease-in-out", "sinus", "half-sinus", "square", "circular" };
int type = 0;

void settings() {
  size(360, 360, P2D);
  pixelDensity(displayDensity());
}

void setup() {
  Form f = new Form(this);
  colorMode(HSB, 360, 100, 100, 100);
  strokeWeight(2);
  fill(#000000);
}

void draw() {
  background(0, 0, 98);
  
  stroke(#0082F5);
  for(int i = 0; i < width; i++) point(i, f.formLerp(0, width, (float)i/width, lerpTypes[type]));
  
  stroke(#C21A01);
  for(int i = 0; i < width; i++) point(i, f.formLerp(0, width, (float)i/width, lerpTypes[type], 10));
  
  text(lerpTypes[type], 10, height - 10);
  
  if(frameCount % 120 == 0) type = (type+1) % 7;
}

void mousePressed() {
  noLoop();
}

void mouseReleased() {
  loop();
}