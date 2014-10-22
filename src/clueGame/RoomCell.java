package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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

	@Override
	public void draw(Graphics g, int x, int y, int ratio, int ratio2) {
		if(roomInitial == 'X')
			g.setColor(Color.BLACK);
		else g.setColor(Color.GRAY);
		if(isDoorway())
		{
			g.setColor(Color.GRAY.darker());
			g.fillRect(x*ratio+10, y*ratio2+10, 25, 25);
			g.setColor(Color.RED);
			switch(doorDirection) {
				case UP: g.fillRect(x*ratio+10, y*ratio2+5, 25, 25/4); break;
				case DOWN: g.fillRect(x*ratio+10, y*ratio2+35, 25, 25/4); break;
				case LEFT: g.fillRect(x*ratio+5, y*ratio2+10, 25/4, 25); break;
				case RIGHT: g.fillRect(x*ratio+35, y*ratio2+10, 25/4, 25); break;
			}
			
		}
		else g.fillRect(x*ratio+10, y*ratio2+10, 25, 25);
	}
}
