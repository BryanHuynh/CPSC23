import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Rogue extends JFrame implements KeyListener{
  private TextWindow textArea;

  public Rogue(){
    init();
    render();
    //loop();
  }

  public void init(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textArea = new TextWindow(10,5);
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
    textArea.render();
  }

  public void update(){

  }

  public void keyPressed(KeyEvent e) {}

  public void keyReleased(KeyEvent e) {
      if(e.getKeyCode()== KeyEvent.VK_D)
        textArea.pX += 1;
      else if(e.getKeyCode()== KeyEvent.VK_A)
        textArea.pX -= 1;
      else if(e.getKeyCode()== KeyEvent.VK_S)
        textArea.pY += 1;
      else if(e.getKeyCode()== KeyEvent.VK_W)
        textArea.pY -= 1;
      this.render();

  }
  public void keyTyped(KeyEvent e) {
      System.out.println("keyTyped");
  }

}
