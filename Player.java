public class Player extends EntityCharacter{


  public Player(int x, int y, char symbol){
    super(x ,y , symbol);
  }
  double alternateXTime = 0.0;


  public Player clone(){
    Player clone = new Player(x,y, symbol);
    clone.setAccuracy(accuracy);
    clone.setHp(hp);
    clone.setAtk(atk);
    clone.setBlocking(blocking);
    clone.setName(name);
    clone.setVisable(isVisable());
    return clone;
  }

  public void update(double delta){
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
