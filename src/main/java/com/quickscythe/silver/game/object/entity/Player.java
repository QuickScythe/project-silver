package com.quickscythe.silver.game.object.entity;

import com.quickscythe.silver.Main;
import com.quickscythe.silver.game.Camera;
import com.quickscythe.silver.game.controllers.EntityController;
import com.quickscythe.silver.game.controllers.GamepadController;
import com.quickscythe.silver.game.controllers.KeyboardController;
import com.quickscythe.silver.game.object.GameObject;
import com.quickscythe.silver.game.object.Illuminant;
import com.quickscythe.silver.game.scene.SceneManager;
import com.quickscythe.silver.gui.Gui;
import com.quickscythe.silver.gui.GuiButton;
import com.quickscythe.silver.gui.GuiManager;
import com.quickscythe.silver.utils.AnimationDirector;
import com.quickscythe.silver.utils.Direction;
import com.quickscythe.silver.utils.GameUtils;
import com.quickscythe.silver.utils.Location;
import com.quickscythe.silver.utils.sprites.SpriteSheetManager;

import java.awt.*;
import java.util.List;

public class Player extends Entity implements Illuminant {

    public AnimationDirector animationDirector = new AnimationDirector(SpriteSheetManager.createSpriteSheet("sprites/player2.png", 24, 24));

    Direction last_direction = Direction.RIGHT;

    public Player(Location location) {
        super(location);
//        sprite = new Sprite(Resources.getImage("logo.png"));

        animationDirector.setAnimation(12);
        collision_box.setSize(width - 14, height - 12);

        controllers.add(new KeyboardController());
        controllers.add(new GamepadController(0));

//        cached_images.put(0D, Resources.getImage("player.png"));
//        cached_images.put(45D, Resources.getImage("player45.png"));
//        cached_images.put(90D, Resources.getImage("player90.png"));
//        cached_images.put(135D, Resources.getImage("player135.png"));
//        cached_images.put(180D, Resources.getImage("player180.png"));
//        cached_images.put(225D, Resources.getImage("player225.png"));
//        cached_images.put(270D, Resources.getImage("player270.png"));
//        cached_images.put(315D, Resources.getImage("player315.png"));
//        image = Resources.getImage("player.png");
    }


    @Override
    public void updateCollisionBox() {
        collision_box.setLocation((int) (predictX() - (width - 14) / 2), (int) (predictY() - (height) / 2) + 12);
//        collision_box.setSize((int) (width - 14), (int) (height - 8));
    }

    @Override
    public void update(List<GameObject> objects) {
//        if(getLocation().getX() < 640)
//            getLocation().setX(640);

//        collision_box.setLocation((int) (predictX() - (getBounds().getWidth() + 2) / 2), (int) (predictY() - (getBounds().getHeight() + 2) / 2));
//        collision_box.setSize((int) (getBounds().getWidth() + 2), (int) (getBounds().getHeight() + 2));

        for (EntityController controller : controllers) {
            if (controller.requestingMenu()) {
                Gui gui = GuiManager.createGui("test");
                GuiButton button = new GuiButton(new Location((double) Main.getWindow().getScreen().getCAMERA().getViewport().width / 2, (double) (Main.getWindow().getScreen().getCAMERA().getViewport().height / 2) + 30), "Continue", 150, 48, () -> {
                    Main.getWindow().getScreen().queueScene(SceneManager.getLevelScene(1));
                    Main.getWindow().getScreen().getCAMERA().closeGui();
                });

                GuiButton button2 = new GuiButton(new Location((double) Main.getWindow().getScreen().getCAMERA().getViewport().width / 2, (double) (Main.getWindow().getScreen().getCAMERA().getViewport().height / 2) + 40 + 48), "Main Menu", 150, 48, () -> {
                    Main.getWindow().getScreen().queueScene(SceneManager.getMenuScene());
                    Main.getWindow().getScreen().getCAMERA().closeGui();
                });

                gui.addElement(button);
                gui.addElement(button2);
                Main.getWindow().getScreen().getCAMERA().openGui(gui);
            }

        }

        super.update(objects);

        if (velocity.getSpeed() > 0) {
            if (last_direction.equals(Direction.RIGHT)) animationDirector.setAnimation(3);
            if (last_direction.equals(Direction.LEFT)) animationDirector.setAnimation(2);
        } else

            animationDirector.setAnimation(14);
        if (velocity.getX() > 0) last_direction = Direction.LEFT;
        if (velocity.getX() < 0) last_direction = Direction.RIGHT;
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        sprite = animationDirector.getAnimation().getFrame();

//        sprite.flip(last_direction.equals(Direction.RIGHT));

//        if (last_direction.equals(Direction.RIGHT) && !getSprite().flipped()) {
//            sprite.flip(true);
//        }
//        if (last_direction.equals(Direction.LEFT) && getSprite().flipped()) {
//            sprite.flip(false);
//        }

//        animationController.setAnimation(6);d
        super.draw(g, camera);
        if (GameUtils.debug()) {
            g.setColor(Color.GREEN);
            g.drawString("Player Location: " + getLocation().toString() + "~(" + getRelativeX(camera) + ", " + getRelativeY(camera) + ")", 0, g.getFontMetrics().getHeight() * 5);
            g.drawString("Player Velocity: " + getVelocity().toString(), 0, g.getFontMetrics().getHeight() * 6);
            g.drawString("Player Directions: ", 0, g.getFontMetrics().getHeight() * 7);
            int i = 8;
            for (Direction direction : getDirections()) {
                g.drawString(" - " + direction.name(), 0, g.getFontMetrics().getHeight() * i);
                i = i + 1;
            }
            g.drawString("Player Collisions: ", 0, g.getFontMetrics().getHeight() * i);
            for (Direction direction : getCollisions()) {
                i = i + 1;
                g.drawString(" - " + direction.name(), 0, g.getFontMetrics().getHeight() * i);


            }
        }
    }

    @Override
    public boolean on() {
        return true;
    }

    @Override
    public int getBrightness() {
        return 1;
    }
}
