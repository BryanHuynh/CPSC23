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
