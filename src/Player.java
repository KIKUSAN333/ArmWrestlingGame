
public class Player extends Character{

	String key;
	
	public Player(String name, int power,String kkey) {
		super(name, power);
		key = kkey;
	}

	@Override
	public void doAction() {
		super.doAction();
	}
	
	public String getKey() {
		return key;
	}

	
	
}
