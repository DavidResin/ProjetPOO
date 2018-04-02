package ch.epfl.imhof.bonus;

import ch.epfl.imhof.Vector3;

/**
 * Une bille modelisee par sa taille, sa masse, sa positionm sa vitesse et son acceleration
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Ball {
	
	private final int size;
	private final double mass;
	
	private Vector3 pos, speed, accel;
	
	/**
	 * Construit une bille selon les arguments donnes et initialise la vitesse et l'acceleration a 0
	 * 
	 * @param size
	 * 				La taille
	 * @param mass
	 * 				La masse
	 * @param x
	 * 				La position x
	 * @param y
	 * 				La position y
	 * @param z
	 * 				La position z
	 * 
	 * @throws IllegalArgumentException
	 * 				Si la taille ou la mass sont negatives ou nulles
	 */
	public Ball(int size, double mass, double x, double y, double z) throws IllegalArgumentException {
		if (size <= 0)
			throw new IllegalArgumentException("La taille de la balle ne peut etre negative ou nulle");
		if (mass <= 0)
			throw new IllegalArgumentException("La masse de la balle ne peut etre negative ou nulle");
		
		this.size = size;
		this.mass = mass;
		
		this.pos = new Vector3(x, y, z);
		this.speed = new Vector3(0, 0, 0);
		this.accel = new Vector3(0, 0, 0);
	}
	
	/**
	 * Met a jour la position, la vitesse et l'acceleration selon les nouvelles valeurs passees en parametre
	 * 
	 * @param pos
	 * 				La nouvelle position
	 * @param speed
	 * 				La nouvelle vitesse
	 * @param accel
	 * 				La nouvelle acceleration
	 */
	public void moveTo(Vector3 pos, Vector3 speed, Vector3 accel) {
		this.pos = pos;
		this.speed = speed;
		this.accel = accel;
	}
	
	/**
	 * Retourne la taille
	 * 
	 * @return
	 * 				La taille
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Retourne la masse
	 * 
	 * @return
	 * 				La masse
	 */
	public double getMass() {
		return mass;
	}
	
	/**
	 * Retourne la position
	 * 
	 * @return
	 * 				La position
	 */
	public Vector3 getPos() {
		return pos;
	}
	
	/**
	 * Retourne la vitesse
	 * 
	 * @return
	 * 				La vitesse
	 */
	public Vector3 getSpeed() {
		return speed;
	}
	
	/**
	 * Retourne l'acceleration
	 * 
	 * @return
	 * 				L'acceleration
	 */
	public Vector3 getAccel() {
		return accel;
	}
}
