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
import clueGame.RoomCell.DoorDirection;

public class Board {
	private BoardCell[][] layout; //need to add appropriate cell data using boardData, or do this while reading in data instead.
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private int numRows;
	private int numColumns;
	private Set<BoardCell> targets;
	private String clueLegendFile;
	private String clueBoardFile;
	private ArrayList<String[]> boardData = new ArrayList<String[]>(); // the array list holds the rows, while each index of row holds a piece of data corresponding to that row and column.
	
	public Board(String layout, String legend){
		clueBoardFile = layout;
		clueLegendFile = legend;
	}
	
	private void setLayoutCells() throws BadConfigFormatException{
		for(int i = 0; i < boardData.size(); i++){//iterate over arrayList (rows)
			for(int j = 0; j < boardData.get(i).length; j++){//iterate over cols
				for(Character c : rooms.keySet()){
					boolean validString = false;
					if(boardData.get(i)[j].length() > 2){
						throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at position (" + (i+1) + "," + (j+1) + ").");
					}
					String tempC = c.toString();
					if(c != 'W'){
						//check if it is a room, or a door of a room.
						if(boardData.get(i)[j].equals(tempC) || boardData.get(i)[j].equals(tempC + "U") ||boardData.get(i)[j].equals(tempC + "D") ||boardData.get(i)[j].equals(tempC + "L") ||boardData.get(i)[j].equals(tempC + "R")){
							//assign room cell with direction, (I'm sure there's a better way to do this than checking which direction it is twice.)
							
							if(boardData.get(i)[j].equals(tempC + "U")){
								layout[i][j] = new RoomCell(i,j,DoorDirection.UP);
							}
							else if(boardData.get(i)[j].equals(tempC + "D")){
								layout[i][j] = new RoomCell(i,j,DoorDirection.DOWN);
							}
							else if(boardData.get(i)[j].equals(tempC + "L")){
								layout[i][j] = new RoomCell(i,j,DoorDirection.LEFT);
							}
							else if(boardData.get(i)[j].equals(tempC + "R")){
								layout[i][j] = new RoomCell(i,j,DoorDirection.RIGHT);
							}
							else if(boardData.get(i)[j].equals(tempC)){
								layout[i][j] = new RoomCell(i,j,DoorDirection.NONE);
							}
							validString = true;
						}
					}
					//check if it is a walkway
					else if(boardData.get(i)[j].equals("W") && boardData.get(i).length == 1){
						validString = true;
						layout[i][j] = new WalkwayCell(i, j);
					}
					if(!validString){
						throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at position (" + (i+1) + "," + (j+1) + ").");
					}
				}
			}
		}
	}
	

	public void loadBoardConfig() throws BadConfigFormatException {
		//Load Legend into rooms map
		loadLegend();
		//Done loading legend, now load board
		loadBoardData();
		//Finally, set the board up
		setLayoutCells();
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
		Map<Character, String> rooms = new HashMap<Character, String>();
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
					rooms.put(lineSplits[0].replaceAll("\\s+","").charAt(0), lineSplits[1].replaceAll("\\s+",""));
				}
				else{
					throw new BadConfigFormatException(clueLegendFile + " is improperly formatted at line " + clueLegendLinesRead + ".");
					//if the size does not equal 2, then there is more than one comma or none at all.
					//Should we throw BadConfigFormatException here? Probably.
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
		return layout[i][j];
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
