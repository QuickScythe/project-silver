package com.quickscythe.silver.game.world;

import com.quickscythe.silver.game.Camera;
import com.quickscythe.silver.game.object.GameObject;
import com.quickscythe.silver.game.object.Illuminant;
import com.quickscythe.silver.game.object.entity.Entity;
import com.quickscythe.silver.game.object.entity.Player;
import com.quickscythe.silver.utils.GameUtils;
import com.quickscythe.silver.utils.Location;
import org.json2.JSONObject;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class World {


    private final Tile[][][] TILES;
    private final int DARKNESS;
    private final Player PLAYER;

    private final List<Entity> ENTITIES = new ArrayList<>();
    private final List<Entity> ENTITIES_ADD = new ArrayList<>();
    private final List<Entity> ENTITIES_REMOVE = new ArrayList<>();
    private final List<GameObject> CACHED_OBJECTS = new ArrayList<>();

    int width;
    int height;

    boolean LOADED = false;

    public World(JSONObject json) {


        DARKNESS = json.has("darkness") ? Math.min(json.getInt("darkness"), 255) : 0;
        width = json.getInt("width");
        height = json.getInt("height");

        if (json.has("player")) {
            JSONObject player = json.getJSONObject("player");
            PLAYER = (Player) spawn(GameUtils.decodeLocation(player.getJSONObject("location")), Player.class);
            CACHED_OBJECTS.add(PLAYER);
        } else PLAYER = null;
        TILES = new Tile[json.getJSONArray("tile_layers").length()][json.getInt("width")][json.getInt("height")];
        System.out.println("Tile_Layers: " + json.getJSONArray("tile_layers").length());
        for (int a = 0; a < json.getJSONArray("tile_layers").length(); a++) {

            JSONObject object = json.getJSONArray("tile_layers").getJSONObject(a);
            for (int b = 0; b < object.getJSONArray("tiles").length(); b++) {
                JSONObject object2 = object.getJSONArray("tiles").getJSONObject(b);
                Location location = GameUtils.decodeLocation(object2.getJSONObject("location"));
                Tile tile = new Tile(this, location, a, GameUtils.decodeSprite(object2.getJSONObject("sprite")));
//                Tile tile = new Tile(location, Tile.TileType.valueOf(object2.getString("type").toUpperCase()), a, this, false, false, false, false);
                TILES[a][tile.getX()][tile.getY()] = tile;
//                System.out.println("saving [" + a + "][" + (int) location.getX() + "][" + (int) location.getY() + "]");

            }
        }


        LOADED = true;


//        for (int a = 0; a < json.getJSONArray("objects").length(); a++) {
//            JSONObject object = json.getJSONArray("objects").getJSONObject(a);
//            GameUtils.spawn(object);
////            GameUtils.createObject(GameUtils.decodeLocation(player.getJSONObject("location")), GameUtils.ObjectType.valueOf(object.getString("type").toUpperCase()).getObjectClass());
//        }
    }

    public void tick() {
        if (!GameUtils.paused() && LOADED) {


            ENTITIES.addAll(ENTITIES_ADD);
            CACHED_OBJECTS.addAll(ENTITIES_ADD);
            ENTITIES_ADD.clear();
            ENTITIES.removeAll(ENTITIES_REMOVE);
            CACHED_OBJECTS.removeAll(ENTITIES_REMOVE);
            ENTITIES_REMOVE.clear();
            for (Entity entity : ENTITIES)
                entity.update(CACHED_OBJECTS);
            if (PLAYER != null) PLAYER.update(CACHED_OBJECTS);
        }
    }


    public Entity spawn(Location loc, Class<? extends Entity> entityClass) {
        Entity entity;
        try {
            Constructor<? extends GameObject> con = entityClass.getConstructor(Location.class);
            entity = (Entity) con.newInstance(loc);

        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);

        }

//        entity.initListeners();

        return entity;
    }

    public void draw(Camera camera, Graphics g) {

        //If not loaded, show black screen.
        if (!LOADED) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, camera.getViewport().width, camera.getViewport().height);
            return;
        }

        //Postion camera
        if (PLAYER != null) {

            camera.getViewport().setLocation((int) (PLAYER.getLocation().getX() - (camera.getViewport().getWidth() / 2)), (int) (PLAYER.getLocation().getY() - (camera.getViewport().getHeight() / 2)));
            if (camera.getViewport().getMinX() < 0)
                camera.getViewport().setLocation(0, (int) camera.getViewport().getLocation().getY());
            if (camera.getViewport().getMinY() < 0)
                camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX(), 0);


            if (camera.getViewport().getMaxX() > width * GameUtils.TILE_SIZE - (GameUtils.TILE_SIZE / 2))
                camera.getViewport().setLocation((int) ((width * GameUtils.TILE_SIZE) - (camera.getViewport().getWidth() + GameUtils.TILE_SIZE / 2)), (int) camera.getViewport().getLocation().getY());
            if (camera.getViewport().getMaxY() > height * GameUtils.TILE_SIZE - (GameUtils.TILE_SIZE / 2))
                camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX(), (int) ((height * GameUtils.TILE_SIZE) - (camera.getViewport().getHeight() + GameUtils.TILE_SIZE / 2)));
