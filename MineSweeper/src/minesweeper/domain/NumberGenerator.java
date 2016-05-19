package minesweeper.domain;

import java.util.ArrayList;
import java.util.List;

public class NumberGenerator {
	
	private int numberOfMines;
	
	public NumberGenerator(){
		this(10);
	}
	
	public NumberGenerator(int numberOfMines){
		this.numberOfMines = numberOfMines;
	}

	public List generateMineLocations(int numberOfSquares) {
		List mineLocs = new ArrayList(numberOfMines);
		//Generate Random mine locations	
		for(int i=0;i<numberOfMines;i++) {
			Integer random = null;

			do {
				random = new Integer((int)(Math.random()*numberOfSquares));
			}while(mineLocs.contains(random));

			mineLocs.add(random);
		}
		return mineLocs;
	}
}
