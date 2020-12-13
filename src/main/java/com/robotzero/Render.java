package com.robotzero;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Render {
  public void line(int x0, int y0, int x1, int y1, BufferedImage image, Color color) {
    for (float t = 0.0f; t < 1.0f; t +=0.01) {
      int x = (int) (x0 + (x1 - x0) * t);
      int y = (int) (y0 + (y1 - y0) * t);
      image.setRGB(x, y, color.getRGB());
    }
  }

  void lineSecond(int x0, int y0, int x1, int y1, BufferedImage image, Color color) {
    for (int x = x0; x <= x1; x++) {
      float t = (x - x0) / (float) (x1 - x0);
      int y = (int) (y0 * (1.0f - t) + y1 * t);
      image.setRGB(x, y, color.getRGB());
    }
  }

  public BufferedImage flipVertically(BufferedImage image) {
    AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
    tx.translate(0, -image.getHeight());
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    image = op.filter(image, null);
    return image;
  }
}
