package unsw.loopmania.Buildings.SpawnBuildings;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import unsw.loopmania.LoopManiaApplication;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;

public abstract class SpawnBuilding extends Building {

    

    public SpawnBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y, ImageView image) {
        super(x, y, image);
    }

    /**
     * Checks whether or not there is a path tile in the 8 surrounding tiles of the building
     * @param orderedPath
     * @return
     */
    public Pair<Integer, Integer> findPathToSpawn (List<Pair<Integer, Integer>> orderedPath) {
        
        int x = this.getX();
        int y = this.getY();

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {

                for (Pair<Integer, Integer> pos : orderedPath) { 
                    if ((pos.getValue0() == i) && (pos.getValue1() == j)) {

                        Pair<Integer, Integer> spawnTile = new Pair<Integer,Integer>(i, j);
                        return spawnTile;
                    }
                }
            }
        }
        
        return null;
    }

    public abstract BasicEnemy spawnAction (int loopCount, boolean isCharcaterOnCastle, Pair<Integer, Integer> spawnTile, List<Pair<Integer, Integer>> orderedPath);

}