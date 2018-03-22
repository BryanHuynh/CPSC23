import java.util.Random;

/**
 * NPC's are non-hostile entities that can communicate with the player using the dialogBox
 */
public class NPC extends EntityCharacter {

    Random rand = new Random();


    public double getAlternateXTime() {
        return alternateXTime;
    }

    public void setAlternateXTime(double alternateXTime) {
        this.alternateXTime = alternateXTime;
    }

    public NPC(int x, int y, char symbol) {
        super(x, y, symbol);
    }

    private double alternateXTime = 0.0;


    /**
     * alternate between capital and lower case
     *
     * @param delta
     */
    public void update(double delta) {
        super.update(delta);

        alternateXTime += delta / 1000;

        if (alternateXTime >= 0.2) {
            alternateXTime = 0;
            //System.out.println("SWITCH");
            if (Character.isLowerCase(this.getSymbol())) {
                this.setSymbol(Character.toUpperCase(this.getSymbol()));
            } else {
                this.setSymbol(Character.toLowerCase(this.getSymbol()));
            }
        }
    }

    public NPC clone() {
        return new NPC(this.getX(), this.getY(), this.getSymbol());
    }

    public String getDialog() {
        return "Hello traveler, I'm " + name + "\n I have " + atk + " damage and I use a " + weapon + " \n I would love to join your party (R to recruit)";
    }


}

