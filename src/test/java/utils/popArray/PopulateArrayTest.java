package utils.popArray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PopulateArrayTest {

    @Test
    public void testPopulateArray() {
        PopulateArray popArray = new PopulateArray();
        String arrayStr = "[[1.0, 2.0, 3.0], [4.0, 5.0, 6.0], [7.0, 8.0, 9.0]]";
        double[][] expectedArray = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[][] actualArray = popArray.populateArray(arrayStr);
        assertArrayEquals(expectedArray, actualArray);
    }
}