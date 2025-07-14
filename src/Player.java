
public class Player extends Character{

	String key;
	
	public Player(String name, int power,String kkey) {
		super(name, power);
		key = kkey;
	}
	
	public String getKey() {
		return key;
	}

	
	
}
