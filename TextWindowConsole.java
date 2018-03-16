public class TextWindowConsole extends TextWindow {

    public TextWindowConsole(int height, int width, Rogue rogue) {
        super(height, width, rogue);
    }

    /**
     * prints the entityMap to the console. null spaces are filled with '.'s
     */
    public void render(Entity[][] entityMap){
        for(int y = 0; y < getHeights(); y++){
            for(int x = 0; x < getWidths(); x++){
                if(entityMap[y][x] == null){
                    System.out.printf("%-2s ",' ');
                }else {
                    if(entityMap[y][x].isVisable()){
                        System.out.printf("%-2s ", String.valueOf(entityMap[y][x].getSymbol()));
                    }else{
                        System.out.printf("%-2s ",' ');
                    }
                }
            }
            System.out.println();
        }

    }
}
