import java.util.ArrayList;

public class CombatConsole extends Combat {
    public CombatConsole(EntityManager em) {
        super(em);
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
}
