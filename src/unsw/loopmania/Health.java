package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Health {
    private SimpleIntegerProperty currHealth;
    private SimpleIntegerProperty maxHealth;
    
    public Health (int health) {
        currHealth = new SimpleIntegerProperty();
        maxHealth = new SimpleIntegerProperty();

        this.currHealth.set(health);
        this.maxHealth.set(health);
    }

    public int getCurrHealth () {
        return this.currHealth.get();
    }

    public SimpleIntegerProperty getCurrHealthProperty() {
        return this.currHealth;
    }

    public int getMaxHealth () {
        return this.maxHealth.get();
    }
    
     /**
     * increases the health by a given amount, but does not go above maxHealth
     * @param health
     */
    public void increaseHealth (int health) {
        if (this.currHealth.get() + health < this.maxHealth.get()) {
            currHealth.set(currHealth.get() + health);
        } else {
            this.currHealth.set(this.maxHealth.get());
        }
    }

    /**
     * decreases the health by a given amount, but does not go below 0
     * @param health
     */
    public void decreaseHealth (int health) {
        if (this.currHealth.get() - health < 0) {
            this.currHealth.set(0);
        } else {
            this.currHealth.set(currHealth.get()-health);
        }
    }
}
