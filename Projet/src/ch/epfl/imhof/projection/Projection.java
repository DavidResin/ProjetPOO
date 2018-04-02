package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * L'interface qui decrit les methodes a utiliser dans les classes de projection
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public interface Projection {
	/**
	 * Transforme un point de la sphere en point du plan
	 * 
	 * @param point
	 *            Point de la sphere
	 * @return Le point projete sur le plan
	 */
	Point project(PointGeo point);

	/**
	 * Transforme un point du plan en point de la sphere
	 * 
	 * @param point
	 *            Point du plan
	 * @return Le point projete sur la sphere
	 */
	PointGeo inverse(Point point);
}
