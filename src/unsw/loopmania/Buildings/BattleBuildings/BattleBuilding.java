package unsw.loopmania.Buildings.BattleBuildings;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.Buildings.Building;

public abstract class BattleBuilding extends Building {
    
    /**
     * if character/enemy is within radius, buildiong performs action.
     */
    private int radius;

    public BattleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y, ImageView image) {
        super(x, y, image);
    }

    public abstract boolean checkRadius(MovingEntity entity);
    public abstract void buildingAction(MovingEntity character, MovingEntity enemy);

    public void setRadius (int radius) {
        this.radius = radius;
    }

    public int getRadius () {
        return this.radius;
    }
    
}
