public class Entity{

  private int x, y;
  private char symbol = ' ';


  public Entity(int x, int y, char symbol){
    this.x = x;
    this.y = y;
    this.symbol = symbol;
  }


  public void moveX(int x){
    this.x = x;
  }

  public void moveY(int y){
    this.y = y;
  }

  @Override
  public String toString(){
    return "x:" + x + " y:" + y + " symbol:" + symbol;
  }


  public void setPosition(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void update(){

  }

	/**
	* Returns value of x
	* @return
	*/
	public int getX() {
		return this.x;
	}

	/**
	* Sets new value of x
	* @param
	*/
	public void setX(int x) {
		this.x = x;
	}

	/**
	* Returns value of y
	* @return
	*/
	public int getY() {
		return this.y;
	}

	/**
	* Sets new value of y
	* @param
	*/
	public void setY(int y) {
		this.y = y;
	}

	/**
	* Returns value of symbol
	* @return
	*/
	public char getSymbol() {
		return this.symbol;
	}

	/**
	* Sets new value of symbol
	* @param
	*/
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}


}
