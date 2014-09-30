package clueGame;

import java.util.Map;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board gameBoard;
	//The CR tests initialize with no parameters and we can't change the tests she wrote. So we need to have this as well.
	public ClueGame(){
		
	}
	//The file names have to include the folder name before the file, eclipse looks for the files in the root directory of the project which in our case they aren't.
	public ClueGame(String layout, String legend) {
		gameBoard = new Board(layout, legend);
		rooms = gameBoard.getRooms();
	}
	
	public void loadConfigFiles() {
		try {
			gameBoard.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	public Board getBoard(){
		return gameBoard;
	}
	public void loadRoomConfig() {
		
		
	}
	
}
