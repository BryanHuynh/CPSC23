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
    fillWithEmptySpace();
    for(NPC n: rogue.em.npcs){
      entityMap[n.getY()][n.getX()] = n;
    }

    for(Obstacle ob: rogue.em.obstacles){
      entityMap[ob.getY()][ob.getX()] = ob;
    }

    for(Enemy en: rogue.em.enemies){
      entityMap[en.getY()][en.getX()] = en;
    }

    entityMap[rogue.player.getY()][rogue.player.getX()] = rogue.player;
    System.out.println();
    printToConsole();
  }



public void printToConsole(){
  for(int y = 0; y < col; y++){
    for(int x = 0; x < row; x++){
      System.out.printf("%-2s ",String.valueOf(entityMap[y][x].getSymbol()));
    }
    System.out.println();
  }
}

  public Entity getCharacter(int x, int y){
    try {
      return entityMap[x][y];
    }catch(Exception e){
      return null;
    }
  }



  public void clearConsole(){

    for (int i = 0; i < 100; ++i) System.out.println();
  }

  public void render(){
    this.setText("");
    for(int y = 0; y < col; y++){
      String str = "";
      for(int x = 0; x < row; x++){
        str += " " + String.valueOf(entityMap[y][x].getSymbol());
      }
      this.append(str);
      this.append("\n");
    }
  }



}
