package minesweeper.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CreateMineFieldTests {

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
	
	private class TestReplacingMinefieldConstructor extends MineField {
		public TestReplacingMinefieldConstructor(int width, int height, int mines){
			super(width, height, mines);
		}
		public List generateMineLocations(){
			return Arrays.asList(new Integer[]{
					32,33,34,42,44,53,37,46,64,88
			});
		}
	}
	
	@Test
	public void defaultParamtersOfConstructorShouldBuildMineField(){
		MineField subject = new TestReplacingMinefieldConstructor(10, 10, 10);

		Map squaresMap = subject.getSquares();
		
		assertTrue( squaresMap.get(32) instanceof MineSquare);
		assertTrue( squaresMap.get(31) instanceof NumberSquare);
		assertTrue( squaresMap.get(30) instanceof BlankSquare);
		assertEquals( 2, ((NumberSquare)squaresMap.get(31)).getValue());

	}
	
	
	@Test
	public void twentyByTwentyFieldShouldBuildMinefield(){
		MineField subject = new TestReplacingMinefieldConstructor(20, 20, 10);

		Map squaresMap = subject.getSquares();
		
		assertTrue( squaresMap.get(32) instanceof MineSquare);
		assertTrue( squaresMap.get(31) instanceof NumberSquare);
		assertTrue( squaresMap.get(30) instanceof BlankSquare);
		assertEquals( 1, ((NumberSquare)squaresMap.get(31)).getValue());

	}
	
	@Test
	public void thirtyByTenFieldShouldBuildMinefield(){
		MineField subject = new TestReplacingMinefieldConstructor(30, 10, 10);

		Map squaresMap = subject.getSquares();
		
		assertTrue( squaresMap.get(32) instanceof MineSquare);
		assertTrue( squaresMap.get(31) instanceof NumberSquare);
		assertTrue( squaresMap.get(30) instanceof BlankSquare);
		assertEquals( 1, ((NumberSquare)squaresMap.get(31)).getValue());

	}
	
	@Test
	public void passInRandomGenerator(){
		NumberGenerator numberGenerator = new NumberGenerator(10);
		
		MineField subject = new MineField(10,10, numberGenerator);
		Map squaresMap = subject.getSquares();
		
		assertEquals( 100, subject.getSquares().size());
		int mineCount = 0;
		for( Object square :  squaresMap.values() ){
			if( square instanceof MineSquare ){
				mineCount++;
			}
		}
		assertEquals(10, mineCount);
	}
	
// replacing tests
	
	private class TestNumberGenerator extends NumberGenerator{
		Integer numbers[];
		
		TestNumberGenerator(Integer[] values){
			super(values.length);
			numbers = values;
		}
		public List generateMineLocations(int numberOfSquares) {
			return Arrays.asList(numbers);
		}
	}
	
	
	@Test
	public void shouldCalculateTypesOfSquaresFromMineSquares(){
		MineField subject = new MineField(10,10, 
				new TestNumberGenerator(new Integer[]{
						32,33,34,42,44,53,37,46,64,88
				}));
		
		
		Map squaresMap = subject.getSquares();
		
		assertTrue( squaresMap.get(32) instanceof MineSquare);
		assertTrue( squaresMap.get(31) instanceof NumberSquare);
		assertTrue( squaresMap.get(30) instanceof BlankSquare);
		assertEquals( 2, ((NumberSquare)squaresMap.get(31)).getValue());
		
	}
	
	@Test
	public void shouldHaveNoMinesWhenNoNumbersGenerated(){
		MineField subject = new MineField(10,10, 
			new TestNumberGenerator(new Integer[]{}));
		
		Map squaresMap = subject.getSquares();
		
		int mineCount = 0;
		for( Object square :  squaresMap.values() ){
			if( square instanceof MineSquare ){
				mineCount++;
			}
		}
		assertEquals(0, mineCount);
	}
	
	@Test
	public void shouldHaveAllMinesWhen100NumbersGenerated(){
		
		Integer allSquaresMines[] = new Integer[100];
		for( int i=0; i<100; i++){
			allSquaresMines[i]=i;
		}
		MineField subject = new MineField(10,10, 
				new TestNumberGenerator(allSquaresMines));
			
		Map squaresMap = subject.getSquares();
		
		int mineCount = 0;
		for( Object square :  squaresMap.values() ){
			if( square instanceof MineSquare ){
				mineCount++;
			}
		}
		assertEquals(100, mineCount);
	}

	
	
}
