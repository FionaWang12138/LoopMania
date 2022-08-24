package unsw.loopmania.Buildings.SpawnBuildings;

import java.io.File;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.Vampire;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends SpawnBuilding {

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, new ImageView(new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString())));
    }

    /**
     * Adds a vampire to an adjacent path tile everytime the loop count becomes a number divisible by 5
     */
    @Override
    public BasicEnemy spawnAction(int loopCount, boolean isCharcaterOnCastle, Pair<Integer, Integer> spawnTile, List<Pair<Integer, Integer>> orderedPath) {
         
        if ((loopCount % 5 == 0) && (isCharcaterOnCastle)) {
    
            int indexInPath = orderedPath.indexOf(spawnTile);
            return new Vampire (new PathPosition(indexInPath, orderedPath));

        }
        
        return null;
    }

}
