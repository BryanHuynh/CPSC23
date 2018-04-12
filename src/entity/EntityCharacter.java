package entity;

import java.io.Serializable;
import java.util.Random;

public class EntityCharacter extends Entity implements Serializable {

    Random rand = new Random();
    protected int hp = rand.nextInt(50 - 5) + 5;
    protected boolean blocking = false;
    protected boolean isDead = false;

    protected String name = "Character";
    protected int atk = rand.nextInt(20 - 5) + 5;
    protected double accuracy = rand.nextInt(100 - 50) + 50;
    protected String weapon = "Wooden Sword";
    protected int maxHp;
    /**
     * anything that can be visually represented on a grid, and also
     * has stats like: name, attack damage, health, accuracy, etc.
     * @param x
     * @param y
     * @param symbol
     */
    public EntityCharacter(int x, int y, char symbol) {
        super(x, y, symbol);
        maxHp = hp;
    }

    public EntityCharacter(){

    }


    /**
     * subtract the characters health for a certain amount of damage
     * @param damage
     */
    public void damage(int damage){
        hp -= damage;
    }

    /**
     * clones entityCharacter
     * @return
     */
    @Override
    public EntityCharacter clone(){
        EntityCharacter en = new EntityCharacter(x,y, symbol);
        en.setAtk(atk);
        en.setMaxHp(maxHp);
        en.setName(name);
        en.setWeapon(weapon);
        en.setAccuracy(accuracy);
        en.setHp(hp);
        en.setBlocking(blocking);
        en.setX(x);
        en.setY(y);
        en.setSymbol(symbol);
        en.setVisable(isVisable());
        return en;
    }



    public void restoreHealth(int restore){
        hp += restore;
    }

    @Override
    public void update(double delta) {

    }


    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean getChance()
    {
        return Math.random() >= 1.0 - (accuracy/100);
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return new String(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getWeapon() {
        return new String(weapon);
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
}
