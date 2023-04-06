package utils;

import utils.ExclusionStopPoints.PointAnalyzer;
import utils.dted.DtedDatabaseHandler;

import java.util.Optional;

// This class is only used to test the reading of dt2 files
public class Points {

    /**
     * Opens a DTED (Digital Terrain Elevation Data) database located at the given path.
     *
     * @param filePath The path of the DTED database.
     * @return The opened DtedDatabaseHandler instance.
     */
    public static DtedDatabaseHandler openDtedDB(String filePath) {
        DtedDatabaseHandler dtedDB = new DtedDatabaseHandler();
        dtedDB.InitializeFromResources(filePath);
        return dtedDB;
    }

    /**
     * Returns the height of the point given by longitude and latitude from a DTED database.
     *
     * @param dbDTED    The DTED database.
     * @param longitude The longitude of the point.
     * @param latitude  The latitude of the point.
     * @return An array containing the longitude, latitude, and height of the point.
     */
    public static double[] getHeight(DtedDatabaseHandler dbDTED, double longitude, double latitude) {
        Optional<Integer> height = dbDTED.QueryLatLonElevation(longitude, latitude);
        double[] coordinates;
        coordinates = new double[]{longitude, latitude, (double) height.get()};
        return coordinates;
    }


    public static double[][][] listAllBounds(String filePath) {
        DtedDatabaseHandler dbDted = openDtedDB(filePath);
        double[][][] dbDTEDBounds = dbDted.listAllBounds();
        return dbDTEDBounds;
    }


    // method that will receive the coordinate for receive the points of the areas around the points

    /**
     * Returns a 3-dimensional array of coordinate data for a DTED database.
     *
     * @param dbDTED  The DTED database.
     * @param lonStep The step size for longitude.
     * @param latStep The step size for latitude.
     * @return A 3-dimensional array of coordinate data.
     */
    public static double[][][] getCoord(DtedDatabaseHandler dbDTED, double lonStep, double latStep) {


        /*
         * Variable "row" and "col" are used to define the number of rows and columns of
         * the matrix
         * The "Math.ceil" is used to round the number up
         * The "Math.abs" is used to get differences positive e negatives
         */

        double[][] mapBounds = dbDTED.mapMinMaxLatLon();

        double latInitial = mapBounds[0][1] - latStep;
        double lonInitial = mapBounds[1][0] + lonStep;
        double latFinal = mapBounds[0][0] + latStep;
        double lonFinal = mapBounds[1][1] - lonStep;

        int row = (int) Math.ceil(Math.abs(latInitial - latFinal) / latStep);
        int col = (int) Math.ceil(Math.abs(lonInitial - lonFinal) / lonStep);


        double[][][] coordData = new double[row][col][3];

        double lat = latInitial;

        PointAnalyzer pointAnalyzer = new PointAnalyzer();

        for (int i = 0; i < row; i++) {
            double lon = lonInitial;
            for (int j = 0; j < col; j++) {
                // here we put the initial coordinates and the file of dted
                coordData[i][j] = getHeight(dbDTED, lon, lat);
                // traverse the longitude (column) per row
                lon += lonStep;
            }
            // later we traverse the latitude (line)
            lat -= latStep;
        }


        return coordData;
    }

    /**
     * Returns a 3-dimensional array of coordinate data for a DTED database at a given path.
     *
     * @param filePath The path of the DTED database.
     * @param lonStep The step size for longitude.
     * @param latStep The step size for latitude.
     * @return A 3-dimensional array of coordinate data.
     */
    public double[][][] Coordinates(String filePath, double lonStep, double latStep) {
        // Here we open the DTED database located at the path "dted/rio"
        DtedDatabaseHandler dbDted = openDtedDB(filePath);
        double[][][] coordData = getCoord(dbDted, lonStep, latStep);
        return coordData;
    }

    public double[][] mapBounds(String filePath) {
        // Here we open the DTED database located at the path "dted/rio"
        DtedDatabaseHandler dbDted = openDtedDB(filePath);
        double[][] mapBounds = dbDted.mapMinMaxLatLon();
        return mapBounds;
    }
}


