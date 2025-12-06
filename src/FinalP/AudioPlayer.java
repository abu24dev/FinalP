package FinalP;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private boolean isMuted = false;
    private long clipTimePosition = 0;

    public AudioPlayer(String filePath) {
        try {
            File audioFile = new File(filePath);

            if (audioFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } else {
                System.out.println("Error: File not found  " + filePath);
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading sound: " + e.getMessage());
        }
    }


    public void playMusic() {
        if (clip != null && !isMuted) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }


    public void toggleMute() {
        if (clip == null) return;

        if (isMuted) {
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMuted = false;
        } else {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
            isMuted = true;
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
}