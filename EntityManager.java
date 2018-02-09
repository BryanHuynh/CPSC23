import java.util.ArrayList;

public class EntityManager{

  ArrayList<Entity> entities = new ArrayList<Entity>();
  ArrayList<NPC> npcs = new ArrayList<NPC>();
  ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

  private Player player;

  EntityManager(){

  }


  public Player createPlayer(int x, int y){
    player = new Player(x, y, 'x');
    entities.add(player);
    return player;
  }

  public EmptySpace createEmptySpace(int x, int y){
    EmptySpace es = new EmptySpace(x, y);
    entities.add(es);
    return es;
  }

  public Obstacle createObstacles(int x, int y, char symbol){
  	Obstacle ob = new Obstacle(x, y, symbol);
  	entities.add(ob);
    obstacles.add(ob);
  	return ob;
  }

  public NPC createNPC(int x, int y, char symbol){
  	NPC npc = new NPC(x, y, symbol);
  	entities.add(npc);
    npcs.add(npc);
  	return npc;
  }

  public Enemy createEnemy(int x, int y, char symbol){
    Enemy enemy = new Enemy(x, y, symbol);
    entities.add(enemy);
    enemies.add(enemy);
    return enemy;
  }


  public void movePlayer(int dx, int dy, TextWindow textArea){
	int x = this.player.getX() + dx;
	int y = this.player.getY() + dy;
	Entity pos = textArea.getCharacter(x, y);
	if(pos == null) return;
	if(pos.getSymbol() == '#')  return;
	if(pos.getSymbol() == 'c' || pos.getSymbol() == 'C') return;
  if(pos.getSymbol() == 'e' || pos.getSymbol() == 'E') return;
	this.player.setPosition(x,y);
  }

  public void update(){
    int[][] obs = new int[obstacles.size()][obstacles.size()];
    if(obstacles.size() > 0){
      for(int x = 0; x < obs[0].length; x++){
        obs[x][0] = obstacles.get(x).getY();
        obs[x][1] = obstacles.get(x).getX();
      }
    }


    for(Enemy enemy: enemies){
      enemy.setPath(AStar.test(0, Rogue.row + 1, Rogue.col + 1, enemy.getY(), enemy.getX(), player.getY(), player.getX(), obs));
      enemy.step();
    }
  }

  public void update(double delta){
  	for(Entity en: entities){
  		en.update(delta);
    }
  }

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




}
