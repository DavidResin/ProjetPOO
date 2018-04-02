package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Un polygône compose d'une ClosedPolyLine et d'eventuels trous representes par une liste de ClosedPolyLine
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Polygon {
	private final ClosedPolyLine shell;
	private final List<ClosedPolyLine> holes;

	/**
	 * Construit un polygône a partir d'une ClosedPolyLine principale (le contour) et d'une liste de ClosedPolyline (les trous) donnees en argument
	 * 
	 * @param shell
	 * 				La ClosedPolyLine representant le contour du polygône
	 * @param holes
	 * 				La liste de trous representes par des ClosedPolyLine	
	 */
	public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
		List<ClosedPolyLine> holesTemp = new ArrayList<>();
		holesTemp.addAll(holes);

		this.shell = shell;
		this.holes = Collections.unmodifiableList(holesTemp);
	}

	/**
	 * Construit un polygône a partir d'une ClosedPolyLine principale donnee en argument et initialise la liste "holes" a l'aide d'une liste vide
	 * 
	 * @param shell
	 * 				La ClosedPolyLine representant le contour du polygône
	 */
	public Polygon(ClosedPolyLine shell) {
		this(shell, Collections.emptyList());
	}

	/**
	 * Retourne une ClosedPolyLine representant le contour du polygône
	 * 
	 * @return Le contour du polygône
	 */
	public ClosedPolyLine shell() {
		return shell;
	}

	/**
	 Retourne une liste immuable contenant des ClosedPolyLine representant les trous du polygône
	 * 
	 * @return Une version immuable de la liste contenant les trous du polygône
	 */
	public List<ClosedPolyLine> holes() {
		return Collections.unmodifiableList(holes);
	}
}
