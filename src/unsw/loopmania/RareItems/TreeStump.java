package unsw.loopmania.RareItems;

import unsw.loopmania.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.BasicItems.DefendingStrategy;
import unsw.loopmania.Enemies.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/**
 * Represents an Anduril, Flame of the West in the backend world
 * An Anduril triples damage done to bosses
 */
public class TreeStump extends StaticEntity implements DefendingStrategy{
    
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/tree_stump.png")).toURI().toString())));
    } 

    public TreeStump getTreeStump() {
        return this;
    }
    
    /**
     * Reduces doggie damage by 5 times
     */
    public void reduceDoggieDamage(Character character, Doggie doggie) {
        character.decreaseHealth(doggie.getDamage() / 5);
    }

    /**
     * Reduces Elan Muske damage by 5 times
     */
    public void reduceElanMuskeDamage(Character character, ElanMuske elanMuske) {
        character.decreaseHealth(elanMuske.getDamage() / 5);
    }

    @Override
    public void reduceSlugDamage(Slug slug, Character c) {
    }

    @Override
    public void reduceZombieDamage(Zombie zombie, Character c) {
    }

    @Override
    public void reduceVampireDamage(Vampire vampire, Character c) { 
    }
}
