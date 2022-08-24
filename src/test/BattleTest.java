package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import unsw.loopmania.*;
import unsw.loopmania.BasicItems.*;

import unsw.loopmania.Buildings.*;
import unsw.loopmania.Buildings.BattleBuildings.BattleBuilding;
import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;
import unsw.loopmania.RareItems.Anduril;

public class BattleTest {
    public static final int START_HEALTH = 100;
    public static final int START_EXP = 0;
    public static final int START_GOLD = 0;
    public static final int  BASE_DAMEGE = 5;
    
    public static final int LOW_HEALTH = 10;
    public static final int MED_HEALTH = 30;
    public static final int HIGH_HEALTH = 100;

    public static final int LOW_DAMAGE = 2;
    public static final int MED_DAMAGE = 5;
    public static final int HIGH_DAMAGE = 10;

    public static final int LOW_EXP = 1;
    public static final int MED_EXP = 2;
    public static final int HIGH_EXP = 5;

    public static final int LOW_GOLD = 1;
    public static final int MED_GOLD = 2;
    public static final int HIGH_GOLD = 3;

    public static final int SHORT_RADIUS = 1;
    public static final int MED_RADIUS = 3;
    public static final int LONG_RADIUS = 5;




    @Test
    public void Test1(){
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        assertEquals(d.getWidth(), 50);
        assertEquals(d.getHeight(), 30);
    }
    
    /**
     * test that character and slug health will decrease after battle
     */
    @Test
    public void TestBattleSlug(){
        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(5, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);
        PathPosition p3 = new PathPosition(2, orderedPath);

        //spawn slug and character next to eachother
        Slug s1 = new Slug(p1);
        enemies.add(s1);
        Character c = new Character(p2);

        //spawn another slug far away from character
        Slug s2 = new Slug(p3);
        enemies.add(s2);

        BattleEnemyController battleEnemyController = new BattleEnemyController();
        Battle battle = new Battle(null, c, battleEnemyController, enemies, s1, new ArrayList<BattleBuilding>(), 50);
        battle.dealDamageOnce();
        

       
        //test that after battle, health of both character and slug is decreased
        assertTrue(s1.getCurrentHealth() < LOW_HEALTH);
        assertTrue(c.getCurrentHealth() < START_HEALTH);

        //slug2 uneffected because it's far away from battle
        assertEquals(s2.getCurrentHealth(), LOW_HEALTH);
    }

    /**
     * Test that a vampire critical bite can occur which decreases the health more
     */
    @Test
    public void TestVampireCritical(){
        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);

