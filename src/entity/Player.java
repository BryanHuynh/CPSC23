package entity;

import java.io.Serializable;

public class Player extends EntityCharacter implements Serializable{


  /**
   * entity character that the user interacts with
   * player is capable of starting combat
   * moving around
   * linking up wth of npc's
   * @param x
   * @param y
   * @param symbol
   */
  public Player(int x, int y, char symbol){
    super(x ,y , symbol);
    setName("Player");
  }

  public Player(){

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
