import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;


public class Rogue extends JFrame implements KeyListener{
  public TextWindow textArea;
  public EntityManager em = new EntityManager();
  public Player player;
  public DialogBox db;
  public NPC c;



  public Rogue(){
    player = em.createPlayer(0,0);
    init();
    runGameLoop();
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
      System.out.println((double) totalTime/1000);
      update(delta);
      render();
      try {
        Thread.sleep(10);
      }catch(Exception e){

      }
     }
  }


  public void init(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textArea = new TextWindow(15,25, this);
    Font font = this.getProggyFont();
    textArea.setFont(font);
    this.addKeyListener(this);
    textArea.addKeyListener(this);
    this.add(textArea);
    this.pack();
    this.setVisible(true);

    db = new DialogBox(textArea);
    generateRowOfObstacles();
    c = em.createNPC(5,4,'c');
  }

  public Font getProggyFont(){
    Font f = new Font("Monospace", Font.LAYOUT_NO_START_CONTEXT, 20);
    try{
      f = Font.createFont(Font.TRUETYPE_FONT,
              new FileInputStream(new File("etc\\ProggyClean.ttf"))).deriveFont(Font.PLAIN, 27);
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
    System.out.println(totalTime);
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

      System.out.println(this.player.toString());

  }
  public void keyTyped(KeyEvent e) {

  }

}
