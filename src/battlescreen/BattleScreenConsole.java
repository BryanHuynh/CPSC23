package battlescreen;

import combat.CombatConsole;
import entity.*;
import party.*;

import java.util.Random;
import java.util.Scanner;

public class BattleScreenConsole extends battlescreen.BattleScreen {
   private CombatConsole combat;

    /**
     *
     * battle screen deals with the combat involved between a single enemy and a party
     * displayed visually through a console
     * @param em
     * @param party
     * @param enemy
     * @param combat
     */
    public BattleScreenConsole(EntityManager em, Party party, Enemy enemy, CombatConsole combat) {
        super(party, enemy, em);
        this.combat = combat;
    }

    /**
     * this method does not run and logical components, only the input.
     * handles the users action towards an enemy through the console
     * member can attack and deal damage equal to it's stats
     * member can run. chance of success is equals to parties run chance
     * member can block. negating chance of getting hit for a single turn
     * member can skip turn. not doing anything
     * @param scanner
     * @param member
     */
    public void action(Scanner scanner, EntityCharacter member) {
        System.out.println(member.getName() + " Attack: A(" + member.getAtk() + " damage)");
        System.out.println(member.getName() + " run   : R(" + party.escapeChance()*100 + "%)");
        System.out.println(member.getName() + " Block : B");
        System.out.println(member.getName() + " Skip  : S");

        if (scanner.hasNext()) {
            String action = scanner.nextLine();
            super.action(action, member);
        }
    }


    /**
     * just prints 3 bars
     */
    public void clear() {
        for (int i = 0; i < 3; i++) {
            System.out.println("---------------------------------------");
        }
    }


    /**
     * starts a loop containing battle sequences
     * game only runs if the player hasn't escaped and enemy is still alive
     * loops through live members and gives them a chance to do an action
     * when all members are done making an action. it's the enemies turn
     */
    public void battle() {
        Scanner scanner = new Scanner(System.in);

        while (running && !ran) {
            if (party.getPartyList().size() > 0) {
                for (EntityCharacter member : party.getLivePartyMembers()) {                           // get member action
                    feed = "";
                    combat.render(enemy);
                    party.render();
                    action(scanner, member);
                    clear();
                    System.out.println(feed);
                    clear();
                    if(!isEnemyAlive()){
                        party.teamRestoreHealth(enemy.getMaxHp()/ 2 / party.getLivePartyMembers().size());
                        running = false ;
                    }

                    if (!running) return;

                }
            }
            enemyTurn();
            clear();
            System.out.println(feed);
            clear();
            if (party.getLivePartyMembers().size() <= 0){
                running = false;
            }
            party.resetBlock();
        }

    }


    /**
     * for debugging
     * @param args
     */
    public static void main(String args[]) {
        EntityManager em = new EntityManager();
        em.createPlayer(0, 0);
        em.getPlayer().setAtk(1);
        em.getPlayer().setHp(1000);

        Party party = new PartyConsole(em.getPlayer());
        party.addMember(em.createNPC(0, 0, 'c'));

        combat.CombatConsole combat = new combat.CombatConsole(em, party);
        BattleScreenConsole bsc = new BattleScreenConsole(em, party, em.createEnemy(0, 0, 'e'), combat);
        bsc.battle();


    }


}
