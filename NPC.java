import java.util.Random;

/**
 * NPC's are non-hostile entities that can communicate with the player using the dialogBox
 */
public class NPC extends Entity {

	Random rand = new Random();
	private String name = "NPC";
	private int atk;
	private double accuracy;
	private String weapon = "Wooden Sword";



	public double getAlternateXTime() {
		return alternateXTime;
	}

	public void setAlternateXTime(double alternateXTime) {
		this.alternateXTime = alternateXTime;
	}

	public NPC(int x, int y, char symbol){
		super(x ,y , symbol);
		atk = rand.nextInt(20-1) + 1;
		accuracy = (rand.nextInt(100-1) + 1);

	}

	private double alternateXTime = 0.0;



	/**
	 * alternate between capital and lower case
	 * @param delta
	 */
	public void update(double delta){
		super.update(delta);

		alternateXTime += delta/1000;

		if(alternateXTime >= 0.2) {
			alternateXTime = 0;
			//System.out.println("SWITCH");
			if (Character.isLowerCase(this.getSymbol())) {
				this.setSymbol(Character.toUpperCase(this.getSymbol()));
			} else {
				this.setSymbol(Character.toLowerCase(this.getSymbol()));
			}
		}
	}

	public NPC clone(){
		return new NPC(this.getX(), this.getY(), this.getSymbol());
	}

	public String getDialog() {
		return "Hello traveler, I'm " + name + "\n I have " + atk + " damage and I use a " + weapon +" \n I would love to join your party (R to recruit)";
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


	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
}
