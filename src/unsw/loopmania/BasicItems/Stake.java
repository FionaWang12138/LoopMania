package unsw.loopmania.BasicItems;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.CharacterFolder.Character;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;


/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends BasicItem implements AttackingStrategy {

    public static final int COST = 10;
    public int damage;
    
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, COST, new ImageView(new Image((new File("src/images/stake.png")).toURI().toString())));
    } 

    public Stake getStake() {
        return this;
    }
    
    /**
     * Reduces slug health by a random value between 2 and 5
     */
    public void reduceSlugHealth(Slug slug, int loopCount) {

        damage = (int)Math.floor(Math.random()*(5-2+1)+2);     
        slug.decreaseHealth(damage);
    }

    /**
     * Reduces zombie health by a random value between 2 and 5
     */
    public void reduceZombieHealth(Zombie zombie, int loopCount) {
        damage = (int)Math.floor(Math.random()*(5-2+1)+2);     
        zombie.decreaseHealth(damage);
    }
    
    /**
     * Reduces vampire health by 30, causing very high damage
     */
    public void reduceVampireHealth(Vampire vampire, int loopCount) {
        vampire.decreaseHealth(30);
    }

    @Override
    public void reduceDoggieHealth(Doggie doggie, Character character) {
    }

    @Override
    public void reduceElanMuskeHealth(ElanMuske elanMuske, Character character) {
    }
}