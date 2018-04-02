package ch.epfl.imhof.dem;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * Une interface pour un modele digital d'elevation
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public interface DigitalElevationModel extends AutoCloseable {
    
    /**
     * Calcule la normale du plan a un point de la carte donne en parametre
     * 
     * @param p
     *              Le point dont on cherche la normale au plan
     * @return
     *              Un vecteur tridimensionnel representant la normale au plan
     */
    public Vector3 normalAt(PointGeo p);
    
    /**
     * Retourne un tableau contenant les 4 points avoisinant le point aux coordonnees donnees
     * 
     * @param x
     * 				La coordonnee x
     * @param y
     * 				La coordonnee y
     * @return
     * 				Un tableau contenant les 4 points voisins
     */
    public short[] neighborsAt(int x, int y);
    
    /**
     * Retourne la hauteur en un point, calculee selon un modele a deux triangles par groupe de 4 points (un triangle 0-1-2 et un triangle 1-3-2), et la multiplie par un facteur
     * 
     * @param x
     * 				La coordonnee x
     * @param y
     * 				La coordonnee y
     * @param offsetX
     * 				Le decalage sur x
     * @param offsetY
     * 				Le decalage sur y
     * @param heightFactor
     * 				Le facteur d'accentuation des pentes
     * @return
     * 				La hauteur calculee selon le modele susmentionne
     */
    public short heightAt(double x, double y, double offsetX, double offsetY, double heightFactor);

    /**
     * Calcule la normale du plan a un point de la carte donne en parametre par ses coordonnees
     * 
     * @param x
     * 				La coordonnee x
     * @param y
     * 				La coordonnee y	
     * @param offsetX
     * 				Le decalage sur x
     * @param offsetY
     * 				Le decalage sur y
     * @return
     *              Un vecteur tridimensionnel representant la normale au plan
     */
	public Vector3 normalAt(double x, double y, double offsetX, double offsetY);
}
