package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * La projection equirectangulaire dans les deux sens (sphere -> plan & plan -> sphere dans cet ordre)
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class EquirectangularProjection implements Projection {

	/** {@inheritDoc} */
	public Point project(PointGeo point) {
		return new Point(point.longitude(), point.latitude());
	}

	/** {@inheritDoc} */
	public PointGeo inverse(Point point) {
		return new PointGeo(point.x(), point.y());
	}
}
