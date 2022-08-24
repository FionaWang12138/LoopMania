package unsw.loopmania;

import unsw.loopmania.BasicItems.BasicItem;
import unsw.loopmania.CharacterFolder.Character;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class HealthPotion extends BasicItem {

    public static final int COST = 40;
    
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y, 40, new ImageView(new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString())));
    }

    public void refillHealth(Character c) {
        c.increaseHealth(100);
    }
}
