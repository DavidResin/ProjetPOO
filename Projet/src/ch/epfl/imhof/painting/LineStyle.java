package ch.epfl.imhof.painting;

import java.awt.BasicStroke;

/**
 * Un style de ligne
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class LineStyle {
	private final Color color;
	private final float thickness;
	private final float[] dashingPattern;
	private final LineCap lineCap;
	private final LineJoin lineJoin;
	
	/**
	 * 
	 * Construit un style de ligne a partir des parametres passes en argument
	 * 
	 * @param color
	 *                 La couleur de ligne
	 * @param thickness
	 *                 L'epaisseur de ligne
	 * @param dashingPattern
	 *                 Le pattern d'alternance de ligne
	 * @param lineCap
	 *                 Le style de terminaison de ligne
	 * @param lineJoin
	 *                 Le style de jonction de ligne
	 * @throws IllegalArgumentException
	 *                 Si l'epaisseur est negative ou si le pattern d'alternance contient des valeurs negatives
	 */
	public LineStyle(float thickness, Color color, LineCap lineCap, LineJoin lineJoin, float[] dashingPattern) throws IllegalArgumentException {
		if (thickness < 0)
		    throw new IllegalArgumentException("L'epaisseur est negative");
		
		for (float i : dashingPattern)
			if (i <= 0)
			    throw new IllegalArgumentException("Le dashing pattern contient des valeurs negatives");
		
		this.color = color;
		this.thickness = thickness;
		this.dashingPattern = dashingPattern;
		this.lineCap = lineCap;
		this.lineJoin = lineJoin;
	}
	
	/**
	 * Construit un style de ligne a partir de la couleur et de l'epaisseur passees en argument, les autres parametres sont mis a une valeur par defaut
	 * 
	 * @param color
5	 *                 La couleur de ligne
	 * @param thickness
	 *                 L'epaisseur de ligne
	 */
	public LineStyle(float thickness, Color color) {
		this(thickness, color, LineCap.BUTT, LineJoin.MITER, new float[0]);
	}
	
	/**
	 * Retourne la couleur de ligne
	 * 
	 * @return
	 *                 La couleur de ligne
	 */
	public Color color() {
	    return color;
	}
	
	/**
	 * Retourne l'epaisseur de ligne
	 * 
	 * @return
	 *                 L'epaisseur de ligne
	 */
	public float thickness() {
	    return thickness;
	}
	
	/**
	 * Retourne le pattern d'alternance de ligne
	 * 
	 * @return
	 *                 Le pattern d'alternance de ligne
	 */
	public float[] dashingPattern() {
	    return dashingPattern;
	}
	
	/**
	 * Retourne le style de terminaison de ligne
	 * 
	 * @return
	 *                 Le style de terminaison de ligne
	 */
	public LineCap lineCap() {
	    return lineCap;
	}
	
	/**
	 * Retourne le style de jonction de ligne
	 * 
	 * @return
	 *                 Le style de jonction de ligne  
	 */
	public LineJoin lineJoin() {
	    return lineJoin;
	}
	
    /**
     * Retourne un nouveau style de ligne avec une epaisseur differente
     * 
     * @param t
     *                 La nouvelle epaisseur
     * @return
     *                 Le nouveau style
     */
    public LineStyle withThickness(float t) {
        return new LineStyle(t, color, lineCap, lineJoin, dashingPattern);
    }
    
	/**
	 * Retourne un nouveau style de ligne avec une couleur differente
	 * 
	 * @param c
	 *                 La nouvelle couleur
	 * @return
	 *                 Le nouveau style
	 */
	public LineStyle withColor(Color c) {
	    return new LineStyle(thickness, c, lineCap, lineJoin, dashingPattern);
	}
    
    /**
     * Retourne un nouveau style de ligne avec une terminaison de ligne differente
     * 
     * @param l
     *                 La nouvelle terminaison de ligne
     * @return
     *                 Le nouveau style
     */
    public LineStyle withLineCap(LineCap l) {
        return new LineStyle(thickness, color, l, lineJoin, dashingPattern);
    }
    
    /**
     * Retourne un nouveau style de ligne avec une jonction de ligne differente
     * 
     * @param l
     *                 La nouvelle jonction de ligne
     * @return
     *                 Le nouveau style
     */
    public LineStyle withLineJoin(LineJoin l) {
        return new LineStyle(thickness, color, lineCap, l, dashingPattern);
    }
    
    /**
     * Retourne un nouveau style de ligne avec un pattern d'alternance different
     * 
     * @param d
     *                 Le nouveau pattern d'alternance
     * @return
     *                 Le nouveau style
     */
    public LineStyle withDashingPattern(float[] d) {
        return new LineStyle(thickness, color, lineCap, lineJoin, d);
    }
    
    /**
     * Une enumeration des terminaisons de ligne possible
     * 
     * @author Magaly Abboud (249344)
     * @author David Resin (225452)
     */
	public enum LineCap {
		BUTT(BasicStroke.CAP_BUTT), ROUND(BasicStroke.CAP_ROUND), SQUARE(BasicStroke.CAP_SQUARE);
		
		private int lineCap;
		
		private LineCap(int lineCap) {
		    this.lineCap = lineCap;
		}
		
		public int getLineCap() {
		    return lineCap;
		}
	}
	
	/**
	 * Une enumeration des jonctions de ligne possible
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public enum LineJoin {
		BEVEL(BasicStroke.JOIN_BEVEL), MITER(BasicStroke.JOIN_MITER), ROUND(BasicStroke.JOIN_ROUND);
		
		private int lineJoin;
		
		private LineJoin(int lineJoin) {
		    this.lineJoin = lineJoin;
		}
		
		public int getLineJoin() {
		    return lineJoin;
		}
	}
}