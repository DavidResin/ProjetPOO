package ch.epfl.imhof.painting;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Un canevas sur lequel seront peints les polygones et polylignes
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Java2DCanvas implements Canvas {

    private final Function<Point, Point> tr;
    private final BufferedImage image;
    private final Graphics2D graph;
    
    /**
     * Le constructeur de canevas
     * 
     * @param bottomLeft
     *              Le point du coin bas-gauche du canevas
     * @param topRight
     *              Le point du coin haut-droite du canevas
     * @param width
     *              La largeur du canevas en pixels
     * @param height
     *              La hauteur du canevas en pixels
     * @param res
     *              La resolution du canevas en points par pouce
     * @param color
     *              La couleur de fond du canevas
     * @throws IllegalArgumentException
     *              Si la hauteur, la largeur ou la resolution ne sont pas positives
     */
    public Java2DCanvas(Point bottomLeft, Point topRight, int width, int height, double res, Color color) throws IllegalArgumentException {
        if (width <= 0)
            throw new IllegalArgumentException("Largeur invalide");
        if (height <= 0)
            throw new IllegalArgumentException("Hauteur invalide");
        if (res <= 0)
            throw new IllegalArgumentException("Resolution invalide");
        
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.graph = image.createGraphics();
        this.tr = Point.alignedCoordinateChange(bottomLeft, new Point(0, 72d * height / res), topRight, new Point(72d * width / res, 0));

        graph.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graph.setColor(color.convert());
        graph.fillRect(0, 0, width, height);
        graph.scale(res / 72d, res / 72d);
    }
    
    public void drawPolyLine(PolyLine p, LineStyle l) {
        Stroke s;
        
        if (l.dashingPattern().length == 0)
            s = new BasicStroke(l.thickness(), l.lineCap().getLineCap(), l.lineJoin().getLineJoin(), 10f);
        else
            s = new BasicStroke(l.thickness(), l.lineCap().getLineCap(), l.lineJoin().getLineJoin(), 10f, l.dashingPattern(), 0f);
        
        graph.setColor(l.color().convert());
        graph.setStroke(s);
        graph.draw(path(p));
    }

    public void drawPolygon(Polygon p, Color c) {
        Area area = new Area(path(p.shell()));
        
        for (PolyLine poly : p.holes())
            area.subtract(new Area(path(poly)));
        
        graph.setColor(c.convert());
        graph.fill(area);
    }

    /**
     * Retourne un Path2D.Double obtenu a partir de la polyligne passee en argument
     * 
     * @param poly
     * 				La polyligne a convertir
     * @return
     * 				Le path obtenu de la polyligne
     */
    private Path2D.Double path(PolyLine poly) {
    	double x, y;
        Path2D.Double path = new Path2D.Double();
        
        for (Point point : poly.points()) {
        	
        	x = tr.apply(point).x();
            y = tr.apply(point).y();
        	
        	if (poly.firstPoint().equals(point))
                path.moveTo(x, y);
            else
                path.lineTo(x, y);
        }
        
        if (poly.isClosed())
            path.closePath();
        
        return path;
    }
    
    /**
     * Retourne la BufferedImage contenant tous les ajouts faits par le biais du Graphics2D cree dans le constructeur
     * 
     * @return
     *              L'image
     */
    public BufferedImage image() {
        return image;
    }
}