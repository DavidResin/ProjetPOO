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
}
