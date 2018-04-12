package battlescreen;

import entity.Enemy;
import entity.EntityCharacter;
import entity.EntityManager;
import party.Party;

import java.awt.*;
import java.util.Random;

public abstract class BattleScreen  {
    protected Enemy enemy;
    protected EntityManager em;
    protected boolean ran = false;
    protected String feed;
    protected Party party;
    protected boolean running = true;
    protected Color isGoodNews = Color.green;

    /**
     * battle screen deals with the combat involved between a single enemy and a party
     * @param party
     * @param enemy
     * @param em
     */
    public BattleScreen(Party party, Enemy enemy, EntityManager em) {
        this.party = party;
        this.enemy = enemy;
        this.em = em;
    }


    /**
     *
     * logical component that deals with member actions
     * @param action
     * @param member
     */
    public void action(String action, EntityCharacter member){
        if (action.equalsIgnoreCase("a") || action.equalsIgnoreCase("attack")) {
            boolean isHit = member.getChance();
            if (isHit) {
                em.damageEnemy(enemy, member.getAtk());
                feed = member.getName() + " Attacked Enemy for: " + member.getAtk();
                isGoodNews = Color.green;
            } else {
                feed = (member.getName() + " missed");
                isGoodNews = Color.red;
            }
        } else if (action.equalsIgnoreCase("b") || action.equalsIgnoreCase("block")) {
            party.setToBlock(member);
            feed = member.getName() + " is blocking";
            isGoodNews = Color.gray;
        } else if (action.equalsIgnoreCase("r") || action.equalsIgnoreCase("run")) {
            ran = party.attemptEscape();
            if (!ran) {
                feed = "Escape failed";
                isGoodNews = Color.red;
            } else {
                feed = "Escape successful";
                isGoodNews = Color.green;
            }
        }
    }


    /**
     * handles the logic of enemies actions in combat.
     * attacks only living party members
     * party members that are blocking will cause the enemy to have its attack negated
     * enemy has a chance of missing
     */
    public void enemyTurn() {
        Random rand = new Random();
        EntityCharacter target = party.getLivePartyMembers().get(rand.nextInt(party.getLivePartyMembers().size()));
        boolean enemyAttacks = enemy.getChance();
        if (enemyAttacks) {
            if (!target.isBlocking()) {
                party.damageCharacter(target, enemy.getAtk());
                feed = target.getName() + " was attacked by enemy for " + enemy.getAtk();
                isGoodNews = Color.red;
                party.removeDeadMembers();
                if (target.isDead()) {
                    feed += "\n " + target.getName() + " DIED!";
                    isGoodNews = Color.red;
                }
            } else {
                feed = target.getName() + " blocked an attack";
                isGoodNews = Color.green;
            }
        } else {
            feed = ("Enemy Missed an attack");
            isGoodNews = Color.green;
        }
        party.resetBlock();
    }


    /**
     * checks to see if enemy is alive,
     * if not then it is tagged and returned true
     * @return
     */
    public boolean isEnemyAlive() {
        if (enemy.getHp() <= 0) {                                                          //if npc is dead then stop llop
            em.getEntities().remove(enemy);
            em.getEnemies().remove(enemy);
            em.removeEnemy(enemy);
            System.out.println("ENEMY WAS DEFEATED!");
            party.teamRestoreHealth(enemy.getMaxHp()/ 2 / party.getPartyList().size());
            System.out.println("each member had their health restored partially");
            return false;
        }
        return true;
    }

    public abstract void battle();





}
