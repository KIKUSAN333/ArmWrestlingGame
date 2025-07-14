import java.awt.Graphics;

public class PVPState extends GameBaseState {
    private Player player1;
    private Player player2;
    
    public PVPState(String player1Name, String player2Name) {
        super();
        player1 = new Player(player1Name, 1, "ENTER");
        player2 = new Player(player2Name, 1, " ");
    }
    
    @Override
    protected String getLeftPlayerName() {
        return player1.getName();
    }
    
    @Override
    protected String getRightPlayerName() {
        return player2.getName();
    }
    
    @Override
    protected void processGameLogic() {
        // PVPでは自動的なゲームロジックなし
        // 必要に応じて実装
    }
    
    @Override
    protected void updatePowerBar() {
        powerBar.updateBar(player1.getPower(), player2.getPower());
    }
    
    @Override
    protected void handlePowerUpConditions() {
        // 右プレイヤーがピンチになったら
        if (powerBar.getCurrentBarPercent() >= 0.7 && !player2.getIsPowerUp()) {
            player2.basePowerUp();
        }
        
        // 左プレイヤーがピンチになったら
        if (powerBar.getCurrentBarPercent() <= 0.3 && !player1.getIsPowerUp()) {
            player1.basePowerUp();
        }
    }
    
    @Override
    protected void handleKeyInput(String typed) {
        // プレイヤー1のキー入力
        if (typed.equals(player1.getKey())) {
            player1.doAction();
        }
        
        // プレイヤー2のキー入力
        if (typed.equals(player2.getKey())) {
            player2.doAction();
        }
    }
    
    @Override
    protected void drawPlayerInfo(Graphics g) {
        g.drawString(player1.getName() + " : " + player1.getPower(), 0, 100);
        g.drawString(player2.getName() + " : " + player2.getPower(), 200, 100);
    }
}