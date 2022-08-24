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
import unsw.loopmania.BasicItems.Staff;
import unsw.loopmania.BasicItems.Stake;
import unsw.loopmania.BasicItems.Sword;
import unsw.loopmania.BasicItems.*;
import unsw.loopmania.Cards.BarracksCard;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.RareItems.Anduril;
import unsw.loopmania.RareItems.TreeStump;

public class Zombie extends BasicEnemy{    
    public Zombie(PathPosition position) {
        super(position, new Health(30), 3, 3, 5, new ImageView(new Image((new File("src/images/zombie.png")).toURI().toString())));
    }
    
    /**
     * Give randomly generated weapon
     * @param x, y, the location of the weapon if one is generated
     * @return StaticEntity
     */
    @Override
    public StaticEntity onDeath(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        //System.out.print("generating random");
        Random random = new Random();
        double r = random.nextDouble();
        double r2 = random.nextInt(200);
        //System.out.print(r);
        if (r < 0.1){
            return new Sword(x, y);
        } else if (r < 0.2) {
            return new Stake(x, y);
        } else if (r < 0.3){
            return new Staff(x, y);
        } else if (r < 0.4){
            return new Armour(x, y);
        } else if (r < 0.5) {
            return new Shield(x, y);
        } else if (r < 0.6) {
            return new Helmet(x, y);
        } else if (r < 0.65) {
            if (r2 <= 10) {
                return new TheOneRing(x, y);
            }
        } else if (r < 0.7) {
            return new BarracksCard(x, y);
        }  else if (r < 0.75) {
            if (r2 <= 50) {
                return new Anduril(x, y);
            }
        }
        else if (r < 0.80) {
            if (r2 <= 100) {
                return new TreeStump(x, y);
            }
        }else if (r < 0.85) {
            return new TrapCard(x, y);
        } else if (r < 0.95) {
            return new CampfireCard(x, y);
        }

        return null;
    }
    

    /**
     * moves slowly, only moves forward 50% of the time
     */
    @Override
    public void move(){
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveDownPath();
        }
    }

    @Override
    public int getGold () {
        return 50;
    }

    @Override
    public int getExperience () {
        return 120;
    }

    @Override
    public void dealDamage(DefendingStrategy defence, Character c, List<BasicEnemy> enemies) {
        if (defence == null){
            c.decreaseHealth(this.getDamage());
        }
        else {
            defence.reduceZombieDamage(this, c);
        }
        
    }
}
