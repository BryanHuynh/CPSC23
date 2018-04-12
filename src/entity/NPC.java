package entity;

import java.util.Random;

/**
 * entity.NPC's are non-hostile entities that can communicate with the player using the dialogbox
 */
public class NPC extends EntityCharacter {

    /**
     * entity character that is able to join up play in a party to do combat
     * @param x
     * @param y
     * @param symbol
     */
    public NPC(int x, int y, char symbol) {
        super(x, y, symbol);

       hp = rand.nextInt(50 - 20) + 20;
    }

    public NPC(){

    }




    private double alternateXTime = 0.0;


    /**
     * alternate between capital and lower case
     *
     * @param delta
     */
    public void update(double delta) {

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
        return "Hello traveler, I'm " + name + " I have " + atk + " damage and I use a " + weapon + "  I would love to join your party (R to recruit)";
    }


}

