import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CharacterTest {
    
    // テスト用の具象クラス
    private static class TestCharacter extends Character {
        public TestCharacter(String name, int power) {
            super(name, power);
        }
    }
    
    @Test
    void キャラクターのコンストラクタで名前とパワーが正しく設定されるかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        assertEquals("テストキャラクター", character.getName());
        assertEquals(100, character.getPower());
        assertEquals(false, character.getIsPowerUp());
    }
    
    @Test
    void doActionメソッドでベースパワーがパワーに加算されるかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        // 初期状態のパワーを確認
        int initialPower = character.getPower();
        
        // doActionを実行（basePowerは初期値1）
        character.doAction();
        
        // パワーが1増加していることを確認
        assertEquals(initialPower + 1, character.getPower());
    }
    
    @Test
    void applyPowerメソッドでパワーが指定した量だけ増加するかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        int initialPower = character.getPower();
        
        // 50パワーを追加
        character.applyPower(50);
        assertEquals(initialPower + 50, character.getPower());
        
        // さらに25パワーを追加
        character.applyPower(25);
        assertEquals(initialPower + 75, character.getPower());
    }
    
    @Test
    void basePowerUpメソッドでベースパワーが増加しパワーアップ状態になるかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        // 初期状態はパワーアップしていない
        assertEquals(false, character.getIsPowerUp());
        
        // basePowerUpを実行
        character.basePowerUp();
        
        // パワーアップ状態になっていることを確認
        assertEquals(true, character.getIsPowerUp());
    }
    
    @Test
    void basePowerUpメソッド実行後のdoActionで増加パワーが変わるかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        // 初期状態でdoActionを実行
        int initialPower = character.getPower();
        character.doAction();
        int powerAfterFirstAction = character.getPower();
        
        // basePowerUpを実行
        character.basePowerUp();
        
        // 再度doActionを実行
        character.doAction();
        int powerAfterSecondAction = character.getPower();
        
        // 最初のdoActionでは1増加、2回目のdoActionでは2増加していることを確認
        assertEquals(initialPower + 1, powerAfterFirstAction);
        assertEquals(powerAfterFirstAction + 2, powerAfterSecondAction);
    }
    
    @Test
    void 複数回basePowerUpを実行してベースパワーが累積されるかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        // 3回basePowerUpを実行
        character.basePowerUp();
        character.basePowerUp();
        character.basePowerUp();
        
        int initialPower = character.getPower();
        
        // doActionを実行（basePowerは4になっているはず）
        character.doAction();
        
        // パワーが4増加していることを確認
        assertEquals(initialPower + 4, character.getPower());
        assertEquals(true, character.getIsPowerUp());
    }
    
    @Test
    void 負の値でapplyPowerを実行してパワーが減少するかテスト() {
        Character character = new TestCharacter("テストキャラクター", 100);
        
        int initialPower = character.getPower();
        
        // 負の値でパワーを適用
        character.applyPower(-30);
        
        // パワーが30減少していることを確認
        assertEquals(initialPower - 30, character.getPower());
    }
}