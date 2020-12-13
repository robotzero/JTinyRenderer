package com.robotzero;

import com.robotzero.external.TGAReader;
import com.robotzero.external.TGAWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class JTinyRenderer {

  public static void main(String [] args) {
    String path = "result/image.bmp";
    Render render = new Render();
    try {
      int width = 100;
      int height = 100;
      BufferedImage image = new BufferedImage(100, 100, TYPE_INT_RGB);
      render.lineSecond(13, 20, 80, 40, image, Color.WHITE);
      render.lineSecond(20, 13, 40, 80, image, Color.RED);
      render.lineSecond(80, 40, 13, 20, image, Color.RED);
      image = render.flipVertically(image);
      int [] pixels = image.getRGB(0, 0, width, height, null, 0, width);

      byte [] buffer = TGAWriter.write(pixels, width, height, TGAReader.ARGB);
      FileOutputStream fos = new FileOutputStream(path.replace(".bmp", ".tga"));
      fos.write(buffer);
      fos.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
