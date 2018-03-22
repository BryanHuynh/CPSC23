public class Player extends EntityCharacter{

  int hp = 100;
  int atk = 50;
  public Player(int x, int y, char symbol){
    super(x ,y , symbol);
  }
  double alternateXTime = 0.0;

  public void update(double delta){
    super.update(delta);
    alternateXTime += delta/1000;

    if(alternateXTime >= 0.2) {
      alternateXTime = 0;
      //System.out.println("SWITCH");
      if (Character.isLowerCase(this.getSymbol())) {
        this.setSymbol(Character.toUpperCase(this.getSymbol()));
      } else {
        this.setSymbol(Character.toLowerCase(this.getSymbol()));
      }
    }
  }



}
