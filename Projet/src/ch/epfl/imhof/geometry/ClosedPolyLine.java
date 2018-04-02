package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Le constructeur de la polyligne fermee
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public class ClosedPolyLine extends PolyLine {

	/**
	 * Construit une polyligne fermee a partir d'une liste de points donnee en parametre
	 * 
	 * @param points
	 * 				La liste de points qui composent la polyligne fermee
	 * @throws IllegalArgumentException
	 * 				Si la liste est vide (erreur detectee dans la super-classe)
	 */
	public ClosedPolyLine(List<Point> points) throws IllegalArgumentException {
		super(points);
	}

	/**
	 * Test de fermeture de la ligne, retourne toujours "true"
	 */
	public boolean isClosed() {
		return true;
	}

	/**
	 * Calcule l'aire de la polyligne fermee par addition et soustraction de triangles
	 * 
	 * @return L'aire de la ClosedPolyLine
	 */
	public double area() {
		double sum = 0;
		
		for(int i = 0; i < points().size(); ++i)
			sum += points().get(i).x() * (points().get(iGen(i + 1)).y() - points().get(iGen(i - 1)).y());
		
		return Math.abs(sum / 2);
	}

	/**
	 * Teste si un point donne en parametre se trouve dans la ClosedPolyLine
	 * 
	 * @param p
	 * 				Le point a tester
	 * @return
	 * 				Une variable booleenne indiquant si le point se trouve dans la polyligne fermee
	 */
	public boolean containsPoint(Point p) {
		int indice = 0;
		
		for (int i = 0; i < points().size(); ++i) {
			
			if (points().get(i).y() <= p.y())
				if ((points().get(iGen(i + 1)).y() > p.y()) && (relPos(p, points().get(i), points().get(iGen(i + 1)))))
					++indice;
			else
				if ((points().get(iGen(i + 1)).y() <= p.y()) && (relPos(p, points().get(iGen(i + 1)), points().get(i))))
					--indice;
		}
		return indice != 0;
	}

	/**
	 * Teste si le point p se trouve strictement a gauche du vecteur p1-p2
	 * 
	 * @param p
	 * 				Le point a tester
	 * @param p1
	 * 				L'origine du vecteur
	 * @param p2
	 * 				Le pointe du vecteur
	 * @return
	 * 				Une variable booleenne indiquant si le point se trouve strictement a gauche du vecteur
	 */
	private boolean relPos(Point p, Point p1, Point p2) {
		return (p1.x() - p.x()) * (p2.y() - p.y()) > (p2.x() - p.x()) * (p1.y() - p.y());
	}

	/**
	 * Applique un modulo n positif a l'entier i donne en argument (n valant la quantite de points composant la polyligne fermee)
	 * 
	 * @param i
	 * 				Le nombre a ajuster modulo n
	 * @return
	 * 				La version ajustee de l'entier i
	 */
	private int iGen(int i) {
		return Math.floorMod(i, points().size());
	}
}
