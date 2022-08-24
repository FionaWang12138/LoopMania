package unsw.loopmania.Buildings.BattleBuildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.MovingEntity;

public class TowerBuilding extends BattleBuilding {

    public static final int HIGH_DAMAGE = 10;
    public static final int LONG_RADIUS = 5;
    
    private int damage;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        
        super(x, y, new ImageView(new Image((new File("src/images/tower.png")).toURI().toString())));
        setRadius(LONG_RADIUS);
        this.damage = HIGH_DAMAGE;
        
    }

    public int getDamage() {
        return this.damage;
    }

    /**
     * Check if enemy is in radius
     */
    @Override
    public boolean checkRadius(MovingEntity entity) {
        
        if (Math.pow((entity.getX()-this.getX()), 2) +  Math.pow((entity.getY()-this.getY()), 2) <= Math.pow(getRadius(),2)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tower deals damage to the enemy in the radius during a battle
     */
    @Override
    public void buildingAction(MovingEntity character, MovingEntity enemy) {
        // attack the enemy by decreasing enemy health by tower damage
        if (checkRadius(enemy)) {
            enemy.decreaseHealth(this.damage);
        }
    }
}