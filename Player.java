public class Player extends Entity{

  public Player(int x, int y, char symbol){
    super(x ,y , symbol);
  }
  double alternateXTime = 0.0;

  public void update(double delta){
    super.update(delta);
    alternateXTime += delta/100;

    if(alternateXTime >= 0.2) {
      alternateXTime = 0;
      //System.out.println("SWITCH");
      if (this.getSymbol() == 'x') {
        this.setSymbol('X');
      } else {
        this.setSymbol('x');
      }
    }
  }



}
