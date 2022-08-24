package unsw.loopmania;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ally extends MovingEntity{

    public Ally(PathPosition position, int health) {
        super(position, new Health(health), new ImageView(new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString())));
    }
    
    
}