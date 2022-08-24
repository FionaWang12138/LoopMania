package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import unsw.loopmania.*;
import unsw.loopmania.BasicItems.*;

import unsw.loopmania.Buildings.*;

import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;

public class CardTest {

    /**
     * Place a vampire castle building in a valid position.
     */
    @Test
    public void Test1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(0, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(2, 0);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);
        JFXPanel jfxPanel = new JFXPanel(); 
        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(vampireCastleCard);

        assertEquals(vampireCastleCard, d.getCardByIndex(0));
    }

    /**
     * Place a zombie pit and a campfire building in the valid positions.
     */
    public void Test2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(0, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(2, 0);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);
        
        JFXPanel jfxPanel = new JFXPanel(); 
        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card zombiiePitCard = new ZombiePitCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(zombiiePitCard);

        Card campfireCard = new CampfireCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(campfireCard);

        assertEquals(zombiiePitCard, d.getCardByIndex(0));
        assertEquals(campfireCard, d.getCardByIndex(1));
    }

    /**
     * Place a vampire castle building in a valid posiiton.
     */
    @Test
    public void TestvampireCastle1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(vampireCastleCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 4);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllSpawnBuildings().size());
    }


    /**
     * Place a vampire castle building in an invalid posiiton.
     */
    @Test
    public void TestvampireCastle2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(vampireCastleCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 3);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllSpawnBuildings().size());
    }

    /**
     * Place a zombie pit building in a valid posiiton.
     */
    @Test
    public void TestZombiePit1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(zombiePitCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 4);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllSpawnBuildings().size());
    }

    /**
     * Place a zombie pit building in an invalid posiiton.
     */
    @Test
    public void TestZombiePit2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(zombiePitCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 3);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllSpawnBuildings().size());
    }

    /**
     * Place a tower building in a valid posiiton.
     */
    @Test
    public void TestTower1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card towerCard = new TowerCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(towerCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 4);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllBattleBuildings().size());
    }

    /**
     * Place a tower castle building in an invalid posiiton.
     */
    @Test
    public void TestTower2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card towerCard = new TowerCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(towerCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 3);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllBattleBuildings().size());
    }

    /**
     * Place a village building in a valid posiiton.
     */
    @Test
    public void TestVillage1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card villageCard = new VillageCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(villageCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 2);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllPathBuildings().size());
    }

    /**
     * Place a village building in an invalid posiiton.
     */
    @Test
    public void TestVillage2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card villageCard = new VillageCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(villageCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 4);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllPathBuildings().size());
    }

    /**
     * Place a barracks building in a valid posiiton.
     */
    @Test
    public void TestBarracks1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(barracksCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 2);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllPathBuildings().size());
    }

    /**
     * Place a barracks building in an invalid posiiton.
     */
    @Test
    public void TestBarracks2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(barracksCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 4);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllPathBuildings().size());
    }

    /**
     * Place a trap building in a valid posiiton.
     */
    @Test
    public void TestTrap1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card trapCard = new TrapCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(trapCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 2);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllPathBuildings().size());
    }

    /**
     * Place a trap building in an invalid posiiton.
     */
    @Test
    public void TestTrap2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card trapCard = new TrapCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(trapCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 4);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllPathBuildings().size());
    }

    /**
     * Place a campfire building in a valid posiiton.
     */
    @Test
    public void TestCampfire1(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card campfireCard = new CampfireCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(campfireCard);
        d.convertCardToBuildingByCoordinates(5, 5, 5, 5);
        assertEquals(0, d.getAllCards().size());
        assertEquals(1, d.getAllBattleBuildings().size());
    }

    /**
     * Place a campfire building in an invalid posiiton.
     */
    @Test
    public void TestCampfire2(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 2);
        Pair<Integer, Integer> path3 = new Pair<Integer,Integer>(1, 3);
        orderedPath.add(path1);
        orderedPath.add(path2);
        orderedPath.add(path3);

        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(10, 10, orderedPath);
        Character c = new Character(null);
        d.setCharacter(c);

        Card campfireCard = new CampfireCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(5));
        d.addCard(campfireCard);
        d.convertCardToBuildingByCoordinates(5, 5, 1, 2);
        assertEquals(1, d.getAllCards().size());
        assertEquals(0, d.getAllBattleBuildings().size());
    }
}
