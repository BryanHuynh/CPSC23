import java.util.Random;

public class EntityCharacter extends Entity {

    Random rand = new Random();
    protected int hp = rand.nextInt(20 - 5) + 5;


    protected String name = "Character";
    protected int atk = rand.nextInt(20 - 5) + 5;
    protected double accuracy = rand.nextInt(100 - 1) + 1;
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
        en.atk = atk;
        en.name = name;
        en.weapon = weapon;
        en.accuracy = accuracy;
        en.hp = hp;
        return en;
    }

    public boolean getChance()
    {
        return Math.random() >= 1.0 - accuracy;
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
}
