package com.quickscythe.titanium.utils;

import com.quickscythe.titanium.game.object.GameObject;
import com.quickscythe.titanium.utils.sprites.Sprite;
import com.quickscythe.titanium.utils.sprites.SpriteSheet;
import com.quickscythe.titanium.utils.sprites.SpriteSheetManager;
import com.studiohartman.jamepad.ControllerManager;
import org.json2.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameUtils {

    public static final int TILE_SIZE = 64;

    private static final List<GameObject> allGameObjects = new ArrayList<>();
    private static final InputManager INPUT_MANAGER = new InputManager();
    private static boolean debug = false;
    private static ControllerManager controllerManager = null;

    private static boolean paused = false;


    public static InputManager getInputManager() {
        return INPUT_MANAGER;
    }


    public static Location decodeLocation(JSONObject json) {
        return new Location(json.getDouble("x"), json.getDouble("y"));
    }

    public static JSONObject encodeLocation(Location location) {
        return new JSONObject("{\"x\":" + location.getX() + ",\"y\":" + location.getY() + "}");
    }

    public static void togglePaused() {
        paused = !paused;
    }

    public static void paused(boolean set_paused) {
        paused = set_paused;
    }

    public static boolean paused() {
        return paused;
    }

    public static void toggleDebug() {
        setDebug(!debug);
    }

    public static void setDebug(boolean set) {
        debug = set;
        System.out.println("Debug is " + debug);

    }

    public static List<GameObject> getObjects() {
        return allGameObjects;
    }

    public static boolean debug() {
        return debug;
    }

    public static double distance(Location loc1, Location loc2) {
        return Math.sqrt(Math.pow(loc2.getX() - loc1.getX(), 2) + Math.pow(loc2.getY() - loc1.getY(), 2));

    }

    public static ControllerManager getControllerManager() {
        if (controllerManager == null) {
            controllerManager = new ControllerManager();
            controllerManager.initSDLGamepad();
        }
        return controllerManager;
    }

    public static Sprite decodeSprite(JSONObject metadata) {
        return new Sprite(SpriteSheetManager.createSpriteSheet(metadata.getString("source"), metadata.getInt("width"), metadata.getInt("height")), metadata.getInt("x"), metadata.getInt("y"), metadata.getInt("width"), metadata.getInt("height"));
    }

    public static JSONObject encodeSprite(Sprite sprite) {
        return sprite.getMetadata();
    }


    public enum Backgrounds {

        MENU("menus/parallax"), BACKGROUND1("background1");

        String folder_name;

        Backgrounds(String folder_name) {
            this.folder_name = folder_name;
        }

        public void loadMap(Map<Integer, Image> map) {
            switch (this) {
                case MENU:
                    map.put(1, Resources.getImage("backgrounds/menus/parallax/sky.png"));
                    map.put(2, Resources.getImage("backgrounds/menus/parallax/clouds_1.png"));
                    map.put(3, Resources.getImage("backgrounds/menus/parallax/rocks.png"));
                    map.put(4, Resources.getImage("backgrounds/menus/parallax/clouds_2.png"));
                    map.put(5, Resources.getImage("backgrounds/menus/parallax/ground.png"));
                    break;
                case BACKGROUND1:
                    map.put(1, Resources.getImage("backgrounds/background1/sky.png"));
                    map.put(2, Resources.getImage("backgrounds/background1/clouds_3.png"));
                    map.put(3, Resources.getImage("backgrounds/background1/rocks_3.png"));
                    map.put(4, Resources.getImage("backgrounds/background1/clouds_2.png"));
                    map.put(5, Resources.getImage("backgrounds/background1/rocks_2.png"));
                    map.put(6, Resources.getImage("backgrounds/background1/clouds_1.png"));
                    map.put(7, Resources.getImage("backgrounds/background1/rocks_1.png"));
                    map.put(8, Resources.getImage("backgrounds/background1/pines.png"));
                    break;
            }
        }


    }
}
