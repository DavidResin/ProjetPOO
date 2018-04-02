package ch.epfl.imhof.painting;

import static org.junit.Assert.assertEquals;
import static ch.epfl.imhof.painting.Color.*;
import org.junit.Test;

public class ColorTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void grayBuilderIsCorrect() {
        gray(0);
        gray(0.5f);
        gray(1);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void grayBuilderFailsOnInvalidValue() {
        gray(2);
    }
    
    @Test
    public void rgbBuilderIsCorrect() {
        rgb(0, 0.5f, 1);
        rgb(0.5f, 1, 0);
        rgb(1, 0, 0.5f);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void rgbBuilderfailsOnInvalidValue() {
        rgb(0, 0.5f, 2);
    }
    
    @Test
    public void rgbByteBuilderIsCorrect() {
        Color test = rgb(3364096);
        assertEquals(0.2f, test.red(), DELTA);
        assertEquals(1/3f, test.green(), DELTA);
        assertEquals(0, test.blue(), DELTA);
    }
    
    @Test
    public void redGetterIsCorrect() {
        assertEquals(1, RED.red(), DELTA);
    }
    
    @Test
    public void greenGetterIsCorrect() {
        assertEquals(1, GREEN.green(), DELTA);
    }
    
    @Test
    public void blueGetterIsCorrect() {
        assertEquals(1, BLUE.blue(), DELTA);
    }
    
    @Test
    public void colorMultiplierIsCorrect() {
        Color test1 = rgb(0.5f, 1, 0);
        Color test2 = rgb(0.25f, 0.5f, 1);
        Color test3 = test1.multiply(test2);
        assertEquals(0.125f, test3.red(), DELTA);
        assertEquals(0.5f, test3.green(), DELTA);
        assertEquals(0, test3.blue(), DELTA);
    }
    
    @Test
    public void colorConverterIsCorrect() {
        java.awt.Color test = rgb(0, 0.25f, 0.75f).convert();
        assertEquals(0, test.getRed(), DELTA);
        assertEquals(64, test.getGreen(), DELTA);
        assertEquals(191, test.getBlue(), DELTA);
    }
}