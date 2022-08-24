package unsw.loopmania.BasicItems;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends BasicItem implements AttackingStrategy {
    
    public static final int COST = 5;
    public int damage = 2;

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, COST,new ImageView(new Image((new File("src/images/staff.png")).toURI().toString())));
    }   
    
    public Staff getStaff() {
        return this;
    }

    /**
     * Reduces slug health by 2
     */
    public void reduceSlugHealth(Slug slug, int loopCount) {
        slug.decreaseHealth(damage);
    }

    /**
     * Reduces zombie health by 2
     */
    public void reduceZombieHealth(Zombie zombie, int loopCount) {
        zombie.decreaseHealth(damage);        
    }
    
    /**
     * Reduces vampire health by 2
     */
    public void reduceVampireHealth(Vampire vampire, int loopCount) {
        vampire.decreaseHealth(damage);
    }

    @Override
    public void reduceDoggieHealth(Doggie doggie, Character character) {
    }

    @Override
    public void reduceElanMuskeHealth(ElanMuske elanMuske, Character character) {
    }
}