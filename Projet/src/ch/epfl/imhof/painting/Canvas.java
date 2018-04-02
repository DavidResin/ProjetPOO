package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

public abstract interface Canvas {
    
    /**
     * Dessine la polyligne donnee en argument sur le canevas avec un style donne
     * 
     * @param p
     *              La polyligne a dessiner
     * @param l
     *              Le style a utiliser
     */
    abstract void drawPolyLine(PolyLine p, LineStyle l);
    
    /**
     * Dessine le polygone donne en argument sur le canevas avec la couleur donnee
     * 
     * @param p
     *              Le polygone a dessiner
     * @param c
     *              Le couleur a utiliser
     */
    abstract void drawPolygon(Polygon p, Color c);
}
