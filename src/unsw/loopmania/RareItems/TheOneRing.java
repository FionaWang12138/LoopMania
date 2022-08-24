package unsw.loopmania.RareItems;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.CharacterFolder.Character;

import java.io.File;

/**
 * Basic Items
 */
public class TheOneRing extends StaticEntity {
    
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y,new ImageView(new Image((new File("src/images/the_one_ring.png")).toURI().toString())) );
    }

    public void reviveCharacter(Character c) {
        c.increaseHealth(100);
    }
   
}