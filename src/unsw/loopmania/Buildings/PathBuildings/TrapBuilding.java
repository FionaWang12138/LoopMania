package unsw.loopmania.Buildings.PathBuildings;

import java.util.List;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Ally;
import unsw.loopmania.Enemies.BasicEnemy;

public class TrapBuilding extends PathBuilding {

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/trap.png")).toURI().toString()))) ;
    }

    /**
     * decreases an enemy's health when it passes over it
     */
    @Override
    public Ally pathAction(Character character, List<BasicEnemy> enemies) {
        
        for (BasicEnemy b : enemies) {
            if (checkOnPath(b)) {
                b.decreaseHealth(b.getCurrentHealth()/5);
            }
        }

        return null;
    }
}
