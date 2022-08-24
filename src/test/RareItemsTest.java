package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.javatuples.Pair;

import unsw.loopmania.*;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.BasicItems.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.GameMode.*;
import unsw.loopmania.RareItems.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;

public class RareItemsTest {
    
    @Test
    public void TestAttackOnDoggieByAnduril() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 1);
        orderedPath.add(path1);
        orderedPath.add(path2);

        Anduril anduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Character c = new Character(new PathPosition(0, orderedPath));
        d.setCharacter(c);
        Doggie doggie = new Doggie(new PathPosition(1, orderedPath));
      
        assertEquals(100, doggie.getCurrentHealth());

        anduril.reduceDoggieHealth(doggie, c);
        assertEquals(doggie.getCurrentHealth(), 85);
        
    }

    @Test
    public void TestAttackOnElanMuskeByAnduril() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 1);
        orderedPath.add(path1);
        orderedPath.add(path2);

        Anduril anduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Character c = new Character(new PathPosition(0, orderedPath));
        d.setCharacter(c);
        ElanMuske elanMuske = new ElanMuske(new PathPosition(1, orderedPath));
      
        assertEquals(200, elanMuske.getCurrentHealth());

        anduril.reduceElanMuskeHealth(elanMuske, c);
        assertEquals(elanMuske.getCurrentHealth(), 185);
    }

    @Test
    public void TestTreeStumpDefenceAgainstDoggie() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 1);
        orderedPath.add(path1);
        orderedPath.add(path2);

        TreeStump treestump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Character c = new Character(new PathPosition(0, orderedPath));
        d.setCharacter(c);
        Doggie doggie = new Doggie(new PathPosition(1, orderedPath));
      
        assertEquals(100, c.getCurrentHealth());

        treestump.reduceDoggieDamage(c, doggie);
        int damageDone = doggie.getDamage() / 5;
        assertEquals(c.getCurrentHealth(), 100 - damageDone);
    }

    @Test
    public void TestTreeStumpDefenceAgainstElanMuske() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 1);
        orderedPath.add(path1);
        orderedPath.add(path2);

        TreeStump treestump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Character c = new Character(new PathPosition(0, orderedPath));
        d.setCharacter(c);
        ElanMuske elanMuske = new ElanMuske(new PathPosition(1, orderedPath));
      
        assertEquals(100, c.getCurrentHealth());

        treestump.reduceElanMuskeDamage(c, elanMuske);
        int damageDone = elanMuske.getDamage() / 5;
        assertEquals(c.getCurrentHealth(), 100 - damageDone);
    }

    @Test
    public void TestTheOneRingRespawnCharacter() {
        JFXPanel jfxPanel = new JFXPanel();
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        
        Pair<Integer, Integer> path1 = new Pair<Integer,Integer>(1, 0);
        Pair<Integer, Integer> path2 = new Pair<Integer,Integer>(1, 1);
        orderedPath.add(path1);
        orderedPath.add(path2);

        TheOneRing oneRing = new TheOneRing(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Character c = new Character(new PathPosition(0, orderedPath));
        d.setCharacter(c);
        ElanMuske elanMuske = new ElanMuske(new PathPosition(1, orderedPath));
      
        assertEquals(100, c.getCurrentHealth());
        int damageDone = elanMuske.getDamage() / 5;
        assertEquals(c.getCurrentHealth(), 100 - damageDone);

        oneRing.reviveCharacter(c);
        assertEquals(100, c.getCurrentHealth());
    }
}