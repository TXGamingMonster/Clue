package clueGame;

public class Card {
	
	public enum CardType{PERSON,WEAPON,ROOM};
	
	public String name;
	public CardType type;
	
	public Card(String n, CardType c) {
		// TODO Auto-generated constructor stub
		name = n;
		type = c;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(CardType type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public CardType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch(type) {
		case PERSON: return this.name + " : Person";
		case WEAPON: return this.name + " : Weapon";
		case ROOM: return this.name + " : Person";
		default: return name;
		}
	}
}
