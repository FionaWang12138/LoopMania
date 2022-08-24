package unsw.loopmania;

import unsw.loopmania.BasicItems.*;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Buildings.BattleBuildings.BattleBuilding;
import unsw.loopmania.Buildings.PathBuildings.JailBuilding;
import unsw.loopmania.Buildings.PathBuildings.PathBuilding;
import unsw.loopmania.Buildings.SpawnBuildings.SpawnBuilding;
import unsw.loopmania.Cards.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.GameMode.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.BasicItems.Sword;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.VampireCastleCard;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.LoopManiaApplication;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */

 public class LoopManiaWorld {
    // TODO = add additional backend functionality

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;
    public static final int rareInventoryWidth = 4;
    public static final int rareInventoryHeight = 2;

    public static final int START_HEALTH = 100;
    public static final int START_EXP = 0;
    public static final int START_GOLD = 0;
    public static final int BASE_DAMAGE = 5;


    public static final int LOW_HEALTH = 10;
    public static final int MED_HEALTH = 30;
    public static final int HIGH_HEALTH = 100;

    public static final int LOW_DAMAGE = 2;
    public static final int MED_DAMAGE = 5;
    public static final int HIGH_DAMAGE = 10;

    public static final int LOW_EXP = 1;
    public static final int MED_EXP = 2;
    public static final int HIGH_EXP = 5;

    public static final int LOW_GOLD = 1;
    public static final int MED_GOLD = 2;
    public static final int HIGH_GOLD = 3;

    public static final int SHORT_RADIUS = 1;
    public static final int MED_RADIUS = 3;
    public static final int LONG_RADIUS = 5;

    public static final int GOAL_WIN_EXP = 500;
    public static final int GOAL_WIN_GOLD = 250;

    /**
     * width and height of the world in GridPane cells
     */
    private int width;
    private int height;
    private int loopCount;

    private List<Entity> nonSpecifiedEntities;
    private List<Gold> goldInTheWorld;
    private HealthPotion thePotion;

    private Character character;
    private List<BasicEnemy> enemies;

    //private List<Building> buildingEntities;
    private HerosCastle heroCastle;
    
    private List<BattleBuilding> battleBuildings;
    private List<PathBuilding>   pathBuildings;
    private List<SpawnBuilding>  spawnBuildings;

    private List<Ally> allies;
    private boolean elanHere;
    //private boolean isDoggieDefeated;
    //private boolean isElanDefeated;
    private boolean expGoalMet;
    //private boolean goldGoaMet;

    private boolean isThereGhost;

    private int gameMode;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.isThereGhost = false;
        this.gameMode = 0;
        this.width = width;
        this.height = height;
        this.nonSpecifiedEntities = new ArrayList<>();
        this.character = null;
        this.enemies = new ArrayList<>();
        this.orderedPath = orderedPath;
        this.heroCastle = new HerosCastle(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        this.battleBuildings = new ArrayList<>();
        this.pathBuildings = new ArrayList<>();
        this.spawnBuildings = new ArrayList<>();
        this.allies = new ArrayList<>();
        this.goldInTheWorld = new ArrayList<>();
        thePotion = null;
        loopCount = 0;
        elanHere = false;
        expGoalMet = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Entity> getNonSpecifiedEntities() {
        return nonSpecifiedEntities;
    }

    ////////// LOOP-RELATED METHODS ///////////////
    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves() {

        character.move();
        
        for (Ally ally : allies) {
            ally.moveDownPath();
        }
        moveBasicEnemies();
        possiblyCollectGold();
        possiblyCollectPotion();
        fluctuateDoggieCoinValue();
        updateLoopCount(character);
        
        
    }

    // Created for testing purposes
    public void runTickMovesCharacter() {
        
        character.moveDownPath();
        
        for (PathBuilding p : pathBuildings) {
            p.pathAction(character, enemies);
        }
    }
    
    // Created for testing purposes
    public void runTickMovesEnemies() {
        
        moveBasicEnemies();
        for (PathBuilding p : pathBuildings) {
            p.pathAction(character, enemies);
        }
    }

    /**
     * @return the amount of loops the character has completed
     */
    public int getLoopCount() {
        return this.loopCount;
    }


    ////////// CASTLE METHODS ///////////////
    /**
     * @param character
     * @return wheather or not the character is on the castle (which is always the first tile on the path)
     */
    public boolean checkCharacterOnCastle () {
        int x = this.heroCastle.getX();
        int y = this.heroCastle.getY();
        
        if (this.character.getX() == x && this.character.getY() == y) {
            return true;
        } else {
            return false;
        }
            
    }
    
    /**
     * increases loopcount whenever character passes the first tile on the path
     * @param character
     */
    public void updateLoopCount (Character character) {
        if (checkCharacterOnCastle()) {
            this.loopCount = this.loopCount + 1;
        }
    }

    public void setCastle (HerosCastle castle) {
        this.heroCastle = castle;
    }
    ////////// MOVING ENTITIES //////////////
    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
        this.allies = character.getAllies();
    }

    public Character getCharacter () {
        return this.character;
    }

    /**
     *  add an enemy to the list of enemies
     *  @param  basicEnemy
     */
    public void addBasicEnemy (BasicEnemy basicEnemy) {
        this.enemies.add(basicEnemy);
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        nonSpecifiedEntities.add(entity);
    }

     /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (BasicEnemy e: enemies){
            e.move();
        }
    }

    public List<BasicEnemy> getAllBasicEnemies () {
        return this.enemies;
    }

     /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    public Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition () {
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2);
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 5)){
            Pair<Integer, Integer> spawnPosition = getRandomPosition();
            return spawnPosition;
        }
        return null;
    }
    
    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies(){

        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            //Slug is randomly spawned
            if (this.isThereGhost == false) {
                Ghost enemy = new Ghost(new PathPosition(indexInPath, orderedPath));
                enemies.add(enemy);
                spawningEnemies.add(enemy);
                this.isThereGhost = true;
            } else {
                Random random = new Random();
                double rate = random.nextDouble();
                if (rate < 0.3) {
                    Slug enemy = new Slug(new PathPosition(indexInPath, orderedPath));
                    enemies.add(enemy);
                    spawningEnemies.add(enemy);
                }
            }
        }

        for (SpawnBuilding b : spawnBuildings) {
            BasicEnemy enemy = b.spawnAction(loopCount, checkCharacterOnCastle(), b.findPathToSpawn(orderedPath), orderedPath);
            
            if (enemy != null) {
                enemies.add(enemy);
                spawningEnemies.add(enemy);
            }
        }

        if (checkCharacterOnCastle()){
            if (loopCount % 20 == 0) {
                Doggie doggie = new Doggie(new PathPosition(5, orderedPath));
                enemies.add(doggie);
                spawningEnemies.add(doggie);
            }

            if(loopCount % 40 == 0 && character.getExperience() >= 10000){
                ElanMuske elan = new ElanMuske(new PathPosition(6, orderedPath));
                enemies.add(elan);
                spawningEnemies.add(elan);
                elanHere = true;
            }
        }

        
        return spawningEnemies;
    }


    ////////// CARD ENTITIES ///////////////
    /**
     * add a card to the collection of cards
     * @param card
     */
    public void addCard (Card card) {
        this.character.addCard(card);
    }

    public Card getCardByIndex (int index) {
        return this.character.getCardByIndex(index);
    }

    public List<Card> getAllCards () {
        return this.character.getAllCards();
    }

     /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: this.character.getAllCards()){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

      /**
     * Given an enemy (defeat), possibly spawn a card given by that defeated enemy
     * @param enemy defeated enemy
     * @return Card
     */
    public Card loadCard(Card card){
        if (this.character.getAllCards().size() >= getWidth()){
            removeCard(0);
        }

        card.setCoordinate(new SimpleIntegerProperty(this.character.getAllCards().size()), new SimpleIntegerProperty(0));
        System.out.println(character.getAllInventoryItems().size());
        if (card != null){
            this.character.getAllCards().add(card);
        }
        return card;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    public void removeCard(int index){
        Card c = this.character.getAllCards().get(index);
        int x = c.getX();
        c.destroy();
        this.character.getAllCards().remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    ////////// Shop-Related method ///////////////
    public List<BasicItem> getAllItemsFromShop() {
        return this.heroCastle.getAllItems();
    }


    /**
     * handles the purchasing of an item from the hero castle, 
     * add the item to invetory if purchase is successful, and deduct coins
     * @param index
     * @return
     */
    public BasicItem buyItem (BasicItem item) {
        if (item.getValue() <= this.getGoldCount()) {
            this.heroCastle.buyItem(item);
            this.addUnequippedItem(item);
            this.decreaseGold(item.getValue());
            return item;
        } else {
            return null;
        }
    }

    public BasicItem sellItemByIndex(int index) {
        return this.character.sellItemByIndex(index);
    }

    ////////// BUILDING ENTITIES ///////////////


    public void addBattleBuilding (BattleBuilding building) {
        this.battleBuildings.add(building);
    }

    public List<BattleBuilding> getAllBattleBuildings () {
        return this.battleBuildings;
    }

    public void addPathBuilding (PathBuilding building) {
        this.pathBuildings.add(building);
    }

    public List<PathBuilding> getAllPathBuildings () {
        return this.pathBuildings;
    }

    public void addSpawnBuilding (SpawnBuilding building) {
        this.spawnBuildings.add(building);
    }

    public List<SpawnBuilding> getAllSpawnBuildings () {
        return this.spawnBuildings;
    }

    public List<Pair<Integer, Integer>> getOrderedPath () {
        return this.orderedPath;
    }
    
    public boolean isAdjacent (int x, int y) {
        if (x == y + 1 || x == y - 1 || x == y) return true;
        return false;
    }

    /**
     * loop through all the the path buildings (barrack, trap, or villiage), possibly spawning a new ally
     * @return Ally
     */
    public Ally pathBuildingAction() {
        Ally ally = null;
        Iterator<PathBuilding> iter = pathBuildings.iterator();
        PathBuilding toRemove = null;
        while (iter.hasNext()) {
            PathBuilding p = iter.next();
            Ally possiblyAlly = p.pathAction(character, enemies);
            if (possiblyAlly != null){
                ally = possiblyAlly;
            }
            else if (p instanceof JailBuilding && p.checkOnPath(character) && allies.size() > 0) {
                Ally allyToRemove = allies.get(allies.size() - 1 );
                allies.remove(allyToRemove);
                allyToRemove.destroy();
                toRemove = p;
            }
        }

        if (toRemove != null) {
            pathBuildings.remove(toRemove);
            toRemove.destroy();
        }   
        return ally;
    }

    /**
     * Given a cell, test if it is adjecent to the path of the game
     * @param cell
     * @return boolean
     */
    public boolean isAdjacentToPath (Pair<Integer, Integer> cell) {
        int value0 = cell.getValue0();
        int value1 = cell.getValue1();
        boolean result = false;
        for (Pair<Integer, Integer> adj : this.orderedPath) {
            if (adj.getValue0() == value0 && adj.getValue1() == value1) return false;
            if (isAdjacent(adj.getValue0(), value0) && isAdjacent(adj.getValue1(), value1)) result = true;
        }
        return result;
    }

    /**
     * Given a cell, test if it is on the path of the game
     * @param cell
     * @return boolean
     */
    public boolean isOnPath (Pair<Integer, Integer> cell) {
        int value0 = cell.getValue0();
        int value1 = cell.getValue1();
        for (Pair<Integer, Integer> adj : this.orderedPath) {
            if (adj.getValue0() == value0 && adj.getValue1() == value1) return true;
        }
        return false;
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        
        Card card = null;
        for (Card c: this.character.getAllCards()){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        Building newBuilding = null;
        // now spawn building
        if (card.isPlaceable(buildingNodeX, buildingNodeY, this.orderedPath)) {
            newBuilding = card.generateEntity(new SimpleIntegerProperty(buildingNodeX),
             new SimpleIntegerProperty(buildingNodeY));
            if (newBuilding instanceof SpawnBuilding) {
                spawnBuildings.add((SpawnBuilding) newBuilding);
            } else if (newBuilding instanceof BattleBuilding) {
                battleBuildings.add((BattleBuilding) newBuilding);
            } else if (newBuilding instanceof PathBuilding) {
                pathBuildings.add((PathBuilding)newBuilding);
            }
            card.destroy();
            this.character.getAllCards().remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
        }
 
        return newBuilding;
    }

    /**
     * each tick there is a 1% chance of spawning a jail building
     * @return JailBuilding
     */
    public JailBuilding possiblySpawnJailBuilding() {
        Random random = new Random();
        double r = random.nextDouble();
        if (r < 0.01) {
            int num_jail = 0;
            for (PathBuilding pathBuilding : getAllPathBuildings()) {
                if (pathBuilding instanceof JailBuilding) num_jail++;
            }
            Pair<Integer, Integer> pos = getRandomPosition();
            if ((pos.getValue0() != 0 || pos.getValue1() != 0) && num_jail < 2) {
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition position = new PathPosition(indexInPath, orderedPath);
                JailBuilding jail = new JailBuilding(position.getX(), position.getY());
                addPathBuilding(jail);
                return jail;
            }
        }
        return null;
        
    }


    ////////// GOLD-RELATED METHODS ///////////////
    /**
     * for every tick, there's a 5% chance that a gold is spawned on a random tile
     * @return Gold
     */
    public Gold possiblySpawnGold() {
        Random random = new Random();
        double r = random.nextDouble();
        if (r < 0.05) {
            Pair<Integer, Integer> pos = getRandomPosition();
            int indexInPath = orderedPath.indexOf(pos);
            PathPosition position = new PathPosition(indexInPath, orderedPath);
            Gold gold = new Gold(position.getX(), position.getY());
            gold.increaseGold(1);
            goldInTheWorld.add(gold);
            return gold;
        }
        return null;
    }

    /**
     * for every tick, check if the character is on top of a gold, if so, pick it up
     */
    public void possiblyCollectGold() {
        for (int i = 0; i < goldInTheWorld.size(); i++){
            Gold g = goldInTheWorld.get(i);
            if (g.getX() == character.getX() && g.getY() == character.getY()){
                character.increaseGold(g);
                goldInTheWorld.remove(g);
                g.destroy();
                goldInTheWorld.remove(g);
                break;  
            }
        }
    }

    /**
     * check how much gold the character has
     * @return int
     */
    public int getGoldCount() {
        return this.character.getGoldCount();
    }

    public int increaseGold (int gold) {
        return this.character.increaseGold(gold);
    }

    public int decreaseGold (int gold) {
        return this.character.decreaseGold(gold);
    }
    
    ////////// HEALTH-RELATED METHODS ///////////////
    /**
     * for each tick, if there isn't already a health potion on the path, possibly spawn one
     * @return HealthPotion
     */
    public HealthPotion possiblySpawnHealthPotion() {
        Random random = new Random();
        double r = random.nextDouble();

        if (r < 0.50 && thePotion == null) {
            Pair<Integer, Integer> pos = getRandomPosition();
            int indexInPath = orderedPath.indexOf(pos);
            PathPosition position = new PathPosition(indexInPath, orderedPath);
            HealthPotion potion = new HealthPotion(position.getX(), position.getY());
            this.thePotion = potion;
            return potion;
        }
        return null;
    }

    /**
     * if the character is on top of the health potion, drink it and destroy the potion
     */
    public void possiblyCollectPotion() {
        if(thePotion != null){
            if (thePotion.getX() == character.getX() && thePotion.getY() == character.getY()){
                character.increaseHealth(100);
                thePotion.destroy();
                thePotion = null;
            }
        }
    }
    

    ////////// INVENTORY METHODS ///////////////

    /**
     * spawn a weapon depending on the enemy looted
     * @param enemy defeated enemy
     * @return possibly a weapon to be spawned in the controller as a JavaFX node
     */
    public BasicItem addUnequippedItem(BasicItem item){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
            //for each weapon lost, 5 gold is gained
            character.increaseGold(5);
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        item.setCoordinate(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));

        System.out.println(character.getAllInventoryItems().size());
        if (item != null){
            this.character.getAllInventoryItems().add(item);
        }
        
        System.out.println(character.getAllInventoryItems().size());
        return item;
    }

    public void addInventoryItem (BasicItem item) {
        this.character.addInventoryItem(item);
    }

    public List<BasicItem> getAllInventoryItems () {
        return this.character.getAllInventoryItems();
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        this.character.getAllInventoryItems().remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e:  this.character.getAllInventoryItems()){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the rare inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item =  this.character.getAllInventoryItems().get(index);
        item.destroy();
        this.character.getAllInventoryItems().remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

        ////////// RARE ITEMS INVENTORY METHODS ///////////////

    /**
     * spawn a rare item depending on the enemy looted
     * @param enemy defeated enemy
     * @return possibly a rare item to be spawned in the controller as a JavaFX node
     */
    public StaticEntity addRareItemToInventory(StaticEntity item){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForRareItem();
        if (firstAvailableSlot == null){
            // eject the oldest rare item and replace it... oldest item is that at beginning of items
            removeItemByPositionInRareInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForRareItem();
            //for each rare item lost, 2 gold is gained
            character.increaseGold(2);
        }
        
        // now we insert the new item, as we know we have at least made a slot available...
        item.setCoordinate(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));

        System.out.print(item);
        System.out.println(character.getRareInventoryItems().size());
        if (item != null){
            this.character.getRareInventoryItems().add(item);
        }
        
        System.out.println(character.getRareInventoryItems().size());
        return item;
    }

    public void addRareItem (StaticEntity item) {
        this.character.addRareItem(item);
    }

    public List<StaticEntity> getAllRareItems () {
        return this.character.getRareInventoryItems();
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeRareInventoryItemByCoordinates(int x, int y){
        Entity item = getRareInventoryItemEntityByCoordinates(x, y);
        removeRareInventoryItem(item);
    }

    /**
     * remove an item from the rare items inventory
     * @param item item to be removed
     */
    public void removeRareInventoryItem(Entity item){
        item.destroy();
        this.character.getRareInventoryItems().remove(item);
    }

    /**
     * return an rare items inventory item by x and y coordinates
     * assumes that no 2 rare items inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return rare items inventory item at the input position
     */
    public Entity getRareInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e:  this.character.getRareInventoryItems()){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the rare items inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInRareInventoryItems(int index){
        Entity item =  this.character.getRareInventoryItems().get(index);
        item.destroy();
        this.character.getRareInventoryItems().remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the rare iteminventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForRareItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<rareInventoryHeight; y++){
            for (int x=0; x<rareInventoryWidth; x++){
                if (getRareInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    ////////// OTHER METHODS ///////////////
    
    /**
     * Generates a random spawn position on the map
     * @return spawnPositino
     */
    private Pair<Integer, Integer> getRandomPosition() {
        Random rand = new Random();
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
        int endNotAllowed = (indexPosition + 3)%orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // choose random choice
        Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

        return spawnPosition;
    }

    /**
     * if the character is close to an enemy, open the battle controller and corresponding screen
     * @return list of enemies which have been killed
     * @throws IOException
     */
    //public List<BasicEnemy> runBattles(LoopManiaWorldController world) {
        public List<BasicEnemy> runBattles(LoopManiaWorldController controller) throws IOException {

            List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
            
            for (BasicEnemy e: enemies){
                
                // Pythagoras: a^2+b^2 < radius^2 to see if within radius
                if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) <= Math.pow(e.getAttackRadius(),2)){
                    BattleEnemyController battleEnemyController = controller.getBattleController();
    
                    Battle battle = new Battle(this, character, battleEnemyController, enemies, e, battleBuildings, loopCount);
                    battleEnemyController.setBattle(battle);
                    try {
                        controller.switchToBattle();
                        //return newBattle.getDefeatedEnemies();
                        return battle.getEnemiesToFight();                        
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
           
            return defeatedEnemies;
        }

    public List<Ally> getAllAllies() {
        return character.getAllies();
    }

    
    public void fluctuateDoggieCoinValue() {
        for (BasicItem item : character.getAllInventoryItems()) {
            if (item instanceof DoggieCoin) {
                DoggieCoin doggieCoin = (DoggieCoin) item;
                doggieCoin.setCoinPrice(elanHere);
            }
        }
    }
    
    public boolean getIsThereGhost () {
        return this.isThereGhost;
    }

    public void setIsThereGhost () {
        this.isThereGhost = !this.isThereGhost;
    }

    public int getExperience() {
        return character.getExperience();
    }

    public int getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
