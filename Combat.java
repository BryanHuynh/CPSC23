import java.util.ArrayList;
public class Combat{
  EntityManager em;
  Combat(EntityManager em){
    this.em = em;
  }

  ArrayList<Enemy> toBeRemoved = new ArrayList<Enemy>();

  public ArrayList<Enemy> combatCheck(){
    ArrayList<Enemy> inRange = new ArrayList<Enemy>();
    for(Enemy enemy: em.getEnemies()){
      if(em.getDistanceBetweenEntities(em.getPlayer(), enemy) < 2){
        inRange.add(enemy);
      }
    }

    return inRange;
  }

  public void startCombat(Player player, ArrayList<Enemy> enemies, String action){
    displayEnemy(em.getPlayer(), enemies);
    action(enemies, action);
  }

  public void action(ArrayList<Enemy> enemies, String action){
    System.out.println(isNumeric(action));
    if(isNumeric(action)){
      int target = (int) Double.parseDouble(action);
      enemies.get(target).hp -= em.getPlayer().atk;
      if(enemies.get(target).hp <= 0){
        //toBeRemoved.add(enemies.get(target));
        em.getEntities().remove(enemies.get(target));
        em.getEnemies().remove(enemies.get(target));
      }
    }
    toBeRemoved = new ArrayList<Enemy>();
  }

  public boolean isNumeric(String str){
  try{
    double d = Double.parseDouble(str);
  }
  catch(NumberFormatException nfe){
    return false;
  }return true;
}


  public void displayEnemy(Player player,  ArrayList<Enemy> enemies){
    ArrayList<String> lines = new ArrayList<String>();

    String formatTitle = "|%1$-10s|";
    lines.add("");
    lines.add("");
    lines.add("");
    lines.add("");
    lines.add("");
    lines.add("");

    for(Enemy enemy: enemies){
      lines.set(0, lines.get(0) + String.format(formatTitle, "enemy(" + enemies.indexOf(enemy) + ")"));
      lines.set(1, lines.get(1) + String.format(formatTitle,"hp: +" + String.valueOf(enemy.hp)));
      lines.set(2, lines.get(2) + String.format(formatTitle,"atk: +" + String.valueOf(enemy.atk)));
      Point pt = new Point(enemy.getX()-player.getX(), player.getY()-enemy.getY());
      lines.set(3, lines.get(3) + String.format(formatTitle,"x: " + String.valueOf(pt.getX())));
      lines.set(4, lines.get(4) + String.format(formatTitle,"y: " + String.valueOf(pt.getY())));

    }
    for(String line: lines){
      System.out.println(line);
    }

  }
}
