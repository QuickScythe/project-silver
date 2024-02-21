package com.quickscythe.silver.gui;

import com.quickscythe.silver.game.Camera;
import com.quickscythe.silver.utils.GameUtils;
import com.quickscythe.silver.utils.InputListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class Gui implements InputListener {


    private final List<GuiElement> ELEMENTS = new ArrayList<>();
    private final List<GuiElement> ADD_ELEMENTS = new ArrayList<>();
    private final Rectangle BOUNDS = new Rectangle(0, 0, 0, 0);

    String id;

    Gui(String id) {
        this.id = id;
        GameUtils.getInputManager().addListener(this);
    }

    public void draw(Camera camera, Graphics g) {

        for (GuiElement elem : ADD_ELEMENTS) {
            elem.display();
            ELEMENTS.add(elem);

        }
        ADD_ELEMENTS.clear();

        int minX = (int) ELEMENTS.get(0).bounds.getMinX();
        int maxX = (int) ELEMENTS.get(0).bounds.getMaxX();
        int minY = (int) ELEMENTS.get(0).bounds.getMinY();
        int maxY = (int) ELEMENTS.get(0).bounds.getMaxY();
        for (GuiElement element : ELEMENTS) {
            element.draw(camera, g);
            if (element.bounds.getMinX() < minX) minX = (int) element.bounds.getMinX();
            if (element.bounds.getMaxX() > maxX) maxX = (int) element.bounds.getMaxX();
            if (element.bounds.getMinY() < minY) minY = (int) element.bounds.getMinY();
            if (element.bounds.getMaxY() > maxY) maxY = (int) element.bounds.getMaxY();
        }
        BOUNDS.setBounds(minX - 10, minY - 10, maxX - minX + 20, maxY - minY + 20);
        g.drawRect((int) BOUNDS.getMinX(), (int) BOUNDS.getMinY(), BOUNDS.width, BOUNDS.height);
    }

    public void close() {
        for (GuiElement elem : ELEMENTS) {
            elem.hide();
        }
    }

    public void addElement(GuiElement element) {
        ADD_ELEMENTS.add(element);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (BOUNDS.contains(e.getPoint())) {
            System.out.println("Clicked in GUI bounds.");
        }
        for (GuiElement element : ELEMENTS) {
            if (element.contains()) element.click();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GuiElement element : ELEMENTS) {
            if (element.clicked) element.unclick();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GuiElement element : ELEMENTS) {
            if (element.contains()) element.hover();
            else element.unhover();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }


}
