import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Rogue extends JFrame implements KeyListener{
  private TextWindow textArea;
  public Player player;

  public Rogue(){
    player = new Player();
    init();
    render();
    //loop();
  }

  public void init(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textArea = new TextWindow(16,16);
    textArea.setFont(new Font("Arial", Font.BOLD, 100));
    this.addKeyListener(this);
    textArea.addKeyListener(this);
    this.add(textArea);
    this.pack();
    this.setVisible(true);
  }

  public void loop(){
    while(true){
      this.update();
      this.render();
    }
  }
  public void render(){
    textArea.render(this);
  }

  public void update(){

  }

  public void keyPressed(KeyEvent e) {}

  public void movePlayer(int dx, int dy){
    int x = this.player.x + dx;
    int y = this.player.y + dy;
    char pos = textArea.getCharacter(x, y);
    if(pos == '^') return;
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

      this.render();

  }
  public void keyTyped(KeyEvent e) {

  }

}
