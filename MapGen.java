
import java.util.Random;
import java.util.ArrayList;

public class MapGen{
  private ArrayList<Room> rooms = new ArrayList<Room>();
  public char[][] map;                //location that map will be stored
  public final int LENGTH = 50;       //pre-determined LENGTH
  public final int HEIGHT = 50;       //pre-determined HEIGHT

  MapGen(){
    map  = new char[HEIGHT][LENGTH];
    fillMap(',');                     // fill the map with empty space
    for(int i = 0; i < 20; i++){      // creating n rooms
          createRoomRandom(5,7);
    }
    printMap();
  }

  /**
  *creates random rooms on the map
  *@param height height of the rooms
  #@param length length of the room
  */
  public void createRoomRandom(int height, int length){
    Random rnd = new Random();

    int x = rnd.nextInt((LENGTH-length-0) + 0);       //generate room length between 0 and length of map - room length
    int y = rnd.nextInt((HEIGHT-height-0) + 0);       //generate room height between 0 and the height of map - height of room
    rooms.add(new Room(height, length, x, y));
    generateRooms();
    createDoors();
  }

  public void createDoors(){
    Random rnd = new Random();
    for(int j = 0; j < HEIGHT; j++){
      for(int i = 0; i < LENGTH; i++){
        if(map[j][i] == '#'){

        }
      }
    }
  }


  private ArrayList<Point> doors = new ArrayList<Point>();

  public void generateRooms(){
    for(Room room: rooms){
      int height = room.getHeight();
      int length = room.getLength();
      int x = room.getX();
      int y = room.getY();

      for(int j = 0; j < height; j++){        //create room of length and height
        for(int i = 0; i < length; i++){
          if(map[j + y][i + x] == ' '){
            map[j + y][i + x] = ' ';
          }else{
            map[j + y][i + x] = '#';
          }
          generateDoor(height, length, x, y);
        }
      }
      for(int j = 1; j < height - 1; j++){    // remove the space inside
        for(int i =1; i < length - 1; i++){
          map[j + y][i + x] = ' ';
        }
      }
    }
  }

public void generateDoor(int height, int length, int x, int y){
  doors.add(new Point((length/2) + x, y + height - 1));
  map[y + height - 1][(length/2) + x] = ' ';

  doors.add(new Point((length/2) + x, y));
  map[y][(length/2) + x] = ' ';

  doors.add(new Point(x, (height / 2) + y));
  map[(height / 2) + y][x] = ' ';

  doors.add(new Point((height / 2) + y,x + length - 1));
  map[(height / 2) + y][x + length - 1] = ' ';
}

/**
*fills the map generator's char[][] map with a certain character
*@param ch the character to fill the map with
*/
  public void fillMap(char ch){
    for(int j = 0; j < map.length; j++){
      for(int i =0; i < map[0].length; i++){
        map[j][i] = ch;
      }
    }
  }

/**
*prints map to console
*/
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
