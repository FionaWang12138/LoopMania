package unsw.loopmania.Buildings.PathBuildings;

import java.util.List;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Ally;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.SpawnBuildings.SpawnBuilding;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.BasicEnemy;

public class BarracksBuilding extends PathBuilding {

    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/barracks.png")).toURI().toString())));
    }

    /**
     * returns ally if character passes over barracks building
     */

    @Override
    public Ally pathAction(Character character, List<BasicEnemy> enemies) {
        if (checkOnPath(character)) {
            int stepsBehind = character.getAllies().size() + 1;
            Ally ally = new Ally(character.getPathPosition().getPreviousPathPosition(stepsBehind), character.getCurrentHealth());
            
            character.addAlly(ally);
            System.out.println("ALLY COUNT");
            System.out.println(character.getAllies().size());

            return ally;
        }

        return null;
    }
}
