package com.quickscythe.silver.game.world;

import com.quickscythe.silver.game.Camera;
import com.quickscythe.silver.utils.GameUtils;
import com.quickscythe.silver.utils.Location;
import com.quickscythe.silver.utils.sprites.Sprite;

import java.awt.*;

public class Tile {

    private final int X;
    private final int Y;

    private final World WORLD;

    private final Sprite SPRITE;

    Tile(World world, Location location, int index, Sprite sprite) {

        this.X = (int) Math.max(location.getX(), 0);
        this.Y = (int) Math.max(location.getY(), 0);

        this.WORLD = world;
        this.SPRITE = sprite;
    }


    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }


    public void draw(Graphics g, Camera camera) {
        if (getWorld() != null) {

            g.drawImage(SPRITE.getImage(), (int) (((this.X * GameUtils.TILE_SIZE) - (GameUtils.TILE_SIZE / 2)) - camera.getViewport().getMinX()), (int) (((this.Y * GameUtils.TILE_SIZE) - (GameUtils.TILE_SIZE / 2)) - camera.getViewport().getMinY()), GameUtils.TILE_SIZE, GameUtils.TILE_SIZE, null);


        }
    }

    public World getWorld() {
        return WORLD;
    }


}