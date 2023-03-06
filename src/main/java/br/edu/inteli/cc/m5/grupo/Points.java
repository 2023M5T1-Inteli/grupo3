package br.edu.inteli.cc.m5.grupo;

import java.util.Optional;

import br.edu.inteli.cc.m5.dted.DtedDatabaseHandler;

public class Points {
  // Open DTed DataBase

  // The purpose of this method is to open a DTED (Digital Terrain Elevation Data) database located at the given path.
  public static DtedDatabaseHandler openDtedDB(String path) {
  // To do this, it creates a new instance of the "DtedDatabaseHandler" class and initializes it.
    DtedDatabaseHandler dtedDB = new DtedDatabaseHandler();
    dtedDB.InitializeFromResources(path);

    return dtedDB;
  }

  // method that will receive the coordinates of the points and return the height of the point
  public static double[] getHeight(DtedDatabaseHandler dbDTED, double longitude, double latitude){
    Optional<Integer> height = dbDTED.QueryLatLonElevation(longitude, latitude);
    double[] coordinates = { longitude, latitude, (double) height.get() };
    return coordinates;
  }

  // method that will receive the coordinate for receive the points of the areas around the point
  public static double[][] getCoord(DtedDatabaseHandler dbDTED, double lonInitial, double latInitial, int row, int col, double lonStep, double latStep) {
    double[][] coordData = new double[row * col][3]; 
  
    int count = 0;
    double lon = lonInitial;
    double lat = latInitial;
  
    for (int i = 0; i < row; i++) { 
      for (int j = 0; j < col; j++) { 
        // here we put the initial coordinates and the file of dted
        coordData[count] = getHeight(dbDTED, lon, lat);
        // traverse the longitude (column) per row 
        lon += lonStep;
        count++;
      }
      //later we traverse the latitude (line)
      lat -= latStep;
      lon = lonInitial;
    }
  
    return coordData;
  }

public static void main(String[] args) {
  // Here we open the DTED database located at the path "dted/rio"
  DtedDatabaseHandler dbRio = openDtedDB("dted/rio");

  // Here we define the initial coordinates, the number of rows and columns, and the step between the points
  int rows = 5, cols = 4;
  double lonInitial = -43.4082;
  double latInitial = -22.1780;
  double lonStep = 0.0013;
  double latStep = 0.0011;

  double[][] coordData = getCoord(dbRio, lonInitial, latInitial, rows, cols, lonStep, latStep);

  for (int i = 0; i < rows * cols; i++) {
    for (int j = 0; j < 3; j++) {
      System.out.println(coordData[i][j]);
    }
  }
}



}