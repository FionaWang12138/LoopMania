package unsw.loopmania;

import unsw.loopmania.BasicItems.*;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Cards.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.GameMode.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;    
    
    private MediaPlayer backGroundMusic;
    private MediaPlayer other;

    
    //private BattleEnemyController battleEnemyController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Music gameMusic = new Music(backGroundMusic);
        Music miscMusic = new Music(other);
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        //load battle screen
        BattleEnemyController battleEnemyController = new BattleEnemyController();
        FXMLLoader battleLoader = new FXMLLoader(getClass().getResource("Battle.fxml"));
        battleLoader.setController(battleEnemyController);
        Parent battleRoot = battleLoader.load();

        //shop sell screen
        ShopSellController shopSellController = new ShopSellController();
        FXMLLoader shopSellLoader = new FXMLLoader(getClass().getResource("ShopSellView.fxml"));
        shopSellLoader.setController(shopSellController);
        Parent shopSellRoot = shopSellLoader.load();

        ShopBuyController shopBuyController = new ShopBuyController();
        FXMLLoader shopBuyLoader = new FXMLLoader(getClass().getResource("ShopBuyView.fxml"));
        shopBuyLoader.setController(shopBuyController);
        Parent shopBuyRoot = shopBuyLoader.load();
        
        Scene scene = new Scene(mainMenuRoot);
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json", battleEnemyController, null);
    
        //LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("basic_world_with_player.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        //endScreen
        EndScreenController endScreenController = new EndScreenController();
        FXMLLoader endScreenLoader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
        endScreenLoader.setController(endScreenController);
        Parent endScreenRoot = endScreenLoader.load();
        

        mainController.setGameRoot(gameRoot);

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setMainMenuSwitcher(() -> {
            mainController.pause();
            switchToRoot(scene, mainMenuRoot, primaryStage);
            gameMusic.stopMusic();      
        });
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
            gameMusic.playMusic("music/halotheme.wav");
        });
        mainController.setBattleSwitcher(() -> {  
            switchToRoot(scene, battleRoot, primaryStage);
            mainController.pause();
            battleEnemyController.startTimer();
            miscMusic.playMusic("music/attack.wav");

        });
        mainController.setShopSellSwitcher(() -> {  
            switchToRoot(scene, shopSellRoot, primaryStage);
            mainController.pause();
            shopSellController.startTimer(mainController);
            miscMusic.playMusic("music/money.wav");
        });
        mainController.setShopBuySwitcher(() -> {  
            switchToRoot(scene, shopBuyRoot, primaryStage);
            mainController.pause();
            shopBuyController.startTimer(mainController);
        });
       
        battleEnemyController.setGameSwitcher(() -> {  
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        battleEnemyController.setEndSwitcher(() -> {  
            switchToRoot(scene, endScreenRoot, primaryStage);
            gameMusic.stopMusic();
            miscMusic.playMusic("music/died.wav");
        });

        shopSellController.setGameSwitcher(() -> {  
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        shopSellController.setShopBuySwitcher(() -> {  
            switchToRoot(scene, shopBuyRoot, primaryStage);
            mainController.pause();
            shopBuyController.startTimer(mainController);
        });

        shopBuyController.setGameSwitcher(() -> {  
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        shopBuyController.setShopSellSwitcher(() -> {  
            switchToRoot(scene, shopBuyRoot, primaryStage);
            mainController.pause();
            shopBuyController.startTimer(mainController);
        });
        
        // deploy the main onto the stage
        gameRoot.requestFocus();
        //battleRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }
    
    @Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    public static void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}