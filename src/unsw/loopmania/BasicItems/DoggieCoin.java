package unsw.loopmania.BasicItems;

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class DoggieCoin extends BasicItem {
    
    public static int COST;

    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, COST, new ImageView(new Image((new File("src/images/doggiecoin.png")).toURI().toString())));
    }    

    /** 
     * Price of DoggieCoin randomly fluctuates between 0 to 100
     * If Elan Muske has been defeated, Price of DoggieCoin plummets
     * and set to 200
     */ 

    public int setCoinPrice(boolean elanHere) {

        Random price = new Random(5);
        if (elanHere == true) {
            COST = price.nextInt(1000) + 100;
        }
        else {
            COST = price.nextInt(100);
        }
        setCost(COST);
        return COST;
    }
    
}
