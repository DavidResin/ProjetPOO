package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Le constructeur de la polyligne ouverte
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public class OpenPolyLine extends PolyLine {

	/**
	 * Construit une polyligne ouverte a partir d'une liste de points donnee en parametre
	 * 
	 * @param points
	 * 				La liste de points qui composent la polyligne ouverte
	 * @throws IllegalArgumentException
	 * 				Si la liste est vide (erreur detectee dans la super-classe)
	 */
	public OpenPolyLine(List<Point> points) throws IllegalArgumentException {
		super(points);
	}

	/**
	 * Test de fermeture de la ligne, retourne toujours "false"
	 */
	public boolean isClosed() {
		return false;
	}
}
