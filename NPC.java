/**
 * NPC's are non-hostile entities that can communicate with the player using the dialogBox
 */
public class NPC extends Entity {


	private String dialog = "";
	public NPC(int x, int y, char symbol){
		super(x ,y , symbol);
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

	public String getDialog() {
		return "";
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}
}
