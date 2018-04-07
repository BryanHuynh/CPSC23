import java.util.Random;
import java.util.Scanner;

public class BattleScreenConsole extends BattleScreen {
    private EntityManager em;
    private Party party;
    private String feed;
    private CombatConsole combat;
    private boolean running;
    private boolean escaped = false;

    public BattleScreenConsole(EntityManager em, Party party, Enemy enemy, CombatConsole combat) {
        super(party, enemy, em);
        this.em = em;
        this.party = party;
        this.combat = combat;
    }

    public void action(Scanner scanner, Enemy enemy, EntityCharacter member) {

        System.out.println(member.getName() + " actions: A(" + member.getAtk() + " damage)");
        System.out.println(member.getName() + " block: B");
        System.out.println(member.getName() + " run: R(" + party.escapeChance()*100 + "%)");

        if (scanner.hasNext()) {
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("a") || action.equalsIgnoreCase("attack")) {
                boolean isHit = member.getChance();
                if (isHit) {
                    em.damageEnemy(enemy, member.getAtk());
                    feed = member.getName() + " Attacked Enemy for: " + member.atk;
                } else {
                    feed = (member.getName() + " missed");
                }
            } else if (action.equalsIgnoreCase("b") || action.equalsIgnoreCase("block")) {
                party.setToBlock(member);
                feed = member.getName() + " is blocking";
            } else if (action.equalsIgnoreCase("r") || action.equalsIgnoreCase("run")) {
                escaped = party.attemptEscape();
                if (!escaped) {
                    feed = "Escape failed";
                } else {
                    feed = "Escape successful";
                }
            }
        }
    }

    public boolean isEnemyAlive(Enemy enemy) {
        if (enemy.getHp() <= 0) {                                                          //if npc is dead then stop llop
            em.getEntities().remove(enemy);
            em.getEnemies().remove(enemy);
            em.removeEnemy(enemy);
            System.out.println("ENEMY WAS DEFEATED!");

            return false;
        }
        return true;
    }


    public void enemyTurn(Enemy enemy) {
        Random rand = new Random();
        EntityCharacter target = party.getLivePartyMembers().get(rand.nextInt(party.getLivePartyMembers().size()));
        boolean enemyAttacks = enemy.getChance();
        if (enemyAttacks) {
            if (!target.isBlocking()) {
                party.damageCharacter(target, enemy.getAtk());
                feed = target.getName() + " was attacked for " + enemy.getAtk();
                party.removeDeadMembers();
                if (target.isDead) {
                    feed += "\n " + target.getName() + " DIED!";
                }
            } else {
                feed = target.getName() + " blocked an attack";
            }
        } else {
            System.out.println("Enemy Missed an attack");
        }
        //render(enemy);
        //party.render();

    }


    public void clear() {
        for (int i = 0; i < 3; i++) {
            System.out.println("---------------------------------------");
        }
    }


    /**
     * starts a loop, containing battle sequences
     */
    public void battle() {
        Scanner scanner = new Scanner(System.in);
        running = true;

        while (running && !escaped) {
            if (party.getPartyList().size() > 0) {
                for (EntityCharacter member : party.getLivePartyMembers()) {                           // get member action
                    clear();
                    System.out.println(feed);
                    clear();
                    feed = "";
                    combat.render(enemy);
                    party.render();
                    action(scanner, enemy, member);

                    running = isEnemyAlive(enemy);

                    if (!running) return;

                }
            }
            enemyTurn(enemy);
            if (party.getLivePartyMembers().size() <= 0) running = false;
            party.resetBlock();
        }

    }


    public static void main(String args[]) {
        EntityManager em = new EntityManager();
        em.createPlayer(0, 0);
        em.getPlayer().setAtk(1);
        em.getPlayer().setHp(1000);

        Party party = new PartyConsole(em.getPlayer());
        party.addMember(em.createNPC(0, 0, 'c'));

        CombatConsole combat = new CombatConsole(em, party);
        BattleScreenConsole bsc = new BattleScreenConsole(em, party, em.createEnemy(0, 0, 'e'), combat);
        bsc.battle();


    }


}
