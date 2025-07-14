import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel {

    private Model model;

    // Sample instance variables:
    private Image image;
    private WavSound sound;

    public View(Model model) {
        this.model = model;

        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("robot.png"));
        // サウンドを読み込む
        sound = new WavSound(getClass().getResource("bomb.wav"));
      }

    /**
     * 画面を描画する
     * @param g  描画用のグラフィックスオブジェクト
     */
    @Override
    public void paintComponent(Graphics g) {
        // 画面をいったんクリア
        clear(g);
        
        //フォントを基準に設定
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        g.setColor(Color.WHITE);
        
        
        // 画像の表示例
        g.drawImage(image, model.getMX(), model.getMY(), this);
        
        
        //モデルから状態を取得し,状態に応じて描画
        State state = model.getState();
        state.paintComponent(g,this);
        
        // Linux でアニメーションをスムーズに描画するため（描画結果が勝手に間引かれることを防ぐ）
        getToolkit().sync();
    }

    /**
     * 画面を黒色でクリア
     * @param g  描画用のグラフィックスオブジェクト
     */
    public void clear(Graphics g) {
        Dimension size = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, size.width, size.height);
    }

    public void playBombSound() {
        sound.play();
    }
    
	public void drawScaledImage(Graphics g, Image img, int x, int y, double scale) {
	    if (img == null) return;
	    
	    // 元の画像のサイズを取得
	    int originalWidth = img.getWidth(this);
	    int originalHeight = img.getHeight(this);
	    
	    // 縮小後のサイズを計算
	    int scaledWidth = (int)(originalWidth * scale);
	    int scaledHeight = (int)(originalHeight * scale);
	    
	    // 縮小して描画
	    g.drawImage(img, x, y, scaledWidth, scaledHeight, this);
	}
}
