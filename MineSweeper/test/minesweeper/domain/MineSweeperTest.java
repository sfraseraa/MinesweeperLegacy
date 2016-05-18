package minesweeper.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MineSweeperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void defaultShouldHaveOneHunderedSquares() {
		MineField subject = new MineField();
		assertEquals( 100, subject.getSquares().size());
	}
	
	@Test
	public void defaultShouldHaveTenMineSquares(){
		MineField subject = new MineField();
		Map squaresMap = subject.getSquares();
		int mineCount = 0;
		for( Object square :  squaresMap.values() ){
			if( square instanceof MineSquare ){
				mineCount++;
			}
		}
		assertEquals(10, mineCount);	
	}

}
