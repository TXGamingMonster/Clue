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
			int rows = 0;
			int cols = 0;
			ArrayList<String[]> tempData = new ArrayList<String[]>();
			while(scanner.hasNextLine()){
				rows++;
				String[] toAdd = scanner.nextLine().split(",");
				if(cols == 0){
					//set column length equal to the first row that contains column data
					cols = toAdd.length;
				}
				if(cols != toAdd.length){
					throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at row " + rows + ". (Number of column mismatch)");
				}
				tempData.add(toAdd);
				numRows = rows;
			}
			numColumns = cols;
			layout = new BoardCell[numRows][numColumns];
			//each String[] in boardData is an array of the column data for the row at that index
			rows = 0;
			for(String[] sa : tempData){
				//the file should be comma delimited, so each row data is the line read in split by a comma, while the row is simply the line read in.
				String[] toAdd = sa;
				cols = 0;
				for(String s : toAdd){
					boolean validString = false;
					for(Character c : rooms.keySet()){
						if(validString)break;
						//if(s.length() > 2){
						//	throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at position (" + (rows) + "," + (cols) + "). (cell label contains unexpected character(s))");
						//}
						BoardCell newCell;
						String tempC = c.toString();
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
						else if(s.equals("W") && s.length() == 1){
							validString = true;
							newCell = new WalkwayCell(rows, cols);
							layout[rows][cols] = newCell;
						}
					}
					if(!validString){
						throw new BadConfigFormatException(clueBoardFile + " is improperly formatted at position (" + (rows) + "," + (cols) + "). (cell label contains unexpected character(s))");
					}
					cols++;
				}
				rows++;
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
					if(lineSplits[1].charAt(0) == ' ') lineSplits[1] = lineSplits[1].substring(1);
					rooms.put(lineSplits[0].charAt(0), lineSplits[1]);
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
