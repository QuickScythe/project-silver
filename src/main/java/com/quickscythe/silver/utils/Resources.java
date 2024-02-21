package com.quickscythe.silver.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class Resources {

//    private static Map<GraphicsDevice, Map<BufferedImage, VolatileImage>> stored_conversions = new HashMap();
//    private static Map<BufferedImage,VolatileImage> getImageConversions(){
//        if(!stored_conversions.containsKey(Main.getWindow().getScreen().getCamera().getGraphicsDevice())) stored_conversions.put(Main.getWindow().getScreen().getCamera().getGraphicsDevice(), new HashMap<>());
//        return stored_conversions.get(Main.getWindow().getScreen().getCamera().getGraphicsDevice());
//    }

    //    public static BufferedImage getImage(String filename) {
////        Thread.currentThread().getContextClassLoader().getResource()
//        return toBufferedImage(new ImageIcon(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filename))).getImage());
//    }
    public static BufferedImage getImage(String filename) {
//        Thread.currentThread().getContextClassLoader().getResource()
        return toBufferedImage(new ImageIcon(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filename))).getImage());
    }

    public static InputStream getFile(String filename) {

        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
    }

    public static FileInputStream getFileInputStream(String filename) throws IOException {

        Path temp = Files.createTempFile("resource",".mp3");
        Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename), temp, StandardCopyOption.REPLACE_EXISTING);
        return new FileInputStream(temp.toFile());
    }

    public static URL getResource(String filename) {
        return Thread.currentThread().getContextClassLoader().getResource(filename);
    }


//    public static VolatileImage createImage(int width, int height) {
//
//        //Main.getWindow().getScreen().getCamera().getGraphicsDevice().getDefaultConfiguration().createCompatibleVolatileImage(img.getWidth(null),img.getHeight(null));
//        return Main.getWindow().getScreen().getCamera().getGraphicsDevice().getDefaultConfiguration().createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);
//    }

    public static BufferedImage flipImage(BufferedImage image) {


//        return toBufferedImage(image.getScaledInstance(-image.getWidth(), image.getHeight(),1));
//
//
////        image = toBufferedImage(image);
//
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
        return createTransformed(image, at);

    }

//    public static VolatileImage createImage(int width, int height) {
//
//        //Main.getWindow().getScreen().getCamera().getGraphicsDevice().getDefaultConfiguration().createCompatibleVolatileImage(img.getWidth(null),img.getHeight(null));
//        return Main.getWindow().getScreen().getCamera().getGraphicsDevice().getDefaultConfiguration().createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);
//    }


    private static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return (newImage);
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);

        // Draw the image on to the buffered image
        Graphics bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }


}
