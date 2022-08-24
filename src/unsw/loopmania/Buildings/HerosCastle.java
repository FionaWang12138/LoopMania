package unsw.loopmania.Buildings;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entity;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.BasicItems.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HerosCastle extends Building {
    private List<BasicItem> items;

    /**
     * Constructor for heros castle to a target cell.
     * @param x x-axis of the cell.
     * @param y y-axis of the cell.
     */
    public HerosCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/heros_castle.png")).toURI().toString())));
        //TODO Auto-generated constructor stub
        this.items = new ArrayList<>();
        this.fillShop();
    }

    /**
     * Get all items on sale in the heros castle.
     * @return A list of basic items.
     */
    public List<BasicItem> getAllItems() {
        this.fillShop();
        return this.items;
    }

    public BasicItem getItemByIndex(int index) {
        return this.items.get(index);
    }

    /**
     * Buy an item by index in the heros castle.
     * @param index the index of the item you want to buy.
     * @return the item you selected.
     */
    public BasicItem buyItem(BasicItem item) {
        this.items.remove(item);
        this.fillShop();
        return item;
    }

    /**
     * Fill the items on sale to 3.
     */
    public void fillShop() {
        while (this.items.size() < 3) {
            this.items.add(generateRandomItem());
        }
    }

    /**
     * Generate a random item in the shop.
     * @return the generated item.
     */
    public BasicItem generateRandomItem () {
        SimpleIntegerProperty x = (SimpleIntegerProperty) this.x();
        SimpleIntegerProperty y = (SimpleIntegerProperty) this.y();
        Random random = new Random();
        double r = random.nextDouble();
        if (r < 0.2){
            return new Sword(x, y);
        } else if (r < 0.4) {
            return new Stake(x, y);
        } else if (r < 0.6){
            return new Staff(x, y);
        } else if (r < 0.8){
            return new Armour(x, y);
        } else {
            return new Shield(x, y);
        }
    }
}