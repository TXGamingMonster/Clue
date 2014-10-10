package clueGame;

public class Card {
	
	public enum CardType{PERSON,WEAPON,ROOM};
	
	public String name;
	public CardType type;
	
	public Card(String n, int t) {
		// TODO Auto-generated constructor stub
		name = n;
		switch(t) {
			case 1: type = CardType.PERSON; break;
			case 2: type = CardType.ROOM; break;
			case 3: type = CardType.WEAPON; break;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public CardType getType() {
		return type;
	}
}
