import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class PVPState implements State{

	private Player player1;
	private Player player2;
	private PowerBar powerBar;
	private Time time;
	private Image image;
	private Image backgroundImage;
	
	private int elapsedCount;

	
	public PVPState(String player1Name,String player2Name) {
		player1 = new Player(player1Name,1,"ENTER");
		player2 = new Player(player2Name,1," ");
		powerBar = new PowerBar();
		time = new Time();
		
		elapsedCount = 0;
		
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
       image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
       backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
		
	}
	

	private State updateState(String typed) {

	    
	    // ゲーム終了条件をチェック
	    String winner = getWinner();
	    
	    if (winner != null) {
	        return new ResultState(winner,powerBar);
	    }
	    
	    // ボス戦への移行
	    if (typed.equals("T")) {
	        return new BossState(this);
	    }
	    
	    return this;
	}
	
	private String getWinner() {
	    double powerPercent = powerBar.getCurrentBarPercent();
		
	    // パワーバーが満タンまたは空の場合
	    if (powerPercent >= 1.0) {
	        return player1.getName();
	    }
	    if (powerPercent <= 0.0) {
	        return player2.getName();
	    }
	    
	    // 時間切れの場合パワーが大きい方が返る
	    if (time.getTime() <= 0) {
	        return powerPercent >= 0.5 ? player1.getName() : player2.getName();
	    }
	    
	    return null; // ゲーム続行
	}

	@Override
	public State processTimeElapsed(int msec) {
		elapsedCount++;
		
		//1秒経過の場合
		if(elapsedCount >= 100) {
			time.updateTime();
			
			//リセット
			elapsedCount = 0;
		}
		
		powerBar.updateBar(player1.getPower(), player2.getPower());
		
		//右プレイヤーがピンチになったら
		if(powerBar.getCurrentBarPercent() >= 0.7 && !player2.getIsPowerUp()) {
			player2.basePowerUp();
		}
		
		//左プレイヤーがピンチになったら
		if(powerBar.getCurrentBarPercent() <= 0.3 && !player1.getIsPowerUp()) {
			player1.basePowerUp();
		}
		
		
		return updateState("");
	}

	@Override
	public State processKeyTyped(String typed) {

		//入力したキーが対応するキーであった場合
		if(typed.equals(player1.getKey())) {
			player1.doAction();
		}
		
		if(typed.equals(player2.getKey())) {
			player2.doAction();
		}
		
		powerBar.updateBar(player1.getPower(), player2.getPower());
		
		State currentState = updateState(typed);
		return currentState;
	}

	@Override
	public void paintComponent(Graphics g,View view) {
		view.drawScaledImage(g,backgroundImage,-150,0,0.35);
		
		
		g.setColor(Color.WHITE);
		g.drawString(player1.getName() + " : " + player1.getPower(),0,100);
		g.drawString(player2.getName() + " : " + player2.getPower(),200,100);
		
	    view.drawScaledImage(g,image,100,150,0.5);

	    
		powerBar.showBar(g);
		time.showTime(g);
	}

}
