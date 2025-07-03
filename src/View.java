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

        // 描画する
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        g.setColor(Color.WHITE);

        g.drawString("Time: " + model.getTime(), 100, 150);
        g.drawString("Key Typed: " + model.getTypedChar(), 100, 200);
        g.drawString("KeyRollover: " + model.getEnableKeyRollover(), 100, 250);
        g.drawString("Mouse Pressed: " + model.getMX() + "," + model.getMY(), 100, 300);

        // 画像の表示例
        g.drawImage(image, model.getMX(), model.getMY(), this);

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
}
