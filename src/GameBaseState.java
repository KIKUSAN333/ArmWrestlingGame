import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class GameBaseState implements State {
    protected PowerBar powerBar;
    protected Time time;
    protected Image image;
    protected Image backgroundImage;
    protected int elapsedCount;
    
    public GameBaseState() {
        powerBar = new PowerBar();
        time = new Time();
        elapsedCount = 0;
        
        // 画像を読み込む
        image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
    }
    
    protected State updateState(String typed) {
        // ゲーム終了条件をチェック
        String winner = getWinner();
        
        if (winner != null) {
            return new ResultState(winner, powerBar);
        }
        
        // ボス戦への移行
        if (typed.equals("T")) {
            return new BossState(this);
        }
        
        return this;
    }
    
    protected String getWinner() {
        double powerPercent = powerBar.getCurrentBarPercent();
        
        // パワーバーが満タンまたは空の場合
        if (powerPercent >= 1.0) {
            return getLeftPlayerName();
        }
        if (powerPercent <= 0.0) {
            return getRightPlayerName();
        }
        
        // 時間切れの場合パワーが大きい方が返る
        if (time.getTime() <= 0) {
            return powerPercent >= 0.5 ? getLeftPlayerName() : getRightPlayerName();
        }
        
        return null; // ゲーム続行
    }
    
    @Override
    public State processTimeElapsed(int msec) {
        elapsedCount++;
        
        // 1秒経過の場合
        if (elapsedCount >= 100) {
            time.updateTime();
            elapsedCount = 0; // リセット
        }
        
        // サブクラス固有の時間経過処理
        processGameLogic();
        
        // パワーバーの更新
        updatePowerBar();
        
        // ピンチ時のパワーアップ処理
        handlePowerUpConditions();
        
        return updateState("");
    }
    
    @Override
    public State processKeyTyped(String typed) {
        // サブクラス固有のキー処理
        handleKeyInput(typed);
        
        // パワーバーの更新
        updatePowerBar();
        
        return updateState(typed);
    }
    
    @Override
    public void paintComponent(Graphics g, View view) {
        // 背景画像
        view.drawScaledImage(g, backgroundImage, -150, 0, 0.35);
        
        // プレイヤー情報表示
        g.setColor(Color.WHITE);
        drawPlayerInfo(g);
        
        // アーム画像
        view.drawScaledImage(g, image, 100, 150, 0.5);
        
        // UI要素
        powerBar.showBar(g);
        time.showTime(g);
    }
    
    // サブクラスで実装する抽象メソッド
    protected abstract String getLeftPlayerName();
    protected abstract String getRightPlayerName();
    protected abstract void processGameLogic();
    protected abstract void updatePowerBar();
    protected abstract void handlePowerUpConditions();
    protected abstract void handleKeyInput(String typed);
    protected abstract void drawPlayerInfo(Graphics g);
}