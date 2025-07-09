import java.awt.Color;
import java.awt.Graphics;

public class PowerBar {
    // バーの最大幅を定義
    final int MAX_BAR_LENGTH = 250;
    final int BAR_X = 100;
    final int BAR_Y = 350;
    final int BAR_WIDTH = 30;
	
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
		
		currentBarPercent = (double) power1/ (power1 + power2) ;
	}
}
