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
import unsw.loopmania.CharacterFolder.JailState;
import unsw.loopmania.CharacterFolder.RegularState;
import unsw.loopmania.CharacterFolder.StunnedState;
import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;
public class CharacterTest {
    
    @Test
    public void testStatesAttack() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        for (int i = 0; i < 100; i++){
            orderedPath.add(new Pair<Integer,Integer>(0, i));
        }

        PathPosition p1 = new PathPosition(5, orderedPath);

        Character c = new Character(p1);

        assertTrue(c.getState() instanceof RegularState);
        Slug slug1 = new Slug(null);
        c.dealDamage(slug1, 0);

        c.changeState(new JailState(c));
        assertTrue(c.getState() instanceof JailState);
        Slug slug2 = new Slug(null);
        c.dealDamage(slug2, 0);

        c.changeState(new StunnedState(c));
        assertTrue(c.getState() instanceof StunnedState);
        Slug slug3 = new Slug(null);
        c.dealDamage(slug3, 0);

        assertTrue(slug1.getCurrentHealth() == slug2.getCurrentHealth());
        assertTrue(slug1.getCurrentHealth() != slug3.getCurrentHealth());
        assertTrue(slug3.getCurrentHealth() == 10);
    }

    /**
     * test that in a stunned state, the character will not move for 10 tick
     */
    @Test

    public void testStatesMove() {
        JFXPanel jfxPanel = new JFXPanel();
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
            
        for (int i = 0; i < 100; i++){
            orderedPath.add(new Pair<Integer,Integer>(0, i));
        }

        PathPosition p1 = new PathPosition(20, orderedPath);

        Character c = new Character(p1);

        c.changeState(new JailState(c));
        for (int i = 0; i < 10; i ++) {
            c.move();
            assertTrue(c.getX() == p1.getX().get());
            assertTrue(c.getY() == p1.getY().get());
            assertTrue(c.getState() instanceof JailState);
        }

        c.move();
        assertTrue(c.getState() instanceof RegularState);
    }

}
