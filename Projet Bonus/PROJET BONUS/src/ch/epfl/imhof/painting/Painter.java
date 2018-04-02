package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 * Une interface fonctionelle définissant un peintre de carte
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public interface Painter {
    public abstract Canvas drawMap(Map m, Canvas c);
    
    /**
     * Retourne un peintre peignant tous les polygones d'une Map avec une certaine couleur passée en argument
     * 
     * @param color
     *              La couleur utilisée pour peindre les polygones
     * @return
     *              Le peintre de polygones
     */
    public static Painter polygon(Color color) {
        return (m, c) -> {
            for (Attributed<Polygon> p : m.polygons()) {
                c.drawPolygon(p.value(), color);
            }
            return c;
        };
    }
    
    /**
     * Retourne un peintre peignant toutes les polylignes d'une Map avec un certain style de ligne passé en argument
     * 
     * @param color
     *              La couleur de ligne
     * @param t
     *              L'épaisseur de ligne
     * @param d
     *              Le pattern d'alternance de ligne
     * @param lC
     *              Le style de terminaison de ligne
     * @param lJ
     *              Le style de jonction de ligne
     * @return
     *              Le peintre de polylignes
     */
    public static Painter line(float t, Color color, LineCap lC, LineJoin lJ, float... d) {
        return line(new LineStyle(t, color, lC, lJ, d));
    }

    /**
     * Retourne un peintre peignant toutes les polylignes d'une Map avec un certain style de ligne passé en argument
     * 
     * @param color
     *              La couleur de ligne
     * @param t
     *              L'épaisseur de ligne
     * @return
     *              Le peintre de polylignes
     */
    public static Painter line(float t, Color color) {
        return line(new LineStyle(t, color));
    }

    /**
     * Retourne un peintre peignant toutes les polylignes d'une Map avec un certain style de ligne passé en argument
     * 
     * @param l
     *              Le style de ligne
     * @return
     *              Le peintre de polylignes
     */
    public static Painter line(LineStyle l) {
        return (m, c) -> {
            for (Attributed<PolyLine> p : m.polyLines()) {
                c.drawPolyLine(p.value(), l);
            }
            return c;
        };
    }
    
    /**
     * Retourne un peintre peignant toutes les polylignes des polygones d'une Map avec un certain style de ligne passé en argument
     * 
     * @param color
     *              La couleur de ligne
     * @param t
     *              L'épaisseur de ligne
     * @param d
     *              Le pattern d'alternance de ligne
     * @param lC
     *              Le style de terminaison de ligne
     * @param lJ
     *              Le style de jonction de ligne
     * @return
     *              Le peintre de polylignes
     */
    public static Painter outline(float t, Color color, LineCap lC, LineJoin lJ, float... d) {
        return outline(new LineStyle(t, color, lC, lJ, d));
    }

    /**
     * Retourne un peintre peignant toutes les polylignes des polygones d'une Map avec un certain style de ligne passé en argument
     * 
     * @param color
     *              La couleur de ligne
     * @param t
     *              L'épaisseur de ligne
     * @return
     *              Le peintre de polylignes
     */
    public static Painter outline(float t, Color color) {
        return outline(new LineStyle(t, color));
    }
    
    /**
     * Retourne un peintre peignant toutes les polylignes des polygones d'une Map avec un certain style de ligne passé en argument
     * 
     * @param l
     *              Le style de ligne
     * @return
     *              Le peintre de polylignes
     */
    public static Painter outline(LineStyle l) {
        return (m, c) -> {
            for (Attributed<Polygon> p : m.polygons()) {
                c.drawPolyLine(p.value().shell(), l);
                
                for (ClosedPolyLine poly : p.value().holes()) {
                    c.drawPolyLine(poly, l);
                }
            }
            return c;
        };
    }
    
    /**
     * Retourne un peintre ne peignant que les polygones et polylignes répondant au prédicat passé en argument
     * 
     * @param p
     *              Le prédicat à vérifier
     * @return
     *              Le peintre restreint
     */
    public default Painter when(Predicate<Attributed<?>> p) {
        return (m, c) -> {
            Map.Builder temp = new Map.Builder();
            
            for (Attributed<Polygon> poly : m.polygons()) {
                if (p.test(poly)) temp.addPolygon(poly);
            }
            
            for (Attributed<PolyLine> poly : m.polyLines()) {
                if (p.test(poly)) temp.addPolyLine(poly);
            }
            
            Map map = temp.build();
            
            return this.drawMap(map, c);
        };
    }
    
    /**
     * Retourne un peintre peignant par dessus le peintre passé en argument, après ayant provoqué la méthode drawMap de ce dernier
     * 
     * @param p
     *              Le peintre au-dessus duquel on va peindre
     * @return
     *              Le peintre composé
     */
    public default Painter above(Painter p) {
        return (m, c) -> (this.drawMap(m, p.drawMap(m, c)));
    }
    
    /**
     * Retourne un peintre peignant ses couches les unes après les autres en commencant par la couche -5 et en remontant jusqu'à la couche 5
     * 
     * @return
     *              Le peintre ordonné
     */
    public default Painter layered() {
        return (m, c) -> {
            Painter p = this.when(Filters.onLayer(-5)); //TODO peut mieux faire ?
            
            for (int i = -4; i < 6; ++i) {
                p = when(Filters.onLayer(i)).above(p);
            }
            return p.drawMap(m, c);
        };
    }
}