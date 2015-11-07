class Cell {
  int id;
  PVector location;

  Cell(boolean save, int id) {
   this.id = id;
   location = f.cellLocation(id);
   location.mult(spacing);
   if(save) cells.add(this);
  }

  void showCluster(int clusterSize) {
    PVector[] c = f.clusterLocations(id, clusterSize);
    for(int i = 0; i < c.length; i++) {
      fill(0, 100, 100);
      rect(c[i].x*spacing, c[i].y*spacing, spacing, spacing);
    }
  }

  void display() {
   noFill();
   stroke(0, 0, 5);
   rect(location.x, location.y, spacing, spacing);
  }
}