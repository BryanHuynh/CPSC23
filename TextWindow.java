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

public class TextWindow extends JTextArea{
  Entity[][] entityMap;
  Rogue rogue;
  int row, col;

  int pX, pY = 0;
  public TextWindow(int rows, int cols, Rogue rogue){
    super();
    this.rogue = rogue;
    this.row = rows;
    this.col = cols;
    entityMap = new Entity[col][row];

    this.setRows(rows);
    this.setColumns(cols);

    fillWithEmptySpace();
    updateEntityMap();
  }


  public void fillWithEmptySpace(){
    for(int y = 0; y < row; y++){
      for(int x = 0; x < col; x++){
          EmptySpace es = rogue.em.createEmptySpace(x,y);
          entityMap[x][y] = es;
      }
    }
  }

  public void update(){
    updateEntityMap();
  }


  public void updateEntityMap(){
    for(Entity en : rogue.em.entities){
      entityMap[en.getX()][en.getY()] = en;
    }
    entityMap[rogue.player.getX()][rogue.player.getY()] = rogue.player;
  }

  public Entity getCharacter(int x, int y){
    try {
      return entityMap[x][y];
    }catch(Exception e){
      return null;
    }
  }

  public void render(Rogue rogue){
    this.selectAll();
    this.replaceSelection("");
    for(int y = 0; y < row; y++){
      String str = "";
      for(int x = 0; x < col; x++){
        str += " " + String.valueOf(entityMap[x][y].getSymbol());
      }
      this.append(str);
      this.append("\n");
    }


  }
}
