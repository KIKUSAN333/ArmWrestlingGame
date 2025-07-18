import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PowerBarTest {
    
    @Test
    void パワーバーのコンストラクタで初期状態が正しく設定されるかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // 初期状態では中央（0.5）になっているはず
        assertEquals(0.0, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void 同じパワーでupdateBarを実行すると中央になるかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // 同じパワーで更新
        powerBar.updateBar(50, 50);
        
        // 中央（0.5）になっているはず
        assertEquals(0.5, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void power1が大きいときにバーが右側に移動するかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // power1が10大きい場合
        powerBar.updateBar(60, 50);
        
        // 0.5より大きくなるはず（右側に移動）
        assertTrue(powerBar.getCurrentBarPercent() > 0.5);
        
        // 計算値を確認: difference=10, ratio=10/20=0.5, percent=0.5+0.5/2=0.75
        assertEquals(0.75, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void power2が大きいときにバーが左側に移動するかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // power2が10大きい場合
        powerBar.updateBar(50, 60);
        
        // 0.5より小さくなるはず（左側に移動）
        assertTrue(powerBar.getCurrentBarPercent() < 0.5);
        
        // 計算値を確認: difference=-10, ratio=-10/20=-0.5, percent=0.5-0.5/2=0.25
        assertEquals(0.25, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void パワー差が基準値以上のときにバーが端に到達するかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // power1が基準値（20）以上大きい場合
        powerBar.updateBar(70, 50);
        
        // 1.0（右端）になるはず
        assertEquals(1.0, powerBar.getCurrentBarPercent(), 0.01);
        
        // power2が基準値（20）以上大きい場合
        powerBar.updateBar(50, 70);
        
        // 0.0（左端）になるはず
        assertEquals(0.0, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void パワー差が基準値を超えてもバーが範囲内に制限されるかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // power1が基準値を大幅に超える場合
        powerBar.updateBar(100, 50);
        
        // 1.0を超えないはず
        assertEquals(1.0, powerBar.getCurrentBarPercent(), 0.01);
        
        // power2が基準値を大幅に超える場合
        powerBar.updateBar(50, 100);
        
        // 0.0を下回らないはず
        assertEquals(0.0, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void updateBarを連続実行してもバーが正しく更新されるかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // 最初の更新
        powerBar.updateBar(60, 50);
        assertEquals(0.75, powerBar.getCurrentBarPercent(), 0.01);
        
        // 2回目の更新
        powerBar.updateBar(50, 60);
        assertEquals(0.25, powerBar.getCurrentBarPercent(), 0.01);
        
        // 3回目の更新
        powerBar.updateBar(55, 55);
        assertEquals(0.5, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void showBarメソッドが例外を発生させないかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // テスト用のGraphicsオブジェクトを作成
        BufferedImage image = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // 例外が発生しないことを確認
        assertDoesNotThrow(() -> {
            powerBar.showBar(g);
        });
        
        // パワーを更新してからも例外が発生しないことを確認
        powerBar.updateBar(60, 50);
        assertDoesNotThrow(() -> {
            powerBar.showBar(g);
        });
        
        g.dispose();
    }
    
    @Test
    void バーの隠し機能が動作するかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // テスト用のGraphicsオブジェクトを作成
        BufferedImage image = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // maxHideCountまでupdateBarを実行してバーを隠す状態にする
        // ランダム値なので最大値の1000回実行
        for (int i = 0; i < 1000; i++) {
            powerBar.updateBar(60, 50);
        }
        
        // showBarを実行して例外が発生しないことを確認
        assertDoesNotThrow(() -> {
            powerBar.showBar(g);
        });
        
        // hideDurationの最大値300回showBarを実行してバーが再表示されることを確認
        for (int i = 0; i < 300; i++) {
            assertDoesNotThrow(() -> {
                powerBar.showBar(g);
            });
        }
        
        g.dispose();
    }
    
    @Test
    void 極端なパワー値でも正しく動作するかテスト() {
        PowerBar powerBar = new PowerBar();
        
        // 非常に大きな値
        powerBar.updateBar(1000, 500);
        assertTrue(powerBar.getCurrentBarPercent() >= 0.0 && powerBar.getCurrentBarPercent() <= 1.0);
        
        // 負の値
        powerBar.updateBar(-50, 50);
        assertTrue(powerBar.getCurrentBarPercent() >= 0.0 && powerBar.getCurrentBarPercent() <= 1.0);
        
        // 0値
        powerBar.updateBar(0, 0);
        assertEquals(0.5, powerBar.getCurrentBarPercent(), 0.01);
    }
    
    @Test
    void パワー差の計算が正確かテスト() {
        PowerBar powerBar = new PowerBar();
        
        // 様々なパワー差をテスト
        int[] testCases = {1, 5, 10, 15, 20, 25};
        double[] expectedResults = {0.525, 0.625, 0.75, 0.875, 1.0, 1.0};
        
        for (int i = 0; i < testCases.length; i++) {
            powerBar.updateBar(50 + testCases[i], 50);
            assertEquals(expectedResults[i], powerBar.getCurrentBarPercent(), 0.01,
                "パワー差" + testCases[i] + "の場合の計算が間違っています");
        }
    }
}