package unsw.loopmania.BasicItems;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import unsw.loopmania.StaticEntity;

/**
 * Basic Items
 */
public class BasicItem extends StaticEntity  {

    public int costOfItem;
    
    public BasicItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int costOfItem, ImageView image) {
        super(x, y, image);
        this.costOfItem = costOfItem;
    } 

    public int getValue() {
        return costOfItem;
    }

    public void setCost(int value) {
        costOfItem = value;
    }   
}