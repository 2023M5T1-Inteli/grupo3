package models.vertex;

import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateVertexTest {

    /*
    * "addEdge" is a void method, so it's hard to determine if it is performing as expected.
    * To verify this, we can initialize two "CoordinateVertex" objects and compare the size of the
    * ArrayList before and after calling "addEdge".
    *
    * By using this approach, we can test both the "addEdge" and "getEdge" methods at the same time.
    * */
    CoordinateVertex currentVertexToTest = new CoordinateVertex((new Point2D.Double(30.4, 12.3)), 1000);
    @Test
    void addEdge() {
        CoordinateVertex targetVertexToTest = new CoordinateVertex((new Point2D.Double(50.3, 20.3)), 500);
        assertEquals(0, currentVertexToTest.getEdges().size());
        currentVertexToTest.addEdge(targetVertexToTest, 500);
        assertEquals(1, currentVertexToTest.getEdges().size());
    }

    /*
    * Asserting the "vertexIndex" property was changed after calling "setIndex" method.
    *
    * By using this approach, we can test both the "setIndex" and "getIndex" methods at the same time.
    * */
    @Test
    void setIndex() {
        currentVertexToTest.setIndex(10);
        assertEquals(10, currentVertexToTest.getIndex());
    }

    /*
    * To verify this method, we will do an assertion on the X and Y values.
    *
    * Using the "currentVertexToTest," the expected values for X and Y are 30.4 and 12.3, respectively.
    * */
    @Test
    void getPosition() {
        assertEquals(30.4, currentVertexToTest.getPosition().getX());
        assertEquals(12.3, currentVertexToTest.getPosition().getY());
    }

}