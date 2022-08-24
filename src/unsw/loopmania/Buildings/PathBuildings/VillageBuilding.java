package unsw.loopmania.Buildings.PathBuildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Ally;
import unsw.loopmania.Enemies.BasicEnemy;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VillageBuilding extends PathBuilding {

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/village.png")).toURI().toString())));
    }

    /**
     * Increase the health of the Character when they pass over the Village building
     */
    @Override
    public Ally pathAction(Character character, List<BasicEnemy> enemies) {
        
        if (checkOnPath(character)) {
            character.increaseHealth(20);
        }
        
        return null;
    }
}
