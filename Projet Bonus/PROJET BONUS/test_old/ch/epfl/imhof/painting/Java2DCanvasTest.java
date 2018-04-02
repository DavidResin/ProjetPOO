package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class Java2DCanvasTest {

    @Test
    public void builderBuildsCorrectly() {
        new Java2DCanvas(new Point(0, 0), new Point(1, 1), 3, 2, 4, Color.WHITE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void builderFailsOnIncorrectWidth() {
        new Java2DCanvas(new Point(0, 0), new Point(1, 1), 0, 2, 4, Color.WHITE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void builderFailsOnIncorrectHeight() {
        new Java2DCanvas(new Point(0, 0), new Point(1, 1), 3, 0, 4, Color.WHITE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void builderFailsOnIncorrectResolution() {
        new Java2DCanvas(new Point(0, 0), new Point(1, 1), 3, 2, 0, Color.WHITE);
    }
    
    @Test
    public void polyLineDrawerTest() throws IOException {
        PolyLine.Builder temp = new PolyLine.Builder();
        temp.addPoint(new Point(50, 50));
        temp.addPoint(new Point(150, 50));
        temp.addPoint(new Point(150, 150));
        temp.addPoint(new Point(100, 100));
        PolyLine line = temp.buildOpen();
        
        Java2DCanvas canvas = new Java2DCanvas(new Point(0, 0), new Point(200, 200), 200, 200, 72, Color.WHITE);
        canvas.drawPolyLine(line, new LineStyle(4, Color.RED, LineStyle.LineCap.ROUND, LineStyle.LineJoin.MITER, new float[]{4, 10, 1, 2}));
        ImageIO.write(canvas.image(), "png", new File("polyLineDrawerTest.png"));
    }
    
    @Test
    public void polygonDrawerTest() throws IOException {
        PolyLine.Builder temp = new PolyLine.Builder();
        temp.addPoint(new Point(50, 50));
        temp.addPoint(new Point(150, 50));
        temp.addPoint(new Point(150, 150));
        temp.addPoint(new Point(50, 150));
        ClosedPolyLine shell = temp.buildClosed();
        
        temp = new PolyLine.Builder();
        temp.addPoint(new Point(70, 70));
        temp.addPoint(new Point(130, 70));
        temp.addPoint(new Point(130, 130));
        ClosedPolyLine hole = temp.buildClosed();
        
        List<ClosedPolyLine> holes = new ArrayList<>();
        holes.add(hole);
        
        Polygon poly = new Polygon(shell, holes);
        
        Java2DCanvas canvas = new Java2DCanvas(new Point(0, 0), new Point(200, 200), 200, 200, 72, Color.WHITE);
        canvas.drawPolygon(poly, Color.RED);
        ImageIO.write(canvas.image(), "png", new File("polygonDrawerTest.png"));
    }
    
    @Test
    public void lausanneTest() throws IOException, SAXException {
        Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water");
        Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
        
        Painter lakesPainter = Painter.polygon(Color.BLUE).when(isLake);
        Painter buildingsPainter = Painter.polygon(Color.BLACK).when(isBuilding);
        
        Painter painter = buildingsPainter.above(lakesPainter);
        
        OSMToGeoTransformer transform = new OSMToGeoTransformer(new CH1903Projection());
        OSMMap temp = OSMMapReader.readOSMFile("data/maps/lausanne.osm.gz", true);
        Map map = transform.transform(temp);
        
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        
        Java2DCanvas canvas = new Java2DCanvas(bl, tr, 800, 530, 72, Color.WHITE);
        
        painter.drawMap(map, canvas);
        ImageIO.write(canvas.image(), "png", new File("loz.png"));
    }
}
