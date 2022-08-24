package unsw.loopmania.Buildings.PathBuildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.Ally;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.BasicEnemy;

public abstract class PathBuilding extends Building{

    public PathBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y, ImageView image) {
        super(x, y, image);
    }
    
    /**
     * pathAction returns Ally when a Barracks building and null if not a barracks building
     */

    public abstract Ally pathAction (Character character, List<BasicEnemy> enemies);

    /**
     * Check if entity is on path
     */
    public boolean checkOnPath (MovingEntity entity) {
        
        if ((entity.getX() == this.getX()) && (entity.getY() == this.getY())) {
            return true;
        } else {
            return false;
        }
    }
}
