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

	
	public PVEState(Model m) {
		model = m;
		player = new Player(m,"test",1,"ENTER");
		enemy = new Enemy(m,"test",1,1);
		powerBar = new PowerBar(m);
		time = new Time();
		
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
		
	}

	@Override
	public State processTimeElapsed(int msec) {

		enemy.doAction();
		
		powerBar.updateBar(player.getPower(), enemy.getPower());
		
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {

		//入力したキーが対応するキーであった場合
		if(typed.equals(player.getKey())) {
			player.doAction();
		}
		
		powerBar.updateBar(player.getPower(), enemy.getPower());
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g,View view) {
		// TODO 自動生成されたメソッド・スタブ
		
		g.setColor(Color.WHITE);
		g.drawString("Player :" + player.getPower(),0,100);
		g.drawString("Enemy  :" + enemy.getPower(),200,100);
		
	    // ======== 画像の3D的X軸回転処理 ========
	    Graphics2D g2d = (Graphics2D) g;

	    if (image != null && image.getWidth(view) > 0) {
	        // -- 設定値 --
	        double angleDegrees = 60.0; //
	        double finalScale = 0.3;
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
