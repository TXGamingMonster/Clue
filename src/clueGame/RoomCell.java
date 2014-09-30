package clueGame;

public class RoomCell extends BoardCell {
	//this needs to be public for test "FourDoorDirections" unless that test is changed.
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE}
	
	private DoorDirection doorDirection;
	private char roomInitial;

	public RoomCell(int row, int column, DoorDirection dir, char initial) {
		super(row, column);
		this.doorDirection = dir;
		this.roomInitial = initial;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if(this.doorDirection != DoorDirection.NONE) return true;
		return super.isDoorway();
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		//return null char
		return roomInitial;
	}

}
