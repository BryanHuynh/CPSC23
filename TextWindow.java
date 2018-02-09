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

/**
 * window that will render the game for both gui and text-based
 */
public class TextWindow extends JTextArea{
  private Entity[][] entityMap;
  private Rogue rogue;
  private int row, col;

  /**
   *
   * @param rows
   * @param cols
   * @param rogue
   */
  public TextWindow(int rows, int cols, Rogue rogue){
    super();
    this.rogue = rogue;
    this.row = rows;
    this.col = cols;
    entityMap = new Entity[col][row];
    this.setRows(rows);
    this.setColumns(cols);
    updateEntityMap();
  }


  public void update(){
    updateEntityMap();
  }


  /**
   * refreshes the entity map. has a priority system.
   * player>enemy>npc>obstacles
   */
  private void updateEntityMap(){
    entityMap = new Entity[entityMap.length][entityMap[0].length];

    EntityManager em = rogue.getEm();

    for(Obstacle ob: em.getObstacles()){
      entityMap[ob.getY()][ob.getX()] = ob;
    }

    for(NPC n: em.getNpcs()){
      entityMap[n.getY()][n.getX()] = n;
    }

    for(Enemy en: em.getEnemies()){
      entityMap[en.getY()][en.getX()] = en;
    }

    entityMap[rogue.getPlayer().getY()][rogue.getPlayer().getX()] = rogue.getPlayer();
  }

  /**
   * get a map from the entitiyMap but only the symbols expressed for each entity
   * @return a 2d array of entity symbols that are currently on the EntityMap
   */
  public char[][] getCharacterMap(){
    char[][] characterMap = new char[entityMap.length][entityMap[0].length];

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
    for(int y = 0; y < col; y++){
      for(int x = 0; x < row; x++){
        if(entityMap[y][x] == null){
          System.out.printf("%-2s ",'.');
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
    Font f = new Font("Monospace", Font.LAYOUT_NO_START_CONTEXT, 40);
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
    this.setText("");
    for(int y = 0; y < col; y++){
      String str = "";
      for(int x = 0; x < row; x++) {
        if (entityMap[y][x] == null) {
          str += " " + '.';
        } else {
          str += " " + String.valueOf(entityMap[y][x].getSymbol());
        }
      }
      this.append(str);
      this.append("\n");
    }
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

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }


}
