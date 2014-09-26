package clueGame;

public class RoomCell extends BoardCell {
	enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE}
	
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

}
