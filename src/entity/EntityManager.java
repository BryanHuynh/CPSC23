package entity;

import utils.NameGenerator;
import utils.Point;
import rogue.Rogue;
import utils.PathFinder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * manages entities: can create entities, update, move player character, and other utils
 * contains arraylist of all entities created with manager
 */
public class EntityManager {

    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<NPC> npcs = new ArrayList<NPC>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private NameGenerator nameGenerator;
    private NameGenerator weaponsGenerator;


    private Player player;

    public EntityManager() {
        nameGenerator = new NameGenerator("additionals/Names.txt");
        weaponsGenerator = new NameGenerator("additionals/weapons.txt");
    }

    /**
     * creates player, and adds to the entities and saves to the entity.Player field
     *
     * @param x
     * @param y
     * @return
     */
    public Player createPlayer(int x, int y) {
        player = new Player(x, y, 'x');
        player.setWeapon(weaponsGenerator.getAName());
        entities.add(player);
        return player;
    }

    /**
     * creates an obstacle and adds to the entities and obstacle arraylist
     *
     * @param x
     * @param y
     * @param symbol img used to represent on the map
     * @return
     */
    public Obstacle createObstacles(int x, int y, char symbol) {
        Obstacle ob = new Obstacle(x, y, symbol);
        entities.add(ob);
        obstacles.add(ob);
        return ob;
    }

    /**
     * creates an entity.NPC and adds to the entities and entity.NPC arraylist
     *
     * @param x
     * @param y
     * @param symbol img used to represent on the map
     * @return
     */
    public NPC createNPC(int x, int y, char symbol) {
        NPC npc = new NPC(x, y, symbol);
        npc.setName(nameGenerator.getAName());
        npc.setWeapon(weaponsGenerator.getAName());
        entities.add(npc);
        npcs.add(npc);
        return npc;
    }


    /**
     * subtracts health from an enemy
     * @param en
     * @param damage
     */
    public void damageEnemy(Enemy en, int damage) {
        for (Enemy enemy : enemies) {
            if (en.equals(enemy)) {
                enemy.damage(damage);
            }
        }
    }

    /**
     * creates an entity.Enemy and adds to the entities and entity.Enemy arraylist
     *
     * @param x
     * @param y
     * @param symbol img used to represent on the map
     * @return
     */
    public Enemy createEnemy(int x, int y, char symbol) {
        Enemy enemy = new Enemy(x, y, symbol);
        enemy.setWeapon(weaponsGenerator.getAName());
        entities.add(enemy);
        enemies.add(enemy);
        return enemy;
    }

    /**
     * from the players currently location moves a step.
     * will prevent the player from phasing through other entities
     *
     * @param dx
     * @param dy
     */
    public void movePlayer(int dx, int dy, char[][] map) {
        int x = this.player.getX() + dx;
        int y = this.player.getY() + dy;

        if (x < 0 || y < 0) return;
        if (x >= map[0].length) return;
        if (y >= map.length) return;
        if (map[y][x] == '.') {
            this.player.setPosition(x, y);
        }
    }


    /**
     * a temporary system to test out the dialog system mixed with the player proximate, [TO BE REMOVED]
     */
    public String playerTalk() {
        for (NPC c : getNpcs()) {
            if (getDistanceBetweenEntities(getPlayer(), c) == 1) {
                if (c.isVisable()) {
                    return c.getDialog();
                }
            }
        }
        return "";
    }

    /**
     * removes enemy from collections and sets tag to not visible
     *
     * @param enemy
     */
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        entities.remove(enemy);
        enemy.setVisable(false);
    }


    /**
     * get recruit if possible
     */
    public NPC recuitment() {
        for (NPC c : getNpcs()) {
            if (getDistanceBetweenEntities(getPlayer(), c) == 1) {
                return c;
            }
        }
        return null;
    }

    /**
     * enemy takes a single step in it's path
     * will stop if space is occupied
     *
     * @param enemy
     */
    public void step(Enemy enemy) {
        if (enemy.getPath().size() > 0) {
            Point pt = enemy.getPath().get(enemy.getPath().size() - 1);
            if (!isSpaceOccupied(pt.getX(), pt.getY())) {
                enemy.setX(pt.getX());
                enemy.setY(pt.getY());
                enemy.getPath().remove(enemy.getPath().size() - 1);
            } else {
                return;
            }
        }
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setNpcs(ArrayList<NPC> npcs) {
        this.npcs = npcs;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * checks if space is occupied
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isSpaceOccupied(int x, int y) {
        for (Enemy entity : enemies) {
            if (entity.getPosition().equals(new Point(x, y))) return true;
        }
        return false;

    }

    public void update(char[][] map) {
        runEnemyAStar(map);
    }

    /**
     * sets the stats for player
     * @param hp
     * @param acc
     * @param atk
     */
    public void setPlayerStats(int hp, int acc, int atk) {
        player.setHp(hp);
        player.setAccuracy(acc);
        player.setAtk(atk);
    }

    /**
     * runs the A* method for all enemies
     */
    public void runEnemyAStar(char[][] map) {

        for (Enemy enemy : enemies) {
            if (getDistanceBetweenEntities(enemy, player) <= 1) continue;
            if (getDistanceBetweenEntities(enemy, player) < 20) {

                ArrayList<Point> obs = new ArrayList<>();
                for (Obstacle ob : obstacles) {
                    obs.add(new Point(ob.getY(), ob.getX()));
                }
                for (NPC ob : npcs) {
                    if (!ob.isVisable()) continue;
                    obs.add(new Point(ob.getY(), ob.getX()));
                }
                /**
                 for (entity.Enemy ob : enemies) {
                 if (!ob.isVisable()) continue;
                 if (ob.equals(enemy)) continue;
                 obs.add(new utils.Point(ob.getY(), ob.getX()));
                 }
                 */

                Point[] oblist = new Point[obs.size()];
                for (int i = 0; i < obs.size(); i++) {
                    oblist[i] = obs.get(i);
                }
                try {
                    ArrayList<Point> path = new PathFinder(Rogue.getwidth() + 1, Rogue.getheight() + 1, new Point(enemy.getY(), enemy.getX()), new Point(player.getY(), player.getX()), oblist).moves;
                    Collections.reverse(path);
                    enemy.setPath(path);
                } catch (Exception e) {
                    System.out.println("no path found");
                }


                if (enemy.getPath().size() > 0) {
                    enemy.getPath().remove(0);
                    enemy.getPath().remove(0);
                }



            }

            step(enemy);

        }
    }

    /**
     * runs update method for all entities
     *
     * @param delta
     */
    public void update(double delta) {
        for (Entity en : entities) {
            en.update(delta);
        }
    }

    /**
     * gets the distance between two entities
     *
     * @param en1
     * @param en2
     * @return
     */
    public double getDistanceBetweenEntities(Entity en1, Entity en2) {
        double x = Math.pow(en1.getX() - en2.getX(), 2);
        double y = Math.pow(en1.getY() - en2.getY(), 2);
        return Math.sqrt(x + y);
    }


    public boolean isAllEnemiesDead(){
        for(Enemy enemy: enemies){
            if(enemy.isVisable()) return false;
        }
        return true;
    }

    /**
     * Returns value of player
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Sets new value of player
     *
     * @param
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public ArrayList<NPC> getNpcs() {
        return new ArrayList<>(npcs);
    }

    public ArrayList<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public ArrayList<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }


}
