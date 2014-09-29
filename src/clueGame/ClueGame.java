package clueGame;

import java.util.Map;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board gameBoard;
	
	public ClueGame(String layout, String legend) {
		gameBoard = new Board(layout, legend);
		loadConfigFiles();
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
		// TODO Auto-generated method stub
		
	}
	
}
