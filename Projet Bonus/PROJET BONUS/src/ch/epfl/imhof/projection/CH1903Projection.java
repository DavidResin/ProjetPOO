package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * La projection CH1903 dans les deux sens (sphère -> plan & plan -> sphère dans cet ordre)
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class CH1903Projection implements Projection {

	/** {@inheritDoc} */
	@Override
	public Point project(PointGeo point) {
		final double l = (Math.toDegrees(point.longitude()) * 3600 - 26782.5) / 10000;
		final double p = (Math.toDegrees(point.latitude()) * 3600 - 169028.66) / 10000;

		final double x = 600072.37 + 211455.93 * l - 10938.51 * l * p - 0.36 * l * Math.pow(p, 2) - 44.54 * Math.pow(l, 3);
		final double y = 200147.07 + 308807.95 * p + 3745.25 * Math.pow(l, 2) + 76.63 * Math.pow(p, 2) - 194.56 * Math.pow(l, 2) * p + 119.79 * Math.pow(p, 3);

		return new Point(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public PointGeo inverse(Point point) {
		final double x = (point.x() - 600000) / 1000000;
		final double y = (point.y() - 200000) / 1000000;

		final double l = 2.6779094 + 4.728982 * x + 0.791484 * x * y + 0.1306 * x * Math.pow(y, 2) - 0.0436 * Math.pow(x, 3);
		final double p = 16.9023892 + 3.238272 * y - 0.270978 * Math.pow(x, 2) - 0.002528 * Math.pow(y, 2) - 0.0447 * Math.pow(x, 2) * y - 0.014 * Math.pow(y, 3);

		return new PointGeo(Math.toRadians(l * 25 / 9), Math.toRadians(p * 25 / 9));
	}
}
