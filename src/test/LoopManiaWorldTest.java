package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hamcrest.core.IsInstanceOf;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import unsw.loopmania.*;
import unsw.loopmania.BasicItems.*;

import unsw.loopmania.Buildings.*;
import unsw.loopmania.Buildings.BattleBuildings.BattleBuilding;
import unsw.loopmania.Buildings.PathBuildings.BarracksBuilding;
import unsw.loopmania.Buildings.PathBuildings.PathBuilding;
import unsw.loopmania.Buildings.SpawnBuildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.SpawnBuildings.ZombiePitBuilding;
import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;
import unsw.loopmania.RareItems.TreeStump;

public class LoopManiaWorldTest {

    /**
     * Tests the path placements 
     * Checks if cells are on the path of the game
     * or adjacent to it
     */
    @Test
    public void pathTests() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(2, 2);
        
        orderedPath.add(path1);


        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);

        assertTrue(d.isOnPath(new Pair(2,2)));
        assertFalse(d.isOnPath(new Pair(2,0)));
        assertFalse(d.isOnPath(new Pair(0,2)));
        assertFalse(d.isOnPath(new Pair(1,1)));

        assertTrue(d.isAdjacentToPath(new Pair(1,1)));
        //not adjecent to pathtile itself
        assertFalse(d.isAdjacentToPath(new Pair(2,2)));
        
        assertFalse(d.isAdjacentToPath(new Pair(0,0)));
        assertTrue(d.isAdjacentToPath(new Pair(1,2)));
        assertTrue(d.isAdjacentToPath(new Pair(2,1)));
    }
    
    /** 
     * Tests that a gold is correctly spawned 
     * and that the goldCount increases
    */
    @Test
    public void goldTest() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        orderedPath.add( new Pair<Integer,Integer>(0, 0));
        orderedPath.add( new Pair<Integer,Integer>(0, 1));
        orderedPath.add( new Pair<Integer,Integer>(0, 2));
        orderedPath.add( new Pair<Integer,Integer>(0, 3));
        orderedPath.add( new Pair<Integer,Integer>(0, 4));
        orderedPath.add( new Pair<Integer,Integer>(0, 5));

        PathPosition p1 = new PathPosition(0, orderedPath);

        Character c = new Character(p1);

        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        d.setCharacter(c);
        
        assertTrue(d.getGoldCount() == 0);

        for (int i = 0; i < 1000; i ++){
            d.possiblySpawnGold();
            d.runTickMoves();
        }
        assertTrue(d.getGoldCount() > 0);
        //expect 50 gold, but since random, accept margin of error
        assertEquals(d.getGoldCount(), 50, 30);
        
    }

    /** 
     * Tests that a health potion is correctly spawned 
     * and that the character's health increases to 100
    */
    @Test
    public void potionTest() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        orderedPath.add( new Pair<Integer,Integer>(0, 0));
        orderedPath.add( new Pair<Integer,Integer>(0, 1));
        orderedPath.add( new Pair<Integer,Integer>(0, 2));
        orderedPath.add( new Pair<Integer,Integer>(0, 3));
        orderedPath.add( new Pair<Integer,Integer>(0, 4));
        orderedPath.add( new Pair<Integer,Integer>(0, 5));

        PathPosition p1 = new PathPosition(0, orderedPath);

        
        Character c = new Character(p1);

        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        d.setCharacter(c);
        c.decreaseHealth(80);

        
        assertTrue(c.getCurrentHealth() == 20);

        for (int i = 0; i < 1000; i ++){
            d.possiblySpawnHealthPotion();
            d.runTickMoves();
        }
        assertTrue(c.getCurrentHealth() == 100);
    }

    /** 
     * Tests that a slug is correctly spawned 
     * and is added to the list of BasicEnemies
    */
    @Test
    public void slugSpawnTest() {
        
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        for (int i = 0; i < 1000; i++){
            orderedPath.add(new Pair<Integer,Integer>(0, i));
        }

        PathPosition p1 = new PathPosition(5, orderedPath);

        Character c = new Character(p1);

        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        d.setCharacter(c);
        for (int i = 0; i < 1000; i ++){
            d.possiblySpawnEnemies();
        }
        int slugCount = 0;
        for (BasicEnemy e: d.getAllBasicEnemies()) {
            if (e instanceof Slug){
                slugCount ++;
            }   
        }
        assertTrue (slugCount > 0);
        assertTrue(slugCount <= 5);
    }

    /** 
     * Tests that all buildings are correctly spawned 
     * and that the possible enemies they create are also 
     * correctly spawned
    */
    @Test
    public void testSpawnWithBuildings() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        for (int i = 0; i < 10; i++){
            orderedPath.add(new Pair<Integer,Integer>(0, i));
        }

        PathPosition p1 = new PathPosition(0, orderedPath);

        Character c = new Character(p1);

        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        d.setCharacter(c);
        d.addSpawnBuilding(new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
        d.addSpawnBuilding(new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2)));

        for (int i = 0; i < 100; i ++){
            d.possiblySpawnEnemies();
            d.runTickMoves();
        }

        int vampireCount = 0;
        int zombieCount = 0;
        //should compelte loop 16 times, expect 16 zombies and 3 ish vampires
        for (BasicEnemy e: d.getAllBasicEnemies()) {
            if (e instanceof Vampire){
                vampireCount ++;
            }
            else if (e instanceof Zombie) {
                zombieCount ++;
            }
        }

        assertEquals(10, zombieCount, 3);
        assertEquals(2, vampireCount, 1);
    }

    /** 
     * Tests that the correct item is added to the 
     * inventory depending of the type of enemy
     * we defeat and loot from
    */
    @Test
    public void unequippedInventoryTest() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, null);
        Character c = new Character(null);
        d.setCharacter(c);
        assertTrue(c.getAllInventoryItems().size() == 0);
        assertTrue(c.getGoldCount() == 0);
        for (int i = 0; i < 200; i++){
            StaticEntity lootedThing = new Slug(null).onDeath(null, null);
            if (lootedThing instanceof BasicItem) {
                d.addUnequippedItem((BasicItem) lootedThing);
            }
            
        }
        assertTrue(c.getAllInventoryItems().size() != 0);
        assertTrue(c.getGoldCount() > 0);
    }


     /** 
     * Tests that the correct item is added to the 
     * inventory depending of the type of enemy
     * we defeat and loot from
    */
    @Test
    public void unequippedRareTest() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, null);
        Character c = new Character(null);
        d.setCharacter(c);
        assertTrue(d.getAllRareItems().size() == 0);
        for (int i = 0; i < 200; i++){
            d.addRareItemToInventory(new TreeStump(null, null));
        }
        assertTrue(d.getAllRareItems().size() != 0);
    }


    /** 
     * Tests that the amount of gold a character owns is increased
     * as he picks up gold off the ground
    */
    @Test
    public void increaseGoldCount() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, null);
        Character c = new Character(null);
        d.setCharacter(c);
        assertTrue(c.getGoldCount() == 0);
        c.increaseGold(2);
        assertTrue(c.getGoldCount() == 2);
    }
    
    /**
     * test the adding and removing of cards
     */
    @Test
    public void testLoadandRemoveCard() {
        
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, null);
        
        Character c = new Character(null);
        d.setCharacter(c);
        
 
        for (int i = 0; i < 200; i++){
            d.loadCard(new CampfireCard(null, null));
        }
        

        int sizeBeforeRemoval = d.getAllCards().size();
        d.removeCard(0);
        int sizeAfterRemoval = d.getAllCards().size();
        assertTrue(sizeAfterRemoval == sizeBeforeRemoval - 1);
    }

    /** 
     * Tests that a character is added to the
     * list of nonSpecifiedEntities in LoopManiaWorld
    */
    @Test 
    public void addEntityTest() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, null);
        int size1 = d.getNonSpecifiedEntities().size();
        assertEquals(0, size1);
        Character c = new Character(null);
        d.setCharacter(c);
        d.addEntity(c);
        int size2 = d.getNonSpecifiedEntities().size();
        assertEquals(1, size2);
    }

    @Test
    public void testDoggieAndElanSpawn() {
         
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        for (int i = 0; i < 100; i++){
            orderedPath.add(new Pair<Integer,Integer>(0, i));
        }

        PathPosition p1 = new PathPosition(5, orderedPath);

        Character c = new Character(p1);

        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        d.setCharacter(c);
        c.addExperience(10000);
        for (int i = 0; i < 5000; i ++){
            c.move();
            d.possiblySpawnEnemies();
        }
        boolean doggie = false;
        boolean elan = false;
        for (BasicEnemy e: d.getAllBasicEnemies()) {
            if (e instanceof Doggie){
                doggie = true;
            }   
            if (e instanceof ElanMuske){
                elan = true;
            }
        }
        assertTrue (doggie);
        assertTrue (elan);
    }

    @Test
    public void testJailSpawn(){
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        for (int i = 0; i < 100; i++){
            orderedPath.add(new Pair<Integer,Integer>(0, i));
        }

        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);

        PathPosition p1 = new PathPosition(5, orderedPath);

        Character c = new Character(p1);

        d.setCharacter(c);

        for (int i = 0; i < 1000; i++){
            d.possiblySpawnJailBuilding();
        }   

        List<PathBuilding> buildings = d.getAllPathBuildings();

        assertTrue(buildings.size() > 0);
        //assertTrue(0 == 1);
    } 

}
