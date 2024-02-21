package com.quickscythe.silver.game.world;

import com.quickscythe.silver.utils.Resources;
import org.json2.JSONObject;

import java.util.Scanner;

public class WorldManager {


    public static World loadWorld(int level) {
//        StringBuilder json_string = new StringBuilder();
//        Scanner scanner = new Scanner(Resources.getFile("levels/level-" + level + ".json"));
//
//        while (scanner.hasNextLine()) {
//            json_string.append(scanner.nextLine());
//        }
//        scanner.close();

        return new World(loadWorldJson(level));
    }

    public static JSONObject loadWorldJson(int level) {
        StringBuilder json_string = new StringBuilder();
        Scanner scanner = new Scanner(Resources.getFile("levels/level-" + level + ".json"));

        while (scanner.hasNextLine()) {
            json_string.append(scanner.nextLine());
        }
        scanner.close();

        return new JSONObject(json_string.toString());
    }
}
