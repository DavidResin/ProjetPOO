package ch.epfl.imhof;

import static ch.epfl.imhof.painting.Color.rgb;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import ch.epfl.imhof.dem.Earth;
import ch.epfl.imhof.dem.HGTDigitalElevationModel;
import ch.epfl.imhof.dem.ReliefShader;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.Color;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.painting.Painter;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

/**
 * Le Main du programme
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Main {

    /**
     * La methode main, dessinant une carte geographique avec relief a partir des donnees passees en argument
     * 
     * @param args
     *              Les arguments, contenant dans l'ordre :
     *                 1) Le nom (chemin) d'un fichier OSM compresse avec gzip contenant les donnees de la carte a dessiner
     *                 2) Le nom (chemin) d'un fichier HGT couvrant la totalite de la zone de la carte a dessiner, zone tampon incluse
     *                 3) La longitude du point bas-gauche de la carte, en degres
     *                 4) La latitude du point bas-gauche de la carte, en degres
     *                 5) La longitude du point haut-droite de la carte, en degres
     *                 6) La latitude du point haut-droite de la carte, en degres
     *                 7) La resolution de l'image a dessiner, en points par pouce (dpi)
     *                 8) Le nom (chemin) du fichier PNG a generer
     * @throws Exception
     *              Si un argument est invalide
     */
    public static void main(String[] args) throws Exception {
        
        // Donnees independantes des arguments
        
        Projection proj = new CH1903Projection();
        OSMToGeoTransformer osmTransformer = new OSMToGeoTransformer(proj);
        Vector3 sun = new Vector3(-1, 1, 1);
        
        
        // Obtention des donnees des arguments
        
        String osmFileName = args[0];
        String hgtFileName = args[1];
        
        double blLong = Math.toRadians(Double.parseDouble(args[2]));
        double blLat = Math.toRadians(Double.parseDouble(args[3]));
        
        double trLong = Math.toRadians(Double.parseDouble(args[4]));
        double trLat = Math.toRadians(Double.parseDouble(args[5]));
        
        double res = Double.parseDouble(args[6]);
        
        String imageName = args[7];
        
        
        // Calcul des donnees relatives aux arguments
        
        Point bl = proj.project(new PointGeo(blLong, blLat));
        Point tr = proj.project(new PointGeo(trLong, trLat));
        
        double resDPM = res / 0.0254;
        
        int height = (int) Math.round(Earth.RADIUS * resDPM * (trLat - blLat) / 25000d);
        int width = (int) Math.round(height * (tr.x() - bl.x()) / (tr.y() - bl.y()));
        
        double radius = resDPM * 0.0017;
        
        
        // Dessin de la carte geographique
        
        OSMMap osmMap = OSMMapReader.readOSMFile("data/maps/" + osmFileName, true);
        Map map = osmTransformer.transform(osmMap);
        
        Java2DCanvas canvas = new Java2DCanvas(bl, tr, width, height, res, Color.WHITE);
        
        Painter painter = SwissPainter.painter();
        painter.drawMap(map, canvas);
        
        BufferedImage mapImage = canvas.image();
        
        
        // Dessin de la carte des reliefs
        
        File hgtFile = new File("data/DEMs/" + hgtFileName);
        HGTDigitalElevationModel hgtDem = new HGTDigitalElevationModel(hgtFile);
        
        ReliefShader shader = new ReliefShader(proj, hgtDem, sun);
        BufferedImage reliefImage = shader.shadedRelief(bl, tr, width, height, radius);
        
        hgtDem.close();
        
        
        // Combinaison des deux images et enregistrement
        
        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < width; ++i) 
            for (int j = 0; j < height; ++j)
                finalImage.setRGB(i, j, rgb(mapImage.getRGB(i, j)).multiply(rgb(reliefImage.getRGB(i, j))).convert().getRGB());
        
        ImageIO.write(finalImage, "png", new File(imageName));
    }
}
