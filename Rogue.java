import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Rogue extends JFrame implements KeyListener{
  private TextWindow textArea;
  public EntityManager em = new EntityManager();
  public Player player;



  public Rogue(){
    player = em.createPlayer(0,0);
    init();
    render();
    //loop();
  }

  public void init(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textArea = new TextWindow(20,5, this);
    textArea.setFont(new Font("Arial", Font.BOLD, 20));
    this.addKeyListener(this);
    textArea.addKeyListener(this);
    this.add(textArea);
    this.pack();
    this.setVisible(true);
  }


  public void render(){
    textArea.render(this);
  }

  public void update(){
    textArea.update();
  }

  public void keyPressed(KeyEvent e) {}

  public void movePlayer(int dx, int dy){
    int x = this.player.getX() + dx;
    int y = this.player.getY() + dy;
    Entity pos = textArea.getCharacter(x, y);
    if(pos == null) return;
    this.player.setPosition(x,y);

  }

  public void keyReleased(KeyEvent e) {
      if(e.getKeyCode()== KeyEvent.VK_D)
        this.movePlayer(1,0);
      else if(e.getKeyCode()== KeyEvent.VK_A)
        this.movePlayer(-1,0);
      else if(e.getKeyCode()== KeyEvent.VK_S)
        this.movePlayer(0,+1);
      else if(e.getKeyCode()== KeyEvent.VK_W)
        this.movePlayer(0,-1);


      System.out.println(this.player.toString());

      this.update();
      this.render();

  }
  public void keyTyped(KeyEvent e) {

  }

}
