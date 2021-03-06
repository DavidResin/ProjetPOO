package ch.epfl.imhof.dem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.function.Function;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.projection.Projection;

/**
 * Un generateur de relief
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class ReliefShader {
    private final Projection proj;
    private final DigitalElevationModel dem;
    private final Vector3 sun;
    
    /**
     * Construit un generateur de relief a partir de la projection, du DigitalElevationModel, et du vecteur lumiere passes en argument
     * 
     * @param proj
     *              La projection permettant de passer de coordonnees terrestres aux corrdonnees WGS 84
     * @param dem
     *              Le DigitalElevationModel a utiliser
     * @param sun
     *              Le vecteur representant la source lumineuse
     */
    public ReliefShader(Projection proj, DigitalElevationModel dem, Vector3 sun) {
        this.proj = proj;
        this.dem = dem;
        this.sun = sun;
    }
    
    /**
     * Construit une BufferedImage du relief d'une zone delimitee par deux points, et floutee selon un certain rayon
     * 
     * @param bl
     *              Le point bas-gauche de la zone a dessiner
     * @param tr
     *              Le point haut-droite de la zone a dessiner
     * @param width
     *              La largeur de l'image finale
     * @param height
     *              La hauteur de l'image finale
     * @param radius
     *              Le rayon de floutage a appliquer a l'image
     * @return
     *              L'image de la partie du DigitalElevationModel choisie, floutee selon le rayon
     * @throws
     *              Si la largeur ou la hauteur est negative ou nulle, ou si le rayon est negatif
     */
    public BufferedImage shadedRelief(Point bl, Point tr, int width, int height, double radius) throws IllegalArgumentException {
        if (width <= 0)
            throw new IllegalArgumentException("Largeur invalide");
        if (height <= 0)
            throw new IllegalArgumentException("Hauteur invalide");
        if (radius < 0)
            throw new IllegalArgumentException("Rayon de floutage invalide");
        
        if (radius > 0) {
            int rayon = (int) Math.ceil(radius) + 1;
            
            int newWidth = width + 2 * rayon;
            int newHeight = height + 2 * rayon;
            
            Function<Point, Point> transform = Point.alignedCoordinateChange(new Point(rayon, height + rayon), bl, new Point(width + rayon, rayon), tr);
            
            BufferedImage image = drawRelief(newWidth, newHeight, transform);
            
            float[] k = kernel(radius);
            
            Kernel kernelVert = new Kernel(1, k.length, k);
            Kernel kernelHoriz = new Kernel(k.length, 1, k);
            
            image = blur(image, kernelVert);
            image = blur(image, kernelHoriz);
            
            return image.getSubimage(rayon, rayon, width, height);
        }
        else {
            Function<Point, Point> transform = Point.alignedCoordinateChange(new Point(0, height), bl, new Point(width, 0), tr);
            
            return drawRelief(width, height, transform);
        }
    }
    
    /**
     * Dessine un relief non floute
     * 
     * @param width
     *              La largeur du relief
     * @param height
     *              La hauteur du relief
     * @param transform
     *              La transformation a utiliser pour passer des points du relief aux points du DigitalElevationModel
     * @return
     *              Une BufferedImage du relief
     */
    private BufferedImage drawRelief(int width, int height, Function<Point, Point> transform) {
        float cos;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                PointGeo point = proj.inverse(transform.apply(new Point(i, j)));
                cos = (float) sun.normalized().scalarProduct(dem.normalAt(point).normalized());
                
                float rg = (cos + 1) / 2;
                float b = (cos * .7f + 1) / 2;
                
                image.setRGB(i, j, new Color(rg, rg, b).getRGB()); 
            }
        }
        
        return image;
    }
    
    /**
     * Calcule le kernel unidimensionnel de floutage selon le rayon
     * 
     * @param r
     *              Le rayon de floutage
     * @return
     *              Un tableau de float contenant les coefficients de floutage
     */
    private float[] kernel(double r) {
        int rayon = (int) Math.ceil(r);
        int n = 2 * rayon + 1;
        float temp = 0f, sum = 0f;
        float[] gauss = new float[n];
        
        for (int i = 0; i < rayon + 1; ++i) { 
            temp = (float) Math.exp(- Math.pow((Math.abs(rayon - i)), 2) * 4.5 / Math.pow(r, 2));
            sum += 2 * temp;
            gauss[i] = temp;
        }
        
        sum -= temp;
        
        for (int i = 0; i < rayon + 1; ++i) {
            gauss[i] = gauss[i] / sum;
            gauss[n - i - 1] = gauss[i];
        }
        
        return gauss;
    }
    
    /**
     * Floute l'image donnee selon le Kernel donne
     * 
     * @param image
     *              L'image a flouter
     * @param k
     *              Le Kernel a utiliser
     * @return
     *              L'image floutee
     */
    private BufferedImage blur(BufferedImage image, Kernel k) {
        ConvolveOp op = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }
}
