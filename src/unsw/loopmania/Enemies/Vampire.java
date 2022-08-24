package unsw.loopmania.Enemies;

import java.io.File;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Health;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.RareItems.TheOneRing;
import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.BasicItems.BasicItem;
import unsw.loopmania.BasicItems.Helmet;
import unsw.loopmania.BasicItems.Shield;
import unsw.loopmania.BasicItems.*;
import unsw.loopmania.Cards.CampfireCard;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.TrapCard;
import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.RareItems.Anduril;
import unsw.loopmania.RareItems.TreeStump;

public class Vampire extends BasicEnemy{

    
    public Vampire(PathPosition position) {
        super(position, new Health(100), 3, 5, 10, new ImageView(new Image((new File("src/images/vampire.png")).toURI().toString())));
    }

    /**
     * a 30% chance of inflicting extra damage (30), otherwise damage is 10
     */
    @Override
    public int getDamage() {
        Random random = new Random();
        double r = random.nextDouble();
        if (r < 0.3) {
            return 30;
        }

        return 10;
    }

    /**
     * Give randomly generated weapon
     * @param x, y, the location of the weapon if one is generated
     * @return StaticEntity
     */
    @Override
    public StaticEntity onDeath(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Random random = new Random();
        double r = random.nextDouble();
        double r2 = random.nextInt(200);
        //System.out.print(r);
        if (r < 0.5){
            return new Sword(x, y);
        } else if (r < 0.3) {
            return new Stake(x, y);
        } else if (r < 0.45){
            return new Staff(x, y);
        } else if (r < 0.6){
            return new VampireCastleCard(x, y);
        } else if (r < 0.70) {
            if (r2 <= 10) {
                return new TheOneRing(x, y);
            }
        } else if (r < 0.75) {
            return new ZombiePitCard(x, y);
        } else if (r < 0.80) {
            if (r2 <= 40) {
                return new Anduril(x, y);
            }
        }
        else if (r < 0.85) {
            if (r2 >= 170) {
                return new TreeStump(x, y);
            }
        }else if (r < 0.9) {
            return new VillageCard(x, y);
        }
        else if (r < 0.95){
            return new TowerCard(x, y);
        }
        return null;

    }

     

    /**
     * move forwards 90% of the time, otherwise stays still
     */
    @Override
    public void move(){
        double directionChoice = (new Random()).nextDouble();
        if (directionChoice < 0.9){
            moveDownPath();
        }
        
    }

    @Override
    public int getGold () {
        return 100;
    }

    @Override
    public int getExperience () {
        return 200;
    }

    @Override
    public void dealDamage(DefendingStrategy defence, Character c, List<BasicEnemy> enemies) {
        if (defence == null){
            c.decreaseHealth(this.getDamage());
        }
        else {
            defence.reduceVampireDamage(this, c);
        }
    }
}
