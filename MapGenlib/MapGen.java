package MapGenlib;

import java.util.Random;
public class MapGen{

  public char[][] map;
  public final int LENGTH = 50;
  public final int HEIGHT = 50;

  MapGen(){
    map  = new char[HEIGHT][LENGTH];
    fillMap(',');
    for(int i = 0; i < 20; i++){
          createRoomRandom(5,7);
    }


    printMap();
  }

  public void createRoomRandom(int height, int length){
    int seed = 238233;
    Random rnd = new Random();
    int x = rnd.nextInt((LENGTH-length-0) + 0);
    System.out.println(x);
    int y = rnd.nextInt((HEIGHT-height-0) + 0);
    System.out.println(y);

    for(int j = 0; j < height; j++){
      for(int i =0; i < length; i++){
        map[j + y][i + x] = '#';
      }
    }

    for(int j = 1; j < height - 1; j++){
      for(int i =1; i < length - 1; i++){
        map[j + y][i + x] = ' ';
      }
    }
  }
  public void fillMap(char ch){
    for(int j = 0; j < map.length; j++){
      for(int i =0; i < map[0].length; i++){
        map[j][i] = ch;
      }
    }
  }

  public void printMap(){
    for(int j = 0; j < map.length; j++){
      for(int i =0; i < map[0].length; i++){
        System.out.printf("%-2s ",map[j][i]);
      }
      System.out.println();
    }
  }




  public static void main(String[] args){
      new MapGen();
  }
}
