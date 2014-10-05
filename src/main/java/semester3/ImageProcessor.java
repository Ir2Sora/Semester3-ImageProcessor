package semester3;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.concurrent.ForkJoinPool;

public class ImageProcessor {

   public static void parallelInvert(BufferedImage img) {
      WritableRaster wr = (WritableRaster) img.getData();
      new ForkJoinPool().invoke(new ImageInvertorAction(wr, 0, img.getWidth(), 0, img.getHeight()));
      img.setData(wr);
   }

   public static void serialInvert(BufferedImage img) {
      WritableRaster wr = (WritableRaster) img.getData();
      int[] pxs = wr.getPixels(0, 0, img.getWidth(), img.getHeight(), (int[]) null);
      
      for (int i = 0; i < pxs.length; i++) {
         pxs[i] = 256 - pxs[i];
      }
      
      wr.setPixels(0, 0, img.getWidth(), img.getHeight(), pxs);
      img.setData(wr);
   }
}
