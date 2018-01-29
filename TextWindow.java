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
  char[][] characterWindow;
  int row, col;

  int pX, pY = 0;
  public TextWindow(int rows, int cols){
    super();
    this.row = rows;
    this.col = cols;
    characterWindow = new char[col][row];
    for(int x = 0; x < cols; x++){
      for(int y = 0; y < row; y++){
        characterWindow[x][y] = '.';
      }
    }
    System.out.println(Arrays.deepToString(characterWindow).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    this.setRows(rows);
    this.setColumns(cols);


  }

  public TextWindow(String map){
    this.loadMaptxt();
  }

  public void addCharacter(int x, int y){
    this.clearScreen();
    characterWindow[y][x] = 'x';
  }




  public void clearScreen(){
    for(int x = 0; x < col; x++){
      for(int y = 0; y < row; y++){
        characterWindow[x][y] = '.';
      }
    }
  }

  public void update(){

  }

  public char getCharacter(int x, int y){
    try {
      return characterWindow[y][x];
    }catch(Exception e){
      return '^';
    }
  }

  public void render(Rogue rogue){
    this.selectAll();
    this.replaceSelection("");
    this.addCharacter(rogue.player.x, rogue.player.y);
    for(int x = 0; x < col; x++){
      String str = "";
      for(int y = 0; y < row; y++){
        str += " " + String.valueOf(characterWindow[x][y]);
      }
      this.append(str);
      this.append("\n");
    }


  }
}
