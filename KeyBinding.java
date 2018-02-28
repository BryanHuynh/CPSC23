public class KeyBinding{
  boolean combat = false;
  boolean npcTalk = false;

  public void print(){
    System.out.print("W moves forward           A moves Left            Q-quit \n");
    System.out.print("S moves Down              D moves Right \n");
    if(combat) System.out.println("K attack \n");
  }
}
