package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private BoardCell[][] layout;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private Set<BoardCell> targets;
	private String clueLegendFile = "ClueFilesUs/ClueLegend.txt";

	public Board(){
		loadBoardConfig();
	}
	
	public void loadBoardConfig() {
		//Load Legend into rooms map
		FileReader clueLegendFileReader = null;
		try {
			clueLegendFileReader = new FileReader(clueLegendFile);
		} catch (FileNotFoundException e) {
			System.out.println("Exception: File Not Found: " + clueLegendFile + "\nExiting program.");
			System.exit(0);
		}
		Scanner scanner = new Scanner(clueLegendFileReader);
		while(scanner.hasNext()){
			String line = scanner.nextLine();
			String[] lineSplits = line.split(",");
			if(lineSplits.length == 2){
				this.rooms.put(lineSplits[0].charAt(0), lineSplits[1]);
			}
			else{
				//throw BadConfigFormatException;
				//if the size does not equal 2, then there is more than one comma. Should we throw BadConfigFormatException here? Probably.
			}
		}
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
		return rooms;
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
