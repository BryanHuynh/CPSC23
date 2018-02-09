import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MapManager {
    private Scanner scanner = new Scanner(System.in);
    private Rogue rogue;
    private char[][] map;

    MapManager(Rogue rogue){
        this.rogue = rogue;
        map = mapLoad();
    }


    public int getMapLength(){
        return map.length;
    }

    public int getMapHeight(){
        return map[0].length;
    }

    public void createMapEntities(){
        Entity[][] entityMap = new Entity[map.length][map[0].length];

        for(int j = 0; j < map.length ; j++ ){
            for(int i = 0; i <  map[0].length; i++){

                if(map[j][i] =='#'){
                    entityMap[j][i] = rogue.em.createObstacles(i,j,'#');
                }else if(map[j][i] == '.'){
                    entityMap[j][i] = rogue.em.createEmptySpace(i,j);

                }
            }
        }
    }


    public char[][] mapLoad(){

        File inFile = new File("src/Map.txt");
        try{
            scanner = new Scanner(inFile);
        }catch(IOException e){
            System.out.println(e);
        }
        String[] size = scanner.nextLine().split("\\s");

        Rogue.row = Integer.parseInt(size[0]);
        Rogue.col = Integer.parseInt(size[1]);

        char[][] array = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
        for(int i=0; i < Integer.parseInt(size[0]); i++) {
            array[i] = scanner.nextLine().toCharArray();
        }
        System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        return array;


    }




}
