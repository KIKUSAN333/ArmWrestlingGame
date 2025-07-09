
public class Enemy extends Character{

	private int level;
	private int doNextActionFrame;
	
	public Enemy(Model model,String name, int power, int level) {
		super(model,name, power);
		this.level = level;
		
		decideNextActionFrame();
		
	}

	@Override
	public void doAction() {
		
		super.doAction();

	}
	
	private void decideNextActionFrame() {
		if(level == 1) {
			doNextActionFrame = 5;
		}
		else if(level == 2) {
			doNextActionFrame = 3;
		}
		else if(level == 3) {
			doNextActionFrame = 1;
		}
		else {
			doNextActionFrame = 10;
		}
	}
	
	public int getDoNextActionFrame() {
		return doNextActionFrame;
	}


}
