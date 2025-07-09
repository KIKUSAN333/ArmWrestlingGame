
abstract class Character {
	private String name;
	private int power;
	private Model model;
	
	public Character(Model model,String name, int power) {
		super();
		this.name = name;
		this.power = power;
		this.model = model;
	}

	public void doAction() {
		applyPower(1);
	}
	
	public void applyPower(int amount) {
		power += amount;
	}
	
	public int getPower() {
		return power;
	}
}
