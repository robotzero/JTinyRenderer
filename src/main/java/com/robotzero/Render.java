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

  void lineThird(int x0, int y0, int x1, int y1, BufferedImage image, Color color) {
    boolean steep = false;
    if (Math.abs(x0 - x1) < Math.abs(y0 - y1)) {
      int tempx0 = x0;
      int tempy0 = y0;
      int tempx1 = x1;
      int tempy1 = y1;
      x0 = tempy0;
      y0 = tempx0;
      x1 = tempy1;
      y1 = tempx1;
      steep = true;
    }

    if (x0 > x1) {
      int tempx0 = x0;
      int tempy0 = y0;
      int tempx1 = x1;
      int tempy1 = y1;
      x0 = tempx1;
      x1 = tempx0;
      y0 = tempy1;
      y1 = tempy0;
    }

    int dx = x1 - x0;
    int dy = y1 - y0;

    int derror2 = Math.abs(dy) * 2;
    float error2 = 0;
    int y = y0;
    for (int x = x0; x <= x1; x++) {
      if (steep) {
        image.setRGB(y, x, color.getRGB()); // if transposed, de−transpose
      } else {
        image.setRGB(x, y, color.getRGB());
      }

      error2 += derror2;
      if (error2 > dx) {
        y += (y1 > y0) ? 1 : -1;
        error2 -= dx * 2;
      }
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

//  bool steep = false;
//    if (std::abs(x0-x1)<std::abs(y0-y1)) { // if the line is steep, we transpose the image
//    std::swap(x0, y0);
//    std::swap(x1, y1);
//    steep = true;
//    }
//    if (x0>x1) { // make it left−to−right
//    std::swap(x0, x1);
//    std::swap(y0, y1);
//    }
//    for (int x=x0; x<=x1; x++) {
//    float t = (x-x0)/(float)(x1-x0);
//    int y = y0*(1.-t) + y1*t;
//    if (steep) {
//    image.set(y, x, color); // if transposed, de−transpose
//    } else {
//    image.set(x, y, color);
//    }
//    }
