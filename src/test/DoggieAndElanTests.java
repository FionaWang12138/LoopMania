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
import unsw.loopmania.CharacterFolder.RegularState;
import unsw.loopmania.CharacterFolder.StunnedState;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;

public class DoggieAndElanTests {

    @Test
    public void TestDoggieSpawn() {
        JFXPanel jfxPanel = new JFXPanel();

        Doggie doggie = new Doggie(null);
        assertTrue(doggie.onDeath(null, null) instanceof DoggieCoin);
        
    }

    /**
     * test that when a doggie and other enemies are in a battle with character, character is sometimes stunned
     */
    @Test
    public void TestDoggieStun() {
        JFXPanel jfxPanel = new JFXPanel();
        //test battle
        List<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 2);
        
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
        Doggie d1 = new Doggie(p2);
        enemies.add(d1);

        Character c = new Character(p2);

        Battle battle = new Battle(null, c, null, enemies, d1, new ArrayList<BattleBuilding>(), 0);
        assertTrue(c.getState() instanceof RegularState);
        battle.dealDamageOnce();
        assertTrue(c.getState() instanceof StunnedState);
        battle.dealDamageOnce();
    }

    /**
     * test that in a battle, Elan will boost enemy health
     */
    @Test
    public void TestElan() {
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


        List<BasicEnemy> enemies1 = new ArrayList<BasicEnemy>();

        Zombie z = new Zombie(p2);
        enemies1.add(z);

        Battle battle1 = new Battle(null, c, null, enemies1, z, new ArrayList<BattleBuilding>(), 0);
        battle1.dealDamageOnce();
        battle1.dealDamageOnce();
       
    
        List<BasicEnemy> enemies2 = new ArrayList<BasicEnemy>();

        Zombie z2 = new Zombie(p2);
        enemies2.add(z2);

        ElanMuske elan = new ElanMuske(p2);
        enemies2.add(elan);


        Battle battle2 = new Battle(null, c, null, enemies2, elan, new ArrayList<BattleBuilding>(), 0);
        battle2.dealDamageOnce();
        battle2.dealDamageOnce();

        assertTrue(z2.getCurrentHealth() > z.getCurrentHealth());
    }
}
