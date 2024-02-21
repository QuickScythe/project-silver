package com.quickscythe.silver.game.controllers;

import com.quickscythe.silver.utils.GameUtils;
import com.quickscythe.silver.utils.InputListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardController implements EntityController, InputListener {

    private final Map<ControllerRequest, Integer> KEYBINDS = new HashMap<>();
    private final List<Integer> PRESSED_KEYS = new ArrayList<>();


    public KeyboardController() {
        GameUtils.getInputManager().addListener(this);
        KEYBINDS.put(ControllerRequest.UP, KeyEvent.VK_W);
        KEYBINDS.put(ControllerRequest.DOWN, KeyEvent.VK_S);
        KEYBINDS.put(ControllerRequest.LEFT, KeyEvent.VK_A);
        KEYBINDS.put(ControllerRequest.RIGHT, KeyEvent.VK_D);
        KEYBINDS.put(ControllerRequest.PAUSE, KeyEvent.VK_ESCAPE);
        KEYBINDS.put(ControllerRequest.MENU, KeyEvent.VK_E);
    }

    private int getKeybind(ControllerRequest request) {
        return KEYBINDS.getOrDefault(request, -1);
    }


    @Override
    public boolean requestingUp() {
        return PRESSED_KEYS.contains(getKeybind(ControllerRequest.UP));
    }

    @Override
    public boolean requestingDown() {
        return PRESSED_KEYS.contains(getKeybind(ControllerRequest.DOWN));
    }

    @Override
    public boolean requestingLeft() {
        return PRESSED_KEYS.contains(getKeybind(ControllerRequest.LEFT));
    }

    @Override
    public boolean requestingRight() {
        return PRESSED_KEYS.contains(getKeybind(ControllerRequest.RIGHT));
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public boolean requestingPause() {
        return PRESSED_KEYS.contains(getKeybind(ControllerRequest.PAUSE));
    }

    @Override
    public boolean requestingMenu() {
        return PRESSED_KEYS.contains(getKeybind(ControllerRequest.MENU));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        PRESSED_KEYS.remove(((Object) e.getKeyCode()));
        PRESSED_KEYS.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        PRESSED_KEYS.remove(((Object) e.getKeyCode()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
