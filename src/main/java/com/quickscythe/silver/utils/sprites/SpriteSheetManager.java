package com.quickscythe.silver.utils.sprites;

import com.quickscythe.silver.utils.GameUtils;

import java.util.HashMap;
import java.util.Map;

public class SpriteSheetManager {

    private static final Map<String, SpriteSheet> CACHED_SHEETS = new HashMap<>();

    public static SpriteSheet getSpriteSheet(String filepath) {
        return createSpriteSheet(filepath, GameUtils.TILE_SIZE, GameUtils.TILE_SIZE);
    }

    public static SpriteSheet createSpriteSheet(String filepath, int width, int height) {
        if (!CACHED_SHEETS.containsKey(filepath)) CACHED_SHEETS.put(filepath, new SpriteSheet(filepath, width, height));
        return CACHED_SHEETS.get(filepath);
    }
}
