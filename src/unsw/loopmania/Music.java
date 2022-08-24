package unsw.loopmania;
import java.io.File;

import javax.swing.JOptionPane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
    
    private MediaPlayer mediaPlayer;

    public Music(MediaPlayer mediaPlayer) {
        
        this.mediaPlayer = mediaPlayer;
    }

    public void playMusic (String musicFile) {

        try {

            Media sound = new Media(new File(musicFile).toURI().toString());
            this.mediaPlayer = new MediaPlayer(sound);
            this.mediaPlayer.play();
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Ur mum?");
        
        }
    }

    public void stopMusic () {
        this.mediaPlayer.stop();
    }
}