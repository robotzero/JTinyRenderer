package com.robotzero;

import com.robotzero.external.TGAReader;
import com.robotzero.external.TGAWriter;
import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjReader;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class JTinyRenderer {

  public static void main(String [] args) {
    String path = "result/image.bmp";
    Render render = new Render();
    int width = 800;
    int height = 800;
//    BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
//
//    // Read an OBJ file
//    try (InputStream objInputStream =
//        new FileInputStream("./data/head.obj")) {
//      Obj model = ObjReader.read(objInputStream);
//
//      for (int i = 0; i < model.getNumFaces(); i++) {
//        ObjFace face = model.getFace(i);
//        for (int j = 0; j < 3; j++) {
//          FloatTuple v0 = model.getVertex(face.getVertexIndex(j));
//          FloatTuple v1 = model.getVertex(face.getVertexIndex((j + 1) % 3));
//          int x0 = (int) ((v0.getX() + 1.0) * width / 2.0);
//          int y0 = (int) ((v0.getY() + 1.0) * height / 2.0);
//          int x1 = (int) ((v1.getX() + 1.0) * width / 2.0);
//          int y1 = (int) ((v1.getY() + 1.0) * height / 2.0);
//          try {
//            render.lineThird(x0, y0, x1, y1, image, Color.WHITE);
//          } catch (Exception e) {
//            System.out.println("START");
//            System.out.println(x0);
//            System.out.println(x1);
//            System.out.println(y0);
//            System.out.println(y1);
//            System.out.println("END");
//          }
//        }
//      }
//
//      image = render.flipVertically(image);
//      int [] pixels = image.getRGB(0, 0, width, height, null, 0, width);
//
//      byte [] buffer = TGAWriter.write(pixels, width, height, TGAReader.ARGB);
//      FileOutputStream fos = new FileOutputStream(path.replace(".bmp", ".tga"));
//      fos.write(buffer);
//      fos.close();
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    try {
      int width1 = 200;
      int height1 = 200;
      BufferedImage image1 = new BufferedImage(width1, height1, TYPE_INT_RGB);

      Vector2f[] t0 = {new Vector2f(10, 70), new Vector2f(50, 160), new Vector2f(70, 80)};
      Vector2f[] t1 = {new Vector2f(180, 50), new Vector2f(150, 1), new Vector2f(70, 180)};
      Vector2f[] t2 = {new Vector2f(180, 150), new Vector2f(120, 160), new Vector2f(130, 180)};
      render.triangle(t0[0], t0[1], t0[2], image1, Color.GREEN);
      render.triangle(t1[0], t1[1], t1[2], image1, Color.GREEN);
      render.triangle(t2[0], t2[1], t2[2], image1, Color.RED);

      image1 = render.flipVertically(image1);
      int [] pixels = image1.getRGB(0, 0, width1, height1, null, 0, width1);

      byte [] buffer = TGAWriter.write(pixels, width1, height1, TGAReader.ARGB);
      FileOutputStream fos = new FileOutputStream(path.replace(".bmp", ".tga").replace("image.", "image1."));
      fos.write(buffer);
      fos.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
