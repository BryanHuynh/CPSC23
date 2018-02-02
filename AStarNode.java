import java.util.List;

/**
 * A class for creating nodes for the A* algorithm, to be used
 * so the enemy NPCs follow the player under a certain range
 * @see <a href="https://en.wikipedia.org/wiki/A*_search_algorithm">A* algorithm</a>
 */
public abstract class AStarNode implements Comparable{

    AStarNode pathParent;
    float costFromStart;
    float estimatedCostToGoal;

    public float getCost() {
        return costFromStart + estimatedCostToGoal;
    }

    pu
}
