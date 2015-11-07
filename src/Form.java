package Form;
import processing.core.*;

public class Form implements PConstants {
  PApplet parent;
  private PVector grid;
  private int cells;

  public Form(PApplet parent) {
    this.parent = parent;
  }
  public Form(PApplet parent, PVector grid) {
    this.parent = parent;
    this.grid = new PVector((int)Math.floor(grid.x), (int)Math.floor(grid.y));
    cells = (int)Math.floor(grid.x) * (int)Math.floor(grid.y);
  }

  // (re)set grid
  public void setGrid(PVector grid) {
    this.grid = new PVector((int)Math.floor(grid.x), (int)Math.floor(grid.y));
    cells = (int)Math.floor(grid.x) * (int)Math.floor(grid.y);
  }

  // function to retrieve the grid
  public PVector grid() {
    return grid;
  }
  // function to retrieve the total ammount of cells in a grid
  public int cells() {
    return cells;
  }

  // get X Y position of cell with a certain ID
  public PVector cellLocation(int id) {
    if(id >= 0 && id < cells) {
      return new PVector(id % grid.x, (float)Math.floor(id / grid.x));
    } else {
      return null;
    }
  }

  private int xS, xE, yS, yE;
  private void clusterLocationInit(int id, int clusterSize) {
    // get the Y and X start and end position for the patch-cluster, min and max to prevent clipping
    xS = (int)Math.max(Math.floor(cellLocation(id).x) - Math.floor(clusterSize / 2.0), 0);
    xE = (int)Math.min(Math.floor(cellLocation(id).x) + Math.ceil(clusterSize / 2.0), grid.x);
    yS = (int)Math.max(Math.floor(cellLocation(id).y) - Math.floor(clusterSize / 2.0), 0);
    yE = (int)Math.min(Math.floor(cellLocation(id).y) + Math.ceil(clusterSize / 2.0), grid.y);
  }

  // get a cluster of cell id's
  public int[] clusterIds(int id, int clusterSize) {
    if(id >= 0 && id < cells) {
      // we want a cluster with a centerpoint
      if(clusterSize % 2.0 == 0) clusterSize++;

      clusterLocationInit(id, clusterSize);

      int[] cellIds = new int[(xE - xS) * (yE - yS)];

      for(int y = yS; y < yE; y++) {
        for(int x = xS; x < xE; x++) {
          int arrayPos = (y - yS) * (xE - xS) + (x - xS);
          int cell = (int)(y*grid.x + x);
          cellIds[arrayPos] = cell;
        }
      }
      return cellIds;
    } else {
      return null;
    }
  }

  // get a cluster of cells locations
  public PVector[] clusterLocations(int id, int clusterSize) {
    if(id >= 0 && id < cells) {
      // we want a cluster with a centerpoint
      if(clusterSize % 2.0 == 0) clusterSize++;

      clusterLocationInit(id, clusterSize);

      PVector[] cellLocations = new PVector[(xE - xS) * (yE - yS)];

      for(int y = yS; y < yE; y++) {
        for(int x = xS; x < xE; x++) {
          int arrayPos = (y - yS) * (xE - xS) + (x - xS);
          int cell = (int)(y*grid.x + x);
          cellLocations[arrayPos] = cellLocation(cell);
        }
      }
      return cellLocations;
    } else {
      return null;
    }
  }

  // Formlerp
  public static float formLerp(float start, float stop, double val, String type) {
    switch(type) {
      default: // normal lerp
        break;
      case "power-of": // power-of response
        val = Math.pow(val, 2);
        break;
      case "ease-in-out": // ease-in-out response
        // val = (1.0 - (Math.cos(val*PI) + 1) / 2.0);
        val = Math.pow(val, 2.0) / (Math.pow(val, 2.0) + Math.pow((1.0-val), 2.0));
        break;
      case "sinus": // full sinus
        val = 1.0 - (Math.cos(val*TWO_PI) + 1) / 2.0;
        break;
      case "half-sinus": // half sinus
        val = 1.0 - (Math.cos(val*PI) + 1) / 2.0;
        break;
      case "square": // square sinus, low sampling
        double valBuffer = 1.0;
        for(int i = 0; i < 3; i++) {
          double sampling = (i * 2.0 + 1.0);
          valBuffer += (4.0 / (sampling * PI)) * Math.sin((sampling * val) * TWO_PI);
        }
        val = valBuffer / 2.0;
        break;
      case "circular": // circular response
        val = 1.0 - Math.sqrt(1.0-Math.pow(val, 2));
        break;
    }
    return (stop - start) * (float)val;
  }

  public static float formLerp(float start, float stop, double val, String type, double degree) {
    switch(type) {
      default:
        break;
      case "power-of": // power-of response
        val = Math.pow(val, degree);
        break;
      case "ease-in-out": // ease-in-out response
        val = Math.pow(val, degree) / (Math.pow(val, degree) + Math.pow((1.0 - val), degree));
        break;
      case "sinus": // power-sinus
        val = 1.0 - Math.pow((Math.cos(val * TWO_PI) + 1.0) / 2.0, degree);
        break;
      case "half-sinus": // half-power-sinus
        val = 1.0 - Math.pow((Math.cos(val * PI) + 1.0) / 2.0, degree);
        break;
      case "square": // square wave with sampling defined
        double valBuffer = 1.0;
        for(int i=0; i<degree; i++) {
          double sampling = (i * 2.0 + 1.0);
          valBuffer += (4 / (sampling * PI)) * Math.sin((sampling * val) * TWO_PI);
        }
        val = valBuffer / 2.0;
        break;
      case "circular": // circular response
        val = 1.0 - Math.sqrt(1.0 - Math.pow(val, degree));
        break;
    }
    return (stop - start) * (float)val;
  }
}
