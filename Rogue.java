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


  private int fps = 60;
  private int frameCount = 0;

  private void gameLoop()
  {
    //This value would probably be stored elsewhere.
    final double GAME_HERTZ = 30.0;
    //Calculate how many ns each frame should take for our target game hertz.
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    //At the very most we will update the game this many times before a new render.
    //If you're worried about visual hitches more than perfect timing, set this to 1.
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    //We will need the last update time.
    double lastUpdateTime = System.nanoTime();
    //Store the last time we rendered.
    double lastRenderTime = System.nanoTime();

    //If we are able to get as high as this FPS, don't render again.
    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

    //Simple way of finding FPS.
    int lastSecondTime = (int) (lastUpdateTime / 1000000000);

    while (true)
    {
      double now = System.nanoTime();
      int updateCount = 0;

      if (true) {
        //Do as many game updates as we need to, potentially playing catchup.
        while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
        {
          update(TIME_BETWEEN_UPDATES);
          lastUpdateTime += TIME_BETWEEN_UPDATES;
          updateCount++;
        }

        //If for some reason an update takes forever, we don't want to do an insane number of catchups.
        //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
        if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
        {
          lastUpdateTime = now - TIME_BETWEEN_UPDATES;
        }

        //Render. To do so, we need to calculate interpolation for a smooth render.
        float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
        render();
        lastRenderTime = now;

        //Update the frames we got.
        int thisSecond = (int) (lastUpdateTime / 1000000000);
        if (thisSecond > lastSecondTime)
        {
          System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
          fps = frameCount;
          frameCount = 0;
          lastSecondTime = thisSecond;
        }

        //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
        while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
        {
          Thread.yield();

          //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
          //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
          //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
          try {Thread.sleep(1);} catch(Exception e) {}

          now = System.nanoTime();
        }
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
