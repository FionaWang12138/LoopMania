package unsw.loopmania;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import unsw.loopmania.BasicItems.BasicItem;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.geometry.HPos;

public class ShopSellController {

    @FXML // fx:id="inventory"
    private GridPane inventory; // Value injected by FXMLLoader

    @FXML // fx:id="buyItem"
    private Button buyItem; // Value injected by FXMLLoader

    @FXML // fx:id="continueGame"
    private Button continueGame; // Value injected by FXMLLoader

    private MenuSwitcher gameSwitcher;

    private MenuSwitcher shopBuySwitcher;

    private LoopManiaWorld loopManiaWorld;

    private LoopManiaWorldController loopManiaWorldController;

    private Timeline timeline;

    private ArrayList<Button> buttons;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                ImageView inventorySlotImage = new ImageView((new File("src/images/empty_slot.png")).toURI().toString());
                inventory.add(inventorySlotImage, x, y);
            }
        }
        assert inventory != null : "fx:id=\"inventory\" was not injected: check your FXML file 'ShopSellView.fxml'.";
        assert buyItem != null : "fx:id=\"buyItem\" was not injected: check your FXML file 'ShopSellView.fxml'.";
        buttons = new ArrayList<Button>();
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    public void setShopBuySwitcher (MenuSwitcher shopBuySwitcher) {
        this.shopBuySwitcher = shopBuySwitcher;
    }

    public void setLoopManiaWorld(LoopManiaWorld loopManiaWorld) {
        this.loopManiaWorld = loopManiaWorld;
    }

    public void startTimer(LoopManiaWorldController loopManiaWorldController){
        // TODO = handle more aspects of the behaviour required by the specification
        this.loopManiaWorldController = loopManiaWorldController;

        System.out.println("starting timer for shop");
        
        List <BasicItem> unequippedInventory = loopManiaWorldController.getWorld().getAllInventoryItems();
        
        inventory.getChildren().clear();
        inventory.getChildren().removeAll();

        for(Button b : buttons) {
            inventory.getChildren().remove(b);
        }
        buttons = new ArrayList<Button>();


        int itemIndex = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                
                if (itemIndex < unequippedInventory.size()){
                    BasicItem currentItem = unequippedInventory.get(itemIndex);
                    Integer cost = currentItem.getValue();

                    Button button = new Button(cost.toString());
                    inventory.add (button, x, y);
                    buttons.add(button);
                    GridPane.setHalignment(button, HPos.CENTER);
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            int row = GridPane.getRowIndex(button);
                            int column = GridPane.getColumnIndex(button);
                            
                            BasicItem itemToSell = getItemByInventoryPosition(row, column);
                            loopManiaWorldController.getWorld().removeUnequippedInventoryItem((Entity) itemToSell);
                            loopManiaWorldController.getWorld().getCharacter().increaseGold(itemToSell.getValue());
                            //inventory.getChildren().remove(itemToSell);
                            inventory.add(new ImageView(new Image((new File("src/images/empty_slot.png")).toURI().toString())), column, row);
                        }
                    });
                }
                itemIndex++;
            }
        }

        GridPane worldInventory = loopManiaWorldController.getUnequippedInventory();
        this.inventory.getChildren().addAll(worldInventory.getChildren());

        

        //ObservableList<Node> worldList = loopManiaWorldController.getUnequippedInventory().getChildren();
        //ObservableList<Node> childList = this.inventory.getChildren();
        EventHandler<ActionEvent> switchToGame = new EventHandler<ActionEvent>(){
            public void handle (ActionEvent t) {
                GridPane worldInventory = loopManiaWorldController.getUnequippedInventory();
                worldInventory.getChildren().addAll(inventory.getChildren());
                inventory.getChildren().clear();
                timeline.stop();
                gameSwitcher.switchMenu();
            }
        };
        
        //trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.getKeyFrames().add(new KeyFrame(Duration.millis(8000), switchToGame));
        this.timeline.play();
    }

    @FXML
    private void switchToGame() throws IOException {
        loopManiaWorldController.getUnequippedInventory().getChildren().addAll(inventory.getChildren());
        timeline.stop();
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToShopBuy() throws IOException {
        loopManiaWorldController.getUnequippedInventory().getChildren().addAll(inventory.getChildren());
        timeline.stop();
        shopBuySwitcher.switchMenu();
    }

    public BasicItem getItemByInventoryPosition(int y, int x){
        List <BasicItem> unequippedInventory = loopManiaWorldController.getWorld().getAllInventoryItems();
        int index = y * 4 + x;
        return unequippedInventory.get(index);
    }


    public void sellItem(Button button) {
        System.out.println("SELL ITEM");
        int row = GridPane.getRowIndex(button);
        System.out.println(row);
        int column = GridPane.getColumnIndex(button);
        System.out.println(column);
        BasicItem itemToSell = getItemByInventoryPosition(row, column);
        loopManiaWorldController.getWorld().removeUnequippedInventoryItem((Entity) itemToSell);
        loopManiaWorldController.getWorld().getCharacter().increaseGold(itemToSell.getValue());

    }
}
