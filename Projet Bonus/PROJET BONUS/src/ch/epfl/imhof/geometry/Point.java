package ch.epfl.imhof.geometry;

import java.util.function.Function;

/**
 * Un point à la surface de la terre en projection équirectangulaire, représenté
 * par des coordonnées cartésiennes
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Point {
	private final double x;
	private final double y;

	/**
	 * Construit un point avec les coordonnées données
	 * 
	 * @param x
	 *            Coordonnée horizontale
	 * @param y
	 *            Coordonnée verticale
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Retourne la coordonnée x du point
	 *
	 * @return La coordonnée x
	 */
	public double x() {
		return x;
	}

	/**
	 * Retourne la coordonnée y du point
	 *
	 * @return La coordonnée y
	 */
	public double y() {
		return y;
	}
	
	/**
	 * Calcule la transformation à effectuer pour passer d'un repère à un autre à partir de deux points donnés dans leur ancien et leur nouveau repère.
	 * 
	 * @param old0
	 *                 Le point p0 avant la transformation
	 * @param new0
	 *                 Le point p0 après la transformation
	 * @param old1
     *                 Le point p1 avant la transformation
     * @param new1
     *                 Le point p1 après la transformation
	 * @return
	 *                 Une fonction anonyme effectuant le changement de coordonnées
	 * @throws IllegalArgumentException
	 *                 Si les deux points ont des coordonnées x ou y égales
	 */
	public static Function<Point, Point> alignedCoordinateChange(Point old0, Point new0, Point old1, Point new1) throws IllegalArgumentException {
	    if (old0.x() == old1.x() || old0.y() == old1.y()) throw new IllegalArgumentException("Les points sont alignés sur l'axe X ou l'axe Y");
	    double coefX = (new1.x() - new0.x()) / (old1.x() - old0.x());
	    double coefY = (new1.y() - new0.y()) / (old1.y() - old0.y());
	    double incrX = new0.x() - coefX * old0.x();
	    double incrY = new0.y() - coefY * old0.y();
	    return (p) -> (new Point(coefX * p.x() + incrX, coefY * p.y() + incrY));
	}
}
