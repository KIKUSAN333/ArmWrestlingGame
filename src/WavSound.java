import java.net.URL;
import javax.sound.sampled.*;

public class WavSound {
    Clip clip;

    public WavSound(URL url) {
        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(url);       
            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null)
            return;
        clip.setFramePosition(0);
        clip.start();
    }
}
