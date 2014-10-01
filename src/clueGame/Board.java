package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private BoardCell[][] layout;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private int numRows;
	private int numColumns;
	private String clueLegendFile;
	private String clueBoardFile;

	public Board(String layout, String legend){
		clueBoardFile = layout;
		clueLegendFile = legend;
		rooms = new HashMap<Character, String>();
		adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
	}

	public void loadBoardConfig() throws BadConfigFormatException {
		//Load Legend into rooms map
		loadLegend();
		//Done loading legend, now load board into layout.
		loadBoardData();
	}

	private void loadBoardData() throws BadConfigFormatException{
		//initialize FileReader and Scanner
		FileReader clueBoardFileReader = null;
		Scanner scanner = null;
		//Try to open the file and set the scanner, throw an exception.
		try {
			clueBoardFileReader = new FileReader(clueBoardFile);
			scanner = new Scanner(clueBoardFileReader);
		} catch (FileNotFoundException e) {
			System.out.println("Exception: File Not Found: " + clueBoardFile + "\nExiting program.");
			System.exit(0);
		}
		finally{
			int rows = 0;//used to keep track of files read in.
			int cols = 0;//used to keep track of columns in current row.
			ArrayList<String[]> tempData = new ArrayList<String[]>(); //used to keep track of rows, and the columns within each row.
			while(scanner.hasNextLine()){
				rows++;
				//move the string in each column to an array.
				String[] toAdd = scanner.nextLine().split(",");
				//if columns hasn't been set, set it to current number of columns in this row.
				if(cols == 0){
					cols = toAdd.length;
				}
				//otherwise check to see if the columns in the first row equals that of the current row. If not throw exception.
				else if(cols != toAdd.length){
					throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at row " + rows + ". (Number of column mismatch)");
				}
				//add column data for this row to the arrayList containing our rows
				tempData.add(toAdd);
				//update the number of rows
				numRows = rows;
			}
			numColumns = cols;
			//set layout equal to the number of rows and columns just read in.
			layout = new BoardCell[numRows][numColumns];
			rows = 0;
			//iterate through rows of data
			for(String[] sa : tempData){
				String[] toAdd = sa;
				//reset cols for iteration
				cols = 0;
				//iterate through the columns in the current row
				for(String s : toAdd){
					//This is used to check if the string is a key from rooms, or a key from rooms concatenated with a direction.
					//If it is not, then an exception is thrown. if if(!validString) is checked.
					boolean validString = false;
					//iterate through the chars read in from the legend. 
					for(Character c : rooms.keySet()){
						//if the string is valid, don't bother comparing to the rest of the chars.
						if(validString)break;
						//make a new boardCell to add into the current cell of layout.
						BoardCell newCell;
						//comvert the character to a string for comparison.
						String tempC = c.toString();
						//if this is not a walkway, check if it's a room. Also check each direction in case it's a door.
						if(c != 'W'){
							if(s.equals(tempC + "U")){
								newCell = new RoomCell(rows,cols, RoomCell.DoorDirection.UP, s.charAt(0));
								layout[rows][cols] = newCell;
								validString = true;
							}
							else if(s.equals(tempC + "D")){
								newCell = new RoomCell(rows, cols, RoomCell.DoorDirection.DOWN, s.charAt(0));
								layout[rows][cols] = newCell;
								validString = true;
							}
							else if(s.equals(tempC + "L")){
								newCell = new RoomCell(rows, cols, RoomCell.DoorDirection.LEFT, s.charAt(0));
								layout[rows][cols] = newCell;
								validString = true;
							}
							else if(s.equals(tempC + "R")){
								newCell = new RoomCell(rows, cols, RoomCell.DoorDirection.RIGHT, s.charAt(0));
								layout[rows][cols] = newCell;
								validString = true;
							}
							else if(s.equals(tempC + "N")){
								newCell = new RoomCell(rows, cols, RoomCell.DoorDirection.NONE, s.charAt(0));
								layout[rows][cols] = newCell;
								validString = true;
							}
							else if(s.equals(tempC)){
								newCell = new RoomCell(rows, cols, RoomCell.DoorDirection.NONE, s.charAt(0));
								layout[rows][cols] = newCell;
								validString = true;
							}
						}
						//otherwise, check if it is a single char 'W'.
						else if(s.equals("W") && s.length() == 1){
							validString = true;
							newCell = new WalkwayCell(rows, cols);
							layout[rows][cols] = newCell;
						}
					}
					if(!validString){
						throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at position (" + (rows) + "," + (cols) + "). (cell label contains unexpected character(s))");
					}
					//iterate the column so we know where to add to layout
					cols++;
				}
				//iterate rows so we know where to add to layout.
				rows++;
			}
			
		}
		//close the file reader. If we can't throw an exception.
		try {
			clueBoardFileReader.close();
		} catch (IOException e) {
			System.out.println("Exception: File: " + clueBoardFile + " did not close properly.\nAttempting to continue..");
			System.exit(0);
		}
	}

	private void loadLegend() throws BadConfigFormatException{
		//initialize file reader and scanner
		FileReader clueLegendFileReader = null;
		Scanner scanner = null;
		//try to open the legend, throw exception if it can't be
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
				//get next line and break it into two sections
				String line = scanner.nextLine();
				String[] lineSplits = line.split(",");
				//if there are more than two sections, it's not formatted properly. Throw an exception
				if(lineSplits.length == 2){
					//otherwise remove the space from the room of the name.
					if(lineSplits[1].charAt(0) == ' ') lineSplits[1] = lineSplits[1].substring(1);
					//and then map the room names value to the char from the legend.
					rooms.put(lineSplits[0].charAt(0), lineSplits[1]);
				}
				else{
					throw new BadConfigFormatException(clueLegendFile + " is improperly formatted at line " + clueLegendLinesRead + ".");
				}
				//increment error tracking counter
				clueLegendLinesRead++;
			}
			//try to close the legend, if it can't be throw exception.
			try {
				clueLegendFileReader.close();
			} catch (IOException e) {
				System.out.println("Exception: File: " + clueLegendFile + " did not close properly.\nAttempting to continue..");
				System.exit(0);
			}
		}
	}

	public void calcAdjacencies() {
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				BoardCell cell = getCellAt(i,j);
				adjacencies.put(cell, getAdjList(i,j));
			}
		}
	}

	public LinkedList<BoardCell> getAdjList(int i, int j) {
		LinkedList<BoardCell> adjacentCells = new LinkedList<BoardCell>();
		//make sure we aren't in a room
		if (layout[i][j].isRoom() && !layout[i][j].isDoorway()) {
			return adjacentCells;
		}
		//check above cell
		if (i > 0 && (layout[i-1][j].isWalkway() || ((layout[i-1][j].isDoorway() && layout[i-1][j].getDoorDirection() ==  RoomCell.DoorDirection.DOWN)))) {
			adjacentCells.add(layout[i-1][j]);
		}
		//check below cell
		if (i< numRows-1 && (layout[i+1][j].isWalkway() || ((layout[i+1][j].isDoorway() && layout[i+1][j].getDoorDirection() ==  RoomCell.DoorDirection.UP)))) {
			adjacentCells.add(layout[i+1][j]);
		}
		//check left cell
		if (j > 0 && (layout[i][j-1].isWalkway() || ((layout[i][j-1].isDoorway() && layout[i][j-1].getDoorDirection() ==  RoomCell.DoorDirection.RIGHT)))) {
			adjacentCells.add(layout[i][j-1]);
		}
		//check right cell
		if (j < numColumns-1 && (layout[i][j+1].isWalkway() || ((layout[i][j+1].isDoorway() && layout[i][j+1].getDoorDirection() ==  RoomCell.DoorDirection.LEFT)))) {
			adjacentCells.add(layout[i][j+1]);
		}
		return adjacentCells;
	}

	public BoardCell getCellAt(int i, int j) {
		return layout[i][j];
	}

	//Not passing tests.
	public void calcTargets(int row, int col, int roll) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		BoardCell cell = getCellAt(row, col);
		recursiveCalcTargets(cell, roll);
	}

	public void recursiveCalcTargets(BoardCell cell, int roll){
		cell = getCellAt(cell.getRow(), cell.getColumn());
		visited.add(cell);
		if(roll == 0){
			targets.add(cell);
		}
		for(BoardCell b : adjacencies.get(cell)){
			if(!visited.contains(b) && roll > 0){
				recursiveCalcTargets(b, roll-1);
				visited.remove(b);
			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	public RoomCell getRoomCellAt(int row, int col){
		return (RoomCell) layout[row][col];
	}

	public int getNumRows(){
		return numRows;
	}
	public int getNumColumns(){
		return numColumns;
	}


}
