/*
  DESCRIPTION:
  Easier way to create and interact with a grid.
  When starting the library, you can give an optional grid parameter to set a grid.
  
  FUNCTIONS:
  setGrid(PVector grid) to set a grid
  grid() returns an int with the dimensions of the grid
  cells() retruns an int with the total amount of cells in the grid
  cellLocation(int id) returns a PVector for the location of the id in the grid
  clusterIds(int id, int size) returns an int array with id's for cells within the size of the cluster. id is always in center
  clusterLocations(int id, int size) Same as clusterIds, but returns a PVector array with locations instead of id's
*/

import Form.*;
Form f;

ArrayList<Cell> cells = new ArrayList<Cell>();

PVector grid = new PVector(100, 75);
float spacing = 5;

int clusterId;
int clusterSize = 5;

void settings() {
  size(int(grid.x*spacing), int(grid.y*spacing), P2D);
  pixelDensity(displayDensity());
}

void setup() {
  f = new Form(this, grid);
  colorMode(HSB, 360, 100, 100, 100);
  
  for (int i = 0; i < f.cells(); i++) new Cell(true, i);
  
  clusterId = (int)random(f.cells() - 1);
}

void draw() {
  background(0, 0, 98);
  
  for (Cell c : cells) {
    c.display();
    if(c.id == clusterId) c.showCluster(clusterSize);
  }
  
  if(frameCount % 60 == 0) {
    clusterId = (int)random(f.cells() - 1);
    clusterSize = (int)random(15);
  }
}