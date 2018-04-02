package ch.epfl.imhof;

/**
 * Un vecteur de R3 represente par ses coordonnees x, y, z
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Vector3 {
    
    private final double x, y, z;
   
    /**
     * Construit un vecteur a partir de ses coordonnees passees en argument
     * 
     * @param x
     *              La coordonnee x
     * @param y
     *              La coordonnee y
     * @param z
     *              La coordonnee z
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Retourne la coordonnee x
     * 
     * @return
     *              La coordonnee x
     */
    public double x() {
        return x;
    }
    
    /**
     * Retourne la coordonnee y
     * 
     * @return
     *              La coordonnee y
     */
    public double y() {
        return y;
    }
    
    /**
     * Retourne la coordonnee z
     * 
     * @return
     *              La coordonnee z
     */
    public double z() {
        return z;
    }
    
    /**
     * Calcule la norme du vecteur
     * 
     * @return
     *              La norme du vecteur
     */
    public double norm() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }
    
    /**
     * Cree un noveau vecteur qui est la version normalisee de this
     * 
     * @return
     *              Le vecteur normalise
     * @throws IllegalArgumentException
     *              Si this a une norme nulle
     */
    public Vector3 normalized() throws IllegalArgumentException {
        double n = norm();
        if (n == 0)
            throw new IllegalArgumentException("La norme du vecteur est nulle");
        
        return new Vector3(x / n, y / n, z / n);
    }
    
    /**
     * Calcule le produit scalaire de this avec le vecteur that
     * 
     * @param that
     *              Le vecteur avec lequel multiplier this
     * @return
     *              Le produit scalaire de this et that
     */
    public double scalarProduct(Vector3 that) {
        return x * that.x() + y * that.y() + z * that.z();
    }
}