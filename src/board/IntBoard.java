package board;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard() {
		super();
		adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
	}
	
	public BoardCell getCell(int r, int c) {
		return new BoardCell(r, c);
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int roll) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell b) {
		LinkedList<BoardCell> adjacentCells = new LinkedList<BoardCell>();
		if(b.getRow() > 0) {
			adjacentCells.add(new BoardCell(b.getRow()-1, b.getColumn()));
		}
		if(b.getColumn() > 0) {
			adjacentCells.add(new BoardCell(b.getRow(), b.getColumn()-1));
		}
		if(b.getRow() < 3) {
			adjacentCells.add(new BoardCell(b.getRow()+1, b.getColumn()));
		}
		if(b.getColumn() < 3) {
			adjacentCells.add(new BoardCell(b.getRow(), b.getColumn()+1));
		}
		
		return adjacentCells;
	}

	
}
