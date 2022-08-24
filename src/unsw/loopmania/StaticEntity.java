package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/**
 * represents a non-moving entity
 * unlike the moving entities, this can be placed anywhere on the game map
 */
public abstract class StaticEntity extends Entity {
    /**
     * x and y coordinates represented by IntegerProperty, so ChangeListeners can be added
     */
    private IntegerProperty x, y;
    //private ImageView image;

    public StaticEntity(SimpleIntegerProperty x, SimpleIntegerProperty y, ImageView image) {
        super(image);
        this.x = x;
        this.y = y;
        //this.image = image;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }

    //public ImageView getImageView() {
    //    return image;
    //}
    public void setCoordinate(SimpleIntegerProperty x, SimpleIntegerProperty y){
        this.x = x;
        this.y = y;
    }
}
