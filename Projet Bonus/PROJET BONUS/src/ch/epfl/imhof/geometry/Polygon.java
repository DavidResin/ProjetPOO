package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Un polygône composé d'une ClosedPolyLine et d'éventuels trous représentés par une liste de ClosedPolyLine
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Polygon {
	private final ClosedPolyLine shell;
	private final List<ClosedPolyLine> holes;

	/**
	 * Construit un polygône à partir d'une ClosedPolyLine principale (le contour) et d'une liste de ClosedPolyline (les trous) données en argument
	 * 
	 * @param shell
	 * 				La ClosedPolyLine représentant le contour du polygône
	 * @param holes
	 * 				La liste de trous représentés par des ClosedPolyLine	
	 */
	public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
		List<ClosedPolyLine> holesTemp = new ArrayList<>();
		holesTemp.addAll(holes);

		this.shell = shell;
		this.holes = Collections.unmodifiableList(holesTemp);
	}

	/**
	 * Construit un polygône à partir d'une ClosedPolyLine principale donnée en argument et initialise la liste "holes" à l'aide d'une liste vide
	 * 
	 * @param shell
	 * 				La ClosedPolyLine représentant le contour du polygône
	 */
	public Polygon(ClosedPolyLine shell) {
		this(shell, Collections.emptyList());
	}

	/**
	 * Retourne une ClosedPolyLine représentant le contour du polygône
	 * 
	 * @return Le contour du polygône
	 */
	public ClosedPolyLine shell() {
		return shell;
	}

	/**
	 Retourne une liste immuable contenant des ClosedPolyLine représentant les trous du polygône
	 * 
	 * @return Une version immuable de la liste contenant les trous du polygône
	 */
	public List<ClosedPolyLine> holes() {
		return Collections.unmodifiableList(holes);
	}
}
