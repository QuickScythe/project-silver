package com.quickscythe.titanium.utils.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.quickscythe.titanium.utils.Resources;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameSound extends PlaybackListener implements Runnable {

    private AdvancedPlayer player;

    public void play() {

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("audio/raid.mp3"));
        sound.play(1F);
        try {

//            Sound sound = new Sound(new BufferedInputStream(Resources.getFile("audio/sub.mp3")));
//            // no need to buffer the SoundInputStream
//
//            // get sound metadata
//            System.out.println(sound.getSamplingFrequency());
//
//            // let's copy the decoded data samples into a file!
//            Files.copy(sound, Paths.get("/raw.raw"));
//
//
//            player = new AdvancedPlayer(Resources.getFileInputStream("audio/raid.mp3"));
////            this.player = new AdvancedPlayer(new FileInputStream("C:\\Users\\Camer\\OneDrive\\Pictures\\Stream Assets\\Audio\\raid2.mp3"), FactoryRegistry.systemRegistry().createAudioDevice());
//
//
//            this.player.setPlayBackListener(this);
//
//            Thread playerThread = new Thread(this, "AudioPlayerThread");
//
//            playerThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent) {
        System.out.println("playbackStarted");
    }

    public void playbackFinished(PlaybackEvent playbackEvent) {
        System.out.println("playbackEnded");
    }

    // Runnable members

    public void run() {
        try {
            this.player.play();
        } catch (javazoom.jl.decoder.JavaLayerException ex) {
            ex.printStackTrace();
        }

    }

}
