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
	public static final int NUM_ROOMS = 11;//including walkway
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;

	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("BoardLayout.csv", "BoardLegend.txt");
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
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Lounge", rooms.get('A'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Closet", rooms.get('C'));
		assertEquals("DiningRoom", rooms.get('D'));
		assertEquals("Pool", rooms.get('P'));
		assertEquals("Laboratory", rooms.get('F'));
		assertEquals("Ballroom", rooms.get('B'));
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
		assertEquals('A', board.getRoomCellAt(1, 23).getInitial());
		assertEquals('L', board.getRoomCellAt(9, 3).getInitial());
		assertEquals('C', board.getRoomCellAt(11, 12).getInitial());
		assertEquals('D', board.getRoomCellAt(12, 23).getInitial());
		assertEquals('P', board.getRoomCellAt(15, 2).getInitial());
		assertEquals('K', board.getRoomCellAt(21, 22).getInitial());
		assertEquals('F', board.getRoomCellAt(21, 0).getInitial());
		assertEquals('B', board.getRoomCellAt(23, 12).getInitial());
	}

	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Game ctor takes config file names
		ClueGame game = new ClueGame("BoardLayoutBadColumns.csv", "BoardLegend.txt");
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
		ClueGame game = new ClueGame("BoardLayoutBadRoom.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("BoardLayout.csv", "BoardLegendBad.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
}
