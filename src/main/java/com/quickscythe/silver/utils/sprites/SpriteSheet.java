package com.quickscythe.silver.utils.sprites;

import com.quickscythe.silver.utils.Resources;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpriteSheet {

    private final Map<Integer, Sprite> SPRITE_HASHES = new HashMap<>();
    private final String SOURCE;
    BufferedImage image;
    int width;
    int height;

    SpriteSheet(String filepath, int size) {
        this(filepath, size, size);
    }

    SpriteSheet(String filepath, int width, int height) {
        this.image = Resources.getImage(filepath);
        this.width = width;
        this.height = height;
        this.SOURCE = filepath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }


    public Sprite getSprite(int x, int y) {
        return getSprite(x, y, width, height);
    }

    public Sprite getSprite(int x, int y, int size) {
        return getSprite(x, y, size, size);
    }

    public Sprite getSprite(int x, int y, int width, int height) {
        int hash = Objects.hash(x, y, width, height);
        if (SPRITE_HASHES.containsKey(hash)) return SPRITE_HASHES.get(hash);

        Sprite sprite = new Sprite(this, x, y, width, height);
        SPRITE_HASHES.put(hash, sprite);
        return sprite;

    }


    public String getSource() {
        return SOURCE;
    }
}
