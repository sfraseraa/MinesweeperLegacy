package minesweeper.domain;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class MineFieldBehavior {

	MineField mineField;
	
	@Before
	public void setUp() throws Exception {
		mineField = new MineField();
	}

	@Test
	public void defaultNumberOfSquaresIs100() {
		
		assertThat (100, is(mineField.getSquares().size()));
		
	}
	
	@Test 
	public void defaultNumberOfMinesIs10(){
		Map squares = mineField.getSquares();
		int mines = 0;
		for(Square square : (Collection<Square>)squares.values()){
			if ( square instanceof MineSquare ) mines++;
		}
		assertThat(10, is (mines) );
	}
	@Test
	public void SixBySevenMineFieldNumberOfSquaresIs42(){
		mineField = new MineField(6,7,10);
		assertThat(42, is(mineField.getSquares().size() ));
	}
	
	@Test 
	public void numberOfMinesIs6(){
		mineField = new MineField(6,7,6);
		Map squares = mineField.getSquares();
		int mines = 0;
		for(Square square : (Collection<Square>)squares.values()){
			if ( square instanceof MineSquare ) mines++;
		}
		assertThat(6, is (mines) );
	}
	
	@Test 
	public void numberOfMinesIsSameAsSquares(){
		mineField = new MineField(6,7,42);
		Map squares = mineField.getSquares();
		int mines = 0;
		for(Square square : (Collection<Square>)squares.values()){
			if ( square instanceof MineSquare ) mines++;
		}
		assertThat(42, is (mines) );
	}

}
