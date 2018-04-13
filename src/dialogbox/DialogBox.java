package dialogbox;

public abstract class DialogBox {

    /**
     * used to show text information to the user
     */
    private String str = "DIALOG BOX";

    public abstract void render();

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
