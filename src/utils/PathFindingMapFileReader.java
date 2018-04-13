package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PathFindingMapFileReader {

    private Scanner scanner;
    private Point start;
    private Point end;
    private Point[] obs;

    /**
     * used to send in maps for the PathFinder to solve
     */
    PathFindingMapFileReader(String filename) {
        try {
            scanner = new Scanner(new File(filename));
        } catch (Exception e) {

        }
        ArrayList<Point> obs = new ArrayList<>();
        int x = 0;
        int y = 0;
        Point start = new Point(0,0);
        Point end = new Point(0,0);;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            char[] chars = line.toCharArray();
            for(Character character: chars){

                if(character == 'O'){
                    obs.add(new Point(x,y));
                }
                if(character == 'S'){
                    start = new Point(x,y);
                }
                if(character == 'X'){
                    end = new Point(x,y);
                }
                x++;
            }

            x = 0;
            y++;
        }
        Point[] obsPoints = new Point[obs.size()];
        obsPoints = obs.toArray(obsPoints);

        this.start = start;
        this.end = end;
        this.obs = obsPoints;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Point[] getObs() {
        return obs;
    }

    public static void main(String[] args) {
    }


}
