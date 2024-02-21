package com.quickscythe.silver.utils.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import javazoom.jl.player.advanced.PlaybackListener;

public class GameSound extends PlaybackListener implements Runnable {



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


    // Runnable members

    public void run() {


    }

}
