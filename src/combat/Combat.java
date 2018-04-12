package combat;

import entity.Enemy;
import entity.EntityManager;
import party.Party;

import java.util.ArrayList;

public class Combat {
    EntityManager em;
    protected Party party;

    /**
     * used to handle the interactions surrounding combat
     * @param em
     */
    Combat(EntityManager em, Party party) {
        this.em = em;
        this.party = party;
    }



    /**
     * looks for enemies that are in range of the player by 1 and returns a list of those enemies
     * @return
     */
    public ArrayList<Enemy> combatCheck() {
        ArrayList<Enemy> inRange = new ArrayList<Enemy>();
        for (Enemy enemy : em.getEnemies()) {
            if (em.getDistanceBetweenEntities(em.getPlayer(), enemy) <= 1.5) {
                if(enemy.isVisable()){
                    inRange.add(enemy);
                }
            }
        }
        return inRange;
    }
}
