package com.quickscythe.titanium.gui;

import com.quickscythe.titanium.game.Camera;
import com.quickscythe.titanium.utils.GameUtils;
import com.quickscythe.titanium.utils.Location;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;

public class GuiElement implements GuiInterface {

    public boolean clicked = false;
    Location location;
    double width;
    double height;
    boolean visible = false;
    boolean hover = false;
    Rectangle bounds;

    public GuiElement(Location location, double width, double height) {
        this.location = location;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle((int) (this.location.getX() - this.width / 2), (int) (this.location.getY() - this.height / 2), (int) width, (int) height);
    }

    public boolean contains() {
        return visible && bounds.contains(GameUtils.getInputManager().getRelativeX(), GameUtils.getInputManager().getRelativeY());
    }


    public void draw(Camera camera, Graphics g) {
        if (visible) {
            Color color = g.getColor();
            g.setColor(hover ? clicked ? Color.RED : Color.BLUE : Color.GREEN);
            g.fillRect((int) (location.getX() - width / 2), (int) (location.getY() - height / 2), (int) width, (int) height);
            g.setColor(color);
        }
    }

    @Override
    public void hover() {
        hover = true;
    }

    @Override
    public void click() {
        clicked = true;
    }

    public void unclick() {
        clicked = false;
    }

    @Override
    public void unhover() {
        hover = false;
    }

    @Override
    public void display() {
        visible = true;
    }

    @Override
    public void hide() {
        visible = false;
    }
}
