package unsw.loopmania.RareItems;

import unsw.loopmania.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.Enemies.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/**
 * Represents an Anduril, Flame of the West in the backend world
 * An Anduril triples damage done to bosses
 */
public class Anduril extends StaticEntity implements AttackingStrategy {
    
    private int damage;
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString())));
    } 

    public Anduril getAnduril() {
        return this;
    }
    
    /**
     * Reduces doggie health by 3 times the normal damage
     */
    public void reduceDoggieHealth(Doggie doggie, Character character) {
        
        damage = character.getBaseDamage() * 3;
        doggie.decreaseHealth(damage);
    }

    /**
     * Reduces Elan Muske health by 3 times the normal damage
     */
    public void reduceElanMuskeHealth(ElanMuske elanMuske, Character character) {
        
        damage = character.getBaseDamage() * 3;
        elanMuske.decreaseHealth(damage);
    }

    @Override
    public void reduceSlugHealth(Slug slug, int loopCount) { 
    }

    @Override
    public void reduceZombieHealth(Zombie zombie, int loopCount) {
    }

    @Override
    public void reduceVampireHealth(Vampire vampire, int loopCount) {
    }
}
