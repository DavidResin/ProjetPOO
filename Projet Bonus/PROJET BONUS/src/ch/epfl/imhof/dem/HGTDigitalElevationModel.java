package ch.epfl.imhof.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.regex.Pattern;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * Un modele digital d'élévation traitant les fichiers HGT
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public class HGTDigitalElevationModel implements DigitalElevationModel {
    
    private ShortBuffer buffer;
    private FileInputStream stream;
    private final double latitude, longitude;
    private final int side;
    private double delta;
    
    /**
     * Construit le modèle d'élévation à partir d'un fichier HGT
     * 
     * @param file
     *              Le fichier HGT à traiter
     * @throws IllegalArgumentException
     *              Si le nom du fichier ne suit pas le modèle {N,S} + {0-9}x2 + {E,W} + {0-9}x3 + ".hgt", ou si son nombre d'éléments n'est pas un carré impair
     * @throws IOException
     *              Si le fichier n'existe pas
     */
    public HGTDigitalElevationModel(File file) throws IllegalArgumentException, IOException {
        String name = file.getName();
        
        this.side = (int) Math.sqrt(file.length() / 2d);
        
        if (!file.exists())
            throw new IOException("Le fichier hgt fourni n'existe pas.");
        if (!(Pattern.matches("^[NS]\\d{2}[EW]\\d{3}\\.hgt$", name))) 
            throw new IllegalArgumentException("Le nom du fichier ne correspond pas au format requis.");
        if (side % 2 != 1)
            throw new IllegalArgumentException("Le nombre d'elements du fichier n'est pas un carre impair.");
                
        long length = file.length();
        
        stream = new FileInputStream(file);
        buffer = stream.getChannel()
            .map(MapMode.READ_ONLY, 0, length)
            .asShortBuffer();
        
        double latTemp = Math.toRadians(Integer.parseInt(name.substring(1, 3)));
        double longTemp = Math.toRadians(Integer.parseInt(name.substring(4, 7)));
        
        if (latTemp >= Math.PI / 2d || latTemp < - Math.PI / 2d || longTemp >= Math.PI || longTemp < - Math.PI)
            throw new IllegalArgumentException("Les coordonnées sont invalides");
        
        this.latitude = name.charAt(0) == 'N' ? latTemp : - latTemp;
        this.longitude = name.charAt(3) == 'E' ? longTemp : - longTemp;
        
        this.delta = Math.toRadians(1d / (side - 1));
    }
    
    public Vector3 normalAt(PointGeo p) throws IllegalArgumentException {
        double pLon = p.longitude();
        double pLat = p.latitude();
        
        double s = Earth.RADIUS * delta;
        
        if (pLat < latitude || pLat > latitude + Math.toRadians(1) || pLon < longitude || pLon > longitude + Math.toRadians(1))
            throw new IllegalArgumentException();
        
        int x = (int) ((pLon - longitude) / delta) ;
        int y = side - (int) ((pLat - latitude) / delta) - 1;
        
        short[] z = neighborsAt(x, y);
        
        return new Vector3(s * (z[0] - z[1] + z[2] - z[3]) / 2, s * (z[0] + z[1] - z[2] - z[3]) / 2, Math.pow(s, 2));
    }
    
    public short[] neighborsAt(int x, int y) {
        short z0 = buffer.get(side * y + x);
        short z1 = buffer.get(side * y + x + 1);
        short z2 = buffer.get(side * (y - 1) + x);
        short z3 = buffer.get(side * (y - 1) + x + 1);
        
        return new short[]{z0, z1, z2, z3};
    }
    
    public Vector3 normalAt(double x, double y, double offsetX, double offsetY) {
    	int tempX = (int) Math.floor(x + offsetX);
    	int tempY = (int) Math.floor(y + offsetY);
    	
    	double s = Earth.RADIUS * delta;
    	
    	short[] z = neighborsAt(tempX, tempY);
    	
    	return new Vector3(s * (z[0] - z[1] + z[2] - z[3]) / 2, s * (z[0] + z[1] - z[2] - z[3]) / 2, Math.pow(s, 2));
    }
    
	public short heightAt(double x, double y, double offsetX, double offsetY, double heightFactor) {
	    double xTemp = x + offsetX;
	    double yTemp = y + offsetY;
	    short[] points = neighborsAt((int) Math.floor(xTemp), (int) Math.floor(yTemp));
		double smallX = xTemp % 1;
		double smallY = yTemp % 1;
		
		if (smallX + smallY <= 1)
			return (short) (heightFactor * (points[0] + (points[1] - points[0]) * smallX + (points[2] - points[0]) * smallY));
		return (short) (heightFactor * (points[3] + (points[2] - points[3]) * (1 - smallX) + (points[1] - points[3]) * (1 - smallY)));
	}

    @Override
    public void close() throws IOException {
        stream.close();
        buffer = null;
    }
}
