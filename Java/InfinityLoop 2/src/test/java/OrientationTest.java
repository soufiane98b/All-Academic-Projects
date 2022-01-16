
import fr.dauphine.JavaAvance.Components.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import fr.dauphine.JavaAvance.Components.Orientation;

public class OrientationTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_turn90_getOriAndValueFrom() {
		Orientation ori1 = Orientation.NORTH.turn90();
		Orientation ori2 = Orientation.EAST.turn90().turn90();
		Orientation ori3 = Orientation.getOrifromValue(2);
		int n = Orientation.WEST.getValuefromOri();
		assertEquals(ori1, Orientation.EAST);
		assertEquals(ori2, Orientation.WEST);
		assertEquals(ori3, Orientation.SOUTH);
		assertEquals(n, 3);
	}
	
	@Test
	public void test_getOpposedPieceCoordinates() {
		Piece p1 = new Piece(20,30);
		Piece p2 = new Piece(22,10);
		int[] n1 = Orientation.NORTH.getOpposedPieceCoordinates(p1);
		int[] n2 = Orientation.EAST.getOpposedPieceCoordinates(p2);
		int[] m1 = new int[] {p1.getPosY() - 1, p1.getPosX()};
		int[] m2 = new int[] {p2.getPosY(), p2.getPosX() + 1};
		assertArrayEquals(n1, m1);
		assertArrayEquals(n2, m2);
		
	}

}
