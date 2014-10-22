package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class WalkwayCell extends BoardCell {
	public WalkwayCell(int row, int column) {
		super(row, column);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isWalkway() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void draw(Graphics g, int x, int y, int ratio, int ratio2) {
		// TODO Auto-generated method stub
		g.setColor(new Color(242,205,30));
		g.fillRect(x*ratio+10, y*ratio2+10, 25, 25);
	}

}
