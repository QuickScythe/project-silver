package com.quickscythe.titanium.game.scene;

import com.quickscythe.titanium.game.Camera;
import com.quickscythe.titanium.game.object.GameObject;
import com.quickscythe.titanium.game.object.Illuminant;
import com.quickscythe.titanium.game.object.entity.Player;
import com.quickscythe.titanium.game.world.World;
import com.quickscythe.titanium.game.world.WorldManager;
import com.quickscythe.titanium.utils.GameUtils;
import com.quickscythe.titanium.utils.Location;
import com.quickscythe.titanium.utils.Resources;
import org.json2.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LevelScene implements Scene {
    public Player player = null;
    Map<Integer, Image> parallax_backgrounds = new HashMap<>();

    private final int TILE_SIZE = 10;
    private int LEVEL_DARKNESS = 0;

    World world;

    LevelScene(int level) {
        GameUtils.getObjects().clear();
        world = WorldManager.loadWorld(level);



    }

    @Override
    public void draw(Camera camera, Graphics g) {

        world.draw(camera, g);

        g.setColor(Color.BLUE);

        //Parallax Background
        double b = camera.getViewport().getLocation().getX() * 0.05;

        int cell = (int) Math.ceil(camera.getViewport().getLocation().getX() / camera.getViewport().getWidth()) - 1;
        if (b > camera.getViewport().getWidth()) {
            b = b - camera.getViewport().getWidth() * cell;
        }
//        System.out.println("B: " + b);
//        System.out.println("Cell: " + cell);
//        for (int i = 0; i < cell + 1; i++) {
//            for (Map.Entry<Integer, Image> entry : parallax_backgrounds.entrySet()) {
//                g.drawImage(entry.getValue(), (int) ((i * camera.getViewport().getWidth()) - (camera.getViewport().getLocation().getX() * entry.getKey())), -0, camera.getViewport().width, camera.getViewport().height, null);
//            }
//        }

        for (int a = 0; a < parallax_backgrounds.size() + 1; a++) {
            for (Map.Entry<Integer, Image> entry : parallax_backgrounds.entrySet()) {
                g.drawImage(entry.getValue(), (int) (-b * (entry.getKey()) + (camera.getViewport().getWidth() * a)), -0, (int) camera.getViewport().width, (int) camera.getViewport().height, null);
            }
        }


        //Draw objects
        g.setColor(Color.RED);
        for (GameObject object : camera.getAllObjects()) {
            object.draw(g, camera);
//            if (object instanceof Player) player = (Player) object;

        }



//        g.fillRect(0,0,camera.getViewport().width,(int)((offsetY - Math.floor(offsetY)) * TILE_SIZE));


        //Overlay Debug info
        if (GameUtils.debug()) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, camera.getViewport().width, camera.getViewport().height);
            g.setColor(Color.GREEN);

        }


    }

    @Override
    public void update(Camera camera) {
        world.tick();


//        if (player != null) {
//            cam.getViewport().setLocation((int) (player.getLocation().getX() - (cam.getViewport().getWidth() / 2)), (int) (player.getLocation().getY() - (cam.getViewport().getHeight() - (cam.getViewport().getHeight() / 10))));
//        }
//
//        for (GameObject object : cam.getAllObjects())
//            object.update(cam.getAllObjects());

    }


}
