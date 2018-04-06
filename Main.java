import java.util.Scanner;

/**
 * starts Rogue
 */
public class Main{

  public static void main(String[] args){
    boolean text = getVersionFromUser();
    if(text){

      RogueText rogue = new RogueText(50,50,10,30,30);

      rogue.textVersionInit();
      rogue.gameScreen = true;
      rogue.textVersionLoop();
    }else{

      RogueGUI rogue = new RogueGUI(50,50,10,30,30);
      rogue.runGameLoop();
    }
  }



  public static boolean getVersionFromUser() {
    boolean textVersion = false;
    Scanner scanner = new Scanner(System.in);
    System.out.println("play the text [yes/no]");

    if (scanner.hasNext()) {
      String in = scanner.nextLine();
      if (in.equalsIgnoreCase("yes")) {
        textVersion = true;

      } else if (in.equalsIgnoreCase("no")) {
        textVersion = false;
      }
    }
    return textVersion;
  }




}
