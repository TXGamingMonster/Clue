import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	
	public IntBoard() {
		super();
		
	}
	
	public BoardCell getCell(int r, int c) {
		return new BoardCell();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int roll) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell b) {
		return new LinkedList<BoardCell>();
	}

	
}
