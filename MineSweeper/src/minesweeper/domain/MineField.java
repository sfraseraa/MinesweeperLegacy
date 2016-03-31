package minesweeper.domain;
import java.util.*;

class MineField {

	private static final int BOARD_WIDTH = 10;
	private static final int NUMBER_OF_MINES = 10;
	private static final int NUMBER_OF_SQUARES = 100;
	private Map squares;
	private List mineLocations; 

	MineField() { 
		squares = new HashMap(NUMBER_OF_SQUARES);

		//Default all square to blank
		for(int i=0;i<NUMBER_OF_SQUARES;i++) {
			squares.put(new Integer(i),new BlankSquare(i));
		}

		//Generate Random mine locations	
		mineLocations = new ArrayList(NUMBER_OF_MINES);
		for(int i=0;i<NUMBER_OF_MINES;i++) {
			Integer random = null;

			do {
				random = new Integer((int)(Math.random()*NUMBER_OF_SQUARES));
			}while(mineLocations.contains(random));

			mineLocations.add(random);
		}
		
		
		// Add Mine Squares
		for(Iterator it = mineLocations.iterator();it.hasNext();) {
			Integer location = (Integer)it.next();
			Square mine = new MineSquare(location.intValue());
			squares.put(location,mine);

			//Get Neighbor Squares
			List neighbors = new ArrayList();

			// if location is not on left side, then add neighbors from left
			if (location%BOARD_WIDTH!=0) {
				neighbors.add(new Integer(location-1));
				if (location>BOARD_WIDTH-1) {
					neighbors.add(new Integer(location-(BOARD_WIDTH + 1)));
				}
				if (location<NUMBER_OF_SQUARES - BOARD_WIDTH) {
					neighbors.add(new Integer(location+(BOARD_WIDTH-1)));
				}
			}

			// if location is not on right side, then add neighbors from right
			if ((location+1)%(BOARD_WIDTH)!=0){ 
				neighbors.add(new Integer(location+1));
				if (location>BOARD_WIDTH-1) {
					neighbors.add(new Integer(location-(BOARD_WIDTH-1)));
				}

				if (location<NUMBER_OF_SQUARES-BOARD_WIDTH) {
					neighbors.add(new Integer(location+(BOARD_WIDTH + 1)));
				}
			}

			// if location is not on top, then add neighbors from top
			if (location>BOARD_WIDTH - 1) {  
				neighbors.add(new Integer(location-BOARD_WIDTH));
			}

			// if location is not on bottom, then add neighbors from bottom
			if (location< NUMBER_OF_SQUARES - BOARD_WIDTH) { 
				neighbors.add(new Integer(location+BOARD_WIDTH));
			}

				
			//Increment or Make neighboring number squares
			for(Iterator it2 = neighbors.iterator();it2.hasNext();) {
				location = (Integer)it2.next();

				//Increment Neighboring Number Squares
				if (squares.get(location) instanceof NumberSquare) {
					NumberSquare ns = (NumberSquare)squares.get(location);
					ns.incrementValue();
				//Make Neighbor Number Square	
				}else if(squares.get(location) instanceof BlankSquare) {
					squares.put(location,new NumberSquare(location.intValue(),1));
				}
			}
		}		
		
		//Each Square Knows its Neighbors for reveal
		for(int location =0;location<NUMBER_OF_SQUARES;location++) {

			Square square = (Square)squares.get(new Integer(location));
			
			//Find Neighbor Squares.
			List neighbors = new ArrayList();

			// if location is not on left side, then add neighbors from left
			if (location%(BOARD_WIDTH)!=0) {
				neighbors.add(new Integer(location-1));
				if (location>(BOARD_WIDTH - 1)) {
					neighbors.add(new Integer(location-(BOARD_WIDTH + 1)));
				}
				if (location<NUMBER_OF_SQUARES- BOARD_WIDTH) {
					neighbors.add(new Integer(location + (BOARD_WIDTH - 1)));
				}
			}

			// if location is not on right side, then add neighbors from right
			if ((location+1)%(BOARD_WIDTH)!=0){ 
				neighbors.add(new Integer(location+1));
				if (location>BOARD_WIDTH - 1) {
					neighbors.add(new Integer(location-(BOARD_WIDTH - 1)));
				}

				if (location<NUMBER_OF_SQUARES - BOARD_WIDTH) {
					neighbors.add(new Integer(location + (BOARD_WIDTH +1 ) ));
				}
			}

			// if location is not on top, then add neighbors from top
			if (location> BOARD_WIDTH -1 ) {  
				neighbors.add(new Integer(location-BOARD_WIDTH));
			}

			// if location is not on bottom, then add neighbors from bottom
			if (location< NUMBER_OF_SQUARES - BOARD_WIDTH) { 
				neighbors.add(new Integer(location+BOARD_WIDTH));
			}

			for(Iterator it = neighbors.iterator();it.hasNext();) {
				square.addNeighbor((Square)(squares.get((Integer)it.next())));
			}
		}
	}

	void uncoverMineSquares() {
		for(Iterator it = mineLocations.iterator();it.hasNext();) {
			Square mine = (Square)squares.get((Integer)it.next());
			mine.uncover();
		}
	}

	public String toString() {

		String board = "";

		for(int i=0;i< NUMBER_OF_SQUARES / BOARD_WIDTH ;i++) {
			for(int j=0;j<BOARD_WIDTH;j++) {
				board = board + "|"+ squares.get(new Integer(j+(i*BOARD_WIDTH)));
			}
			board = board + "\n";
		}
		return board;
	}


	Map getSquares(){
		return squares;
	}

	void uncover(int location) {
		Square square = (Square)squares.get(new Integer(location));
		square.uncover();
	}
}