package clueGame;

public class Solution {
	private String person, weapon, room;

	public Solution(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	public boolean isSolution(String person, String weapon, String room)
	{
		return true;//this.person.equals(person)&&this.weapon.equals(weapon)&&this.room.equals(room);
	}
	public boolean hasSolution() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
