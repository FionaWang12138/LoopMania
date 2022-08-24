package unsw.loopmania;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import unsw.loopmania.BasicItems.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ShopBuyController {

    @FXML // fx:id="listItems"
    private GridPane listItems; // Value injected by FXMLLoader

    @FXML // fx:id="sellItem"
    private Button sellItem; // Value injected by FXMLLoader

    private MenuSwitcher gameSwitcher;

    private MenuSwitcher shopSellSwitcher;

    private Timeline timeline;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        for (int x = 0; x < 3; x++) {
            ImageView inventorySlotImage = new ImageView((new File("src/images/empty_slot.png")).toURI().toString());
            listItems.add(inventorySlotImage, 0, x);
        }
        assert listItems != null : "fx:id=\"listItems\" was not injected: check your FXML file 'ShopBuyView.fxml'.";
        assert sellItem != null : "fx:id=\"sellItem\" was not injected: check your FXML file 'ShopBuyView.fxml'.";
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    public void setShopSellSwitcher (MenuSwitcher shopSellSwitcher) {
        this.shopSellSwitcher = shopSellSwitcher;
    }

    public void startTimer(LoopManiaWorldController loopManiaWorldController){

        List <BasicItem> itemsFromShop = loopManiaWorldController.getWorld().getAllItemsFromShop();
        listItems.getChildren().clear();

        for (int x = 0; x < 3; x++) {
            BasicItem currentItem = itemsFromShop.get(x);
            ImageView inventorySlotImage = currentItem.getImageView();

            Integer cost = currentItem.getValue();

            Button button = new Button(cost.toString());
            listItems.add (button, 0, x);
            GridPane.setHalignment(button, HPos.RIGHT);
            listItems.add(inventorySlotImage, 0, x);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    int x = GridPane.getRowIndex(button);
                    loopManiaWorldController.getWorld().buyItem(currentItem);
                    loopManiaWorldController.onLoad(currentItem);
                    //inventory.getChildren().remove(itemToSell);
                    listItems.add(new ImageView(new Image((new File("src/images/empty_slot.png")).toURI().toString())), 0, x);
                }
            });
        }

        EventHandler<ActionEvent> switchToGame = new EventHandler<ActionEvent>(){
            public void handle (ActionEvent t) {
                timeline.stop();
                gameSwitcher.switchMenu();
            }
        };
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.getKeyFrames().add(new KeyFrame(Duration.millis(8000), switchToGame));
        this.timeline.play();
    }

    @FXML
    private void switchToGame() throws IOException {
        timeline.stop();
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToShopSell() throws IOException {
        timeline.stop();
        shopSellSwitcher.switchMenu();
    }
}
