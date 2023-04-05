package utils;

import org.json.JSONArray;
import utils.ExclusionStopPoints.PointAnalyzer;
import utils.dted.DtedDatabaseHandler;

import java.awt.geom.Point2D;
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

  public static double[][][] getCoord(DtedDatabaseHandler dbDTED, double lonInitial, double latInitial, double lonFinal,
                                    double latFinal, double lonStep, double latStep, JSONArray exclusionPoints) {


    /*
     * Variable "row" and "col" are used to define the number of rows and columns of
     * the matrix
     * The "Math.ceil" is used to round the number up
     * The "Math.abs" is used to get differences positive e negatives
     */
    int row = (int) Math.ceil(Math.abs(latInitial - latFinal) / latStep);
    int col = (int) Math.ceil(Math.abs(lonInitial - lonFinal) / lonStep);


    double[][][] coordData = new double[row][col][3];

    int count = 0;
    double lat = latInitial;

    double lonStep1 = 0.0014;
    double latStep1 = 0.0011;

    PointAnalyzer pointAnalyzer = new PointAnalyzer();

    for (int i = 0; i < row; i++) {
      double lon = lonInitial;
      for (int j = 0; j < col; j++) {
        // here we put the initial coordinates and the file of dted

        if (exclusionPoints.length() != 0) {
          for (int k = 0; k < exclusionPoints.length(); k++) {
            JSONArray excludePoint = exclusionPoints.getJSONArray(k);

            double excludeLong = excludePoint.getDouble(0);
            double excludeLat = excludePoint.getDouble(1);
            double radius = excludePoint.getDouble(2);

            boolean isInExclusionZone = pointAnalyzer.isExclusionPoint(new Point2D.Double(lon, lat), new Point2D.Double(excludeLong, excludeLat), radius);

            if (isInExclusionZone) {
              coordData[i][j] = null;
            } else {
              coordData[i][j] = getHeight(dbDTED, lon, lat);
            }
          }
        }

        // traverse the longitude (column) per row
        lon += lonStep;
        count++;
        
      }
      // later we traverse the latitude (line)
      lat -= latStep;

    }



    return coordData;
  }

  public double[][][] Coordinates(String filePath, double lonInitial, double latInitial, double lonFinal, double latFinal, double lonStep1, double latStep1, JSONArray exclusionPoints) {
    // Here we open the DTED database located at the path "dted/rio"


    DtedDatabaseHandler dbRio = openDtedDB(filePath);

    double[][][] coordData = getCoord(dbRio, lonInitial, latInitial, lonFinal, latFinal, lonStep1, latStep1, exclusionPoints);



    return coordData;
  }
}


