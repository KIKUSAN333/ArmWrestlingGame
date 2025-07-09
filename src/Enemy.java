
public class Enemy extends Character{

	private int level;
	private int baseActionPoint;
	private int doActionPoint;
	
	public Enemy(Model model,String name, int power, int level) {
		super(model,name, power);
		this.level = level;
		
		doActionPoint = 0;
		decideNextActionFrame();
		
	}

	@Override
	public void doAction() {
		
		doActionPoint++;
		
		//行動ポイントが基準の行動ポイントより多いなら
		if(doActionPoint >= baseActionPoint) {
			super.doAction();
			
			//リセットする
			doActionPoint = 0;
		}

	}
	
	private void decideNextActionFrame() {
		if(level == 1) {
			baseActionPoint = 5;
		}
		else if(level == 2) {
			baseActionPoint = 3;
		}
		else if(level == 3) {
			baseActionPoint = 1;
		}
		else {
			baseActionPoint = 10;
		}
	}
	


}
