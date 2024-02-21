package com.quickscythe.silver.utils.sprites;

import com.quickscythe.silver.utils.Resources;
import org.json2.JSONObject;

import java.awt.*;

public class Sprite implements Cloneable {

    private final JSONObject METADATA = new JSONObject();
    Image image;
    boolean flipped = false;
    Image flipped_image;

    public Sprite(SpriteSheet sheet, int x, int y, int width, int height) {
        METADATA.put("source", sheet.getSource());
        METADATA.put("x", x);
        METADATA.put("y", y);
        METADATA.put("width", width);
        METADATA.put("height", height);

        this.image = sheet.getBufferedImage().getSubimage(x, y, width, height);
        this.flipped_image = Resources.flipImage(Resources.toBufferedImage(this.image));


    }


    private Sprite(Image image) {
        this.image = image;
        this.flipped_image = Resources.flipImage(Resources.toBufferedImage(this.image));
    }

    public Image getImage() {
        if (flipped) System.out.println("flipped");
        return flipped ? flipped_image : image;
    }

    //    public void flip() {
//        flipped = !flipped;
//        System.out.println("Flip");
//    }
    public void flip(boolean flipped) {
        this.flipped = flipped;
    }


    public boolean flipped() {
        return flipped;
    }

    @Override
    public Sprite clone() {
        return new Sprite(new SpriteSheet(METADATA.getString("source"), METADATA.getInt("width"), METADATA.getInt("height")), METADATA.getInt("x"), METADATA.getInt("y"), METADATA.getInt("width"), METADATA.getInt("height"));
    }

    public JSONObject getMetadata() {
        return METADATA;
    }
}
