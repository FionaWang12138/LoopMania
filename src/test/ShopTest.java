package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.discovery.predicates.IsTestMethod;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import unsw.loopmania.*;
import unsw.loopmania.BasicItems.*;

import unsw.loopmania.Buildings.*;
import unsw.loopmania.Buildings.BattleBuildings.BattleBuilding;
import unsw.loopmania.Buildings.BattleBuildings.CampfireBuilding;
import unsw.loopmania.Buildings.BattleBuildings.TowerBuilding;
import unsw.loopmania.Buildings.PathBuildings.BarracksBuilding;
import unsw.loopmania.Buildings.PathBuildings.PathBuilding;
import unsw.loopmania.Buildings.PathBuildings.TrapBuilding;
import unsw.loopmania.Buildings.PathBuildings.VillageBuilding;
import unsw.loopmania.Buildings.SpawnBuildings.SpawnBuilding;
import unsw.loopmania.Buildings.SpawnBuildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.SpawnBuildings.ZombiePitBuilding;
import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;

public class ShopTest {

    @Test
    public void Test1() {
        JFXPanel jfxPanel = new JFXPanel(); 
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());

        HerosCastle herosCastle = new HerosCastle(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        d.setCastle(herosCastle);

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(0, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(0,5);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        
        Character c = new Character(p1);
        c.increaseGold(1000);
        d.setCharacter(c);
        List<BasicItem> itemList = d.getAllItemsFromShop();
        BasicItem item = d.buyItem(itemList.get(0));
        assertEquals(item, d.getAllInventoryItems().get(0));
    }

    @Test
    public void Test2() {
        JFXPanel jfxPanel = new JFXPanel(); 
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());

        HerosCastle herosCastle = new HerosCastle(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        d.setCastle(herosCastle);

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(0, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(0,5);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        
        Character c = new Character(p1);
        c.increaseGold(1);
        d.setCharacter(c);

        d.setCharacter(c);
        List<BasicItem> itemList = d.getAllItemsFromShop();
        BasicItem item = d.buyItem(itemList.get(0));
        assertEquals(0, d.getAllInventoryItems().size());
    }
    
}
