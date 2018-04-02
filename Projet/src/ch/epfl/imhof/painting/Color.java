package ch.epfl.imhof.painting;

/**
 * Une couleur definie par ses composantes rouge, verte et bleue.
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Color {
	private final float red;
	private final float green;
	private final float blue;
	
	public static Color WHITE = gray(1);
	public static Color BLACK = gray(0);
	
	public static Color RED = rgb(1, 0, 0);
	public static Color GREEN = rgb(0, 1, 0);
	public static Color BLUE = rgb(0, 0, 1);
	
	/**
	 * Construit une couleur a partir des valeurs de rouge, vert et bleu passes en argument
	 * 
	 * @param red
	 *                 Le niveau de rouge
	 * @param green
	 *                 Le niveau de vert
	 * @param blue
	 *                 Le niveau de bleu
	 */
	private Color(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Construit une couleur en niveau de gris
	 * 
	 * @param x
	 *                 Le niveau de gris
	 * @return
	 *                 Une couleur en niveau de gris
	 * @throws IllegalArgumentException
	 *                 Si la proportion n'est pas dans l'intervalle [0;1]
	 */
	public static Color gray(float x) throws IllegalArgumentException {
		if (! (0.0 <= x && x <= 1.0))
		    throw new IllegalArgumentException("Composante invalide : " + x);
		return new Color(x, x, x);
	}
	
	/**
	 * Construit une couleur a partir des niveaux de couleurs de base passes en argument
	 * 
	 * @param r
	 *                 Le niveau de rouge
	 * @param g
	 *                 Le niveau de vert
	 * @param b
	 *                 Le niveau de bleu
	 * @return
	 *                 Une couleur
	 * @throws IllegalArgumentException
	 *                 Si le niveau d'une ou plusieurs couleurs est hors de l'intervalle [0;1]
	 */
	public static Color rgb(float r, float g, float b) throws IllegalArgumentException {
	    if (! (0.0 <= r && r <= 1.0))
            throw new IllegalArgumentException("Composante rouge invalide : " + r);
        if (! (0.0 <= g && g <= 1.0))
            throw new IllegalArgumentException("Composante verte invalide : " + g);
        if (! (0.0 <= b && b <= 1.0))
            throw new IllegalArgumentException("Composante bleue invalide : " + b);
		return new Color(r, g, b);
	}
	
	/**
	 * Construit une couleur a partir du int passe en argument apres l'avoir decompose en trois octets contenant les valeurs du rouge, du vert et du bleu
	 * 
	 * @param color
	 *                 Le int contenant les trois octets pour le rouge, le vert et le bleu
	 * @return
	 *                 Une couleur
	 */
	public static Color rgb(int color) {
		return new Color(((color >> 16) & 0xFF) / 255f, ((color >>  8) & 0xFF) / 255f, (color & 0xFF) / 255f);
	}
	
	/**
	 * Retourne la proportion de rouge
	 * 
	 * @return
	 *                 La proportion de rouge
	 */
	public float red() {
		return red;
	}
	
	/**
     * Retourne la proportion de vert
     * 
     * @return
     *                 La proportion de vert
     */
	public float green() {
		return green;
	}
	
	/**
     * Retourne la proportion de bleu
     * 
     * @return
     *                 La proportion de bleu
     */
	public float blue() {
		return blue;
	}
	
	/**
	 * Melange this avec la couleur passee en argument
	 * 
	 * @param c
	 *                 La couleur a melanger avce this
	 * @return
	 *                 Une nouvelle couleur resultant du melange
	 */
	public Color multiply(Color c) {
		return new Color(red * c.red(), green * c.green(), blue * c.blue());
	}
	
	/**
	 * Convertit this en java.awt.color
	 * 
	 * @return
	 *                 La conversion de this en java.awt.color
	 */
	public java.awt.Color convert() {
		return new java.awt.Color(red, green, blue);
	}
}
