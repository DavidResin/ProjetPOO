package ch.epfl.imhof;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3Test {

    private static final double DELTA = 0.000001;
    
    @Test
    public void builderIsCorrect() {
        new Vector3(1, 2, 3);
        new Vector3(-1, -2, -3);
        new Vector3(0.5, 1.5, 2.5);
        new Vector3(0, 0, 0);
    }
    
    @Test
    public void gettersAreCorrect() {
        Vector3 temp = new Vector3(1, 2, 3);
        assertEquals(1, temp.x(), DELTA);
        assertEquals(2, temp.y(), DELTA);
        assertEquals(3, temp.z(), DELTA);
    }
    
    @Test
    public void normComputationIsCorrect() {
        Vector3 temp = new Vector3(3, -4, 12);
        assertEquals(13, temp.norm(), DELTA);
    }
    
    @Test
    public void normalizationIsCorrect() {
        Vector3 temp = new Vector3(3, -4, 12);
        Vector3 result = temp.normalized();
        assertEquals(3 / 13d, result.x(), DELTA);
        assertEquals(-4 / 13d, result.y(), DELTA);
        assertEquals(12 / 13d, result.z(), DELTA);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void normalizationFailsOnZeroVector() {
        Vector3 temp = new Vector3(0, 0, 0);
        temp.normalized();
    }
    
    @Test
    public void scalarProductComputationIsCorrect() {
        Vector3 temp1 = new Vector3(1, 3, 0);
        Vector3 temp2 = new Vector3(4, 2, 2);
        double result = temp1.scalarProduct(temp2);
        assertEquals(10, result, DELTA);
    }
}
