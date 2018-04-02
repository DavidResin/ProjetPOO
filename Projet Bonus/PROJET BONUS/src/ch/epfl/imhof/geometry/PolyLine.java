package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Une polyligne composée de points sur le plan
 *
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public abstract class PolyLine {
	private final List<Point> points;

	/**
	 * Construit une polyligne d'après une liste de points donnée en argument
	 * 
	 * @param points
	 *             La liste des points de la ligne
	 * @throws IllegalArgumentException
	 *             si la liste de points est vide
	 */
	public PolyLine(List<Point> points) throws IllegalArgumentException {

		if (points.isEmpty()) {
			throw new IllegalArgumentException("La liste de points est vide");
		}

		this.points = Collections.unmodifiableList(new ArrayList<>(points));
	}

	/**
	 * Méthode abstraite de test de fermeture de la ligne
	 */
	public abstract boolean isClosed();

	/**
	 * Retourne une liste immuable des points de la polyligne
	 * 
	 * @return La liste immuable des points
	 */
	public List<Point> points() {
		return Collections.unmodifiableList(points);
	}

	/**
	 * Retourne le premier point de la polyligne
	 * 
	 * @return Le premier point de la polyligne
	 */
	public Point firstPoint() {
		return points.get(0);
	}

	/**
	 * Le constructeur de la polyligne
	 *
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Builder {
		private final List<Point> points = new ArrayList<>();

		/**
		 * Ajoute un point à la liste en construction
		 * 
		 * @param newPoint
		 * 				Le point à ajouter
		 */
		public void addPoint(Point newPoint) {
			points.add(newPoint);
		}

		/**
		 * Retourne une version ouverte de la polyligne
		 * 
		 * @return Une OpenPolyline construite à partir des points donnés
		 */
		public OpenPolyLine buildOpen() {
			return new OpenPolyLine(points);
		}

		/**
		 * Retourne une version fermée de la polyligne
		 * 
		 * @return Une ClosedPolyline construite à partir des points donnés
		 */
		public ClosedPolyLine buildClosed() {
			return new ClosedPolyLine(points);
		}
	}
}
