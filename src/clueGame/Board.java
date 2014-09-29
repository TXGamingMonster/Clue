package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import clueGame.BadConfigFormatException;//used to throw error in loadBoardConfig

public class Board {
	private BoardCell[][] layout;
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private int numRows;
	private int numColumns;
	private Set<BoardCell> targets;
	private String clueLegendFile = "ClueFilesUs/ClueLegend.txt";
	private String clueBoardFile = "ClueFilesUs/BoardLayout.csv";
	private ArrayList<String[]> boardData = new ArrayList<String[]>();
	
	public Board(){
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			System.out.println("Program will now exit.");
			System.exit(0);
		}
	}

	public void loadBoardConfig() throws BadConfigFormatException {
		//Load Legend into rooms map
		loadLegend();
		//Done loading legend, now load board
		loadBoardData();
	}
	
	private void loadBoardData() throws BadConfigFormatException{
		FileReader clueBoardFileReader = null;
		Scanner scanner = null;
		try {
			clueBoardFileReader = new FileReader(clueBoardFile);
			scanner = new Scanner(clueBoardFileReader);
		} catch (FileNotFoundException e) {
			System.out.println("Exception: File Not Found: " + clueBoardFile + "\nExiting program.");
			System.exit(0);
		}
		finally{
			//each String[] in boardData is an array of the column data for the row at that index
			while(scanner.hasNext()){
				//the file should be comma delimited, so each row data is the line read in split by a comma, while the row is simply the line read in.
				String[] toAdd = scanner.nextLine().split(",");
				if(numColumns == 0){
					//set column length equal to the first row that contains column data
					numColumns = toAdd.length;
				}
				if(numColumns != toAdd.length){
					throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at row " + numRows+1 + ".");
				}
				//not sure how we should check if the row[column] data is valid.
				boardData.add(toAdd);
				numRows = boardData.size();//update number of rows that have been read in 
			}
		}
		try {
			clueBoardFileReader.close();
		} catch (IOException e) {
			System.out.println("Exception: File: " + clueBoardFile + " did not close properly.\nAttempting to continue..");
			System.exit(0);
		}
	}
	
	private void loadLegend() throws BadConfigFormatException{
		FileReader clueLegendFileReader = null;
		Scanner scanner = null;
		try {
			clueLegendFileReader = new FileReader(clueLegendFile);
			scanner = new Scanner(clueLegendFileReader);
		} catch (FileNotFoundException e) {
			System.out.println("Exception: File Not Found: " + clueLegendFile + "\nExiting program.");
			System.exit(0);
		}
		finally{
			int clueLegendLinesRead = 1;//keep a counter for throwing errors for specific lines.
			while(scanner.hasNext()){
				String line = scanner.nextLine();
				String[] lineSplits = line.split(",");
				if(lineSplits.length == 2){
					//the replace all removes spaces as well as non visible characters.
					this.rooms.put(lineSplits[0].replaceAll("\\s+","").charAt(0), lineSplits[1].replaceAll("\\s+",""));
				}
				else{
					throw new BadConfigFormatException(clueLegendFile + " is improperly formatted at line " + clueLegendLinesRead + ".");
					//if the size does not equal 2, then there is more than one comma. Should we throw BadConfigFormatException here? Probably.
				}
				//increment error tracking counter
				clueLegendLinesRead++;
			}
			try {
				clueLegendFileReader.close();
			} catch (IOException e) {
				System.out.println("Exception: File: " + clueLegendFile + " did not close properly.\nAttempting to continue..");
				System.exit(0);
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
		return numRows;
	}
	public int getNumColumns(){
		return numColumns;
	}


}
