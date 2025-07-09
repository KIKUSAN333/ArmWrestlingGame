import java.awt.Color;
import java.awt.Graphics;

public class PowerBar {
    // バーの最大幅を定義
    final int MAX_BAR_LENGTH = 250;
    final int BAR_X = 100;
    final int BAR_Y = 350;
    final int BAR_WIDTH = 30;
    
    //パワー差がつくと、バーが完全に片方に寄るという基準値
    final int POWER_ADVANTAGE_TO_WIN = 20; 
	
	private double currentBarPercent;
	private Model model;
	
	
	
	public PowerBar(Model m) {
		super();
		this.model = m;

		currentBarPercent = 0;
	}

	public void showBar(Graphics g) {
	    //character2のパワーバーを描画
	    g.setColor(Color.BLUE);
	    g.fillRect(BAR_X, BAR_Y, MAX_BAR_LENGTH, BAR_WIDTH);
	    
	    //現在の割合からバーの幅を計算
	    int currentBarWidth = (int) (MAX_BAR_LENGTH * currentBarPercent);
	    
	    //character1のパワーバーを描画
	    g.setColor(Color.RED);
	    g.fillRect(BAR_X, BAR_Y, currentBarWidth, BAR_WIDTH);
	}

	public void updateBar(int power1,int power2) {
	    //２人のパワーの差を計算
	    int difference = power1 - power2;

	    //差を基準値で割り、割合を計算（-1.0 ～ 1.0 の範囲になる）
	    //基準値で割ることで、絶対値に関係なく差の大きさだけが問題になる
	    double differenceRatio = (double) difference / POWER_ADVANTAGE_TO_WIN;

	    //割合をバーのパーセンテージ（0.0 ～ 1.0）に変換
	    //中心を0.5として、そこから差の分だけずらす
	    currentBarPercent = 0.5 + (differenceRatio / 2.0);

	    //計算結果が 0.0 ～ 1.0 の範囲に収まるように調整
	    //パワー差が基準値を超えた場合への対処
	    if (currentBarPercent > 1.0) {
	        currentBarPercent = 1.0;
	    } else if (currentBarPercent < 0.0) {
	        currentBarPercent = 0.0;
	    }
	}
}
