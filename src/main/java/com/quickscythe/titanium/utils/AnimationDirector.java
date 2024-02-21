package com.quickscythe.titanium.utils;

import com.quickscythe.titanium.utils.sprites.Sprite;
import com.quickscythe.titanium.utils.sprites.SpriteSheet;

import java.awt.*;
import java.util.Date;

public class AnimationDirector {

    SpriteSheet sprite_sheet;
    Animation[] animations;
    int current_animation;


    public AnimationDirector(SpriteSheet sprite_sheet) {
        this.sprite_sheet = sprite_sheet;
        animations = new Animation[sprite_sheet.getBufferedImage().getHeight() / sprite_sheet.getHeight()];


        for (int i = 0; i < animations.length; i++) {

            Color color = new Color(sprite_sheet.getBufferedImage().getRGB(i, sprite_sheet.getBufferedImage().getHeight() - 1));
            System.out.println("Animation " + i + " has " + color.getRed() + " frames");
            animations[i] = new Animation(sprite_sheet, i, color.getRed(), color.getGreen());
        }
    }

    public Animation getAnimation() {
        return getAnimation(current_animation);
    }

    public void setAnimation(int animation_id) {
        if (current_animation != animation_id) {
            this.current_animation = animation_id;
            animations[current_animation].update();
        }
    }

    public Animation getAnimation(int animation) {
        try {
            return animations[animation];
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new RuntimeException("");

        }
    }


    public static class Animation {
        Sprite[] frames;
        int speed;
        int frame = 0;

        long last_frame_update;

        int id;

        public Animation(SpriteSheet sheet, int id, int frames, int speed) {

//            BufferedImage bimg = Resources.toBufferedImage(image);
            this.speed = speed;
            this.frames = new Sprite[frames];
            for (int i = 0; i < frames; i++) {
                this.frames[i] = sheet.getSprite(sheet.getWidth() * i, id * sheet.getHeight(), sheet.getWidth(), sheet.getHeight());
//                this.frames[i] = new Sprite(bimg.getSubimage(size * i, 0, size, size));
            }
            update();

        }

        public void update() {
            last_frame_update = new Date().getTime();
        }

        public Sprite getFrame() {
            if (GameUtils.paused()) {
                last_frame_update = new Date().getTime();
                return frames[frame];
            }
            if (new Date().getTime() - last_frame_update >= speed) {
                last_frame_update = new Date().getTime();
                frame = frame + 1;
                if (frame >= frames.length) {
                    frame = 0;
                }

            }
            return frames[frame];

        }
    }


}
