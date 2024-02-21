package com.quickscythe.titanium.utils.sounds;

import com.quickscythe.titanium.utils.Resources;

import javax.sound.sampled.*;
import java.io.IOException;

public enum Sounds {
    MUSIC_BACKGROUND_1("menu"), UI_BUTTON_CLICK("ui_click"), UI_BUTTON_UNCLICK("ui_unclick");

//    final Clip CLIP;


    Sounds(String filename) {


        System.out.println("New sounds");

//        try {
//            AudioInputStream stream = AudioSystem.getAudioInputStream(Resources.getFile("audio/" + filename + ".wav"));
//            CLIP = AudioSystem.getClip();
//            CLIP.open(stream);
//            CLIP.addLineListener(new SoundListener(CLIP));
//
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
    }

//    public Clip getClip() {
//        CLIP.setFramePosition(0);
//        return CLIP;
//    }
}
