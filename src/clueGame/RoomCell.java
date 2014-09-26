package clueGame;

public class RoomCell extends BoardCell {
	//this needs to be public for test "FourDoorDirections" unless that test is changed.
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE}
	
	private DoorDirection doorDirection;
	private char roomInitial;

	public RoomCell(int row, int column) {
		super(row, column);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}

	public String getDoorDirection() {
		return null;
	}

	public char getInitial() {
		//return null char
		return '\u0000';
	}

}
