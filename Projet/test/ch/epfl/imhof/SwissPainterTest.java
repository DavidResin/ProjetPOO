package ch.epfl.imhof;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.Color;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.projection.CH1903Projection;

public class SwissPainterTest {

    @Test
    public void lausannePrint() throws IOException, SAXException {
        OSMToGeoTransformer transform = new OSMToGeoTransformer(new CH1903Projection());
        OSMMap temp = OSMMapReader.readOSMFile("data/maps/lausanne.osm.gz", true);
        Map map = transform.transform(temp);
        
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        
        Java2DCanvas canvas = new Java2DCanvas(bl, tr, 1600, 1060, 150, Color.WHITE);
        
        SwissPainter.painter().drawMap(map, canvas);
        
        ImageIO.write(canvas.image(), "png", new File("lausanneTest2.png"));
    }
    
    @Test
    public void interlakenPrint() throws IOException, SAXException {
        OSMToGeoTransformer transform = new OSMToGeoTransformer(new CH1903Projection());
        OSMMap temp = OSMMapReader.readOSMFile("data/maps/interlaken.osm.gz", true);
        Map map = transform.transform(temp);
        
        Point bl = new Point(628590, 168210);
        Point tr = new Point(635660, 172870);
        
        Java2DCanvas canvas = new Java2DCanvas(bl, tr, 800, 530, 72, Color.WHITE);
        
        SwissPainter.painter().drawMap(map, canvas);
        
        ImageIO.write(canvas.image(), "png", new File("interlakenTest2.png"));
    }
}
