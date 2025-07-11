import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public class PVEState implements State{
	private Model model;
	private Player player;
	private Enemy enemy;
	private PowerBar powerBar;
	private Time time;
	private Image image;
	private Image backgroundImage;
	
	private int elapsedCount;

	
	public PVEState(Model m) {
		model = m;
		player = new Player(m,"test",1,"ENTER");
		enemy = new Enemy(m,"enemy",1,3);
		powerBar = new PowerBar(m);
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
	        return new ResultState(winner);
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
	        return player.getName();
	    }
	    if (powerPercent <= 0.0) {
	        return enemy.getName();
	    }
	    
	    // 時間切れの場合パワーが大きい方が返る
	    if (time.getTime() <= 0) {
	        return powerPercent >= 0.5 ? player.getName() : enemy.getName();
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
		
		enemy.doAction();
		powerBar.updateBar(player.getPower(), enemy.getPower());
		
		//CPUがピンチになったら
		if(powerBar.getCurrentBarPercent() >= 0.7 && !enemy.getIsPowerUp()) {
			enemy.basePowerUp();
		}
		
		return updateState("");
	}

	@Override
	public State processKeyTyped(String typed) {

		//入力したキーが対応するキーであった場合
		if(typed.equals(player.getKey())) {
			player.doAction();
		}
		
		powerBar.updateBar(player.getPower(), enemy.getPower());
		
		State currentState = updateState(typed);
		return currentState;
	}

	@Override
	public void paintComponent(Graphics g,View view) {
		// TODO 自動生成されたメソッド・スタブ
		g.drawImage(backgroundImage,1,1,view);
		
		
		g.setColor(Color.WHITE);
		g.drawString("Player :" + player.getPower(),0,100);
		g.drawString("Enemy  :" + enemy.getPower(),200,100);
		
	    // ======== 画像の3D的X軸回転処理 ========
	    Graphics2D g2d = (Graphics2D) g;

	    if (image != null && image.getWidth(view) > 0) {
	        // -- 設定値 --
	        double angleDegrees = 0.0; //
	        double finalScale = 0.5;
	        int imageX = 130;
	        int imageY = 150;
	        double perspectiveFactor = image.getHeight(view) * finalScale * 0.5;

	        // 1. 変換の準備
	        double angleRadians = Math.toRadians(angleDegrees);
	        // ★★★常に正の値になるよう Math.abs() を使う★★★
	        double rotationEffectScaleY = Math.abs(Math.cos(angleRadians));
	        double yShift = Math.sin(angleRadians) * perspectiveFactor;

	        // 2. AffineTransformで変換を設定
	        AffineTransform tx = new AffineTransform();
	        
	        double anchorX = image.getWidth(view) / 2.0;
	        double anchorY = image.getHeight(view); 

	        // 3. 変換を正しい順序で適用
	        tx.translate(imageX, imageY + yShift);
	        tx.scale(finalScale, finalScale);
	        tx.translate(anchorX, anchorY);
	        tx.scale(1.0, rotationEffectScaleY);
	        tx.translate(-anchorX, -anchorY);

	        // 4. 作成した変換を適用して画像を描画
	        g2d.drawImage(image, tx, view);
	    }

	    
		powerBar.showBar(g);
		time.showTime(g);
	}

}
