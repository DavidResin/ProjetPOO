package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineStyleTest {

    private static final double DELTA = 0.000001;
    
    @Test
    public void lineStyleBuildersAreCorrect() {
        new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.MITER, new float[0]);
        new LineStyle(1, Color.WHITE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void lineStyleBuilderFailsOnNegativeThickness() {
        new LineStyle(-1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.MITER, new float[0]);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void lineStyleBuilderFailsOnNegativeDashingPattern() {
        new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.MITER, new float[]{-1});
    }
    
    @Test
    public void colorGetterIsCorrect() {
        assertEquals(Color.WHITE, new LineStyle(1, Color.WHITE).color());
    }
    
    @Test
    public void thicknessGetterIsCorrect() {
        assertEquals(1, new LineStyle(1, Color.WHITE).thickness(), DELTA);
    }
    
    @Test
    public void dashingPatternGetterIsCorrect() {
        float[] dP = {1, 3, 2};
        LineStyle test = new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.BEVEL, dP);
        for(int i = 0; i < test.dashingPattern().length; ++i) {
            assertEquals(dP[i], test.dashingPattern()[i], DELTA);
        }
    }
    
    @Test
    public void lineCapGetterIsCorrect() {
        LineStyle test = new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.BEVEL, new float[0]);
        assertEquals(LineStyle.LineCap.BUTT, test.lineCap());
    }
    
    @Test
    public void lineJoinGetterIsCorrect() {
        LineStyle test = new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.BEVEL, new float[0]);
        assertEquals(LineStyle.LineJoin.BEVEL, test.lineJoin());
    }
    
    @Test
    public void colorChangerIsCorrect() {
        assertEquals(Color.BLACK, new LineStyle(1, Color.WHITE).withColor(Color.BLACK).color());
    }
    
    @Test
    public void thicknessChangerIsCorrect() {
        assertEquals(2, new LineStyle(1, Color.WHITE).withThickness(2).thickness(), DELTA);
    }
    
    @Test
    public void dashingChangerGetterIsCorrect() {
        float[] dP = new float[0];
        float[] dP2 = {1, 3, 2};
        LineStyle test = new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.BEVEL, dP).withDashingPattern(dP2);
        for(int i = 0; i < test.dashingPattern().length; ++i) {
            assertEquals(dP2[i], test.dashingPattern()[i], DELTA);
        }
    }
    
    @Test
    public void lineCapChangerIsCorrect() {
        LineStyle test = new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.MITER, new float[0]);
        assertEquals(LineStyle.LineCap.ROUND, test.withLineCap(LineStyle.LineCap.ROUND).lineCap());
    }
    
    @Test
    public void lineJoinChangerIsCorrect() {
        LineStyle test = new LineStyle(1, Color.WHITE, LineStyle.LineCap.BUTT, LineStyle.LineJoin.BEVEL, new float[0]);
        assertEquals(LineStyle.LineJoin.MITER, test.withLineJoin(LineStyle.LineJoin.MITER).lineJoin());
    }
}
