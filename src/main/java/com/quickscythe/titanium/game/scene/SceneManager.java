package com.quickscythe.titanium.game.scene;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private static final Map<String, Scene> SCENE_MAP = new HashMap<>();

    private static boolean init = false;


    public static void init() {
        if (!init) {
            SCENE_MAP.put("menu", new MenuScene());
            init = true;
        }

    }

    public static Scene getScene(String key) {
        return SCENE_MAP.getOrDefault(key, null);
    }

    public static void saveScene(String name, Scene scene) {
        SCENE_MAP.put(name, scene);
    }

    public static Scene getMenuScene() {
        return getScene("menu");
    }

    public static Scene getLevelScene(int level) {
        if (getScene("level" + level) == null) {
            saveScene("level" + level, new LevelScene(level));
        }
        return getScene("level" + level);
    }

    public static Scene getLevelEditorScene(int level) {
        if (getScene("leveleditor" + level) == null) {
            saveScene("leveleditor" + level, new LevelEditorScene(level));
        }
        return new LevelEditorScene(level);
    }
}
