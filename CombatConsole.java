import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CombatConsole extends Combat {

    /**
     * mainly used to printout the status of combat to the console
     *
     * @param em
     * @param party
     */
    public CombatConsole(EntityManager em, Party party) {
        super(em, party);
    }

    public void render(Player player, ArrayList<Enemy> enemies) {
        ArrayList<String> lines = new ArrayList<String>();

        String formatTitle = "|%1$-10s|";
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");

        for (Enemy enemy : enemies) {
            lines.set(0, lines.get(0) + String.format(formatTitle, "enemy(" + enemies.indexOf(enemy) + ")"));
            lines.set(1, lines.get(1) + String.format(formatTitle, "hp: " + String.valueOf(enemy.getHp())));
            lines.set(2, lines.get(2) + String.format(formatTitle, "atk: +" + String.valueOf(enemy.getAtk())));
            Point pt = new Point(enemy.getX() - player.getX(), player.getY() - enemy.getY());
            lines.set(3, lines.get(3) + String.format(formatTitle, "x: " + String.valueOf(pt.getX())));
            lines.set(4, lines.get(4) + String.format(formatTitle, "y: " + String.valueOf(pt.getY())));

        }
        for (String line : lines) {
            System.out.println(line);
        }

    }

    public void render(Enemy enemy) {
        ArrayList<String> lines = new ArrayList<String>();

        String formatTitle = "|%1$-10s|";
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");


        lines.set(0, lines.get(0) + String.format(formatTitle, "enemy(" + ")"));
        lines.set(1, lines.get(1) + String.format(formatTitle, "hp: " + String.valueOf(enemy.getHp())));
        lines.set(2, lines.get(2) + String.format(formatTitle, "atk: +" + String.valueOf(enemy.getAtk())));
        lines.set(3, lines.get(3) + String.format(formatTitle, "accuracy: +" + String.valueOf(enemy.getAccuracy())));
        lines.set(4, lines.get(4) + String.format(formatTitle, "weapon: " + enemy.getWeapon()));



        for (String line : lines) {
            System.out.println(line);
        }

    }

    public void action(Scanner scanner, Enemy enemy, EntityCharacter member) {

        System.out.println(member.getName() + " actions: -attack(" + member.getAtk() + ")");
        if (scanner.hasNext()) {
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("attack")) {
                boolean isHit = member.getChance();
                if (isHit) {
                    em.damageEnemy(enemy, member.getAtk());
                    System.out.println(member.getName() + " Attacked Enemy for: " + member.atk);
                } else {
                    System.out.println(member.getName() + " missed");
                }

                render(enemy);
                party.render();
            }
            render(enemy);
            party.render();
        }
    }

    public boolean isEnemyAlive(Enemy enemy) {
        if (enemy.getHp() <= 0) {                                                          //if npc is dead then stop llop
            em.getEntities().remove(enemy);
            em.getEnemies().remove(enemy);
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
                System.out.println(target.getName() + " was attacked for " + enemy.getAtk());
            } else {
                System.out.println(target.getName() + " blocked an attack");
            }
        } else {
            System.out.println("Enemy Missed an attack");
        }
        party.render();

    }


    /**
     * starts a loop, containing battle sequences
     *
     * @param enemy
     */
    public void battle(Enemy enemy) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            render(enemy);
            party.render();
            if (party.getPartyList().size() > 0) {
                for (EntityCharacter member : party.getLivePartyMembers()) {                           // get member action
                    action(scanner, enemy, member);
                    running = isEnemyAlive(enemy);
                    if(!running) return;
                }
            }

            enemyTurn(enemy);
            if(party.getLivePartyMembers().size() <= 0) running = false;

        }

    }


    public static void main(String args[]) {
        EntityManager em = new EntityManager();
        em.createPlayer(0, 0);
        Party party = new PartyConsole(em.getPlayer());
        party.addMember(em.createNPC(0, 0, 'c'));

        CombatConsole combat = new CombatConsole(em, party);
        combat.battle(em.createEnemy(0, 0, 'e'));


    }
}
