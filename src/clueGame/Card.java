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
	
	public Card(String n, String c) {
		// TODO Auto-generated constructor stub
		name = n;
		switch(c) {
		case "PERSON": type = CardType.PERSON; break;
		case "WEAPON": type = CardType.WEAPON; break;
		case "ROOM": type = CardType.ROOM; break;
		}
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
		case ROOM: return this.name + " : Room";
		default: return name;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Card c = (Card)obj;
		return this.type == c.getType();
	}
}
