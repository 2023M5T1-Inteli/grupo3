package utils.popArray;

import java.util.Arrays;

public class PopulateArray {

    public PopulateArray() {

    }
    /**
     * This method populates a 2D double array by parsing a string representation of the array.
     *
     * @param arrayStr a String in the formart of an array of arrays as: "[[ a, b, c ], [d, e, f] ... ]"
     * @return a 2D array as:
     * [
     *  {a, b, c},
     *  {d, e, f} ...
     * ]
     * */
    public double[][] populateArray(String arrayStr) {
        String str = arrayStr.replaceAll("\\[\\[", "["); // remove double opening brackets
        str = str.replaceAll("\\]\\]", "]"); // remove double closing brackets
        str = str.substring(1, str.length() - 1);
        String[] strArr = str.split("\\], \\[");

        double[][] popArray = new double[strArr.length][];


        for (int i = 0; i < strArr.length; i++) {
            String[] subArr = strArr[i].split(", ");
            popArray[i] = new double[subArr.length];
            for (int j = 0; j < subArr.length; j++) {
                popArray[i][j] = Double.parseDouble(subArr[j]);
            }
        }



        return popArray;
    }
}
