package clueTests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class BoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("ClueFilesUs/BoardLayout.csv", "ClueFilesUs/BoardLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(3, 1);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(12, 3);
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(2, 12);
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(7, 6);
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(22, 8);
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are BROWN on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<BoardCell> testList = board.getAdjList(8, 6);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(8, 7)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(4, 9);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(4, 8)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(6, 11);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(7, 11)));
		//TEST DOORWAY UP
		testList = board.getAdjList(12, 0);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(11, 0)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(19, 5);
		Assert.assertTrue(testList.contains(board.getCellAt(18, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(19, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(19, 4)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(7, 12);
		Assert.assertTrue(testList.contains(board.getCellAt(6, 12)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 11)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(12, 16);
		Assert.assertTrue(testList.contains(board.getCellAt(12, 17)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(13, 16)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(15, 15);
		Assert.assertTrue(testList.contains(board.getCellAt(15, 14)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(14, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 15)));
		Assert.assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, two walkway pieces
		LinkedList<BoardCell> testList = board.getAdjList(0, 7);
		Assert.assertTrue(testList.contains(board.getCellAt(0, 8)));
		Assert.assertTrue(testList.contains(board.getCellAt(1, 7)));
		Assert.assertEquals(2, testList.size());
		
		// Test on left edge of board, two walkway pieces
		testList = board.getAdjList(4, 0);
		Assert.assertTrue(testList.contains(board.getCellAt(5, 0)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 1)));
		Assert.assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(11, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(11, 3)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 5)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(12,7);
		Assert.assertTrue(testList.contains(board.getCellAt(12, 8)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 7)));
		Assert.assertTrue(testList.contains(board.getCellAt(13, 7)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, in a corner
		testList = board.getAdjList(23, 9);
		Assert.assertTrue(testList.contains(board.getCellAt(23, 8)));
		Assert.assertEquals(1, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(15, 23);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 23)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 22)));
		Assert.assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(18, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(18, 3)));
		Assert.assertTrue(testList.contains(board.getCellAt(18, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(17, 4)));
		Assert.assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(23, 6, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(23, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(22, 6)));	
		
		board.calcTargets(5, 0, 1);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(4, 0)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 1)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(23, 6, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(23, 8)));
		Assert.assertTrue(targets.contains(board.getCellAt(21, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(22, 7)));
		
		board.calcTargets(5, 0, 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(4, 1)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 2)));	
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(23, 6, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(20, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(21, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(22, 7)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(5, 0, 4);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(5, 2)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 3)));	
		Assert.assertTrue(targets.contains(board.getCellAt(5, 4)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT GREEN on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(5, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(4, 3)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 4)));	
		Assert.assertTrue(targets.contains(board.getCellAt(4, 5)));	
		Assert.assertTrue(targets.contains(board.getCellAt(4, 1)));	
		Assert.assertTrue(targets.contains(board.getCellAt(5, 6)));	
	}	
	
	// Test getting into a room
	// These are LIGHT GREEN on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(15, 7, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		// directly left and right
		Assert.assertTrue(targets.contains(board.getCellAt(15, 5)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 9)));
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(13, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 7)));
		// one up/down, one left/right (can't go down/right)
		Assert.assertTrue(targets.contains(board.getCellAt(14, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(14, 8)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 6)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(8, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(5, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(11, 7)));
		// right one then up two
		Assert.assertTrue(targets.contains(board.getCellAt(6, 8)));
		// up one then right two
		Assert.assertTrue(targets.contains(board.getCellAt(7, 9)));
		// up two then left one
		Assert.assertTrue(targets.contains(board.getCellAt(6, 6)));
		// down then left/right
		Assert.assertTrue(targets.contains(board.getCellAt(10, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(10, 8)));
		// into the room
		Assert.assertTrue(targets.contains(board.getCellAt(8, 6)));
	}

	// Test getting out of a room
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(3, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(4, 6)));
		// Take two steps
		board.calcTargets(3, 6, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(5, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 5)));
	}

}