//            if (camera.getViewport().getMinY() < 0)
//                camera.getViewport().setLocation((int) camera.getViewport().getLocation().getX(), 0);
        }
        //Reset frame
        g.fillRect(0, 0, camera.getViewport().width, camera.getViewport().height);

        //draw tiles (& player if layers >= 5)
        for (int index = 0; index < TILES.length; index++) {
            if (index == 5 && PLAYER != null) PLAYER.draw(g, camera);
            for (int x = 0; x < TILES[index].length; x++) {
                for (int y = 0; y < TILES[index][x].length; y++) {
                    if (camera.canSee(x * GameUtils.TILE_SIZE, y * GameUtils.TILE_SIZE, GameUtils.TILE_SIZE, GameUtils.TILE_SIZE) && TILES[index][x][y] != null)
//                    System.out.println("Looking for [" + index + "][" + x + "][" + y + "]");
                        TILES[index][x][y].draw(g, camera);
                }
            }
        }
        if (TILES.length <= 5 && PLAYER != null) PLAYER.draw(g, camera);

        for (Entity entity : ENTITIES)
            entity.draw(g, camera);


        //Darkness

        if (DARKNESS > 0) {
            double offsetX = 0;
            double offsetY = 0;

            int shadow_size = GameUtils.TILE_SIZE / 2;
            Color color = new Color(0, 0, 0, 0);
            Location location = new Location(0, 0);

            int brightness = DARKNESS;
            for (int x = 0; x != (camera.getViewport().width / shadow_size) + 3; x++) {
                for (int y = 0; y != (camera.getViewport().height / shadow_size) + 3; y++) {
                    brightness = DARKNESS;
                    offsetX = camera.getViewport().getMinX() / shadow_size;
                    offsetY = camera.getViewport().getMinY() / shadow_size;
                    location.setX((x * shadow_size - ((offsetX - Math.floor(offsetX)) * shadow_size)) - shadow_size);
                    location.setY((y * shadow_size - ((offsetY - Math.floor(offsetY)) * shadow_size)) - shadow_size);


                    for (GameObject object : CACHED_OBJECTS) {
                        if (!(object instanceof Illuminant)) continue;
                        brightness = (int) Math.min(Math.max(GameUtils.distance(location, object.getLocation().convertToRelative(camera)) - ((Illuminant) object).getBrightness(), 0), brightness);
                    }

                    g.setColor(new Color(0, 0, 0, brightness));
                    g.fillRect((int) location.getX(), (int) location.getY(), shadow_size, shadow_size);

                }
            }
        }


    }


    public List<GameObject> getCachedObjects() {
        return CACHED_OBJECTS;
    }


}

