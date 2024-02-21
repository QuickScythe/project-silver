package com.quickscythe.silver.utils;

import com.quickscythe.silver.Main;
import com.quickscythe.silver.utils.sounds.AudioManager;
import com.quickscythe.silver.utils.sounds.Sounds;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private final List<InputListener> LISTENERS = new ArrayList<>();
    private final List<InputListener> ADD_LISTENERS = new ArrayList<>();
    int i = 0;
    int mx = -1;
    int my = -1;

    private void updateListeners() {
        LISTENERS.addAll(ADD_LISTENERS);
        ADD_LISTENERS.clear();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("TEST@");
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.keyPressed(e);

        if (e.getKeyCode() == KeyEvent.VK_F3) {
            GameUtils.toggleDebug();
        }
        if (e.getKeyCode() == KeyEvent.VK_L) {
            AudioManager.playSound(Sounds.UI_BUTTON_CLICK, 1);
//            Main.getWindow().getScreen().queueScene(SceneManager.getLevelEditorScene(1));


        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

//            Main.getWindow().getScreen().getCamera().queueScene(new LevelScene(1));
            GameUtils.togglePaused();
            System.out.println("Paused: " + GameUtils.paused());
        }
//        if (e.getKeyCode() == KeyEvent.VK_E) {
////            ((LevelScene) Main.getWindow().getScreen().getCamera().getScene()).player.animationController.setAnimation(7);
//
//            if (Main.getWindow().getScreen().getCamera().getGui() == null) {
//                Gui gui = GuiManager.createGui("test");
//                GuiButton button = new GuiButton(new Location((double) Main.getWindow().getScreen().getCamera().getViewport().width / 2, (double) (Main.getWindow().getScreen().getCamera().getViewport().height / 2) + 30), "Continue", 150, 48, () -> {
//                    Main.getWindow().getScreen().getCamera().queueScene(SceneManager.getLevelScene(1));
//                    Main.getWindow().getScreen().getCamera().closeGui();
//                });
//
//                GuiButton button2 = new GuiButton(new Location((double) Main.getWindow().getScreen().getCamera().getViewport().width / 2, (double) (Main.getWindow().getScreen().getCamera().getViewport().height / 2) + 40 + 48), "Main Menu", 150, 48, () -> {
//                    Main.getWindow().getScreen().getCamera().queueScene(SceneManager.getMenuScene());
//                    Main.getWindow().getScreen().getCamera().closeGui();
//                });
//
//                gui.addElement(button);
//                gui.addElement(button2);
//                Main.getWindow().getScreen().getCamera().openGui(gui);
//            } else {
//                Main.getWindow().getScreen().getCamera().closeGui();
//            }
//
//        }

    }

    public int getRelativeX() {

        return (int) (mx * Main.getWindow().getScreen().getCAMERA().getScaleX());
    }

    public int getRelativeY() {
        return (int) (my * Main.getWindow().getScreen().getCAMERA().getScaleY());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        updateListeners();

        for (InputListener listener : LISTENERS)
            listener.mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateListeners();
        for (InputListener listener : LISTENERS)
            listener.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateListeners();
        mx = e.getX();
        my = e.getY();
//        System.out.println(getRelativeX());
        for (InputListener listener : LISTENERS)
            listener.mouseMoved(e);
    }

    public void addListener(InputListener listener) {
        ADD_LISTENERS.add(listener);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

//        double a = 1920D / 1080D;
//        double h = Main.getWindow().getScreen().getCamera().getViewport().getHeight() + e.getPreciseWheelRotation();
//        double w = h * a;
//        System.out.println(e.getPreciseWheelRotation());
//        Main.getWindow().getScreen().getCamera().setSize((int) w, (int) h);
    }
}