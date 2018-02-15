
public class Room{
  private int height, length, x, y;

  public Room(int height, int length, int x, int y){
    this.height = height;
    this.length = length;
    this.x = x;
    this.y = y;
  }


	/**
	* Returns value of height
	* @return
	*/
	public int getHeight() {
		return this.height;
	}

	/**
	* Returns value of length
	* @return
	*/
	public int getLength() {
		return this.length;
	}

	/**
	* Returns value of x
	* @return
	*/
	public int getX() {
		return this.x;
	}

	/**
	* Returns value of y
	* @return
	*/
	public int getY() {
		return this.y;
	}

	/**
	* Sets new value of height
	* @param
	*/
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	* Sets new value of length
	* @param
	*/
	public void setLength(int length) {
		this.length = length;
	}

	/**
	* Sets new value of x
	* @param
	*/
	public void setX(int x) {
		this.x = x;
	}

	/**
	* Sets new value of y
	* @param
	*/
	public void setY(int y) {
		this.y = y;
	}

}
