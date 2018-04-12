package map;

import entity.Entity;
import rogue.Rogue;


public abstract class MapDisplay {


    private int heights;
    private int widths;
    private Rogue rogue;

    /**
     * handles displaying the map that visually shows entity locations
     *
     * @param heights
     * @param widths
     * @param rogue
     */
    public MapDisplay(int heights, int widths, Rogue rogue) {
        this.heights = heights;
        this.widths = widths;
        this.rogue = rogue;
    }


    public abstract void render(Entity[][] entityMap);


    /**
     * clears the console for the text version
     */
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
    }


    public int getHeights() {
        return heights;
    }


    public int getWidths() {
        return widths;
    }

    public Rogue getRogue() {
        return rogue;
    }

}
