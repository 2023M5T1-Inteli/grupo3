package utils;

import utils.dted.DtedDatabaseHandler;

import java.util.Optional;

public class PointsB {
  // Open DTed DataBase

  public static DtedDatabaseHandler openDtedDB(String path) {

    // Create a new DtedDatabaseHandler object
    DtedDatabaseHandler dtedDB = new DtedDatabaseHandler();

    // Initialize the object from resources located at the given path
    dtedDB.InitializeFromResources(path);

    return dtedDB;
  }

  // Get the height data for a given longitude and latitude using the given DtedDatabaseHandler object
  public static double[] getHeight(DtedDatabaseHandler dbDTED, double longitude, double latitude){

    // Query the database for the elevation at the given coordinates
    Optional<Integer> height = dbDTED.QueryLatLonElevation(longitude, latitude);]

    // Create an array with the longitude, latitude, and height data and return it
    double[] coordinates = { longitude, latitude, (double) height.get() };
    return coordinates;
  }

  // Generate a 2D array of coordinate data using the given DtedDatabaseHandler object
  public static double[][] getCoord(DtedDatabaseHandler dbDTED, double lonInitial, double latInitial, int row, int col, double lonStep, double latStep) {
    
    // Create a 2D array with dimensions based on the number of rows and columns specified
    double[][] coordData = new double[row * col][3]; 
  
    int count = 0;
    double lon = lonInitial;
    double lat = latInitial;
  
    // Loop over the specified number of rows and columns, generating coordinate data and storing it in the coordData array
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

  // Open a DTED database handler for Rio
  DtedDatabaseHandler dbRio = openDtedDB("dted/rio");

  // Define the number of rows and columns to retrieve, as well as the initial longitude and latitude values and the step size for both
  int rows = 5, cols = 4;
  double lonInitial = -43.4082;
  double latInitial = -22.1780;
  double lonStep = 0.0013;
  double latStep = 0.0011;

  // Retrieve coordinate data using the DTED database handler, with the parameters defined above
  double[][] coordData = getCoord(dbRio, lonInitial, latInitial, rows, cols, lonStep, latStep);

  // Print out the retrieved coordinate data
  for (int i = 0; i < coordData.length; i++) {
    System.out.println(i + ": (" + coordData[i][1] + ", " + coordData[i][0] + ", " + coordData[i][2] + ")");
  }
}



}