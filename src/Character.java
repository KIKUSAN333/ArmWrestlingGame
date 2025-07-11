
abstract class Character {
	private String name;
	private int power;
	private int basePower;
	private boolean isPowerUp;
	
	protected Character(String name, int power) {
		super();
		this.name = name;
		this.power = power;
		basePower = 1;
		isPowerUp = false;
	}

	public void doAction() {
		applyPower(basePower);
	}
	
	public void applyPower(int amount) {
		power += amount;
	}
	
	public int getPower() {
		return power;
	}
	
	public void basePowerUp() {
		this.basePower++;
		isPowerUp = true;
	}
	
	public boolean getIsPowerUp(){
		return isPowerUp;
	}
	
	public String getName() {
		return name;
	}
	
}
