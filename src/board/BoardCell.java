package board;

public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getRow() == ((BoardCell) obj).getRow() && this.getColumn() == ((BoardCell) obj).getColumn())
			return true;
		return false;
	}

}
