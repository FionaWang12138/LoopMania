package unsw.loopmania.Enemies;

import java.util.List;
import java.util.Random;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import unsw.loopmania.Health;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.BasicItems.BasicItem;
import unsw.loopmania.BasicItems.DefendingStrategy;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.CharacterFolder.Character;


/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity {
    private int attackRadius;
    private int supportRadius;
    private int damage;

    public BasicEnemy(PathPosition position, Health health, int attackRadius, int supportRadius, int damage, ImageView image) {
    //public BasicEnemy(PathPosition position, Health health, int attackRadius, int supportRadius, int damage) {
        super(position, health, image);
        this.attackRadius = attackRadius;
        this.supportRadius = supportRadius;
        this.damage = damage;
        //this.image = image;
    }

    public int getAttackRadius() {
        return attackRadius;
    }

    public int getSupportRadius() {
        return supportRadius;
    }

    public int getDamage() {
        return damage;
    }
    
    
    public abstract StaticEntity onDeath(SimpleIntegerProperty x, SimpleIntegerProperty y);
    //public abstract Card giveCardWhenLooted(SimpleIntegerProperty x, SimpleIntegerProperty y);
    public abstract void move();

    public abstract int getGold();

    public abstract int getExperience();

    public abstract void dealDamage(DefendingStrategy defence, Character c, List<BasicEnemy> enemies);
}
