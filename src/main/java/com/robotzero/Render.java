package com.robotzero;

import org.joml.Vector2f;
import org.joml.Vector2i;

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

  public void triangle(Vector2f t0, Vector2f t1, Vector2f t2, BufferedImage image, Color color) {

    if (t0.y > t1.y) swap(t0, t1);
    if (t0.y > t2.y) swap(t0, t2);
    if (t1.y > t2.y) swap(t1, t2);
//    lineThird(t0.x, t0.y, t1.x, t1.y, image, Color.GREEN);
//    lineThird(t1.x, t1.y, t2.x, t2.y, image, Color.GREEN);
//    lineThird(t2.x, t2.y, t0.x, t0.y, image, Color.RED);
//    lineThird(t0.x, t0.y, t1.x, t1.y, image, color);
//    lineThird(t1.x, t1.y, t2.x, t2.y, image, color);
//    lineThird(t2.x, t2.y, t0.x, t0.y, image, color);

    int total_height = (int) t2.y - (int) t0.y;
    for (int y = (int)t0.y; y <= (int)t1.y; y++) {
      int segment_height = (int) t1.y - (int) t0.y + 1;
      float alpha = (float) (y - t0.y) / total_height;
      float beta  = (float) (y - t0.y) / segment_height; // be careful with divisions by zero
      Vector2f A = (new Vector2f(t2).sub(t0)).mul(alpha).add(t0);
      Vector2f B = (new Vector2f(t1).sub(t0)).mul(beta).add(t0);
      image.setRGB((int)A.x, y, Color.RED.getRGB());
      image.setRGB((int)B.x, y, Color.GREEN.getRGB());
    }
  }

  private void swap(Vector2f v1, Vector2f v2) {
    Vector2f v1old = new Vector2f(v1.x, v1.y);
    Vector2f v2old = new Vector2f(v2.x, v2.y);

    v1.x = v2.x;
    v1.y = v2.y;
    v2.x = v1old.x;
    v2.y = v1old.y;
  }
}
