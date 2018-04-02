package ch.epfl.imhof.dem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import ch.epfl.imhof.PointGeo;

public class HGTDigitalElevationModelTest {

    @Test (expected = IllegalArgumentException.class)
    public void builderFailsOnIncorrectName() throws IOException {
        File file = new File(getClass().getResource("/testFiles/dummy.hgt").getFile());
        HGTDigitalElevationModel test = new HGTDigitalElevationModel(file);
        test.close();
    }

    @Test (expected = IOException.class)
    public void builderFailsOnNonExistentFile() throws IllegalArgumentException, IOException {
        File file = new File("test");
        HGTDigitalElevationModel test = new HGTDigitalElevationModel(file);
        test.close();
    }
    
    @Test
    public void builderBuildsOnCorrectName() throws IllegalArgumentException, IOException {
        File file = new File(getClass().getResource("/DEMs/N46E006.hgt").getFile());
        HGTDigitalElevationModel test = new HGTDigitalElevationModel(file);
        test.close();
    }
    
    @Test
    public void N46E007Test() throws IllegalArgumentException, IOException {
        File file = new File(getClass().getResource("/DEMs/N46E007.hgt").getFile());
        HGTDigitalElevationModel test = new HGTDigitalElevationModel(file);
        BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        double c;
        int n;
        for (int i = 0; i < 800; ++i) {
            for (int j = 0; j < 800; ++j) {
                c = (test.normalAt(new PointGeo(Math.toRadians(7.2 + i / 1333d), Math.toRadians(46.8 - j / 1333d))).normalized().y() + 1) / 2d;
                n = (int)(c * 255.9999);
                n <<= 8;
                n += (int)(c * 255.9999);
                n <<= 8;
                n += (int)(c * 255.9999);
                image.setRGB(i, j, n);
            }
        }
        test.close();
        ImageIO.write(image, "png", new File("hgtTest.png"));
    }
}
