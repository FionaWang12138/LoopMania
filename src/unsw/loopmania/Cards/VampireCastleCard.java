package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    /**
     * Constructor for vampire castle card.
     * @param x x-axis.
     * @param y y-axis.
     */
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/vampire_castle_card.png")).toURI().toString())));
        this.setPlacementStrategy(new AdjacentToPathStrategy());
    }    
}
