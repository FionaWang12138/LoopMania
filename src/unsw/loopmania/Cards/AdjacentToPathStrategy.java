package unsw.loopmania.Cards;

import java.util.List;

import  org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;

public class AdjacentToPathStrategy implements PlacementStrategy {
    
    /**
     * Return true if the cell is adjacent to the path, else false.
     */
    @Override
    public boolean isPlaceable(int x, int y, List<Pair<Integer, Integer>> Path) {
        boolean result = false;
        for (Pair<Integer, Integer> adj : Path) {
            if (adj.getValue0() == x && adj.getValue1() == y) return false;
            if (isAdjacent(adj.getValue0(), x) && isAdjacent(adj.getValue1(), y)) result = true;
        }
        return result;
    }

    /**
     * Check if the two 2 values are adjacent or equal.
     * @param x value1.
     * @param y value2.
     * @return true if adjacent, else false.
     */
    public boolean isAdjacent (int x, int y) {
        if (x == y + 1 || x == y - 1 || y == x) return true;
        return false;
    }
}
