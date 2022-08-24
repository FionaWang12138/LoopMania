package unsw.loopmania;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.javatuples.Pair;
import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.BasicItems.DefendingStrategy;
import unsw.loopmania.Buildings.BattleBuildings.BattleBuilding;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.Ghost;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Enemies.Vampire;

public class Battle {
    LoopManiaWorld loopManiaWorld;
    private List<BasicEnemy> enemiesToFight;
    private Character c;
    private BattleEnemyController controller;
    private List<BasicEnemy> enemies;
    private BasicEnemy mainEnemy;
    private List<BattleBuilding> battleBuildings;
    private int loopCount;
    private List<Ally> allies;

    public Battle(LoopManiaWorld loopManiaWorld, Character c, BattleEnemyController controller, List<BasicEnemy> enemies, BasicEnemy mainEnemy,  List<BattleBuilding> battleBuildings, int loopCount) {
        this.loopManiaWorld = loopManiaWorld;
        this.c = c;
        this.controller = controller;
        this.enemies = enemies;
        enemiesToFight = new ArrayList<BasicEnemy>();;
        this.mainEnemy = mainEnemy;
        this.battleBuildings = battleBuildings;
        this.loopCount = loopCount;
        this.allies = c.getAllies();

        //checks that the main enemy passed in has character in battle radius
        if (Math.pow((c.getX()-mainEnemy.getX()), 2) +  Math.pow((c.getY()-mainEnemy.getY()), 2) <= Math.pow(mainEnemy.getAttackRadius(),2)) {
            enemiesToFight.add(mainEnemy);
            //search through all the enemies, and find the ones that have character within the support radius
            for(BasicEnemy e1: enemies){
                if (Math.pow((c.getX()-e1.getX()), 2) +  Math.pow((c.getY()-e1.getY()), 2) <= Math.pow(e1.getSupportRadius(),2)
                && !enemiesToFight.contains(e1)) {
                    enemiesToFight.add(e1);
                }
            }
        }
        
    }
    
    /**
     * when the battle starts, we loop through the list of enemies
     * each enemy will deal damage to and recieve damate from an allied soldier and character
     */
    public void dealDamageOnce(){

        boolean allEnemiesDead = true;
        AttackingStrategy weapon = c.getEquippedWeapon ();
        DefendingStrategy defence = c.getEquippedDefence();
        
        for (int i = 0; i < enemiesToFight.size(); i ++) {
            BasicEnemy e = enemiesToFight.get(i);
            if(e.getCurrentHealth() <= 0){
                continue;
            }
            for (BattleBuilding b : battleBuildings) {
                b.buildingAction(c, e);
            }
            
            if (allies.size() > 0){
                alliesAction(e);
            }

            c.dealDamage(e, loopCount);
            if (e.getCurrentHealth() > 0) {
                allEnemiesDead = false;
            }

            e.dealDamage(defence, c, enemies);
            if (c.getCurrentHealth() <= 0) {
                c.destroy();
                controller.endGame();
            }          
        }
        //once all enemies are dead, we can return to main screen
        if (allEnemiesDead){
            handleAllEnemyDeath();
        }

    }

    public Character getCharacter(){
        return c;
    }

    public List<BasicEnemy> getEnemiesToFight() {
        //System.out.println(defeatedEnemies.size());
        return enemiesToFight;
    }

    /**
     * at the event that all enemies die, we kill the enemy, increase character gold, pauses the battle and go back to the main game
     */
    public void handleAllEnemyDeath () {
        for (BasicEnemy enemy: enemiesToFight){
            enemy.destroy();
            enemies.remove(enemy);
            c.increaseGold(enemy.getGold());
            c.addExperience(enemy.getExperience());
            if (enemy instanceof Ghost) {
                this.loopManiaWorld.setIsThereGhost();
            }
        }
        if (controller != null) {
            controller.pauseBattle();
        }
        return;
    }

    /**
     * ally will deal damage to the enemy, and recieve damage from the enemy
     * There is a 10% chance of a critical bite white turns ally into zombie
     * @param e
     */
    public void alliesAction(BasicEnemy e) {
        
        Ally lastAlly = allies.get(allies.size() - 1);
        e.decreaseHealth(5);
        lastAlly.decreaseHealth(e.getDamage());

        if (e instanceof Zombie) {
            Random random = new Random();
            double chance = random.nextDouble();
            if (chance < 0.1){
                //CRITICAL ZOMBIE BITE
                lastAlly.destroy();
                allies.remove(lastAlly);  
                enemiesToFight.add(new Zombie(e.getPathPosition()));
            }
        } 

        else if (lastAlly.getCurrentHealth() <= 0){
            lastAlly.destroy();
            allies.remove(lastAlly);  
        }
    }

}
