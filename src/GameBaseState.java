import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public abstract class GameBaseState implements State {
    protected PowerBar powerBar;
    protected Time time;
    protected Map<Integer, Image> armImages;
    protected Image backgroundImage;
    protected int elapsedCount;
    
    // 腕の角度の範囲
    private static final int MIN_ARM_ANGLE = -80;
    private static final int MAX_ARM_ANGLE = 80;
    private static final int ARM_ANGLE_STEP = 10;
    
    public GameBaseState() {
        powerBar = new PowerBar();
        time = new Time();
        elapsedCount = 0;
        
        // 腕画像を事前に全て読み込む
        loadArmImages();
        
        // 背景画像を読み込む
        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
    }
    
    /**
     * 腕の画像を全て読み込んでMapに格納
     */
    private void loadArmImages() {
        armImages = new HashMap<>();
        
        for (int angle = MIN_ARM_ANGLE; angle <= MAX_ARM_ANGLE; angle += ARM_ANGLE_STEP) {
            String filename = "arm_" + String.format("%02d", angle) + ".png";
            try {
                Image armImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(filename));
                armImages.put(angle, armImage);
            } catch (Exception e) {
                System.err.println("画像の読み込みに失敗しました: " + filename);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * パワーバーの値に基づいて適切な腕の角度を計算
     * @return 腕の角度（-80から80の範囲）
     */
    private int calculateArmAngle() {
        double powerPercent = powerBar.getCurrentBarPercent();
        
        // パワーバーの値（0.0-1.0）を腕の角度（-80から80）にマッピング
        // 0.0 → -80度（右側に傾く）
        // 0.5 → 0度（中央）
        // 1.0 → 80度（左側に傾く）
        int angle = (int) (MIN_ARM_ANGLE + (powerPercent * (MAX_ARM_ANGLE - MIN_ARM_ANGLE)));
        
        // 10度単位に丸める
        angle = Math.round(angle / (float) ARM_ANGLE_STEP) * ARM_ANGLE_STEP;
        
        // 範囲内に収める
        angle = Math.max(MIN_ARM_ANGLE, Math.min(MAX_ARM_ANGLE, angle));
        
        return angle;
    }
    
    /**
     * 現在のパワーバーの値に応じた腕の画像を取得
     * @return 腕の画像
     */
    private Image getCurrentArmImage() {
        int angle = calculateArmAngle();
        Image armImage = armImages.get(angle);
        
        // 画像が見つからない場合は中央の画像を使用
        if (armImage == null) {
            armImage = armImages.get(0);
        }
        
        return armImage;
    }
    
    protected State updateState(String typed) {
        // ゲーム終了条件をチェック
        String winner = getWinner();
        String player1 = getCharacter1Name();
        String player2 = getCharacter2Name();
        
        if (winner != null) {
            return new ResultState(winner, powerBar, getCurrentArmImage(),player1,player2);
        }
        
        // ボスが来た画面の移行
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
        
        
        // パワーバーの値に応じた腕画像を描画
        Image currentArmImage = getCurrentArmImage();
        if (currentArmImage != null) {
            g.drawImage(currentArmImage, -70, 0, view);
        }
        
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
    protected abstract String getCharacter1Name();
    protected abstract String getCharacter2Name();
}