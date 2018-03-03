/**
 * window that will render the game for both gui and text-based
 * handles pl
 */
public class TextWindow {


    private int heights;
    private int widths;
    private Rogue rogue;

    /**
     * @param heights
     * @param widths
     * @param rogue
     */
    public TextWindow(int heights, int widths, Rogue rogue) {
        this.heights = heights;
        this.widths = widths;
        this.rogue = rogue;
    }


    public void render(Entity[][] entityMap) {
        System.out.println("TEXTWINDOW RENDER METHOD SHOULD BE OVERRIDEN");
    }


    /**
     * clears the console for the text version
     */
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
    }


    public int getHeights() {
        return heights;
    }

    public void setHeights(int heights) {
        this.heights = heights;
    }

    public int getWidths() {
        return widths;
    }

    public void setWidths(int widths) {
        this.widths = widths;
    }

    public Rogue getRogue() {
        return rogue;
    }

    public void setRogue(Rogue rogue) {
        this.rogue = rogue;
    }
}
