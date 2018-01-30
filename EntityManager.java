import java.util.ArrayList;

public class EntityManager{

  ArrayList<Entity> entities = new ArrayList<Entity>();

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
  	return ob;
  }


  public void movePlayer(int dx, int dy, TextWindow textArea){
	int x = this.player.getX() + dx;
	int y = this.player.getY() + dy;
	Entity pos = textArea.getCharacter(x, y);
	if(pos == null) return;
	if(pos.getSymbol() == '#')  return;
	this.player.setPosition(x,y);
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
