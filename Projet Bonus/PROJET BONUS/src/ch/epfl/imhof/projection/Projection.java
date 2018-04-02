package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * L'interface qui décrit les méthodes à utiliser dans les classes de projection
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public interface Projection {
	/**
	 * Transforme un point de la sphère en point du plan
	 * 
	 * @param point
	 *            Point de la sphère
	 * @return Le point projeté sur le plan
	 */
	Point project(PointGeo point);

	/**
	 * Transforme un point du plan en point de la sphère
	 * 
	 * @param point
	 *            Point du plan
	 * @return Le point projeté sur la sphère
	 */
	PointGeo inverse(Point point);
}
