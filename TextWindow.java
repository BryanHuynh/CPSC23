import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.io.FileReader;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Document;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleContext;
import java.awt.Color;
import javax.swing.text.StyleConstants;
import javax.swing.text.AttributeSet;
import javax.swing.JScrollPane;
import javax.swing.text.SimpleAttributeSet;
/**
 * window that will render the game for both gui and text-based
 */
public class TextWindow extends JFrame implements KeyListener{
  private Entity[][] entityMap;
  private Rogue rogue;
  private int height, width;
  private JTextPane tPane = new JTextPane();

  /**
   *
   * @param heights
   * @param widths
   * @param rogue
   */
  public TextWindow(int heights, int widths, Rogue rogue){
    super();
    this.rogue = rogue;
    this.height = heights;
    this.width = widths;
    entityMap = new Entity[height][width];
    this.setheight(heights);
    this.setwidth(widths);
    updateEntityMap();
  }



  /**
   * initiates the frames required to used the gui
   */
  public void initGUI(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tPane.setFont(getProggyFont());
    this.addKeyListener(this);
    tPane.addKeyListener(this);
    this.add(tPane);

    this.pack();
    this.setVisible(true);
  }



  public void keyPressed(KeyEvent e) {}



  public void keyReleased(KeyEvent e) {
    EntityManager em = rogue.getEm();
      if(e.getKeyCode()== KeyEvent.VK_D)
        em.movePlayer(1,0, this.getCharacterMap());
      else if(e.getKeyCode()== KeyEvent.VK_A)
        em.movePlayer(-1,0, this.getCharacterMap());
      else if(e.getKeyCode()== KeyEvent.VK_S)
        em.movePlayer(0,+1, this.getCharacterMap());
      else if(e.getKeyCode()== KeyEvent.VK_W)
        em.movePlayer(0,-1, this.getCharacterMap());
      //System.out.println(this.player.toString());
      em.update(this.getCharacterMap());
      System.out.println(em.getPlayer().toString());
  }
  public void keyTyped(KeyEvent e) {

  }



  public void update(){
    updateEntityMap();
  }


  /**
   * refreshes the entity map. has a priority system.
   * player>enemy>npc>obstacles
   */
  private void updateEntityMap(){
    entityMap = new Entity[height][width];

    EntityManager em = rogue.getEm();


    for(NPC n: em.getNpcs()){
      entityMap[n.getY()][n.getX()] = n;
    }

    for(Enemy en: em.getEnemies()){
      entityMap[en.getY()][en.getX()] = en;
    }


    for(Obstacle ob: em.getObstacles()){
      entityMap[ob.getY()][ob.getX()] = ob;
    }
    entityMap[em.getPlayer().getY()][em.getPlayer().getX()] = em.getPlayer();
  }

  /**
   * get a map from the entitiyMap but only the symbols expressed for each entity
   * @return a 2d array of entity symbols that are currently on the EntityMap
   */
  public char[][] getCharacterMap(){
    char[][] characterMap = new char[height][width];

    for(int j= 0; j < characterMap.length; j++){
      for(int i= 0; i < characterMap[0].length; i++){
        characterMap[j][i] = '.';
      }
    }

    EntityManager em = rogue.getEm();

    for(NPC n: em.getNpcs()){
      characterMap[n.getY()][n.getX()] = n.getSymbol();
    }

    for(Obstacle ob: em.getObstacles()){
      characterMap[ob.getY()][ob.getX()] = ob.getSymbol();
    }

    for(Enemy en: em.getEnemies()){
      characterMap[en.getY()][en.getX()] = en.getSymbol();
    }

    return characterMap;
  }


  /**
   * prints the entityMap to the console. null spaces are filled with '.'s
   */
  public void printToConsole(){
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        if(entityMap[y][x] == null){
          System.out.printf("%-2s ",' ');
        }else {
          System.out.printf("%-2s ", String.valueOf(entityMap[y][x].getSymbol()));
        }
        }
      System.out.println();
    }
  }

  /**
   * gets the symbol of the entity at a current location
   * @param x
   * @param y
   * @return
   */
  public Character getCharacter(int x, int y){
    char[][] characterMap = getCharacterMap();
    try {
      return characterMap[y][x];
    }catch(Exception e){
      return null;
    }
  }

  /**
   * gets the font that will be used for the gui.
   * This font is specifially monospaced, making all text line up perfectly
   *
   * @return
   */
  public Font getProggyFont(){
    Font f = new Font("Monospace", Font.LAYOUT_NO_START_CONTEXT, 0);
    try{
      f = Font.createFont(Font.TRUETYPE_FONT,
              new FileInputStream(new File("ProggyClean.ttf"))).deriveFont(Font.PLAIN, 25);
    }catch(Exception e){
      System.out.println(e);

    }
    return f;
  }

  /**
   * clears the console for the text version
   */
  public void clearConsole(){
    System.out.print("\033[H\033[2J");
  }

  /**
   * exclusively used for the GUI version.
   * renders the entityMap to the screen
   */
  public void render(){
    tPane.setText("");
    for(int y = 0; y < width - 1; y++){
      String str = "";
      for(int x = 0; x < height - 1; x++) {
        if (entityMap[y][x] == null) {
          appendToPane(tPane, str, Color.green);
          appendToPane(tPane, " .", Color.black);
          str = "";
        } else if(entityMap[y][x] instanceof Player){
          appendToPane(tPane, str, Color.green);
          appendToPane(tPane," "+String.valueOf(entityMap[y][x].getSymbol()), Color.magenta);
          str = "";
        }else if(entityMap[y][x] instanceof Enemy){
          appendToPane(tPane, str, Color.green);
          appendToPane(tPane," "+String.valueOf(entityMap[y][x].getSymbol()), Color.red);
          str = "";
        }else {
          if(str == ""){
            str += " "+String.valueOf(entityMap[y][x].getSymbol());
          }else if(entityMap[y][x].getSymbol() == str.charAt(0)){
            str += " "+String.valueOf(entityMap[y][x].getSymbol());
          }else{
            appendToPane(tPane, str, Color.green);
            str = " "+String.valueOf(entityMap[y][x].getSymbol());
          }
        }
      }
      appendToPane(tPane, str, Color.green);
      str = "";
      append("\n");
    }
  }

  public void append(String s) {
   try {
      Document doc = tPane.getDocument();
      doc.insertString(doc.getLength(), s, null);
   } catch(Exception exc) {
      exc.printStackTrace();
   }
}

private void appendToPane(JTextPane tp, String msg, Color c){
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

    aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Monospaced");
    aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

    int len = tp.getDocument().getLength();
    tp.setCaretPosition(len);
    tp.setCharacterAttributes(aset, false);
    tp.replaceSelection(msg);
}

  public Entity[][] getEntityMap() {
    return entityMap;
  }

  public void setEntityMap(Entity[][] entityMap) {
    this.entityMap = entityMap;
  }

  public Rogue getRogue() {
    return rogue;
  }

  public void setRogue(Rogue rogue) {
    this.rogue = rogue;
  }

  public int getheight() {
    return height;
  }

  public void setheight(int height) {
    this.height = height;
  }

  public int getwidth() {
    return width;
  }

  public void setwidth(int width) {
    this.width = width;
  }


}
