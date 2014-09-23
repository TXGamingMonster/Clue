import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class IntBoardTests {
	private IntBoard board;
	@Before
	public void setupBoard(){
		board = new IntBoard();
	}

	@Test
	public void testAdjacencyTopLeft(){
		BoardCell cell = board.getCell(0,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertEquals(2, testList.size());
		}
	public void testAdjacencyTopRight(){
		BoardCell cell = board.getCell(0,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		Assert.assertTrue(testList.contains(board.getCell(0,2)));
		Assert.assertTrue(testList.contains(board.getCell(1,3)));
		Assert.assertEquals(2, testList.size());
		}
	public void testAdjacencyBottomLeft(){
		BoardCell cell = board.getCell(3,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		Assert.assertTrue(testList.contains(board.getCell(2,0)));
		Assert.assertTrue(testList.contains(board.getCell(3,1)));
		Assert.assertEquals(2, testList.size());
		}
	public void testAdjacencyBottomRight(){
		BoardCell cell = board.getCell(3,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
		Assert.assertEquals(2, testList.size());
		}
	
}
