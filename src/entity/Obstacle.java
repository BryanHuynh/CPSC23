package entity;

/**
 * non hostile entities, that provide obstruction
 */
public class Obstacle extends Entity {



	@Override
	public void update(double delta) {

	}

	/**
	 * entity that entitycharacters cannot pass through
	 * @param x
	 * @param y
	 * @param symbol
	 */
	public Obstacle(int x, int y, char symbol){
		super(x, y , symbol);
	}
}
