package ch.epfl.imhof;

/**
 * Un vecteur de R3 représenté par ses coordonnées x, y, z
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Vector3 {
    
    private final double x, y, z;
   
    /**
     * Construit un vecteur à partir de ses coordonnées passées en argument
     * 
     * @param x
     *              La coordonnée x
     * @param y
     *              La coordonnée y
     * @param z
     *              La coordonnée z
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3(double m) {
    	this.x = m;
    	this.y = m;
    	this.z = m;
    }
    
    /**
     * Retourne la coordonnée x
     * 
     * @return
     *              La coordonnée x
     */
    public double x() {
        return x;
    }
    
    /**
     * Retourne la coordonnée y
     * 
     * @return
     *              La coordonnée y
     */
    public double y() {
        return y;
    }
    
    /**
     * Retourne la coordonnée z
     * 
     * @return
     *              La coordonnée z
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
     * Crée un noveau vecteur qui est la version normalisée de this
     * 
     * @return
     *              Le vecteur normalisé
     * @throws IllegalArgumentException
     *              Si this a une norme nulle
     */
    public Vector3 normalized() throws IllegalArgumentException {
        double n = norm();
        if (n == 0) throw new IllegalArgumentException("La norme du vecteur est nulle");
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
    
    public Vector3 add(Vector3 that) {
    	return new Vector3(x + that.x(), y + that.y(), z + that.z());
    }
    
    public Vector3 multiply(Vector3 that) {
    	return new Vector3(x * that.x(), y * that.y(), z * that.z());
    }
    
    public Vector3 invert() {
    	return new Vector3(- x, - y, - z);
    }
    
    public Vector3 withX(double newX) {
        return new Vector3(newX, y, z);
    }
    
    public Vector3 withY(double newY) {
        return new Vector3(x, newY, z);
    }
    
    public Vector3 withZ(double newZ) {
        return new Vector3(x, y, newZ);
    }
}