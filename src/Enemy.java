import java.util.Random;

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
			decideNextActionFrame();
		}

	}
	
	private void decideNextActionFrame() {
		Random random = new Random();
		
		if(level == 1) {
			baseActionPoint = random.nextInt(20,40);
		}
		else if(level == 2) {
			baseActionPoint = random.nextInt(10,20);
		}
		else if(level == 3) {
			baseActionPoint = random.nextInt(5,15);
		}
		else {
			baseActionPoint = 10;
		}
	}
	


}
