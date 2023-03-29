package utils;

import utils.dted.DtedDatabaseHandler;

import java.util.Optional;

// This class is only used to test the reading of dt2 files
public class PointsB {

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

    coordinates = new double[] { longitude, latitude, (double) height.get() };

    return coordinates;
  }

  // method that will receive the coordinate for receive the points of the areas
  // around the points

  public static double[][] getCoord(DtedDatabaseHandler dbDTED, double lonInitial, double latInitial, double lonFinal,
      double latFinal, double lonStep, double latStep) {

    /*
     * Variable "row" and "col" are used to define the number of rows and columns of
     * the matrix
     * The "Math.ceil" is used to round the number up
     * The "Math.abs" is used to get differences positive e negatives
     */
    int row = (int) Math.ceil(Math.abs(latInitial - latFinal) / latStep);
    int col = (int) Math.ceil(Math.abs(lonInitial - lonFinal) / lonStep);

    double[][] coordData = new double[row * col][3];

    int count = 0;
    double lat = latInitial;

    for (int i = 0; i < row; i++) {
      double lon = lonInitial;
      for (int j = 0; j < col; j++) {
        // here we put the initial coordinates and the file of dted
        coordData[count] = getHeight(dbDTED, lon, lat);
        // traverse the longitude (column) per row
        lon += lonStep;
        count++;
      }
      // later we traverse the latitude (line)
      lat -= latStep;
    }

    return coordData;
  }

  public static void main(String[] args) {
    // filePath is the file path of the DTED database.
    String filePath = "dted/rio";
    DtedDatabaseHandler dbRio = openDtedDB(filePath);

    /*
     * Example for values in saopaulo filePath
     */
    // double lonInitial = -46.4081;
    // double latInitial = -23.5770;
    // double lonFinal = -46.4171;
    // double latFinal = -23.1862;

    /*
     * Example for values in rio filePath
     */
    double lonInitial = -43.3282;
    double latInitial = -22.18;
    double lonFinal = -43.2976;
    double latFinal = -22.13;

    double lonStep = 0.0014; // LonStep choosen about the value of 120m in Rio de Janeiro.
    double latStep = 0.0011;

    double[][] coordData = getCoord(dbRio, lonInitial, latInitial, lonFinal, latFinal, lonStep, latStep);
    ;

    /*
     * Transform the error "NullPointerException" in a message for user
     */

    // try {
    // coordData = getCoord(dbRio, lonInitial, latInitial, lonFinal, latFinal,
    // lonStep, latStep);
    // } catch (NullPointerException e) {
    // System.err.println("Coordenada fora do mapa");
    // return;
    // }

    // Print the coordinates
    for (int i = 0; i < coordData.length; i++) {
      for (int j = 0; j < 3; j++) {
        // [[lon, lat, height], [lon, lat, height], ...
        System.out.println(coordData[i][j]);
      }
    }
  }

}