package com.quickscythe.silver.utils.sounds;


public class AudioManager {


    public static void playSound(Sounds sound, float volume) {
        new GameSound().play();


//        Clip clip = sound.getClip();
//        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        volumeControl.setValue(volume);
//
//        FloatControl bassControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
//        bassControl.setValue(1);
//        clip.start();


//        try (final AudioInputStream in = getAudioInputStream(Resources.getFile("audio/raid.mp3"))) {
//
//            final AudioFormat outFormat = getOutFormat(in.getFormat());
//            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);
//
//            try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
//
//                if (line != null) {
//                    line.open(outFormat);
//                    line.start();
//                    stream(getAudioInputStream(outFormat, in), line);
//                    line.drain();
//                    line.stop();
//                }
//            }
//
//        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
//            throw new IllegalStateException(e);
//        }


    }


}
