import java.util.ArrayList;
import java.util.Random;

/**
 * manages entities: can create entities, update, move player character, and other utils
 * contains arraylist of all entities created with manager
 */
public class EntityManager{

  private ArrayList<Entity> entities = new ArrayList<Entity>();
  private ArrayList<NPC> npcs = new ArrayList<NPC>();
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

  private Player player;

  EntityManager(){

  }

    /**
     * creates player, and adds to the entities and saves to the Player field
     * @param x
     * @param y
     * @return
     */
  public Player createPlayer(int x, int y){
    player = new Player(x, y, 'x');
    entities.add(player);
    return player;
  }


    /**
     * creates an obstacle and adds to the entities and obstacle arraylist
     * @param x
     * @param y
     * @param symbol img used to represent on the map
     * @return
     */
  public Obstacle createObstacles(int x, int y, char symbol){
  	Obstacle ob = new Obstacle(x, y, symbol);
  	entities.add(ob);
    obstacles.add(ob);
  	return ob;
  }

    /**
     * creates an NPC and adds to the entities and NPC arraylist
     * @param x
     * @param y
     * @param symbol img used to represent on the map
     * @return
     */
  public NPC createNPC(int x, int y, char symbol){
  	NPC npc = new NPC(x, y, symbol);
  	entities.add(npc);
    npcs.add(npc);
  	return npc;
  }

    /**
     * creates an Enemy and adds to the entities and Enemy arraylist
     * @param x
     * @param y
     * @param symbol img used to represent on the map
     * @return
     */
  public Enemy createEnemy(int x, int y, char symbol){
    Enemy enemy = new Enemy(x, y, symbol);
    entities.add(enemy);
    enemies.add(enemy);
    return enemy;
  }

    /**
     * from the players currently location moves a step.
     * will prevent the player from phasing through other entities
     * @param dx
     * @param dy
     * @param textArea
     */
  public void movePlayer(int dx, int dy, char[][] map){
	int x = this.player.getX() + dx;
	int y = this.player.getY() + dy;

  if(x < 0 || y < 0) return;
  if(x > map[0].length) return;
  if(y > map.length) return;
	if(map[y][x] == '#')  return;
	if(map[y][x] =='c' || map[y][x] =='C') return;
  if(map[y][x] =='e'  || map[y][x] =='E' ) return;
    this.player.setPosition(x,y);
  }


  public void update(char[][] map){
    runEnemyAStar(map);
  }

    /**
     * runs the A* method for all enemies
     */
  public void runEnemyAStar(char[][] map){
      int[][] obs = new int[obstacles.size()][obstacles.size()];
      if(obstacles.size() > 0){
          for(int x = 0; x < obs[0].length; x++){
              obs[x][0] = obstacles.get(x).getY();
              obs[x][1] = obstacles.get(x).getX();
          }
      }
      for(Enemy enemy: enemies){
        if(getDistanceBetweenEntities(enemy, player) < 3){
            enemy.setPath(AStar.test(0, Rogue.getwidth() + 1, Rogue.getheight() + 1, enemy.getY(), enemy.getX(), player.getY(), player.getX(), obs));
        }

          enemy.step();
      }
  }

  public void update(double delta){
  	for(Entity en: entities){
  		en.update(delta);
    }
  }

    /**
     * gets the distance between two entities
     * @param en1
     * @param en2
     * @return
     */
  public double getDistanceBetweenEntities(Entity en1, Entity en2){
  	double x = Math.pow(en1.getX()-en2.getX(), 2);
  	double y = Math.pow(en1.getY()-en2.getY(), 2);
  	return Math.sqrt(x + y);
  }


	/**
	* Returns value of player
	* @return
	*/
	public Player getPlayer() {
		return this.player;
	}

	/**
	* Sets new value of player
	* @param
	*/
	public void setPlayer(Player player) {
		this.player = player;
	}


    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public void setNpcs(ArrayList<NPC> npcs) {
        this.npcs = npcs;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }



}
