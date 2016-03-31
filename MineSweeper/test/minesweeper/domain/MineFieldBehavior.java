package minesweeper.domain;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsInstanceOf;

public class MineFieldBehavior {

	MineField mineField;
	
	@Before
	public void setUp() throws Exception {
		mineField = new MineField();
	}

	@Test
	public void defaultNumberOfSquaresIs100() {
		
		assertThat (mineField.getSquares().size(), is(100));
		
	}
	
	@Test 
	public void defaultNumberOfMinesIs10(){
		Map squares = mineField.getSquares();
		int mines = 0;
		for(Square square : (Collection<Square>)squares.values()){
			if ( square instanceof MineSquare ) mines++;
		}
		assertThat(mines, is (10) );
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
		assertThat(mines, is (6) );
	}
	
	@Test 
	public void numberOfMinesIsSameAsSquares(){
		mineField = new MineField(6,7,42);
		Map squares = mineField.getSquares();
		int mines = 0;
		for(Square square : (Collection<Square>)squares.values()){
			if ( square instanceof MineSquare ) mines++;
		}
		assertThat(mines, is (42) );
	}
	
	@Test
	public void mineFieldMineAndNumberSquaresAreCalculated(){
		mineField = new MineField(){
			public List generateRandomMineLocations(int mines){
				return Arrays.asList(new Integer[]{
						32,33,34,42,44,53,37,46,64,88
						
				});
			}
		};
		Map squares = mineField.getSquares();
		assertThat(squares.get(new Integer(32)), instanceOf( MineSquare.class) );
		assertThat(squares.get(new Integer(22)), instanceOf( NumberSquare.class) );
		assertThat(squares.get(new Integer(12)), instanceOf( BlankSquare.class) );
		
	}

}
