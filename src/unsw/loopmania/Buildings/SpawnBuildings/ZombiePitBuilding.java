package unsw.loopmania.Buildings.SpawnBuildings;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class ZombiePitBuilding extends SpawnBuilding {

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/zombie_pit.png")).toURI().toString())));
    }

    /**
     * Spawns a zombie to an adjacent tile when the character creates another loop
     */
    @Override
    public BasicEnemy spawnAction(int loopCount, boolean isCharcaterOnCastle, Pair<Integer, Integer> spawnTile, List<Pair<Integer, Integer>> orderedPath) {
                
       if (isCharcaterOnCastle) {
    
            int indexInPath = orderedPath.indexOf(spawnTile);
            return new Zombie (new PathPosition(indexInPath, orderedPath));

        }
        
        return null;
    }    
}
