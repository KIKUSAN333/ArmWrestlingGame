
abstract class Character {
	private String name;
	private int power;
	private Model model;
	private int basePower;
	private boolean isPowerUp;
	
	public Character(Model model,String name, int power) {
		super();
		this.name = name;
		this.power = power;
		this.model = model;
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
