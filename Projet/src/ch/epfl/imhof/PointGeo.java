package ch.epfl.imhof;

import java.lang.Math;

/**
 * Un point a la surface de la terre, represente par sa latitude et sa longitude
 * en radians
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class PointGeo {
	private final double longitude;
	private final double latitude;

	/**
	 * Construit un point avec la longitude et la latitude donnees
	 * 
	 * @param longitude
	 *            longitude du point, en radians
	 * @param latitude
	 *            latitude du point, en radians
	 * @throws IllegalArgumentException
	 *            si la longitude est hors de l'intervalle [-π; π]
	 * @throws IllegalArgumentException
	 * 			  si la latitude est hors de l'intervalle [-π/2; π/2]
	 */
	public PointGeo(double longitude, double latitude) throws IllegalArgumentException {
		this.longitude = longitude;
		this.latitude = latitude;

		if ((longitude < -Math.PI) || (longitude > Math.PI))
            throw new IllegalArgumentException("Longitude invalide : " + longitude);
        if ((latitude < -Math.PI / 2) || (latitude > Math.PI / 2))
            throw new IllegalArgumentException("Latitude invalide : " + latitude);
	}

	/**
	 * Retourne la longitude du point
	 * 
	 * @return La longitude
	 */
	public double longitude() {
		return longitude;
	}

	/**
	 * Retourne la latitude du point
	 * 
	 * @return La latitude
	 */
	public double latitude() {
		return latitude;
	}
}
