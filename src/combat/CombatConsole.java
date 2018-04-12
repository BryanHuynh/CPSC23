package combat;

import entity.Enemy;
import entity.EntityManager;
import entity.Player;
import party.Party;
import utils.*;

import java.util.ArrayList;

public class CombatConsole extends Combat {
    /**
     * mainly used to printout the status of enemies
     *
     * @param em
     * @param party
     */
    public CombatConsole(EntityManager em, Party party) {
        super(em, party);
    }

    /**
     * visually display enemy stats through console
     * @param player
     * @param enemies
     */
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

    /**
     *
     * visually display enemy stats through console
     * @param enemy
     */
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


}
