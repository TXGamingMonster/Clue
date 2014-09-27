package clueTests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.RoomCell;

public class FileInitTests {

	private static Board board;
	public static final int NUM_ROOMS = 10;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;

	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Study", rooms.get('S'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}

	// Test a doorway in each direction, plus two cells that are not
	// a doorway.
	@Test
	public void FourDoorDirections() {
		// Test one each RIGHT/LEFT/UP/DOWN
		RoomCell room = board.getRoomCellAt(3, 6);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(4, 9);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(16, 9);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		room = board.getRoomCellAt(8, 6);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(0, 0);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(6, 5);
		assertFalse(cell.isDoorway());		

	}

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(576, totalCells);
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(17, numDoors);
	}

	// Test a few room cells to ensure the room initial is
	// correct.
	@Test
	public void testRoomInitials() {
		assertEquals('S', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('H', board.getRoomCellAt(4, 11).getInitial());
		assertEquals('L', board.getRoomCellAt(9, 3).getInitial());
		assertEquals('K', board.getRoomCellAt(21, 22).getInitial());
		assertEquals('F', board.getRoomCellAt(21, 0).getInitial());
	}

	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Game ctor takes config file names
		ClueGame game = new ClueGame("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		// You may change these calls if needed to match your function names
		// My loadConfigFiles has a try/catch, so I can't call it directly to
		// see test throwing the BadConfigFormatException
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegendBadFormat.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
}
<<<<<<< HEAD
=======

>>>>>>> f2e8367716ca87f30b6d905016b372bfadb58e56