        boolean criticalOccured = false;
        for(int i = 0; i < 1000; i++) {
            List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
            //spawn slug and character next to eachother
            Vampire v1 = new Vampire(p1);
            enemies.add(v1);
            Character c = new Character(p2);

            //spawn another slug far away from character

            BattleEnemyController battleEnemyController = new BattleEnemyController();
            Battle battle = new Battle(null, c, battleEnemyController, enemies, v1, new ArrayList<BattleBuilding>(), 0);
            battle.dealDamageOnce();

            if (c.getCurrentHealth() < 100 - HIGH_DAMAGE) {
                criticalOccured = true;
            }
        }
        assertTrue(criticalOccured);

    }


    /**
     * test that when a character is in an enemy's support radius, it will join in on an existing fight
     */
    @Test
    public void TestBattleSupportRadius(){

        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        //LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(5, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);
        PathPosition p3 = new PathPosition(2, orderedPath);

        //spawn a vampire that is 4 units away from character. Within support radius, but not battle radius.
        Vampire v1 = new Vampire(p3);
        enemies.add(v1);
        Character c = new Character(p2);

        //d.runBattles(null);
        Battle battle = new Battle(null, c, null, enemies, v1, new ArrayList<BattleBuilding>(), 50);
        battle.dealDamageOnce();
        assertTrue(v1.getCurrentHealth() == HIGH_HEALTH);
        assertTrue(c.getCurrentHealth() == START_HEALTH);

        
        //spawn slug and character next to eachother, should trigger zombie to join
        Slug s1 = new Slug(p1);
        enemies.add(s1);
        Battle battle1 = new Battle(null, c, null, enemies, s1,  new ArrayList<BattleBuilding>(), 50);
        battle1.dealDamageOnce();

        //d.runBattles(new LoopManiaWorldController(world, initialEntities);

        //test that after battle, health of both character and slug is decreased
        assertTrue(v1.getCurrentHealth() < HIGH_HEALTH);
        assertTrue(c.getCurrentHealth() < START_HEALTH);
        assertTrue(s1.getCurrentHealth() < LOW_HEALTH);
        
    }

    /**
     * test that enemies experience more damange if the battle takes place with weapons
     */
    @Test
    public void TestBattleWithWeapons(){

        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        //LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(5, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);
        PathPosition p3 = new PathPosition(2, orderedPath);

        Vampire v1 = new Vampire(p3);
        enemies.add(v1);
        Slug s1 = new Slug(p1);
        enemies.add(s1);
        Zombie z1 = new Zombie(p1);
        enemies.add(z1);

        Character c = new Character(p2);
        c.changeEquippedWeapon(new Sword(null, null));

        Battle battle1 = new Battle(null, c, null, enemies, s1,  new ArrayList<BattleBuilding>(), 50);
        battle1.dealDamageOnce();

        //d.runBattles(new LoopManiaWorldController(world, initialEntities);

        //test that after battle, health of both character and slug is decreased to more than the basic damage
        assertTrue(v1.getCurrentHealth() < HIGH_HEALTH - c.getBaseDamage());
        assertTrue(c.getCurrentHealth() < START_HEALTH);
        assertTrue(s1.getCurrentHealth() < LOW_HEALTH - c.getBaseDamage());
        assertTrue(z1.getCurrentHealth() < MED_HEALTH - c.getBaseDamage());
    }


    /**
     * Test that the character will experience less damage if the battle takes place with an armour
     */
    @Test
    public void TestBattleWithArmour(){

        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        //LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(5, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);
        PathPosition p3 = new PathPosition(2, orderedPath);

        Vampire v1 = new Vampire(p3);
        enemies.add(v1);
        Slug s1 = new Slug(p1);
        enemies.add(s1);
        Zombie z1 = new Zombie(p1);
        enemies.add(z1);

        Character c = new Character(p2);
        c.changeEquippedDefence(new Armour(null, null));
        Battle battle1 = new Battle(null, c, null, enemies, s1,  new ArrayList<BattleBuilding>(), 50);
        battle1.dealDamageOnce();

        //d.runBattles(new LoopManiaWorldController(world, initialEntities);

        //test that after battle, health of both character and slug is decreased
       
        assertTrue(v1.getCurrentHealth() == HIGH_HEALTH - c.getBaseDamage());
        assertTrue(c.getCurrentHealth() > START_HEALTH - v1.getDamage() - s1.getDamage() - z1.getDamage() );
        assertTrue(s1.getCurrentHealth() == LOW_HEALTH - c.getBaseDamage());
        assertTrue(z1.getCurrentHealth() == MED_HEALTH - c.getBaseDamage());
    }

    /**
     * Test that the character will experience less damage if the battle takes place with a shield
     */
    @Test
    public void TestBattleWithShield(){

        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        //LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);

        
        //c.changeEquippedWeapon(new Stake(null, null));
        int c1HigherHealth = 0;
        int c2HigherHealth = 0;
        //count number of battle won if there is shield
        int battlesWonNoShield = 0;
        for (int i = 0; i < 1000; i ++){
            //two battles occur, exactly the same except on has shield and one doesnt
            Character c1 = new Character(p2);
            List<BasicEnemy> enemies1 = new ArrayList<BasicEnemy>();
            Vampire v1 = new Vampire(p1);
            enemies1.add(v1);

            Character c2 = new Character(p2);
            c2.changeEquippedDefence(new Shield(null, null));
            List<BasicEnemy> enemies2 = new ArrayList<BasicEnemy>();
            Vampire v2 = new Vampire(p1);
            enemies2.add(v2);

            Battle battle1 = new Battle(null, c1, null, enemies1, v1,  new ArrayList<BattleBuilding>(), 50);
            battle1.dealDamageOnce();

            Battle battle2 = new Battle(null, c2, null, enemies2, v2,  new ArrayList<BattleBuilding>(), 50);
            battle2.dealDamageOnce();

            if (c1.getCurrentHealth() < c2.getCurrentHealth()) {
                c2HigherHealth ++;
            }
            else if (c2.getCurrentHealth() < c1.getCurrentHealth()) {
                c1HigherHealth ++;
            }
        }
        
        System.out.println(c1HigherHealth);
        System.out.println(c2HigherHealth);
        assertTrue(c1HigherHealth < c2HigherHealth);
    }

    /**
     * test that a critical bite can occur for zombie, which increases the amount of enemies
     */
    @Test
    public void testCriticalBite () {
        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        //LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);

        Character c = new Character(p2);
        Ally ally = new Ally(p1, 100);
        c.addAlly(ally);
        List<BasicEnemy> enemies1 = new ArrayList<BasicEnemy>();
       
        Zombie z = new Zombie (p2);
        enemies1.add(z);

        Battle battle1 = new Battle(null, c, null, enemies1, z,  new ArrayList<BattleBuilding>(), 50);


        int enemiesSize = battle1.getEnemiesToFight().size();
        for (int i = 0; i < 200; i ++){
            battle1.dealDamageOnce();
            c.increaseHealth(100);
            ally.increaseHealth(100);
            z.increaseHealth(100);
        }  

        int enemiesSize2 = battle1.getEnemiesToFight().size();

        assertTrue(enemiesSize2 > enemiesSize);
    }

    @Test
    public void testBattleAnduril() {
        
        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        //LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);

        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        ElanMuske elan = new ElanMuske(p1);
        Doggie doggie = new Doggie(p1);
        enemies.add(elan);
        enemies.add(doggie);

        Character c = new Character(p1);
        c.changeEquippedWeapon(new Anduril(null, null));
        Battle battle1 = new Battle(null, c, null, enemies, elan,  new ArrayList<BattleBuilding>(), 50);
        battle1.dealDamageOnce();


        System.out.println(elan.getCurrentHealth());
        System.out.println(doggie.getCurrentHealth());

        List<BasicEnemy> enemies2 = new ArrayList<BasicEnemy>();
        ElanMuske elan2 = new ElanMuske(p1);
        Doggie doggie2 = new Doggie(p1);
        
        Character c2 = new Character(p1);
        Battle battle2 = new Battle(null, c2, null, enemies2, elan2,  new ArrayList<BattleBuilding>(), 50);
        battle2.dealDamageOnce();

       
        assertTrue(elan.getCurrentHealth() < elan2.getCurrentHealth());
        assertTrue(doggie.getCurrentHealth() < doggie2.getCurrentHealth());
    
    }
}
