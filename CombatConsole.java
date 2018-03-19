import java.util.ArrayList;
import java.util.Scanner;

public class CombatConsole extends Combat {

    /**
     * mainly used to printout the status of combat to the console
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
            lines.set(1, lines.get(1) + String.format(formatTitle, "hp: +" + String.valueOf(enemy.hp)));
            lines.set(2, lines.get(2) + String.format(formatTitle, "atk: +" + String.valueOf(enemy.atk)));
            Point pt = new Point(enemy.getX() - player.getX(), player.getY() - enemy.getY());
            lines.set(3, lines.get(3) + String.format(formatTitle, "x: " + String.valueOf(pt.getX())));
            lines.set(4, lines.get(4) + String.format(formatTitle, "y: " + String.valueOf(pt.getY())));

        }
        for (String line : lines) {
            System.out.println(line);
        }

    }

    public void render(Player player, Enemy enemy) {
        ArrayList<String> lines = new ArrayList<String>();

        String formatTitle = "|%1$-10s|";
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");


        lines.set(0, lines.get(0) + String.format(formatTitle, "enemy(" + ")"));
        lines.set(1, lines.get(1) + String.format(formatTitle, "hp: +" + String.valueOf(enemy.hp)));
        lines.set(2, lines.get(2) + String.format(formatTitle, "atk: +" + String.valueOf(enemy.atk)));
        Point pt = new Point(enemy.getX() - player.getX(), player.getY() - enemy.getY());
        lines.set(3, lines.get(3) + String.format(formatTitle, "x: " + String.valueOf(pt.getX())));
        lines.set(4, lines.get(4) + String.format(formatTitle, "y: " + String.valueOf(pt.getY())));


        for (String line : lines) {
            System.out.println(line);
        }

    }


    /**
     * starts a loop, containing battle sequences
     * @param enemy
     */
    public void battle(Enemy enemy) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        Player player = super.em.getPlayer();
        while (running) {
            render(player, enemy);
            System.out.println("Player actions: -attack("+player.atk+")" );
            if (scanner.hasNext()) {                                                      //get player action
                String action = scanner.nextLine();
                if (action.equalsIgnoreCase("attack")) {
                    enemy.hp -= player.atk;
                    System.out.println("Player Attacked Enemy for: " + player.atk);
                    render(player, enemy);
                    if (enemy.hp <= 0) {                                                          //if npc is dead then stop loop
                        running = false;
                        em.getEntities().remove(enemy);
                        em.getEnemies().remove(enemy);
                        return;
                    }
                }
            }
            if (super.getParty().getPartyList().size() > 0) {
                for (NPC member : super.getParty().getPartyList()) {                           // get npc action
                    System.out.println(member.getName()+" actions: -attack("+member.getAtk()+")" );
                    if (scanner.hasNext()) {
                        String action = scanner.nextLine();
                        if (action.equalsIgnoreCase("attack")) {
                            enemy.hp -= member.getAtk();
                            System.out.println(member.getName() + " Attacked Enemy for: " + member.getAtk());
                            render(player, enemy);
                            if (enemy.hp <= 0) {                                                          //if npc is dead then stop loop
                                running = false;
                                em.getEntities().remove(enemy);
                                em.getEnemies().remove(enemy);
                                return;
                            }
                        }
                    }
                }
            }

            if (enemy.hp <= 0) {                                                          //if npc is dead then stop llop
                running = false;
                em.getEntities().remove(enemy);
                em.getEnemies().remove(enemy);
            }

        }

    }
}
