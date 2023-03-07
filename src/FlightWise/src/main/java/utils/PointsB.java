package utils;

import utils.dted.DtedDatabaseHandler;

import java.util.Optional;

public class PointsB {
  // Open DTed DataBase

  public static DtedDatabaseHandler openDtedDB(String path) {
    DtedDatabaseHandler dtedDB = new DtedDatabaseHandler();
    dtedDB.InitializeFromResources(path);

    return dtedDB;
  }




  public static double[] getHeight(DtedDatabaseHandler dbDTED, double longitude, double latitude){
    Optional<Integer> height = dbDTED.QueryLatLonElevation(longitude, latitude);
    double[] coordinates = { longitude, latitude, (double) height.get() };
    return coordinates;
  }

  
  public static double[][] getCoord(DtedDatabaseHandler dbDTED, double lonInitial, double latInitial, int row, int col, double lonStep, double latStep) {
    double[][] coordData = new double[row * col][3]; 
  
    int count = 0;
    double lon = lonInitial;
    double lat = latInitial;
  
    for (int i = 0; i < row; i++) { 
      for (int j = 0; j < col; j++) { 
        coordData[count] = getHeight(dbDTED, lon, lat);
        lon += lonStep;
        count++;
      }
      lat -= latStep; 
      lon = lonInitial;
    }
  
    return coordData;
  }

public static void main(String[] args) {
  DtedDatabaseHandler dbRio = openDtedDB("dted/rio");

  int rows = 5, cols = 4;
  double lonInitial = -43.4082;
  double latInitial = -22.1780;
  double lonStep = 0.0013;
  double latStep = 0.0011;

  double[][] coordData = getCoord(dbRio, lonInitial, latInitial, rows, cols, lonStep, latStep);

  for (int i = 0; i < coordData.length; i++) {
    System.out.println(i + ": (" + coordData[i][1] + ", " + coordData[i][0] + ", " + coordData[i][2] + ")");
  }
}



}