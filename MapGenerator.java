import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    char[][] map;
    private int roomsize = 10;
    private int numOfEnemies = 10;
    private int numOfNpcs = 10;

    /**
     * randomly generates a map
     */
    MapGenerator(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        map = new char[height][length];
        this.roomsize = roomsize;
        this.numOfEnemies = numOfEnemies;
        this.numOfNpcs = numOfNpcs;
        fillMap(map);
        generateRooms(map);
        removePadding(map);
        constructPath(map);
        addEntity('x', map);

        for (int numEnemies = 0; numEnemies < numOfEnemies; numEnemies++) {
            addEntity('e', map);
        }
        for (int numNPC = 0; numNPC < numOfNpcs; numNPC++) {
            addEntity('c', map);
        }
        printMap();
        System.out.println("enemies counted: " + mapCounter('e', map));
    }

    public void addEntity(char symbol, char[][] map) {
        ArrayList<Point> free = new ArrayList<Point>();
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == ' ' && map[j][i] != 'e') free.add(new Point(i, j));
            }
        }
        Random randomizer = new Random();
        Point freept = free.get(randomizer.nextInt(free.size()));
        int x = freept.getX();
        int y = freept.getY();
        map[y][x] = symbol;
    }

    public void generateRooms(char[][] map) {

        Random rnd = new Random();
        ArrayList<Point> taken = new ArrayList<>();
        for (int nrooms = 0; nrooms < 10; nrooms++) {
            //int x = rnd.nextInt((length-5-0) + 0);       //generate room length between 0 and length of map - room length
            //int y = rnd.nextInt((height-5-0) + 0);       //generate room height between 0 and the height of map - height of room
            ArrayList<Point> free = new ArrayList<>();
            for (int j = 2; j < map.length - roomsize - 1; j++) {
                for (int i = 2; i < map[0].length - roomsize - 1; i++) {
                    if (map[j][i] == '.') {
                        boolean isFree = true;
                        for (int jy = 0; jy < roomsize + 1; jy++) {
                            for (int ix = 0; ix < roomsize + 1; ix++) {
                                if (map[j + jy][i + ix] == '#') isFree = false;
                            }
                        }
                        if (isFree) free.add(new Point(i, j));
                    }
                }
            }
            if (free.size() <= 0) {
                map = new MapGenerator(map.length, map[0].length, roomsize, numOfEnemies, numOfNpcs).map;
            }
            Random randomizer = new Random();
            Point freept = free.get(randomizer.nextInt(free.size()));
            int x = freept.getX();
            int y = freept.getY();


            //System.out.println(x +", "+ y);

            boolean flag = false;
            if (!flag) {
                for (int j = 0; j < roomsize; j++) {
                    for (int i = 0; i < roomsize; i++) {
                        taken.add(new Point(i + x, j + y));
                    }
                }
                constructRoom(x, y, map);
                constuctCorridor(map);
            }
        }

    }

    public int mapCounter(char symbol, char[][] map) {
        int count = 0;
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                //System.out.print(map[j][i]);
                if (map[j][i] == symbol) count++;
            }
        }
        return count;
    }


    public void constructRoom(int x, int y, char[][] map) {
        int height = roomsize;
        int length = roomsize;
        for (int j = 0; j < height + 2; j++) {        //create room of length and height
            for (int i = 0; i < length + 2; i++) {
                map[j + y - 1][i + x - 1] = 'P';
            }
        }
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < length; i++) {
                if (j == 0 && i == 0) {
                    map[j + y][i + x] = '#';
                    continue;
                }
                if (j == height - 1 && i == 0) {
                    map[j + y][i + x] = '#';
                    continue;
                }
                if (j == height - 1 && i == length - 1) {
                    map[j + y][i + x] = '#';
                    continue;
                }
                if (j == 0 && i == length - 1) {
                    map[j + y][i + x] = '#';
                    continue;
                }
                map[j + y][i + x] = 'W';
            }
        }
        for (int j = 1; j < height - 1; j++) {    // remove the space inside
            for (int i = 1; i < length - 1; i++) {
                map[j + y][i + x] = ' ';
            }
        }
    }

    public void removePadding(char[][] map) {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == 'P') map[j][i] = '.';
            }
        }

    }

    public void constuctCorridor(char[][] map) {
        ArrayList<Point> walls = new ArrayList<Point>();
        //look for walls
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == 'W') {
                    walls.add(new Point(i, j));
                }
            }
        }
        //randomly choose a wall
        Random randomizer = new Random();
        Point pt = walls.get(randomizer.nextInt(walls.size()));

        Point pt2 = walls.get(randomizer.nextInt(walls.size()));


        //replace all W's with #
        for (Point wall : walls) {
            map[wall.getY()][wall.getX()] = '#';
        }
        //replace random location with D

        map[pt.getY()][pt.getX()] = 'D';
        map[pt2.getY()][pt2.getX()] = 'D';
    }

    public void constructPath(char[][] map) {
        //search for doors
        ArrayList<Point> doors = new ArrayList<Point>();
        //look for walls
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == 'D') {
                    doors.add(new Point(i, j));
                }
            }
        }
        // construct template map for a*
        // look for obs
        ArrayList<Point> obstacles = new ArrayList<Point>();
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == '#') {
                    obstacles.add(new Point(i, j));
                }
            }
        }
        System.out.println(mapCounter('#', map));

        int[][] obs = new int[obstacles.size()][obstacles.size()];
        if (obstacles.size() > 0) {                       //if there are obs
            for (int x = 0; x < obs[0].length; x++) {     //
                obs[x][1] = obstacles.get(x).getX();
                obs[x][0] = obstacles.get(x).getY();
            }
        }


        // submit a*
        System.out.println("before a*");
        printMap(map);
        while (doors.size() != 0) {
            Point now = doors.get(0);
            doors.remove(now);
            Point closest = null;
            for (int iter = 0; iter < 5; iter++) {
                for (Point door : doors) {
                    if (closest == null) {
                        closest = door;
                    } else {
                        if (now.getDistance(closest) > now.getDistance(door)) {
                            closest = door;
                        }
                    }
                }
                //doors.remove(closest);
                if (closest != null) {
                    try {
                        Point[] oblist = new Point[obs.length];
                        for (int i = 0; i < obstacles.size(); i++) {
                            oblist[i] = new Point(obstacles.get(i).getY(), obstacles.get(i).getX());
                        }
                        ArrayList<Point> path = new PathFinder(map.length, map[0].length, new Point(now.getY(), now.getX()), new Point(closest.getY(), closest.getX()), oblist).moves;
                        //replace all points retrieved by A* with P representing paths
                        for (Point pt : path) {
                            map[pt.getY()][pt.getX()] = ' ';
                        }
                        map[now.getY()][now.getX()] = ' ';
                    } catch (Exception e) {
                        System.out.println("if you see this message just cry and start it again");
                    }

                }

            }
        }

    }


    public char[][] mapConvertForAstar(char[][] map) {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == '#') map[j][i] = 'O';
                if (map[j][i] == ' ') map[j][i] = 'O';
            }
        }
        return map;
    }


    public void fillMap(char[][] map) {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                map[j][i] = '.';
            }
        }
    }

    public void printMap() {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                System.out.printf("%-2s ", map[j][i]);
            }
            System.out.println();
        }
    }

    public void printMap(char[][] map) {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                System.out.printf("%-2s ", map[j][i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new MapGenerator(50, 50, 10, 25, 25);
    }

}
