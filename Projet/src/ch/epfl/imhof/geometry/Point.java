package ch.epfl.imhof.geometry;

import java.util.function.Function;

/**
 * Un point a la surface de la terre en projection equirectangulaire, represente
 * par des coordonnees cartesiennes
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Point {
	private final double x;
	private final double y;

	/**
	 * Construit un point avec les coordonnees donnees
	 * 
	 * @param x
	 *            Coordonnee horizontale
	 * @param y
	 *            Coordonnee verticale
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Retourne la coordonnee x du point
	 *
	 * @return La coordonnee x
	 */
	public double x() {
		return x;
	}

	/**
	 * Retourne la coordonnee y du point
	 *
	 * @return La coordonnee y
	 */
	public double y() {
		return y;
	}
	
	/**
	 * Calcule la transformation a effectuer pour passer d'un repere a un autre a partir de deux points donnes dans leur ancien et leur nouveau repere.
	 * 
	 * @param old0
	 *                 Le point p0 avant la transformation
	 * @param new0
	 *                 Le point p0 apres la transformation
	 * @param old1
     *                 Le point p1 avant la transformation
     * @param new1
     *                 Le point p1 apres la transformation
	 * @return
	 *                 Une fonction anonyme effectuant le changement de coordonnees
	 * @throws IllegalArgumentException
	 *                 Si les deux points ont des coordonnees x ou y egales
	 */
	public static Function<Point, Point> alignedCoordinateChange(Point old0, Point new0, Point old1, Point new1) throws IllegalArgumentException {
	    if (old0.x() == old1.x() || old0.y() == old1.y())
	        throw new IllegalArgumentException("Les points sont alignes sur l'axe X ou l'axe Y");
	    
	    double coefX = (new1.x() - new0.x()) / (old1.x() - old0.x());
	    double coefY = (new1.y() - new0.y()) / (old1.y() - old0.y());
	    
	    double incrX = new0.x() - coefX * old0.x();
	    double incrY = new0.y() - coefY * old0.y();
	    
	    return (p) -> (new Point(coefX * p.x() + incrX, coefY * p.y() + incrY));
	}
}
