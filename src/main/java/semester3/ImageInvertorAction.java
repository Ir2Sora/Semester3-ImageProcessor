package semester3;

import java.awt.image.WritableRaster;
import java.util.concurrent.RecursiveAction;

class ImageInvertorAction extends RecursiveAction {

   private static final int THRESHOLD = 500;
   private WritableRaster img;
   private int x1;
   private int x2;
   private int y1;
   private int y2;

   public ImageInvertorAction(WritableRaster img, int x1, int x2, int y1, int y2) {

      this.img = img;
      this.x1 = x1;
      this.x2 = x2;
      this.y1 = y1;
      this.y2 = y2;
   }

   @Override
   protected void compute() {
      if (x1 >= x2 || y1 >= y2) {
         return;
      }

      if (smallEnough()) {
         int[] pxs = img.getPixels(x1, y1, x2 - x1, y2 - y1, (int[]) null);

         for (int i = 0; i < pxs.length; i++) {
            pxs[i] = 256 - pxs[i];
         }

         img.setPixels(x1, y1, x2 - x1, y2 - y1, pxs);
         return;
      }

      int xMiddle = mean(x1, x2);
      int yMiddle = mean(y1, y2);

      invokeAll(new ImageInvertorAction(img, x1, xMiddle, y1, yMiddle),
              new ImageInvertorAction(img, xMiddle, x2, y1, yMiddle),
              new ImageInvertorAction(img, x1, xMiddle, yMiddle, y2),
              new ImageInvertorAction(img, xMiddle, x2, yMiddle, y2));
   }

   private boolean smallEnough() {
      return THRESHOLD > (x2 - x1) * (y2 - y1);
   }
   
   private int mean(int left, int right) {
      return left + (right - left) / 2;
   }
}
