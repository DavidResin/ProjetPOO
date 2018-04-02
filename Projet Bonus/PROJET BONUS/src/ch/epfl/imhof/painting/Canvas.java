package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

public abstract interface Canvas {
    
    /**
     * Dessine la polyligne donnée en argument sur le canevas avec un style donné
     * 
     * @param p
     *              La polyligne à dessiner
     * @param l
     *              Le style à utiliser
     */
    abstract void drawPolyLine(PolyLine p, LineStyle l);
    
    /**
     * Dessine le polygone donné en argument sur le canevas avec la couleur donnée
     * 
     * @param p
     *              Le polygone à dessiner
     * @param c
     *              Le couleur à utiliser
     */
    abstract void drawPolygon(Polygon p, Color c);
}
