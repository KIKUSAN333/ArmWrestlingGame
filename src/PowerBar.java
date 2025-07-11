import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;

public class PowerBar {
    // バーの最大幅を定義
    final int MAX_BAR_LENGTH = 250;
    final int BAR_X = 175;
    final int BAR_Y = 50;
    final int BAR_WIDTH = 30;
    
    final int BARFRAME_LENGTH = 5;
    final int BARFRAME_WIDTH = 3;
    
    //パワー差がつくと、バーが完全に片方に寄るという基準値
    final int POWER_ADVANTAGE_TO_WIN = 20; 
    
	
	private double currentBarPercent;
	private Model model;
	
	private int hideCount;
	private boolean isHidingBar;
	
	private int maxHideCount = 400;
	private int hideDuration = 200;
	
	private Random random;
	
	public PowerBar(Model m) {
		super();
		this.model = m;

		currentBarPercent = 0;
		hideCount = 0;
		
		random = new Random();
	
		decideHideParameters();
	}
	
	private void decideHideParameters() {
		maxHideCount = random.nextInt(300,1000);
		hideDuration = random.nextInt(100,300);
	}

	// 毎フレーム呼ばれる描画関数
	public void showBar(Graphics g) {
	    updateBarVisibility();  // 状態だけ更新
	    g.setColor(Color.WHITE);
	    g.fillRect(BAR_X - BARFRAME_LENGTH, BAR_Y - BARFRAME_WIDTH, MAX_BAR_LENGTH + BARFRAME_LENGTH * 2, BAR_WIDTH + BARFRAME_WIDTH * 2);

	    if (isHidingBar) {
	        // 隠すときはグレー
	        g.setColor(Color.GRAY);
	        g.fillRect(BAR_X, BAR_Y, MAX_BAR_LENGTH, BAR_WIDTH);
	    } else {
	        // 背景バー（character2）
	        g.setColor(Color.BLUE);
	        g.fillRect(BAR_X, BAR_Y, MAX_BAR_LENGTH, BAR_WIDTH);

	        // 前面バー（character1）
	        int currentBarWidth = (int) (MAX_BAR_LENGTH * currentBarPercent);
	        g.setColor(Color.RED);
	        g.fillRect(BAR_X, BAR_Y, currentBarWidth, BAR_WIDTH);
	    }
	}

	//状態管理関数
	private void updateBarVisibility() {
	    if (isHidingBar) {
	        hideCount--;
	        
	        if (hideCount <= maxHideCount - hideDuration) {
	            isHidingBar = false;
	            hideCount = 0;
	            
	            decideHideParameters();
	        }
	    }
	}

	//外部から呼ばれるバー更新処理
	public void updateBar(int power1, int power2) {
	    // パワー差を割合に変換
	    int difference = power1 - power2;
	    double differenceRatio = (double) difference / POWER_ADVANTAGE_TO_WIN;
	    currentBarPercent = 0.5 + (differenceRatio / 2.0);
	    currentBarPercent = Math.max(0.0, Math.min(1.0, currentBarPercent)); //範囲制限

	    // バーが表示されているときだけカウントを増やす
	    if (!isHidingBar) {
	        hideCount++;
	        
	        if (hideCount >= maxHideCount) {
	            isHidingBar = true;
	        }
	    }
	}
	
	public double getCurrentBarPercent(){
		return currentBarPercent;
	}
}
