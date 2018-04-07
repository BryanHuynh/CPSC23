import java.util.Random;

public class EntityCharacter extends Entity {

    Random rand = new Random();
    protected int hp = rand.nextInt(20 - 5) + 5;
    protected boolean blocking = false;
    protected boolean isDead = false;

    protected String name = "Character";
    protected int atk = rand.nextInt(20 - 5) + 5;
    protected double accuracy = rand.nextInt(100 - 50) + 50;
    protected String weapon = "Wooden Sword";

    public EntityCharacter(int x, int y, char symbol) {
        super(x, y, symbol);
    }


    public void damage(int damage){
        hp -= damage;
    }

    @Override
    public EntityCharacter clone(){
        EntityCharacter en = new EntityCharacter(x,y, symbol);
        en.setAtk(atk);
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

    @Override
    public void update(double delta) {

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
