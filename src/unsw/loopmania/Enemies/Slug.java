package unsw.loopmania.Enemies;

import unsw.loopmania.LoopManiaWorld;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import jdk.dynalink.beans.StaticClass;
import unsw.loopmania.Health;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.RareItems.TheOneRing;
import unsw.loopmania.BasicItems.Armour;
import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.BasicItems.BasicItem;
import unsw.loopmania.BasicItems.Stake;
import unsw.loopmania.BasicItems.Sword;
import unsw.loopmania.BasicItems.*;
import unsw.loopmania.Cards.*;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Buildings.SpawnBuildings.*;
import unsw.loopmania.Buildings.BattleBuildings.*;
import unsw.loopmania.Buildings.PathBuildings.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.RareItems.Anduril;
import unsw.loopmania.RareItems.TreeStump;


public class Slug extends BasicEnemy{

    static int gold = 10;
    static int experience = 8;
    
    public Slug (PathPosition position) {
        super(position, new Health(10), 1, 1, 2, new ImageView(new Image((new File("src/images/slug.png")).toURI().toString())) );
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
        if (r < 0.05){
            return new Sword(x, y);
        } 
        else if (r < 0.1) {
            return new Stake(x, y);
        } else if (r < 0.15){
            return new Staff(x, y);
        } else if (r < 0.2){
            return new Armour(x, y);
        } else if (r < 0.25) {
            return new Shield(x, y);
        } else if (r < 0.3) {
            return new Helmet(x, y);
        }else if (r < 0.35){
            return new VampireCastleCard(x, y);
        } else if (r < 0.40) {
            return new ZombiePitCard(x, y);
        } else if (r < 0.45) {
            return new TowerCard(x, y);
        } else if (r < 0.50) {
            return new VillageCard(x, y);
        } else if (r < 0.55) {
            return new BarracksCard(x, y);
        } else if (r < 0.60) {
            return new TrapCard(x, y);
        } else if (r < 0.65) {
            return new CampfireCard(x, y);
        }
        else if (r < 0.70) {
            if (r2 <= 2) {
                return new TheOneRing(x, y);
            }
        }
        else if (r < 0.75) {
            if (r2 <= 20 ) {
                return new Anduril(x, y);
            }
        }
        else if (r < 0.90) {
            if (r2 <= 40) {
                return new TreeStump(x, y);
            }
        }
        return null;
    }

  

    /**
     * slugs move forwards 25% of the time, backwards 25% of the time, and stay still otherwises
     */
    @Override
    public void move(){
        int directionChoice = (new Random()).nextInt(4);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    @Override
    public int getGold () {
        return 3;
    }

    @Override
    public int getExperience () {
        return 10;
    }

    @Override
    public void dealDamage(DefendingStrategy defence, Character c, List<BasicEnemy> enemies) {
        if (defence == null){
            c.decreaseHealth(this.getDamage());
        }
        else {
            defence.reduceSlugDamage(this, c);
        }
        
    }
}
