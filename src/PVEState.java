
public class PVEState extends GameBaseState {
    private Player player;
    private Enemy enemy;
    
    public PVEState(int level) {
        super();
        player = new Player("PLAYER", 1, "ENTER");
        enemy = new Enemy("ENEMY", 1, level);
    }
    
    @Override
    protected String getLeftPlayerName() {
        return player.getName();
    }
    
    @Override
    protected String getRightPlayerName() {
        return enemy.getName();
    }
    
    @Override
    protected void processGameLogic() {
        enemy.doAction();
    }
    
    @Override
    protected void updatePowerBar() {
        powerBar.updateBar(player.getPower(), enemy.getPower());
    }
    
    @Override
    protected void handlePowerUpConditions() {
        // CPUがピンチになったら
        if (powerBar.getCurrentBarPercent() >= 0.7 && !enemy.getIsPowerUp()) {
            enemy.basePowerUp();
        }
        
        // プレイヤーがピンチになったら
        if (powerBar.getCurrentBarPercent() <= 0.3 && !player.getIsPowerUp()) {
            player.basePowerUp();
        }
    }
    
    @Override
    protected void handleKeyInput(String typed) {
        // 入力したキーが対応するキーであった場合
        if (typed.equals(player.getKey())) {
            player.doAction();
        }
    }

	@Override
	protected String getCharacter1Name() {
		return null;
	}

	@Override
	protected String getCharacter2Name() {
		return null;
	}
    
}