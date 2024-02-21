package com.quickscythe.silver.game.object;

import com.quickscythe.silver.game.Camera;
import com.quickscythe.silver.utils.Direction;
import com.quickscythe.silver.utils.GameUtils;
import com.quickscythe.silver.utils.Location;
import com.quickscythe.silver.utils.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    protected Sprite sprite = null;
    protected List<GameObject> intersections = new ArrayList<>();
    protected List<Direction> collision_directions = new ArrayList<>();
    protected int width = GameUtils.TILE_SIZE;
    protected int height = GameUtils.TILE_SIZE;
    Location location;
    Rectangle bounds;

    public GameObject(Location location) {
        this.location = location;
        this.bounds = new Rectangle((int) location.getX() - (width / 2), (int) location.getY() - (height / 2), width, height);
    }

    public void update(List<GameObject> objects) {
        bounds.setBounds((int) (location.getX() - ((width / 2))), (int) (location.getY() - ((height / 2))), (int) (width), (int) (height));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;

    }


    public void draw(Graphics g, Camera camera) {


        if (camera != null) {
            if (getSprite() == null)
                g.fillRect((int) (getRelativeX(camera) - getRelativeWidth(camera) / 2), (int) (getRelativeY(camera) - getRelativeHeight(camera) / 2), getRelativeWidth(camera), getRelativeHeight(camera));
            else {

                g.drawImage(getSprite().getImage(), (int) (getRelativeX(camera) - getRelativeWidth(camera) / 2), (int) (getRelativeY(camera) - getRelativeHeight(camera) / 2), getRelativeWidth(camera), getRelativeHeight(camera), null);
            }
        }

        if (GameUtils.debug()) {

            g.setColor(Color.RED);
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public int getRelativeWidth(Camera camera) {
        return (int) (bounds.getWidth());
    }

    public int getRelativeHeight(Camera camera) {
        return (int) (bounds.getHeight());

    }

    public int getRelativeX(Camera camera) {
        return (int) (getLocation().getX() - (camera.getViewport().getBounds().getMinX()));
//        return (int) (((bounds.getX() + (width / 2)) - camera.getViewport().getX()) * camera.getScaleX());
    }

    public int getRelativeY(Camera camera) {
        return (int) (getLocation().getY() - (camera.getViewport().getBounds().getMinY()));
//        return (int) (((bounds.getY() + (height / 2)) - camera.getViewport().getY()) * camera.getScaleY());
    }

    public Location getLocation() {
        return location;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isCollidable() {
        return !collision_directions.isEmpty();
    }

    public boolean collides(Direction direction) {
        return collision_directions.contains(direction);
    }
}
