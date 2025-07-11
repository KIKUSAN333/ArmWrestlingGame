
public class Player extends Character{

	String key;
	
	public Player(String name, int power,String kkey) {
		super(name, power);
		key = kkey;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doAction() {
		// TODO 自動生成されたメソッド・スタブ
		super.doAction();
	}
	
	public String getKey() {
		return key;
	}

	
	
}
