package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import unsw.loopmania.*;

import unsw.loopmania.BasicItems.*;

import unsw.loopmania.Buildings.*;

import unsw.loopmania.Cards.*;

import unsw.loopmania.Enemies.*;

import unsw.loopmania.GameMode.*;

public class WorldMapTest {
    @Test
    public void Test1(){
        JFXPanel jfxPanel = new JFXPanel(); 
        LoopManiaWorld d = new LoopManiaWorld(50, 30, new ArrayList<>());
        assertEquals(d.getWidth(), 50);
        assertEquals(d.getHeight(), 30);
    }
}
