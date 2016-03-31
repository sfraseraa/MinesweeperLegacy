package minesweeper.domain;
import java.util.*;

class MineField {

	private Map squares;
	private List mineLocations; 

	MineField() { 
		squares = new HashMap(100);

		//Default all square to blank
		for(int i=0;i<100;i++) {
			squares.put(new Integer(i),new BlankSquare(i));
		}

		//Generate Random mine locations	
		mineLocations = new ArrayList(10);
		for(int i=0;i<10;i++) {
			Integer random = null;

			do {
				random = new Integer((int)(Math.random()*100));
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
			if (location%(10)!=0) {
				neighbors.add(new Integer(location-1));
				if (location>9) {
					neighbors.add(new Integer(location-11));
				}
				if (location<90) {
					neighbors.add(new Integer(location+9));
				}
			}

			// if location is not on right side, then add neighbors from right
			if ((location+1)%(10)!=0){ 
				neighbors.add(new Integer(location+1));
				if (location>9) {
					neighbors.add(new Integer(location-9));
				}

				if (location<90) {
					neighbors.add(new Integer(location+11));
				}
			}

			// if location is not on top, then add neighbors from top
			if (location>9) {  
				neighbors.add(new Integer(location-10));
			}

			// if location is not on bottom, then add neighbors from bottom
			if (location<90) { 
				neighbors.add(new Integer(location+10));
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
		for(int location =0;location<100;location++) {

			Square square = (Square)squares.get(new Integer(location));
			
			//Find Neighbor Squares.
			List neighbors = new ArrayList();

			// if location is not on left side, then add neighbors from left
			if (location%(10)!=0) {
				neighbors.add(new Integer(location-1));
				if (location>9) {
					neighbors.add(new Integer(location-11));
				}
				if (location<90) {
					neighbors.add(new Integer(location+9));
				}
			}

			// if location is not on right side, then add neighbors from right
			if ((location+1)%(10)!=0){ 
				neighbors.add(new Integer(location+1));
				if (location>9) {
					neighbors.add(new Integer(location-9));
				}

				if (location<90) {
					neighbors.add(new Integer(location+11));
				}
			}

			// if location is not on top, then add neighbors from top
			if (location>9) {  
				neighbors.add(new Integer(location-10));
			}

			// if location is not on bottom, then add neighbors from bottom
			if (location<90) { 
				neighbors.add(new Integer(location+10));
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

		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				board = board + "|"+ squares.get(new Integer(j+(i*10)));
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