import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Runtime;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
public class Rogue extends JFrame implements KeyListener{
  public TextWindow textArea;
  public EntityManager em = new EntityManager();
  public Player player;
  public DialogBox db;
  public NPC c;
  public Scanner scanner;
  public boolean textVersion;
  public static int row, col;



public Rogue(){
  player = em.createPlayer(0,0);
  init();
  scanner = new Scanner(System.in);
  System.out.print("play the text[yes/no]");
  if(scanner.hasNext()){
    String in = scanner.nextLine();
    if(in.equalsIgnoreCase("yes")){
      textVersion = true;
    }else if(in.equalsIgnoreCase("no")){
      initGUI();
      textVersion = false;
    }
  }
  if(textVersion){
    textVersionLoop();
  }else{
    runGameLoop();
  }
}

public void textVersionLoop(){
  textArea.clearConsole();
  textArea.update();
  textArea.printToConsole();
  db.renderToConsole();
  System.out.println();

  while(textVersion){
    if(scanner.hasNext()){
      String input = scanner.nextLine();

      if(input.equals("w")){
        em.movePlayer(0,-1, textArea);
      }else if(input.equals("a")){
        em.movePlayer(-1,0, textArea);
      }else if(input.equals("s")){
        em.movePlayer(0,+1, textArea);
      }else if(input.equals("d")){
        em.movePlayer(1,0, textArea);
      }
      textArea.clearConsole();

      textArea.update();

      if(em.getDistanceBetweenEntities(player, c) == 1){
        db.str = ("wanna buy some drugs kid?");
      }else{
        db.str = ("These fonts will be familiar to many programmers," +
                " as they’re commonly used for coding. Programming fonts are a " +
                "natural place to look for good roguelike fonts, since they’re both monospaced " +
                "and designed to facilitate character recognition.");
      }

      em.update();
      textArea.printToConsole();
      db.renderToConsole();
      System.out.println();
    }
  }
}




  public void runGameLoop() {
    Thread loop = new Thread() {
      public void run(){
        gameLoop();
      }
    };
    loop.start();
  }

  private void gameLoop() {
    long now = System.currentTimeMillis();
    long delta = 0;
    long totalTime = 0;
    while(true){
      delta = System.currentTimeMillis() - now;
      now = System.currentTimeMillis();
      totalTime += delta;
      //System.out.println((double) totalTime/1000);
      update(delta);
      render();
      try {
        Thread.sleep(15);
      }catch(Exception e){
      }
     }
  }


  public void initGUI(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Font font = this.getProggyFont();
    textArea.setFont(font);
    this.addKeyListener(this);
    textArea.addKeyListener(this);
    this.add(textArea);
    this.pack();
    this.setVisible(true);
  }

  public void init(){
    char[][] map = mapLoad();
    //map = transposeMap(map);
    Rogue.row = map.length;
    Rogue.col = map[0].length;

    textArea = new TextWindow(row + 1, col + 1, this);
    mapToEntity(map);
    db = new DialogBox(textArea);
    //generateRowOfObstacles();
    c = em.createNPC(5,4,'c');
    Enemy e = em.createEnemy(24,24,'e');
  }

  public void mapToEntity(char[][] map){
    Entity[][] entityMap = new Entity[map.length][map[0].length];
    for(int j = 0; j < map.length; j++ ){
      for(int i = 0; i < map[0].length; i++){
        if(map[j][i] =='#'){
          entityMap[j][i] = em.createObstacles(i,j,'#');
        }else if(map[j][i] == '.'){
          entityMap[j][i] = em.createEmptySpace(i,j);
        }
      }
    }
  }

  public char[][] transposeMap(char[][] map){
    char[][] nmap = new char[map[0].length][map.length];

    for(int j = 0; j < map.length; j++ ){
      for(int i = 0; i < map[0].length; i++){
        nmap[i][j] = map[j][i];
      }
    }
    return nmap;
  }


  public char[][] mapLoad(){
    File inFile = new File("Map.txt");
    try{
      scanner = new Scanner(inFile);
    }catch(IOException e){

    }


    String[] size = scanner.nextLine().split("\\s");

    Rogue.row = Integer.parseInt(size[0]);
    Rogue.col = Integer.parseInt(size[1]);

    char[][] array = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
    for(int i=0; i < Integer.parseInt(size[0]); i++) {
        array[i] = scanner.nextLine().toCharArray();
    }
    return array;
  }


  public Font getProggyFont(){
    Font f = new Font("Monospace", Font.LAYOUT_NO_START_CONTEXT, 40);
    try{
      f = Font.createFont(Font.TRUETYPE_FONT,
              new FileInputStream(new File("etc\\ProggyClean.ttf"))).deriveFont(Font.PLAIN, 40);
    }catch(Exception e){
      System.out.println(e);

    }
    return f;
  }

  public void generateRowOfObstacles(){
    for(int x = 0; x < 20; x++){
      if(x == 5 || x == 10) continue;
      em.createObstacles(x,5, '#');
    }
  }


  public void render(){
    textArea.render();
    db.render();
  }

  double totalTime = 0.0;

  public void update(double delta){
    textArea.update();
    totalTime += delta/1000000000;
    em.update(delta);
    if(em.getDistanceBetweenEntities(player, c) == 1){
      db.str = ("wanna buy some drugs kid?");
    }else{
      db.str = ("These fonts will be familiar to many programmers," +
              " as they’re commonly used for coding. Programming fonts are a " +
              "natural place to look for good roguelike fonts, since they’re both monospaced " +
              "and designed to facilitate character recognition.");
    }

  }

  public void keyPressed(KeyEvent e) {}



  public void keyReleased(KeyEvent e) {
      if(e.getKeyCode()== KeyEvent.VK_D)
        em.movePlayer(1,0, textArea);
      else if(e.getKeyCode()== KeyEvent.VK_A)
        em.movePlayer(-1,0, textArea);
      else if(e.getKeyCode()== KeyEvent.VK_S)
        em.movePlayer(0,+1, textArea);
      else if(e.getKeyCode()== KeyEvent.VK_W)
        em.movePlayer(0,-1, textArea);
      //System.out.println(this.player.toString());
      em.update();
  }
  public void keyTyped(KeyEvent e) {

  }

}
