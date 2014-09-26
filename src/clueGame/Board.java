package clueGame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private BoardCell[][] layout;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private Set<BoardCell> targets;
	
	public void loadBoardConfig() {
		
	}

	public void calcAdjacencies() {
		
	}

	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return null;
	}

	public BoardCell getCellAt(int i, int j) {
		return null;
	}

	public void calcTargets(int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}

	public Set<BoardCell> getTargets() {
		return null;
	}

	public Map<Character, String> getRooms() {
		return null;
	}
	public RoomCell getRoomCellAt(int row, int col){
		return null;
	}
	
	public int getNumRows(){
		return 0;
	}
	public int getNumColumns(){
		return 0;
	}
	
	
}
