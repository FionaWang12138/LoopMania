package unsw.loopmania;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import unsw.loopmania.Enemies.BasicEnemy;

public class BattleEnemyController {
    private MenuSwitcher gameSwitcher;
    private MenuSwitcher endScreenSwitcher;
    private Battle battle;
    private Timeline timeline;

    @FXML
    private Label healthEnemy;

    @FXML
    private Label healthCharacter;
    
    @FXML
    private GridPane grid;

    @FXML
    private ImageView characterView;

    @FXML
    private Label characterHealth;
    
    public BattleEnemyController() {
      
    }
        
    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    public void setEndSwitcher(MenuSwitcher endScreenSwitcher){
        this.endScreenSwitcher = endScreenSwitcher;
    }

    @FXML
    private void switchToGame() throws IOException {
        
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToEnd() throws IOException {
        
        endScreenSwitcher.switchMenu();
    }
    

    public void setBattle(Battle battle){
        this.battle = battle;
        
    }

    public void startTimer(){
        // TODO = handle more aspects of the behaviour required by the specification
        System.out.println("starting timer enemy fight");
        
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        characterView.setImage(battle.getCharacter().getImageView().getImage());
        characterHealth.textProperty().bind(battle.getCharacter().getCurrHealthProperty().asString());
        List<BasicEnemy> enemiesList = battle.getEnemiesToFight();
        int currentEnemyNum = 0;
        
        grid.getChildren().clear();
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                if (currentEnemyNum < enemiesList.size()){
                    BasicEnemy currentEnemy = enemiesList.get(currentEnemyNum);
                    ImageView enemyImage = currentEnemy.getImageView();
                    this.grid.add(enemyImage, x, y);
                    

                    Label healthNumber = new Label();
                    //healthNumber.textProperty().bind(new SimpleIntegerProperty (currentEnemy.getCurrentHealth()).asString());
                    healthNumber.textProperty().bind(currentEnemy.getCurrHealthProperty().asString());
                    this.grid.add(healthNumber, x, y);
                    GridPane.setHalignment(healthNumber, HPos.RIGHT);

                    currentEnemyNum++;
                }
                /*
                else {
                    grid.add(new ImageView(new Image((new File("src/images/blank.png")).toURI().toString()) ), x, y);
                }
                */

            }
        }
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            battle.dealDamageOnce();
        }));
    
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void pauseBattle() {
        try {
            

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            timeline.stop();
            switchToGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endGame() {
        try {
            timeline.stop();
            switchToEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GridPane getGrid () {
        return this.grid;
    }

}