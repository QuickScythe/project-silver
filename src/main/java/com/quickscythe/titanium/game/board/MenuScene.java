package com.quickscythe.titanium.game.board;

import com.quickscythe.titanium.Main;
import com.quickscythe.titanium.game.Camera;
import com.quickscythe.titanium.gui.GuiButton;
import com.quickscythe.titanium.gui.GuiElement;
import com.quickscythe.titanium.utils.GameUtils;
import com.quickscythe.titanium.utils.Location;
import com.quickscythe.titanium.utils.Resources;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuScene implements Scene, MouseMotionListener, MouseListener {

    Map<Integer, Image> parallax_backgrounds = new HashMap<>();
    double i;
    Map<String, Image> cached_images = new HashMap<>();
    List<GuiElement> gui_elements = new ArrayList<>();


    public MenuScene() {
        GameUtils.Backgrounds.MENU.loadMap(parallax_backgrounds);
        i = 0;
        cacheImage("title", "gui/title.png");
        cacheImage("background", "backgrounds/menus/still_menu.png");

        GuiButton button = new GuiButton(new Location(300,300),"Continue", ()->{
            Main.getWindow().getScreen().getCamera().queueBoard(new LevelScene(1));
        });
        gui_elements.add(button);
        Main.getWindow().addMouseListener(this);
        Main.getWindow().addMouseMotionListener(this);
    }

    private void cacheImage(String name, String path) {
        cached_images.put(name, Resources.getImage(path));
    }


    @Override
    public void draw(Camera camera, Graphics g) {
        for (int a = 0; a < parallax_backgrounds.size() + 1; a++) {
            for (Map.Entry<Integer, Image> entry : parallax_backgrounds.entrySet()) {
                g.drawImage(entry.getValue(), (int) (-i * (entry.getKey()) + (camera.getViewport().getWidth() * a)), -0, (int) camera.getViewport().getWidth(), (int) camera.getViewport().getHeight(), null);
            }
        }
        if (i > camera.getViewport().getWidth())
            i = 0;
        i = i + 0.1;

        g.drawImage(cached_images.get("title"), (int) (camera.getViewport().getWidth() / 2 - (camera.getViewport().getWidth() * 0.4) / 2), (int) (camera.getViewport().getHeight() / 4), (int) (camera.getViewport().getWidth() * 0.4), (int) (camera.getViewport().getHeight() * 0.15), null);

        g.drawString("Project Titanium", 100, 100);

        for (GuiElement element : gui_elements) {
            element.draw(camera, g);
        }



//        g.drawImage(background,0,0,(int) camera.getViewport().getWidth(), (int) camera.getViewport().getHeight(), null);
    }

    @Override
    public void update(Camera camera) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GuiElement element : gui_elements) {
            if (element.contains(new Location(e.getPoint().getX() - 8, e.getY() - 31)))
                element.click();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GuiElement element : gui_elements) {
            if (element.clicked)
                element.unclick();
        }
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

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GuiElement element : gui_elements) {
            if (element.contains(new Location(e.getPoint().getX()-8, e.getY()-31)))
                element.hover();
            else element.unhover();
        }
    }
}
