package com.quickscythe.titanium.gui;

import com.quickscythe.titanium.game.Camera;
import com.quickscythe.titanium.utils.Location;
import com.quickscythe.titanium.utils.sounds.AudioManager;
import com.quickscythe.titanium.utils.sounds.Sounds;
import com.quickscythe.titanium.utils.sprites.SpriteSheet;
import com.quickscythe.titanium.utils.sprites.SpriteSheetManager;

import java.awt.*;

public class GuiButton extends GuiElement {

    private final SpriteSheet SPRITE_SHEET;
    String value;
    Runnable runnable;


    public GuiButton(Location location, String value, int width, int height, Runnable runnable) {
        super(location, width, height);
        this.value = value;
        this.runnable = runnable;
        SPRITE_SHEET = SpriteSheetManager.createSpriteSheet("sprites/gui/buttons1.png", 48, 48);
    }

    @Override
    public void draw(Camera camera, Graphics g) {


        Color color = g.getColor();

        g.drawImage(SPRITE_SHEET.getSprite(0, 0, 16, 48).getImage(), (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getHeight() / 3, (int) bounds.getHeight(), null);
        g.drawImage(SPRITE_SHEET.getSprite(16, 0, 16, 48).getImage(), (int) (bounds.getMinX() + (bounds.getHeight() / 3)), (int) bounds.getMinY(), (int) (bounds.getWidth() - ((bounds.getHeight() / 3) * 2)), (int) bounds.getHeight(), null);
        g.drawImage(SPRITE_SHEET.getSprite(32, 0, 16, 48).getImage(), (int) (bounds.getMinX() + bounds.getWidth() - (bounds.getHeight() / 3)), (int) bounds.getMinY(), (int) bounds.getHeight() / 3, (int) bounds.getHeight(), null);
        g.setColor(Color.WHITE);

        if (!hover) {
//            g.setColor(Color.WHITE);
//            g.drawImage(SPRITE_SHEET.getSprite(0, 0, 16, 48).getImage(), (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getHeight()/3, (int) bounds.getHeight(), null);
//            g.drawImage(SPRITE_SHEET.getSprite(16, 0, 16, 48).getImage(), (int) (bounds.getMinX() + (bounds.getHeight()/3)), (int) bounds.getMinY(), (int) (bounds.getWidth() - ((bounds.getHeight()/3) * 2)), (int) bounds.getHeight(), null);
//            g.drawImage(SPRITE_SHEET.getSprite(32, 0, 16, 48).getImage(), (int) (bounds.getMinX() + bounds.getWidth() - (bounds.getHeight()/3)), (int) bounds.getMinY(), (int) bounds.getHeight()/3, (int) bounds.getHeight(), null);
            g.drawString(value, (int) (location.getX() - g.getFontMetrics().stringWidth(value) / 2), (int) (location.getY() + g.getFontMetrics().getHeight() / 4));
        } else {
//            g.setColor(Color.DARK_GRAY);
//            g.drawImage(SPRITE_SHEET.getSprite(0, 48, 16, 48).getImage(), (int) bounds.getMinX(), (int) bounds.getMinY(), (int) (bounds.getHeight()/3), (int) bounds.getHeight(), null);
//            g.drawImage(SPRITE_SHEET.getSprite(16, 48, 16, 48).getImage(), (int) (bounds.getMinX() + (bounds.getHeight()/3)), (int) bounds.getMinY(), (int) (bounds.getWidth() - ((bounds.getHeight()/3) * 2)), (int) bounds.getHeight(), null);
//            g.drawImage(SPRITE_SHEET.getSprite(32, 48, 16, 48).getImage(), (int) (bounds.getMinX() + bounds.getWidth() - (bounds.getHeight()/3)), (int) bounds.getMinY(), (int) (bounds.getHeight()/3), (int) bounds.getHeight(), null);
            g.drawString("> " + value + " <", (int) (location.getX() - g.getFontMetrics().stringWidth("> " + value + " <") / 2), (int) (location.getY() + g.getFontMetrics().getHeight() / 4));
        }


        g.setColor(color);

    }

    @Override
    public void hover() {
        if(!hover){
            AudioManager.playSound(Sounds.UI_BUTTON_CLICK,-10);
        }
        super.hover();
    }

    @Override
    public void unhover() {
        if(hover){
            AudioManager.playSound(Sounds.UI_BUTTON_UNCLICK,-40F);
        }
        super.unhover();
    }

    @Override
    public void unclick() {
        super.unclick();
        runnable.run();
    }
}
