package com.quickscythe.titanium.game;

import com.quickscythe.titanium.Main;
import com.quickscythe.titanium.Screen;
import com.quickscythe.titanium.game.object.GameObject;
import com.quickscythe.titanium.game.scene.Scene;
import com.quickscythe.titanium.gui.Gui;
import com.quickscythe.titanium.utils.GameUtils;
import com.quickscythe.titanium.utils.Location;

import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

public class Camera {

    Rectangle viewport;


    Graphics frame_graphics;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Map<GraphicsDevice, VolatileImage> monitor_storage = new HashMap<>();
    GraphicsDevice current_screen = ge.getDefaultScreenDevice();
    Gui gui = null;
    private double rwidth = 1920/10;
    private double rheight = 1080/10;


    public Camera(int width, int height) {
        viewport = new Rectangle(0, 0, (int) width, (int) height);
        rwidth = width;
        rheight = height;

//        ge.getScreenDevices()
        for (GraphicsDevice gd : ge.getScreenDevices()) {
            monitor_storage.put(gd, gd.getDefaultConfiguration().createCompatibleVolatileImage((int) rwidth, (int) rheight));
            monitor_storage.get(gd).setAccelerationPriority(0);
            frame_graphics = monitor_storage.get(gd).createGraphics();
        }

    }

    public void init() {


    }


    public List<GameObject> getAllObjects() {
        List<GameObject> objects = new ArrayList<>();
        for (GameObject object : GameUtils.getObjects())
            if (object.getBounds().intersects(viewport)) objects.add(object);


        return objects;
    }


    public void update(Scene scene) {
        scene.update(this);
    }

    public GraphicsDevice getGraphicsDevice() {
        return current_screen;
    }


    public void draw(Screen screen) {
        if (Main.getWindow() == null) return;
        Graphics g = screen.getGraphics();

//
        if (!Main.getWindow().getGraphicsConfiguration().getDevice().equals(current_screen))
            current_screen = Main.getWindow().getGraphicsConfiguration().getDevice();

        if (monitor_storage.get(current_screen).getWidth() != rwidth || monitor_storage.get(current_screen).getHeight() != rheight) {
            monitor_storage.put(current_screen, current_screen.getDefaultConfiguration().createCompatibleVolatileImage((int) rwidth, (int) rheight));
            monitor_storage.get(current_screen).setAccelerationPriority(0);
        }


        monitor_storage.get(current_screen).validate(ge.getDefaultScreenDevice().getDefaultConfiguration());
        frame_graphics = monitor_storage.get(current_screen).createGraphics();
        frame_graphics.setFont(frame_graphics.getFont().deriveFont(Font.BOLD));
        frame_graphics.setFont(frame_graphics.getFont().deriveFont(15F));
        frame_graphics.setColor(Color.BLACK);
        frame_graphics.fillRect(0, 0, (int) rwidth, (int) rheight);
        screen.getScene().draw(this, frame_graphics);
        if (GameUtils.debug()) {
            frame_graphics.drawRect(viewport.x, viewport.y, viewport.width, viewport.height);

            frame_graphics.setColor(Color.GREEN);
            frame_graphics.drawString("FPS: " + Main.getWindow().getScreen().getFPS(), 0, g.getFontMetrics().getHeight());
            frame_graphics.drawString("TPS: " + Main.getWindow().getScreen().getTPS(), 0, g.getFontMetrics().getHeight() * 2);
            frame_graphics.drawString("Objects: " + GameUtils.getObjects().size(), 0, g.getFontMetrics().getHeight() * 3);
            frame_graphics.drawString("Rendered Objects: " + getAllObjects().size(), 0, g.getFontMetrics().getHeight() * 4);
            String s = "Viewport Size: " + getViewport().getWidth() + ", " + getViewport().getHeight();
            frame_graphics.drawString(s, getViewport().width - g.getFontMetrics().stringWidth(s), g.getFontMetrics().getHeight());
            s = "Viewport Scale: " + 1;
            frame_graphics.drawString(s, getViewport().width - g.getFontMetrics().stringWidth(s), g.getFontMetrics().getHeight() * 2);
            s = "Viewport Location: (" + getViewport().getLocation().getX() + ", " + getViewport().getLocation().getY() + ")";
            frame_graphics.drawString(s, getViewport().width - g.getFontMetrics().stringWidth(s), g.getFontMetrics().getHeight() * 3);


        }
        if (GameUtils.paused()) {
            frame_graphics.setColor(new Color(0, 0, 0, 150));
            frame_graphics.fillRect(0, 0, getViewport().width, getViewport().height);
            Font font = frame_graphics.getFont();
            frame_graphics.setFont(frame_graphics.getFont().deriveFont(20F));
            frame_graphics.setColor(Color.decode("#33CEFF"));
            frame_graphics.drawString("PAUSED", getViewport().width / 2 - frame_graphics.getFontMetrics().stringWidth("PAUSED") / 2, getViewport().height / 2 - frame_graphics.getFontMetrics().getHeight() / 2);
            frame_graphics.setFont(font);
        }
        if (gui != null) {
            gui.draw(this, frame_graphics);
        }
        if (g != null)
            g.drawImage(monitor_storage.get(current_screen), 0, 0, screen.getWidth(), screen.getHeight(), null);
//        frame.flush();
        frame_graphics.dispose();


    }

    public void openGui(Gui gui) {
        this.gui = gui;
        GameUtils.paused(true);

    }

    public void closeGui() {
        gui.close();
        gui = null;
        GameUtils.paused(false);
    }

    public void setSize(int width, int height) {
        rwidth = width;
        rheight = height;
        viewport.setSize(width, height);
    }

    public Rectangle getViewport() {
        return viewport;
    }

    public boolean canSee(Location loc) {
        return viewport.contains(loc.getX(), loc.getY());
    }

    public boolean canSee(int x, int y, int width, int height) {
        return viewport.intersects(x - width / 2, y - height / 2, width, height);
    }


    public double getScaleX() {
        return (double) viewport.width / Main.getWindow().getScreen().getWidth();
    }

    public double getScaleY() {
        return (double) viewport.height / Main.getWindow().getScreen().getHeight();
    }

    public Gui getGui() {

        return gui;
    }
}
