package unsw.loopmania.Buildings.PathBuildings;

import java.io.File;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Ally;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.CharacterFolder.JailState;
import unsw.loopmania.Enemies.BasicEnemy;

public class JailBuilding extends PathBuilding {

    public JailBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/jail.png")).toURI().toString())));
    }

    /**
     * when a character lands on a jail, its state will be changed to jail state, and health halved
     */
    @Override
    public Ally pathAction(Character character, List<BasicEnemy> enemies) {
        if (checkOnPath(character) && character.getAllies().size() == 0){
            if (! (character.getState() instanceof JailState)) {
                character.changeState(new JailState(character));
                character.decreaseHealth(character.getCurrentHealth()/2);
            }
            
        }
        return null;
    }
    
}
