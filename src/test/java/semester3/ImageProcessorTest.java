package semester3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.junit.Test;

public class ImageProcessorTest {

   @Test
   public void testInvert() throws IOException, URISyntaxException {
      URL url = this.getClass().getResource("/test.JPG");
      File testPic = new File(url.getFile());

      BufferedImage img = ImageIO.read(testPic);

      long start = System.nanoTime();
      ImageProcessor.parallelInvert(img);
      long end = System.nanoTime();

      System.out.println("parallel invert");
      System.out.printf("duration: %,d \n", end - start);

      ImageIO.write(img, "jpg", new File("dest.JPG"));
   }

   @Test
   public void testSerialInvert() throws IOException, URISyntaxException {
      URL url = this.getClass().getResource("/test.JPG");
      File testPic = new File(url.getFile());

      BufferedImage img = ImageIO.read(testPic);

      long start = System.nanoTime();
      ImageProcessor.serialInvert(img);
      long end = System.nanoTime();

      System.out.println("serial invert");
      System.out.printf("duration: %,d \n", end - start);

      ImageIO.write(img, "jpg", new File("dest.JPG"));
   }
}
