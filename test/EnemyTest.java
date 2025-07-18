import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EnemyTest {
    
    @Test
    void エネミーのコンストラクタで名前とパワーとレベルが正しく設定されるかテスト() {
        Enemy enemy = new Enemy("テストエネミー", 50, 1);
        
        assertEquals("テストエネミー", enemy.getName());
        assertEquals(50, enemy.getPower());
        assertEquals(false, enemy.getIsPowerUp());
    }
    
    @Test
    void レベル1のエネミーがbaseActionPointの範囲内で行動するかテスト() {
        Enemy enemy = new Enemy("レベル1エネミー", 50, 1);
        
        // 複数回テストして範囲を確認
        for (int i = 0; i < 10; i++) {
            Enemy testEnemy = new Enemy("テスト", 50, 1);
            int initialPower = testEnemy.getPower();
            
            // baseActionPointは20-39の範囲なので、最大39回doActionを実行
            for (int j = 0; j < 39; j++) {
                testEnemy.doAction();
            }
            
            // 少なくとも1回はsuperクラスのdoActionが実行されているはず
            assertTrue(testEnemy.getPower() >= initialPower + 1);
        }
    }
    
    @Test
    void レベル2のエネミーがbaseActionPointの範囲内で行動するかテスト() {
        Enemy enemy = new Enemy("レベル2エネミー", 50, 2);
        
        // 複数回テストして範囲を確認
        for (int i = 0; i < 10; i++) {
            Enemy testEnemy = new Enemy("テスト", 50, 2);
            int initialPower = testEnemy.getPower();
            
            // baseActionPointは10-19の範囲なので、最大19回doActionを実行
            for (int j = 0; j < 19; j++) {
                testEnemy.doAction();
            }
            
            // 少なくとも1回はsuperクラスのdoActionが実行されているはず
            assertTrue(testEnemy.getPower() >= initialPower + 1);
        }
    }
    
    @Test
    void レベル3のエネミーがbaseActionPointの範囲内で行動するかテスト() {
        Enemy enemy = new Enemy("レベル3エネミー", 50, 3);
        
        // 複数回テストして範囲を確認
        for (int i = 0; i < 10; i++) {
            Enemy testEnemy = new Enemy("テスト", 50, 3);
            int initialPower = testEnemy.getPower();
            
            // baseActionPointは5-9の範囲なので、最大9回doActionを実行
            for (int j = 0; j < 9; j++) {
                testEnemy.doAction();
            }
            
            // 少なくとも1回はsuperクラスのdoActionが実行されているはず
            assertTrue(testEnemy.getPower() >= initialPower + 1);
        }
    }
    
    @Test
    void レベル4以上のエネミーが固定のbaseActionPointで行動するかテスト() {
        Enemy enemy = new Enemy("レベル4エネミー", 50, 4);
        
        int initialPower = enemy.getPower();
        
        // baseActionPointは固定で10なので、10回doActionを実行
        for (int i = 0; i < 10; i++) {
            enemy.doAction();
        }
        
        // ちょうど1回superクラスのdoActionが実行されているはず
        assertEquals(initialPower + 1, enemy.getPower());
    }
    
    @Test
    void doActionPointがbaseActionPointに達するまではパワーが増加しないかテスト() {
        Enemy enemy = new Enemy("テストエネミー", 50, 4); // レベル4で固定baseActionPoint=10
        
        int initialPower = enemy.getPower();
        
        // 9回doActionを実行（baseActionPointの10未満）
        for (int i = 0; i < 9; i++) {
            enemy.doAction();
        }
        
        // パワーは増加していないはず
        assertEquals(initialPower, enemy.getPower());
        
        // 10回目で増加
        enemy.doAction();
        assertEquals(initialPower + 1, enemy.getPower());
    }
    
    @Test
    void doActionPointがbaseActionPointに達した後リセットされるかテスト() {
        Enemy enemy = new Enemy("テストエネミー", 50, 4); // レベル4で固定baseActionPoint=10
        
        int initialPower = enemy.getPower();
        
        // 10回doActionを実行してパワーを1増加
        for (int i = 0; i < 10; i++) {
            enemy.doAction();
        }
        assertEquals(initialPower + 1, enemy.getPower());
        
        // さらに9回実行してもパワーは増加しない（リセットされている）
        for (int i = 0; i < 9; i++) {
            enemy.doAction();
        }
        assertEquals(initialPower + 1, enemy.getPower());
        
        // 10回目で再度増加
        enemy.doAction();
        assertEquals(initialPower + 2, enemy.getPower());
    }
    
    @Test
    void basePowerUpメソッド実行後のdoActionで増加パワーが変わるかテスト() {
        Enemy enemy = new Enemy("テストエネミー", 50, 4); // レベル4で固定baseActionPoint=10
        
        // basePowerUpを実行
        enemy.basePowerUp();
        
        int initialPower = enemy.getPower();
        
        // 10回doActionを実行
        for (int i = 0; i < 10; i++) {
            enemy.doAction();
        }
        
        // パワーが2増加していることを確認（basePowerが2になっているため）
        assertEquals(initialPower + 2, enemy.getPower());
        assertEquals(true, enemy.getIsPowerUp());
    }
    
    @Test
    void 異なるレベルのエネミーが適切な頻度で行動するかテスト() {
        Enemy level1 = new Enemy("レベル1", 0, 1);
        Enemy level2 = new Enemy("レベル2", 0, 2);
        Enemy level3 = new Enemy("レベル3", 0, 3);
        
        // 100回doActionを実行
        for (int i = 0; i < 100; i++) {
            level1.doAction();
            level2.doAction();
            level3.doAction();
        }
        
        // レベルが高いほど行動頻度が高い（パワーの増加が多い）ことを確認
        assertTrue(level3.getPower() > level2.getPower());
        assertTrue(level2.getPower() > level1.getPower());
    }
}