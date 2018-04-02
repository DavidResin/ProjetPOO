package ch.epfl.imhof.bonus;

import static ch.epfl.imhof.painting.Color.rgb;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.SwissPainter;
import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.dem.DigitalElevationModel;
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
 * Une simulation de billes chutant le long des pentes d'un modele de relief numerique. Seuls les attributs qui seront utilises lors de la simulation sont memorises en attributs de classe pour des questions de lisibilite
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Flipper extends Application {

	private List<Ball> balls = new ArrayList<Ball>();
	private DigitalElevationModel hgtDem;
	private Image image;
	private int windowWidth, windowHeight;
	private boolean showVectors;
	private double offsetX, offsetY, gravity, bumpFactor, heightFactor;
	private Vector3 friction, dot5 = new Vector3(0.5);
	
	@Override
	public void start(Stage stage) throws Exception {
	    
		// Donnees independantes des arguments
        
        Projection proj = new CH1903Projection();
        OSMToGeoTransformer osmTransformer = new OSMToGeoTransformer(proj);
        Vector3 sun = new Vector3(-1, 1, 1);
        
        
        // Obtention des donnees des arguments et test de leur validite
        
        List<String> args = getParameters().getRaw();
        
        String osmFileName = args.get(0);
        String hgtFileName = args.get(1);
        String imageName = args.get(7);
        double blLong = Math.toRadians(Double.parseDouble(args.get(2)));
        double blLat = Math.toRadians(Double.parseDouble(args.get(3)));
        double trLong = Math.toRadians(Double.parseDouble(args.get(4)));
        double trLat = Math.toRadians(Double.parseDouble(args.get(5)));
        double res = Double.parseDouble(args.get(6));
        double tempX = (Double.parseDouble(args.get(4)) % 1) * 3601;
        double tempY = (1 - Double.parseDouble(args.get(3)) % 1) * 3601;
        boolean altitudeMap = Boolean.parseBoolean(args.get(14));
        
        offsetX = (Double.parseDouble(args.get(2)) % 1) * 3601;
        offsetY = (1 - Double.parseDouble(args.get(5)) % 1) * 3601;
        gravity = Double.parseDouble(args.get(8));
        bumpFactor = Double.parseDouble(args.get(9));
        heightFactor = Double.parseDouble(args.get(10));
        friction = new Vector3(Double.parseDouble(args.get(11)));
        windowWidth = Integer.parseInt(args.get(12));
        showVectors = Boolean.parseBoolean(args.get(13));
        
        if (gravity <= 0)
			throw new IllegalArgumentException("La gravite ne peut etre negative ou nulle");
		if (bumpFactor < 0)
			throw new IllegalArgumentException("La force de rebond des bords ne peut etre negative");
		if (heightFactor < 0)
			throw new IllegalArgumentException("Le facteur d'amplitude des pentes ne peut etre negatif");
		if (friction.x() < 0 || friction.x() > 1)
			throw new IllegalArgumentException("Le coefficient de frottement doit etre compris entre 0 et 1");
		
		
		// Calcul des donnees relatives aux arguments
        
        PointGeo blDeg = new PointGeo(blLong, blLat);
        PointGeo trDeg = new PointGeo(trLong, trLat);
		Point bl = proj.project(blDeg);
        Point tr = proj.project(trDeg);
        double resDPM = res / 0.0254;
        int height = (int) Math.round(Earth.RADIUS * resDPM * (trLat - blLat) / 25000);
        int width = (int) Math.round(height * (tr.x() - bl.x()) / (tr.y() - bl.y()));
        String mapName = imageName.substring(0, 1).toUpperCase() + imageName.substring(1, imageName.length() - 4);
        File hgtFile = new File("data/DEMs/" + hgtFileName);
        
        hgtDem = new HGTDigitalElevationModel(hgtFile);
        windowHeight = windowWidth * height / width;
        
        double xFactor = (tempX - offsetX) / windowWidth;
        double yFactor = (tempY - offsetY) / windowHeight;
        
        
        // Dessin de l'image (carte d'altitude ou carte topographique ombree selon le parametre altitudeMap)
        
        BufferedImage awtImage = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        
        if (altitudeMap) {
        	int n, min = 1 << 16, max = 0;
        	double iTemp, jTemp, temp, diff;
        	
        	for (int i = 0; i < windowWidth; ++i) {
        		for (int j = 0; j < windowHeight; ++j) {
        			iTemp = i * xFactor;
        			jTemp = j * yFactor;
        			temp = hgtDem.heightAt(iTemp, jTemp, offsetX, offsetY, heightFactor);
        			if (temp < min)
        				min = (int) temp;
        			if (temp > max)
        				max = (int) temp;
        		}
        	}
        	
        	diff = Math.log(max - min) / (Math.log(2));
        	
        	for (int i = 0; i < windowWidth; ++i) {
        		for (int j = 0; j < windowHeight; ++j) {
        			iTemp = i * xFactor;
        			jTemp = j * yFactor;
        			n = (int) ((hgtDem.heightAt(iTemp, jTemp, offsetX, offsetY, heightFactor) - min) / diff);
        			n <<= 8;
        			n |= (int) ((hgtDem.heightAt(iTemp, jTemp, offsetX, offsetY, heightFactor) - min) / diff);
        			n <<= 8;
        			n |= (int) ((hgtDem.heightAt(iTemp, jTemp, offsetX, offsetY, heightFactor) - min) / diff);
        			awtImage.setRGB(i, j, n);
        		}
        	}
        }
        else {
        	double radius = resDPM * 0.0017;
        	
        	OSMMap osmMap = OSMMapReader.readOSMFile("data/maps/" + osmFileName, true);
            Map map = osmTransformer.transform(osmMap);
            
            Java2DCanvas canvas = new Java2DCanvas(bl, tr, width, height, res, Color.WHITE);
            
            Painter painter = SwissPainter.painter();
            painter.drawMap(map, canvas);
            
            BufferedImage mapImage = canvas.image();
        	
        	ReliefShader shader = new ReliefShader(proj, hgtDem, sun);
            BufferedImage reliefImage = shader.shadedRelief(bl, tr, width, height, radius);
            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            for (int i = 0; i < width; ++i)
                for (int j = 0; j < height; ++j)
                    finalImage.setRGB(i, j, rgb(mapImage.getRGB(i, j)).multiply(rgb(reliefImage.getRGB(i, j))).convert().getRGB());
        
            Graphics g = awtImage.createGraphics();
            g.drawImage(finalImage, 0, 0, windowWidth, windowHeight, null);
            g.dispose();
        }
        
        image = SwingFXUtils.toFXImage(awtImage, null);
		
        
        // Mise en place des elements graphiques
        
        Canvas canvasFXImage = new Canvas(windowWidth, windowHeight);
        GraphicsContext gcImage = canvasFXImage.getGraphicsContext2D();
        Canvas canvasFX = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvasFX.getGraphicsContext2D();
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.setFill(javafx.scene.paint.Color.RED);
        gcImage.drawImage(image, 0.0, 0.0);
        
        Group root = new Group();
        root.getChildren().addAll(canvasFXImage, canvasFX);
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			double temp;
			public void handle(MouseEvent e) {
				temp = Math.random() * 20000 + 10000;
				addBall((int) Math.cbrt(temp), 100 * temp, e.getSceneX(), e.getSceneY(), hgtDem.heightAt(e.getSceneX(), e.getSceneY(), offsetX, offsetY, heightFactor));
			}
		});
		stage.setResizable(false);
		stage.setWidth(windowWidth);
		stage.setHeight(windowHeight);
        stage.setTitle("Flipper sur " + mapName);
        stage.setScene(new Scene(root));
        stage.show();
        
        
        // Definition du comportement physique des boules
        
        new AnimationTimer() {
			
			@Override
			public void handle(long now) {
			    
			    Vector3 nowVect = new Vector3(0.05);
			    Vector3 nowVect2 = new Vector3(0.0025);
			    Vector3 g = new Vector3(0, 0, - gravity);
			    Vector3 tempPos, tempSpeed, tempAccel, tempNormal;
			    int tempSize;
			    
			    gc.clearRect(0, 0, windowWidth, windowHeight);
			    
				for (Ball ball : balls) {
					
					tempAccel = g;
					tempSpeed = tempAccel.multiply(nowVect).add(ball.getSpeed());
					tempPos = dot5.multiply(tempAccel.multiply(nowVect2)).add(tempSpeed.multiply(nowVect)).add(ball.getPos());
					tempSize = ball.getSize();
					
					if (tempPos.x() < 0) {
					    tempPos = tempPos.withX(0);
					    tempSpeed = tempSpeed.withX(- tempSpeed.x() * bumpFactor);
					}
					else if (tempPos.x() > windowWidth) {
					    tempPos = tempPos.withX(windowWidth);
					    tempSpeed = tempSpeed.withX(- tempSpeed.x() * bumpFactor);
					}
					
					if (tempPos.y() < 0) {
                        tempPos = tempPos.withY(0);
                        tempSpeed = tempSpeed.withY(- tempSpeed.y() * bumpFactor);
                    }
                    else if (tempPos.y() > windowHeight) {
                        tempPos = tempPos.withY(windowHeight);
                        tempSpeed = tempSpeed.withY(- tempSpeed.y() * bumpFactor);
                    }
					
					if (tempPos.z() < hgtDem.heightAt(tempPos.x() * xFactor, tempPos.y() * yFactor, offsetX, offsetY, heightFactor)) {
						tempPos = new Vector3(tempPos.x(), tempPos.y(), hgtDem.heightAt(tempPos.x() * xFactor, tempPos.y() * yFactor, offsetX, offsetY, heightFactor));
						tempNormal = hgtDem.normalAt(tempPos.x() * xFactor, tempPos.y() * yFactor, offsetX, offsetY);
						tempAccel = tempAccel.add(new Vector3(tempNormal.normalized().scalarProduct(tempAccel.normalized())).multiply(tempNormal.normalized().multiply(new Vector3(- tempAccel.norm()))));
						tempSpeed = tempSpeed.add(new Vector3(tempNormal.normalized().scalarProduct(tempSpeed.normalized())).multiply(tempNormal.normalized().multiply(new Vector3((1 + bumpFactor) * (- tempSpeed.norm()))))).multiply(friction);
					}
					
					if (showVectors) {
					    gc.setStroke(javafx.scene.paint.Color.GREEN);
					    gc.strokeLine(tempPos.x(), tempPos.y(), tempPos.x() + tempSpeed.x() * 5, tempPos.y() + tempSpeed.y() * 5);
					    gc.setStroke(javafx.scene.paint.Color.BLUE);
					    gc.strokeLine(tempPos.x(), tempPos.y(), tempPos.x() + tempAccel.x() * 5, tempPos.y() + tempAccel.y() * 5);
					    gc.setStroke(javafx.scene.paint.Color.BLACK);
					}
					
					ball.moveTo(tempPos, tempSpeed, tempAccel.multiply(new Vector3(1 / ball.getMass())));
					gc.fillOval(tempPos.x() - tempSize / 2, tempPos.y() - tempSize / 2, tempSize, tempSize);
					gc.strokeOval(tempPos.x() - tempSize / 2, tempPos.y() - tempSize / 2, tempSize, tempSize);
				}
			}
		}.start();
	}
	
	/**
	 * Ajoute une nouvelle balle a la liste, selon les parametres donnes
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
	 */
	public void addBall(int size, double mass, double x, double y, double z) {
		if (z < hgtDem.heightAt(x, y, offsetX, offsetY, heightFactor))
			z = hgtDem.heightAt(x, y, offsetX, offsetY, heightFactor);
		
		balls.add(new Ball(size, mass, x, y, z));
	}
}
