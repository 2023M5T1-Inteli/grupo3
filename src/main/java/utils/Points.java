package utils;

import utils.dted.DtedDatabaseHandler;

import java.util.Optional;

// This class is only used to test the reading of dt2 files
public class Points {

  // The purpose of this method is to open a DTED (Digital Terrain Elevation Data)
  // database located at the given path.
  public static DtedDatabaseHandler openDtedDB(String filePath) {
    // To do this, it creates a new instance of the "DtedDatabaseHandler" class and
    // initializes it.
    DtedDatabaseHandler dtedDB = new DtedDatabaseHandler();
    dtedDB.InitializeFromResources(filePath);

    return dtedDB;
  }

  // method that will receive the coordinates of the points and return the height
  // of the point
  public static double[] getHeight(DtedDatabaseHandler dbDTED, double longitude, double latitude) {
    Optional<Integer> height = dbDTED.QueryLatLonElevation(longitude, latitude);
    double[] coordinates;

    coordinates = new double[]{longitude, latitude, (double) height.get()};

    return coordinates;
  }

  // method that will receive the coordinate for receive the points of the areas around the points

  public static double[][] getCoord(DtedDatabaseHandler dbDTED, double lonInitial, double latInitial, double lonFinal,
                                    double latFinal, double lonStep, double latStep) {

    int row = (int) Math.ceil(Math.abs(latInitial - latFinal) / latStep);
    int col = (int) Math.ceil(Math.abs(lonInitial - lonFinal) / lonStep);

    double[][] coordData = new double[row][col * 3];

    int count = 0;
    double lat = latInitial;

//    double lonStep1 = 0.0014;
//    double latStep1 = 0.0011;

    for (int i = 0; i < row; i++) {
      double lon = lonInitial;

      double[] auxArray = new double[col * 3];

      for (int j = 0; j < col; j++) {
        double[] coordinates = getHeight(dbDTED, lon, lat);
        auxArray[j * 3] = coordinates[0];
        auxArray[j * 3 + 1] = coordinates[1];
        auxArray[j * 3 + 2] = coordinates[2];
        lon += lonStep;
      }

      System.arraycopy(auxArray, 0, coordData[count], 0, auxArray.length);
      count++;
      lat -= latStep;
    }

    return coordData;
}

  public double[][] Coordinates(String filePath, double lonInitial, double latInitial, double lonFinal, double latFinal, double lonStep1, double latStep1) {
    // Here we open the DTED database located at the path "dted/rio"
    DtedDatabaseHandler dbRio = openDtedDB(filePath);

    double[][] coordData = getCoord(dbRio, lonInitial, latInitial, lonFinal, latFinal, lonStep1, latStep1);

    return coordData;
  }



  public static void main(String[] args) {
    
    Points points = new Points();
    double[][] coordData = points.Coordinates("dted/rio", -43.1, -22.1, -43.0, -22.0, 0.0014, 0.0011);

    for (int i = 0; i < coordData.length; i++) {
      for (int j = 0; j < coordData[i].length; j++) {
        System.out.print( " "+ coordData[i][j]+ " ");
      }
      System.out.println();
    }
  }
}












