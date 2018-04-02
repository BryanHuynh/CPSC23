

public class Point{

	private int x,y;
  /**
  * a class that stores x,y integers
  *@param x
  *@param y
  */
  Point(int x, int y){
    this.x = x;
    this.y = y;
  }


  public double getDistance(Point point){
  	double dx = Math.pow(point.getX() - x, 2);
	double dy = Math.pow(point.getY() - y, 2);
  	return Math.sqrt(dx + dy);
  }

  public Point clone(){
  	return new Point(x,y);
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
	* @param x
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
	* @param y
	*/
	public void setY(int y) {
		this.y = y;
	}

	public String toString(){
		return "[" +x + ", " + y +"]";
	}
}
