package com.quickscythe.titanium.game.scene;

import com.badlogic.gdx.Game;
import com.quickscythe.titanium.Main;
import com.quickscythe.titanium.game.Camera;
import com.quickscythe.titanium.game.world.World;
import com.quickscythe.titanium.utils.GameUtils;
import com.quickscythe.titanium.utils.InputListener;
import com.quickscythe.titanium.utils.Location;
import com.quickscythe.titanium.utils.sprites.Sprite;
import com.quickscythe.titanium.utils.sprites.SpriteSheet;
import com.quickscythe.titanium.utils.sprites.SpriteSheetManager;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LevelEditorScene implements Scene, InputListener {

    double mx = -1;
    double my = -1;

    double rwidth = 0;
    double rheight = 0;

    Camera camera;

    int index = 0;

    int last_mouse_click = 0;

//    Map<Integer, Map<Point, Sprite>> OBJECTS = new HashMap<>();
//
//    Map<Point, Sprite> add_objects = new HashMap<>();

    private final JSONObject WORLD = new JSONObject();
    Sprite[] sprites;
    int selected = 0;

    Sprite sprite = SpriteSheetManager.createSpriteSheet("sprites/paths/paths2.png", 16, 16).getSprite(0, 0);

    LevelEditorScene(int level) {
        SpriteSheet sheet = SpriteSheetManager.createSpriteSheet("sprites/paths/paths2.png", 16, 16);

        sprites = new Sprite[(sheet.getBufferedImage().getWidth() / sheet.getWidth()) * (sheet.getBufferedImage().getHeight() / sheet.getHeight())];
        int x = 0;
        int y = 0;
        int width = (sheet.getBufferedImage().getWidth() / sheet.getWidth()) - 1;
        System.out.println("Looking for width: " + width);
        for (int i = 0; i < sprites.length; i++) {

            if (x > width) {
                x = 0;
                y = y + 1;
            }
            System.out.println("X: " + x);
            sprites[i] = sheet.getSprite(x * sheet.getWidth(), y * sheet.getHeight());
            x = x + 1;
        }

        JSONArray layers = new JSONArray();
        JSONArray layer0 = new JSONArray();
        JSONObject tile1 = new JSONObject();
        tile1.put("sprite", GameUtils.encodeSprite(sprites[0]));
        tile1.put("location", GameUtils.encodeLocation(new Location(5, 5)));
        layer0.put(tile1);
        layers.put(new JSONObject().put("tiles",layer0));
        WORLD.put("tile_layers", layers);
        WORLD.put("width", 100);
        WORLD.put("height",100);



//        JSONObject json = WorldManager.loadWorldJson(level);
//
//
//        if (json != null) {
//            for (int a = 0; a < json.getJSONArray("tiles").length(); a++) {
//                OBJECTS.put(a, new HashMap<>());
//                System.out.println("Layer: " + a);
//                JSONObject tile_list = json.getJSONArray("tiles").getJSONObject(a);
//                for (int b = 0; b < tile_list.getJSONArray("tiles").length(); b++) {
//                    JSONObject tile = tile_list.getJSONArray("tiles").getJSONObject(b);
//                    Location location = GameUtils.decodeLocation(tile.getJSONObject("location"));
//                    OBJECTS.get(a).put(new Point((int) location.getX(), (int) location.getY()), GameUtils.decodeSprite(tile.getJSONObject("sprite")));
//                }
//            }
//        }
        GameUtils.getInputManager().addListener(this);
    }

    @Override
    public void draw(Camera camera, Graphics g) {
//        sprite = SpriteSheetManager.getSpriteSheet("sprites/paths/paths2.png").getSprite(16, 16);
//        sprite = new SpriteSheet("sprites/paths/paths2.png",16,16).getSprite(16,16);
//        for (Map.Entry<Point, Sprite> entry : add_objects.entrySet()) {
//            getObjectsInIndex().put(entry.getKey(), entry.getValue());
//        }
//        add_objects.clear();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, camera.getViewport().width, camera.getViewport().height);
        new World(WORLD).draw(camera, g);
        g.setColor(Color.RED);
        int cellX = getCellX();
        int cellY = getCellY();
        g.fillRect((int) (((cellX * GameUtils.TILE_SIZE)) - camera.getViewport().getMinX()) - (GameUtils.TILE_SIZE / 2), (int) (((cellY * GameUtils.TILE_SIZE)) - camera.getViewport().getMinY()) - (GameUtils.TILE_SIZE / 2), GameUtils.TILE_SIZE, GameUtils.TILE_SIZE);

//System.out.println(selected);
        g.drawImage(sprites[selected].getImage(), (int) (((cellX * GameUtils.TILE_SIZE)) - camera.getViewport().getMinX()) - (GameUtils.TILE_SIZE / 2), (int) (((cellY * GameUtils.TILE_SIZE)) - camera.getViewport().getMinY()) - (GameUtils.TILE_SIZE / 2), GameUtils.TILE_SIZE, GameUtils.TILE_SIZE, null);

        g.setColor(Color.WHITE);
        g.drawString("Index: " + index, 0, g.getFontMetrics().getHeight());


    }

    public int getCellX() {
        try {
            return (int) (Math.ceil((mx + (camera.getViewport().getMinX() + ((double) GameUtils.TILE_SIZE / 2))) / GameUtils.TILE_SIZE) - 1);
        } catch (NullPointerException ex) {
            return 2;
        }
    }

    public int getCellY() {
        try {
            return (int) (Math.ceil((my + camera.getViewport().getMinY() + (((double) GameUtils.TILE_SIZE / 2))) / GameUtils.TILE_SIZE) - 1);
        } catch (NullPointerException ex) {
            return 2;
        }
    }


    public void save() {
        File file = new File("C:/Users/Camer/Desktop/levels/new-level.json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        JSONObject world = generateJson();
        try {
            Files.write(file.toPath(), WORLD.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private JSONObject generateJson() {
//        JSONObject world = new JSONObject();
//        world.put("darkness", 0);
//        int minX = 0;
//        int minY = 0;
//        int maxX = 0;
//        int maxY = 0;
//
//        int max_indexes = 0;
//        for (Map.Entry<Integer, Map<Point, Sprite>> entry : OBJECTS.entrySet()) {
////        for (int i = 0; i < world.getInt("layers"); i++) {
//            if (OBJECTS.containsKey(max_indexes)) for (Point p : getObjectsInIndex(max_indexes).keySet()) {
//                if (p.getX() < minX) minX = p.x;
//                if (p.getY() < minY) minY = p.y;
//                if (p.getX() > maxX) maxX = p.x;
//                if (p.getY() > maxY) maxY = p.y;
//            }
//            max_indexes = max_indexes + 1;
//        }
//
//        int width = maxX - minX;
//        int height = maxY - minY;
//
//        world.put("width", width + 1);
//        world.put("height", height + 1);
//        world.put("layers", Math.max(1, max_indexes));
//
//        world.put("tile_layers", new JSONArray());
//        for (int i = 0; i < max_indexes; i++) {
//            JSONObject tiles = new JSONObject();
//            tiles.put("tiles", new JSONArray());
//            for (Map.Entry<Point, Sprite> entry : getObjectsInIndex(i).entrySet()) {
//                JSONObject object = new JSONObject();
//                object.put("location", GameUtils.encodeLocation(new Location(entry.getKey().x - minX, entry.getKey().y - minY)));
////                object.put("type", entry.getValue().name());
//
//                //TODO finish this
//                object.put("sprite", GameUtils.encodeSprite(entry.getValue()));
//
//                tiles.getJSONArray("tiles").put(object);
//            }
//            world.getJSONArray("tile_layers").put(tiles);
//
//        }
////        JSONObject player = new JSONObject();
////        player.put("location", GameUtils.encodeLocation(new Location(-minX * GameUtils.TILE_SIZE, -minY * GameUtils.TILE_SIZE)));
////        world.put("player", player);
//
//        if (!world.has("player")) {
//            JSONObject player = new JSONObject();
//            player.put("location", GameUtils.encodeLocation(new Location(5 * GameUtils.TILE_SIZE, 5 * GameUtils.TILE_SIZE)));
//        }
//        return world;
//    }

    public JSONArray getObjectsInIndex() {

        return getObjectsInIndex(index);
    }

    public JSONArray getObjectsInIndex(int index) {
        JSONArray tiles = WORLD.getJSONArray("tile_layers");
        while(tiles.length() < index){
            tiles.put(new JSONArray());
        }
        return tiles.getJSONObject(index).getJSONArray("tiles");
    }


    @Override
    public void update(Camera camera) {
        this.camera = camera;
        rwidth = camera.getViewport().getWidth() / Main.getWindow().getScreen().getWidth();
        rheight = camera.getViewport().getHeight() / Main.getWindow().getScreen().getHeight();

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        last_mouse_click = e.getButton();

        System.out.println("Looking for: " + MouseEvent.BUTTON2 + ", but got: " + e.getButton());

        if (e.getButton() == MouseEvent.BUTTON2) {
            JSONObject tile = new JSONObject();
            tile.put("sprite",GameUtils.encodeSprite(sprites[selected]));
            tile.put("location", GameUtils.encodeLocation(new Location(getCellX(),getCellY())));
            getObjectsInIndex().put(tile);
        }
//        if (e.getButton() == MouseEvent.BUTTON2) add_objects.put(new Point(getCellX(), getCellY()), sprites[selected]);
        if (e.getButton() == MouseEvent.BUTTON1) {
            selected = selected + 1 > sprites.length - 1 ? 0 : selected + 1;
            System.out.println(selected);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            selected = selected - 1 < 0 ? sprites.length - 1 : selected - 1;
            System.out.println(selected);
        }


//        if (e.getButton() == MouseEvent.BUTTON2) getObjectsInIndex().remove(new Point(getCellX(), getCellY()));

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

        mx = -2 * GameUtils.TILE_SIZE;
        my = -2 * GameUtils.TILE_SIZE;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mx = e.getX() * rwidth;
        my = e.getY() * rheight;
        System.out.println(last_mouse_click);
        if (last_mouse_click == MouseEvent.BUTTON2) {
            JSONObject tile = new JSONObject();
            tile.put("sprite",GameUtils.encodeSprite(sprites[selected]));
            tile.put("location", GameUtils.encodeLocation(new Location(getCellX(),getCellY())));
            getObjectsInIndex().put(tile);
        }
//        if (e.getButton() == MouseEvent.BUTTON1) {
//            selected = selected + 1 > sprites.length - 1 ? 0 : selected + 1;
//            System.out.println(selected);
//        }
//        if (e.getButton() == MouseEvent.BUTTON3) {
//            selected = selected - 1 < 0 ? sprites.length - 1 : selected - 1;
//            System.out.println(selected);
//        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mx = e.getX() * rwidth;
        my = e.getY() * rheight;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double h = Main.getWindow().getScreen().getCAMERA().getViewport().getHeight() + e.getPreciseWheelRotation();
        System.out.println(h);
        if (h < 0) {
            selected = Math.min(selected + 1, sprites.length);
        } else {
            selected = Math.max(selected - 1, 0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_S) {
            save();
        }

        if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            camera.getViewport().setLocation((int) (camera.getViewport().getLocation().getX() - 1), (int) camera.getViewport().getLocation().getY());
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            camera.getViewport().setLocation((int) (camera.getViewport().getLocation().getX() + 1), (int) camera.getViewport().getLocation().getY());
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            camera.getViewport().setLocation((int) (camera.getViewport().getLocation().getX()), (int) camera.getViewport().getLocation().getY() - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            camera.getViewport().setLocation((int) (camera.getViewport().getLocation().getX()), (int) camera.getViewport().getLocation().getY() + 1);
        }

        if (e.getKeyCode() == KeyEvent.VK_ADD) {
            index = index + 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
            index = Math.max(index - 1, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
