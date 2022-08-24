package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import unsw.loopmania.*;
import unsw.loopmania.BasicItems.*;

import unsw.loopmania.Buildings.*;
import unsw.loopmania.Buildings.BattleBuildings.BattleBuilding;
import unsw.loopmania.Buildings.PathBuildings.BarracksBuilding;
import unsw.loopmania.Cards.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;

public class AllyTest {

    /**
     * test that when a character goes past a barrack, an ally is spawned
     */
    @Test
    public void spawnAllyWhenCharacterPastBarrack(){
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(0, 2);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);


        LoopManiaWorld d = new LoopManiaWorld(50, 30, orderedPath);
        Character c = new Character(p1);
        d.setCharacter(c);
        d.addPathBuilding(new BarracksBuilding(p2.getX(), p2.getY()));


        //test that ally will not spaw if character not on barrack
        Ally ally1 = d.pathBuildingAction();
        assertTrue(ally1 == null);

        assertTrue(c.getX() == p1.getX().getValue() || c.getX() == p1.getY().getValue());
        d.runTickMoves();

        //after runTickMove character should have mooved
        assertTrue(c.getX() != p1.getX().getValue() || c.getX() != p1.getY().getValue());
        
        //once character is on barrack, ally should spawn
        Ally ally2 = d.pathBuildingAction();
        assertTrue(ally2 != null);
        
        assertTrue(c.getAllies().size() != 0);


        //now test allie in fight
        
    }
    
    /**
     * test that when an allys is in a battle, character health decreasses less
     */
    @Test
    public void testBattleWithAlly() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(0, 1);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(0, 2);
        
        orderedPath.add(path1);
        orderedPath.add(path2);

        PathPosition p1 = new PathPosition(0, orderedPath);
        PathPosition p2 = new PathPosition(1, orderedPath);

        int withAllyHigherHealth = 0;
        int withoutAllyHigherHealth = 0;
        //loop to account for random chance of critical bite
        for (int i = 0; i < 200; i ++){

            Character c = new Character(p1);
            //test fight withough allies
            List<BasicEnemy> enemyList = new ArrayList();
            Zombie z1 = new Zombie(p1);
            enemyList.add(z1);
            Battle battle = new Battle(null, c, null, enemyList, z1, new ArrayList<BattleBuilding>(), 0);
            while (z1.getCurrentHealth() > 0) {
                battle.dealDamageOnce();
            }
            int healthOfCharacterWithoutAlly = c.getCurrentHealth();
            
            //now battle with ally
            Character c2 = new Character(p1);
            c2.addAlly(new Ally(p1, 100));
            List<BasicEnemy> enemyList2 = new ArrayList();
            Zombie z2 = new Zombie(p1);
            enemyList2.add(z2);
            Battle battle2 = new Battle(null, c2, null, enemyList2, z2, new ArrayList<BattleBuilding>(), 0);
            while (z2.getCurrentHealth() > 0) {
                battle2.dealDamageOnce();
            }
            int healthOfCharacterWithAlly = c2.getCurrentHealth();

            if (healthOfCharacterWithAlly > healthOfCharacterWithoutAlly ) {
                withAllyHigherHealth++;
            }
            else {
                withoutAllyHigherHealth++;
            }
        }

        assertTrue(withoutAllyHigherHealth < withAllyHigherHealth);
    }


}