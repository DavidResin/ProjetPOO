package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Une carte composée de polylignes et de polygones
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Map {
	private final List<Attributed<PolyLine>> polyLines;
	private final List<Attributed<Polygon>> polygons;

	/**
	 * Construit une carte à partir des listes de polylignes et de polygones accompagnés d'éventuels attributs
	 * 
	 * @param polyLines
	 * 				La liste de Attributed<PolyLine>
	 * @param polygons
	 * 				La liste de Attributed<Polygon>
	 */
	public Map(List<Attributed<PolyLine>> polyLines, List<Attributed<Polygon>> polygons) {
		List<Attributed<PolyLine>> polyLinesTemp = new ArrayList<>();
		List<Attributed<Polygon>> polygonsTemp = new ArrayList<>();

		polyLinesTemp.addAll(polyLines);
		polygonsTemp.addAll(polygons);

		this.polyLines = Collections.unmodifiableList(polyLinesTemp);
		this.polygons = Collections.unmodifiableList(polygonsTemp);
	}

	/**
	 * Retourne la liste de Attributed<PolyLine>
	 * 
	 * @return
	 * 				La liste de Attributed<PolyLine>
	 */
	public List<Attributed<PolyLine>> polyLines() {
		return polyLines;
	}

	/**
	 * Retourne la liste de Attributed<Polygon>
	 * 
	 * @return
	 * 				La liste de Attributed<Polygon>
	 */
	public List<Attributed<Polygon>> polygons() {
		return polygons;
	}

	/**
	 * Un builder de Map
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public static final class Builder {
		private List<Attributed<PolyLine>> polyLines = new ArrayList<>();
		private List<Attributed<Polygon>> polygons = new ArrayList<>();

		/**
		 * Ajoute un nouveau Attributed<PolyLine> à la liste
		 * 
		 * @param newPolyLine
		 * 				Le Attributed<PolyLine> à ajouter
		 */
		public void addPolyLine(Attributed<PolyLine> newPolyLine) {
			polyLines.add(newPolyLine);
		}

		/**
		 * Ajoute un nouveau Attributed<Polygon> à la liste
		 * 
		 * @param newPolyLine
		 * 				Le Attributed<Polygon> à ajouter
		 */
		public void addPolygon(Attributed<Polygon> newPolygon) {
			polygons.add(newPolygon);
		}

		/**
		 * Construit une nouvelle Map à partir des listes du builder
		 * 
		 * @return
		 * 				Une nouvelle Map construite à partir des listes du builder
		 */
		public Map build() {
			return new Map(polyLines, polygons);
		}
	}
}
