package minesweeper.domain;
import java.util.*;

class MineField {

	private int width = 10;
	private int mines = 10;
	private int numberOfSquares = 100;
	private Map squares;
	private List mineLocations; 

	MineField(){
		this( 10, 10, 10);
	}
	
	MineField(int width, int height, int mines ) { 
		this.width = width;
		this.mines = mines;
		numberOfSquares = width * height;
				
		squares = new HashMap(numberOfSquares);

		defaultAllSquaresToBlank();
	
		mineLocations = generateRandomMineLocations(mines);

		for(Iterator it = mineLocations.iterator();it.hasNext();) {
			Integer location = (Integer)it.next();
			Square mine = new MineSquare(location.intValue());
			squares.put(location,mine);

			List neighbors = getNeighbors(width, location);

			incrementNeighborNumberSquares(neighbors);
		}		

		squaresKnowTheirNeighborsForReveiling(width);
	}

	private void squaresKnowTheirNeighborsForReveiling(int width) {
		for(int location =0;location<numberOfSquares;location++) {

			Square square = (Square)squares.get(new Integer(location));
			
			//Find Neighbor Squares.
			List neighbors = getNeighbors(width, location);

			for(Iterator it = neighbors.iterator();it.hasNext();) {
				square.addNeighbor((Square)(squares.get((Integer)it.next())));
			}
		}
	}

	private void incrementNeighborNumberSquares(List neighbors) {
		Integer location;
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

	private List getNeighbors(int width, Integer location) {
		List neighbors = new ArrayList();

		// if location is not on left side, then add neighbors from left
		addLeftNeighbors(width, location, neighbors);

		// if location is not on right side, then add neighbors from right
		addRightNeighbors(width, location, neighbors);

		// if location is not on top, then add neighbors from top
		addTopNeighbors(width, location, neighbors);

		// if location is not on bottom, then add neighbors from bottom
		addBottomNeighbors(width, location, neighbors);
		return neighbors;
	}

	private void addBottomNeighbors(int width, Integer location, List neighbors) {
		if (location< numberOfSquares - width) { 
			neighbors.add(new Integer(location+width));
		}
	}

	private void addTopNeighbors(int width, Integer location, List neighbors) {
		if (location>width - 1) {  
			neighbors.add(new Integer(location-width));
		}
	}

	private void addRightNeighbors(int width, Integer location, List neighbors) {
		if ((location+1)%(width)!=0){ 
			neighbors.add(new Integer(location+1));
			if (location>width-1) {
				neighbors.add(new Integer(location-(width-1)));
			}

			if (location<numberOfSquares-width) {
				neighbors.add(new Integer(location+(width + 1)));
			}
		}
	}

	private void addLeftNeighbors(int width, Integer location, List neighbors) {
		if (location%width!=0) {
			neighbors.add(new Integer(location-1));
			if (location>width-1) {
				neighbors.add(new Integer(location-(width + 1)));
			}
			if (location<numberOfSquares - width) {
				neighbors.add(new Integer(location+(width-1)));
			}
		}
	}

	protected List generateRandomMineLocations(int mines) {
		List mineLocations = new ArrayList(mines);
		for(int i=0;i<mines;i++) {
			Integer random = null;

			do {
				random = new Integer((int)(Math.random()*numberOfSquares));
			}while(mineLocations.contains(random));

			mineLocations.add(random);
		}
		return mineLocations;
	}

	private void defaultAllSquaresToBlank() {
		for(int i=0;i<numberOfSquares;i++) {
			squares.put(new Integer(i),new BlankSquare(i));
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

		for(int i=0;i< numberOfSquares / width ;i++) {
			for(int j=0;j<width;j++) {
				board = board + "|"+ squares.get(new Integer(j+(i*width)));
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