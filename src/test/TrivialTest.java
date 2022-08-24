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


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class TrivialTest {
    @Test
    public void blahTest(){
        assertEquals("a", "a");
    }
    
    @Test
    public void blahTest2(){
        JFXPanel jfxPanel = new JFXPanel(); 
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertEquals(d.getWidth(), 1);
    }
}
