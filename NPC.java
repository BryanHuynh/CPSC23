public class NPC extends Entity {

	public NPC(int x, int y, char symbol){
		super(x ,y , symbol);
	}

	public String dialog(){
		return "";
	}

	double alternateXTime = 0.0;

	public void update(double delta){
		super.update(delta);
		alternateXTime += delta/100;

		if(alternateXTime >= 0.2) {
			alternateXTime = 0;
			//System.out.println("SWITCH");
			if (this.getSymbol() == 'c') {
				this.setSymbol('C');
			} else {
				this.setSymbol('c');
			}
		}
	}
}
