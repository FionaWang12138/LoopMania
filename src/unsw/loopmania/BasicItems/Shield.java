package unsw.loopmania.BasicItems;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;


/**
 * represents an equipped or unequipped shield in the backend world
 */
public class Shield extends BasicItem implements DefendingStrategy {

    public static final int COST = 20;
    
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, COST, new ImageView(new Image((new File("src/images/shield.png")).toURI().toString())));
    }    

    public Shield getShield() {
        return this;
    }

    /**
     * Reduces the damage done by the slug by 1/2
     * Slug deals damage of 1
     * Character loses 1 health
     */
    public void reduceSlugDamage(Slug slug, Character c) {
        c.decreaseHealth(1);
    }

    /**
     * Reduces the damage done by the zombie by 1/2
     * Zombie deals damage of 3
     * Character loses 3 health
     */
    public void reduceZombieDamage(Zombie zombie, Character c) {
        c.decreaseHealth(3);
    }

    /**
     * Reduces the damage done by the vampire by 30 if critical bite
     * Otherwise Vampire deals damage of 5
     * The chance of critical bite by Vampire is reduced by 60% 
     */
    public void reduceVampireDamage(Vampire vampire, Character c) {
        Random random = new Random();
        double r = random.nextDouble();

        // reduces chance of critical bite by 60%, original chance is 30%, now the chance is 12%
        if (r <= 0.12) {
            c.decreaseHealth(30);
        }
        else {
            c.decreaseHealth(5);
        }
    }
    
    @Override
    public void reduceDoggieDamage(Character character, Doggie doggie) {
    }

    @Override
    public void reduceElanMuskeDamage(Character character, ElanMuske elanMuske) {  
    }
}