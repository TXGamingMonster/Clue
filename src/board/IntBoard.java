package board;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private static final int NUM_ROWS = 4;
	private static final int NUM_COLUMNS = 4;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] board;

	public IntBoard() {
		super();
		adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		board = new BoardCell[NUM_ROWS][NUM_COLUMNS];
		for (int r=0; r<NUM_ROWS; r++) {
			for (int c=0; c<NUM_COLUMNS; c++) {
				board[r][c] = new BoardCell(r,c);
			}
		}
	}

	public BoardCell getCell(int r, int c) {
		return board[r][c];
	}

	public void calcAdjacencies() {
		for (int r=0; r<NUM_ROWS; r++) {
			for (int c=0; c<NUM_COLUMNS; c++) {
				adjacencies.put(board[r][c], getAdjList(board[r][c]));
			}
		}
	}

	public void calcTargets(BoardCell cell, int roll) {
		cell = getCell(cell.getRow(), cell.getColumn());
		//add cell to visited list, as we are at 
		visited.add(cell);
		//check if we have moved as far as possible, if so then add as a valid target.
		if(roll == 0){
			targets.add(cell);
		}
		for (BoardCell b : adjacencies.get(cell)) {
			if (!visited.contains(b))
				calcTargets(b, roll-1);
		}

	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public LinkedList<BoardCell> getAdjList(BoardCell b) {
		LinkedList<BoardCell> adjacentCells = new LinkedList<BoardCell>();
		if(b.getRow() > 0) {
			adjacentCells.add(new BoardCell(b.getRow()-1, b.getColumn()));
		}
		if(b.getColumn() > 0) {
			adjacentCells.add(new BoardCell(b.getRow(), b.getColumn()-1));
		}
		if(b.getRow() < NUM_ROWS-1) {
			adjacentCells.add(new BoardCell(b.getRow()+1, b.getColumn()));
		}
		if(b.getColumn() < NUM_COLUMNS-1) {
			adjacentCells.add(new BoardCell(b.getRow(), b.getColumn()+1));
		}

		return adjacentCells;
	}

}
