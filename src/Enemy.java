import java.security.SecureRandom;

public class Enemy extends Character{

	private int level;
	private int baseActionPoint;
	private int doActionPoint;
	
	private SecureRandom random;
	
	public Enemy(String name, int power, int level) {
		super(name, power);
		this.level = level;
		
		doActionPoint = 0;
		random = new SecureRandom();
		
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
		
		if(level == 1) {
			baseActionPoint = random.nextInt(20,40);
		}
		else if(level == 2) {
			baseActionPoint = random.nextInt(10,20);
		}
		else if(level == 3) {
			baseActionPoint = random.nextInt(5,10);
		}
		else if(level == 4) {
			baseActionPoint = random.nextInt(3,10);
		}
		else {
			baseActionPoint = 10;
		}
	}
	
	


}
