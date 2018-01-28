import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
public class TextWindow extends JTextArea{
  char[][] characterWindow;
  int row, col;

  int pX, pY = 0;
  public TextWindow(int rows, int cols){

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
    System.out.println("text window created");
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

  public void render(){
    this.selectAll();
    this.replaceSelection("");
    this.addCharacter(pX,pY);
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
