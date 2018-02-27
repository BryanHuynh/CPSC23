import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Rogue{
  private TextWindow textArea;
  private EntityManager em = new EntityManager();
  private Player player;
  private DialogBox db;
  private NPC c;
  private Scanner scanner;
  private boolean textVersion;
  private static int height, width;
  private MapManager mm;


public Rogue(){

  init();
  scanner = new Scanner(System.in);
  System.out.println("play the text [yes/no]");

  if(scanner.hasNext()){
    String in = scanner.nextLine();
    if(in.equalsIgnoreCase("yes")){
      textVersion = true;
    }else if(in.equalsIgnoreCase("no")){
      textArea.initGUI();
      textVersion = false;
    }
  }
  if(textVersion){
    textVersionLoop();
  }else{
    runGameLoop();
  }
}

public void randomlyPlacePlayer(){
  ArrayList<Point> free = new ArrayList<>();
  for(int j = 1; j < height - 1; j ++){
    for(int i = 1; i < width - 1; i++){
      if(mm.getMap()[j][i] == ' '){
        free.add(new Point(j,i));
      }
    }
  }
  Random randomizer = new Random();
  Point freept = free.get(randomizer.nextInt(free.size()));
  player = em.createPlayer(freept.getX(), freept.getY());
}
public void randomlyPlaceEnemy(){
  ArrayList<Point> free = new ArrayList<>();
  for(int j = 1; j < height - 1; j ++){
    for(int i = 1; i < width - 1; i++){
      if(mm.getMap()[j][i] == ' '){
        free.add(new Point(j,i));
      }
    }
  }
  Random randomizer = new Random();
  Point freept = free.get(randomizer.nextInt(free.size()));
  em.createEnemy(freept.getX(), freept.getY(),'e');
}
  /**
   * game loop used for the text version of the game
   */
  public void textVersionLoop(){
  textArea.clearConsole();
  textArea.update();
  textArea.printToConsole();
  db.renderToConsole();
  System.out.println();

  while(textVersion){
    System.out.print("W moves forward           A moves Left            Q-quit \n");
    System.out.print("S moves Down              D moves Right");
    if(scanner.hasNext()){
      textPlayerControl();
      textArea.clearConsole();
      textArea.update();
      playerTalk();
      em.update(textArea.getCharacterMap());
      textArea.printToConsole();
      db.renderToConsole();
      System.out.println();
    }
  }
}

  /**
   * a temporary system to test out the dialog system mixed with the player proximate, [TO BE REMOVED]
   */
  public void playerTalk(){
    if(em.getDistanceBetweenEntities(em.getPlayer(), c) == 1){
      db.setStr("This NPC is talking to you because you are only a block away");
    }else{
      db.setStr("There is a theory which states that if ever anyone discovers exactly " +
              "what the Universe is for and why it is here, it will instantly disappear " +
              "and be replaced by something even more bizarre and inexplicable. There is " +
              "another theory which states that this has already happened.");
    }
  }

  /**
   * recieves input from the console and moves the player by an increment
   */
  public void textPlayerControl(){
    String input = scanner.nextLine();

    if(input.equals("w")){
      em.movePlayer(0,-1, textArea.getCharacterMap());
    }else if(input.equals("a")){
      em.movePlayer(-1,0, textArea.getCharacterMap());
    }else if(input.equals("s")){
      em.movePlayer(0,+1, textArea.getCharacterMap());
    }else if(input.equals("d")){
      em.movePlayer(1,0, textArea.getCharacterMap());
    }else if(input.equals("q")){
      textVersion = false;
    }
    System.out.println(em.getPlayer().toString());
  }


  /**
   * system to start the gui game loop
   */
  public void runGameLoop() {
    Thread loop = new Thread() {
      public void run(){
        gameLoop();
      }
    };
    loop.start();
  }


  /**
   * gameloop for the gui
   */
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




  /**
   * starts basic things required to run the game, console or gui
   */
  public void init(){

    mm = new MapManager(this);

    Rogue.height = mm.getMapLength();
    Rogue.width = mm.getMapHeight();
    mm.createMapEntities();
    //randomlyPlacePlayer();
    //randomlyPlaceEnemy();
    textArea = new TextWindow(height + 1, width + 1, this);

    db = new DialogBox(textArea);
    //generateheightOfObstacles();
    c = em.createNPC(5,4,'c');
    Enemy e = em.createEnemy(24,24,'e');

  }


  /**
   * rendering the game
   */
  public void render(){
    textArea.render();
    db.render();
  }

  double totalTime = 0.0;


  /**
   * this update method is used exclusively for the GUI
   * @param delta
   */
  public void update(double delta){
    textArea.update();
    totalTime += delta/1000000000;
    em.update(delta);
    playerTalk();
  }





  public TextWindow getTextArea() {
    return textArea;
  }

  public void setTextArea(TextWindow textArea) {
    this.textArea = textArea;
  }

  public EntityManager getEm() {
    return em;
  }

  public void setEm(EntityManager em) {
    this.em = em;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public DialogBox getDb() {
    return db;
  }

  public void setDb(DialogBox db) {
    this.db = db;
  }

  public NPC getC() {
    return c;
  }

  public void setC(NPC c) {
    this.c = c;
  }

  public Scanner getScanner() {
    return scanner;
  }

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

  public boolean isTextVersion() {
    return textVersion;
  }

  public void setTextVersion(boolean textVersion) {
    this.textVersion = textVersion;
  }

  public static int getheight() {
    return height;
  }

  public static void setheight(int height) {
    Rogue.height = height;
  }

  public static int getwidth() {
    return width;
  }

  public static void setwidth(int width) {
    Rogue.width = width;
  }

  public MapManager getMm() {
    return mm;
  }

  public void setMm(MapManager mm) {
    this.mm = mm;
  }

  public double getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(double totalTime) {
    this.totalTime = totalTime;
  }

}
