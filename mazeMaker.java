public class mazeMaker{
	// Creating a maze 
	public char[][] makeMaze() {
		char[][] maze = {
				{'O','O','O','O','O','O','O','O'},
				{'O','.','.','.','.','.','.','O'},
				{'O','S','O','O','.','O','.','O'},
				{'O','.','.','.','.','.','.','O'}, // You can put any maze you want here
				{'O','O','.','.','O','.','O','O'},
				{'O','.','O','.','.','.','.','O'},
				{'O','.','.','.','.','.','X','O'},
				{'O','O','O','O','O','O','O','O'},
		};
		return maze;
		}
	
	/* This updates the maze and prints it out.
	 * It'll be called on a loop within the main function after every move */
	public char[][] mazeUpdate(char[][] map) {
		for(int row = 0; row < map.length; row ++) {
			System.out.println(map[row]); 
		} 
			return map;
	}
	// Loops through the maze to find S

	/**
	 * returns a tuple (x,y)
	 * @param map
	 * @return
	 */
	public int[] findS(char[][] map){
		int locationX = 0;
		int locationY = 0;
		for(int row = 0; row < map.length; row++) {
			for(int column = 0; column < map[row].length; column ++) { 
				if(map[row][column] == 'S') {
					locationX = row;
					locationY = column;
				}
			}
		}
		int[] location = new int[2];
		location[0] = locationX;
		location[1] = locationY;
		/* Returning a list of integers where the first number
		is the row, and the second number is the column */		
		return location;
	}
	// Loops through the maze to find X
	public int[] findX(char[][] map){
		int locationX = 0;
		int locationY = 0;
		for(int row = 0; row < map.length; row++) {
			for(int column = 0; column < map[row].length; column ++) {
				if(map[row][column] == 'X') {
					locationX = row;
					locationY = column;
				}
			}
		}
		int[] location = new int[2];
		location[0] = locationX;
		location[1] = locationY;
		/* Returning a list of integers where the first number
		is the row, and the second number is the column*/ 
		return location; 
	}
	
	public int findLowest(int[] nums, int currentDValue) { 
/* This takes in a list of D Values for spots around where S currently is. It returns 
 * the smallest value from that list. Based the index of the smallest number, S moves accordingly.
 * It also takes in the D Value of the spot S is currently on, and, if that spot is 0 raises our flag
 * (We check if D Value is 0 because the distance from the spot X is on from itself is 0)*/
		int lowest = nums[0];
		int index = 0;
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] <= lowest) {
				lowest = nums[i];
				index = i;
			}
		}
		
		if(currentDValue == 0) {
			index = 7; // 7 is just an arbitrarily set flag value. 
		}
		return index;
	}
	
	public char[][] mazeMove(int[][][] mazeAndDValues, char[][] map) {
/* This is the actual method that makes our S character move. It does so by checking 
 * the D Values around where S currently is, and moving in that direction. 
 * */
		int[] sSpot = this.findS(map);
		
		int rightOfS = mazeAndDValues[sSpot[0]][sSpot[1] +1 ][0];
		int leftOfS = mazeAndDValues[sSpot[0] -1 ][sSpot[1]][0];
		int aboveS = mazeAndDValues[sSpot[0]][sSpot[1] - 1][0];
		int belowS = mazeAndDValues[sSpot[0] + 1][sSpot[1]][0];
		int atS = mazeAndDValues[sSpot[0]][sSpot[1]][0];
		int[] dValues = new int[4];
		dValues[0] = rightOfS;
		dValues[1] = leftOfS;
		dValues[2] = aboveS;
		dValues[3] = belowS;
		
		if(findLowest(dValues, atS) != 7) {
			
		
		if(findLowest(dValues, atS) == 0) {
			System.out.println("S is at: [" + sSpot[1] + "," + sSpot[0] + "]" );
			map[sSpot[0]][sSpot[1]] = '.';
			map[sSpot[0]][sSpot[1] +1 ] = 'S';
			System.out.println();
			mazeUpdate(map);
			mazeMove(mazeAndDValues, map);
		}
		
		else if(findLowest(dValues, atS) == 1) {
			System.out.println("S is at: [" + sSpot[1] + "," + sSpot[0] + "]" );
			map[sSpot[0]][sSpot[1]] = '.';
			map[sSpot[0] -1 ][sSpot[1]] = 'S';
			System.out.println();
			mazeUpdate(map);
			mazeMove(mazeAndDValues, map);			
		}
		
		else if(findLowest(dValues, atS) == 2) {
			System.out.println("S is at: [" + sSpot[1] + "," + sSpot[0] + "]" );
			map[sSpot[0]][sSpot[1]] = '.';
			map[sSpot[0]][sSpot[1] -1 ] = 'S';
			System.out.println();
			mazeUpdate(map);
			mazeMove(mazeAndDValues, map);
		}
		
		else if(findLowest(dValues, atS) == 3) {
			System.out.println("S is at: [" + sSpot[1] + "," + sSpot[0] + "]" );
			map[sSpot[0]][sSpot[1]] = '.';
			map[sSpot[0] +1 ][sSpot[1]] = 'S';
			System.out.println();
			mazeUpdate(map);
			mazeMove(mazeAndDValues, map);
			}
		}		
		return map;
	
	}
	
	
	
	
	
	
	// Actual A* calculations 
	public void mazeMath(char[][] map, int[] finishCoords, int[] startCoords){
/* This loops through every spot on the map, calculates how far it is from
 * whatever spot X is occupying, and adds that to a 3 dimensional list.
 * If the spot is occupied by an O character it's assigned a distance value 
 * of 99999*/		
		int finishX = finishCoords[0];
		int finishY = finishCoords[1];
		int numOfRows = map.length;
		int numOfColumns = map[0].length;
		int[][][] spotsAndDValues = new int[numOfRows][numOfColumns][1];
		int xDistance = 0;
		int yDistance = 0;

		for(int row = 0; row < map.length; row++) {
			for(int column = 0; column < map[row].length; column ++) {
				xDistance = Math.abs((finishX - row) * 10);
				yDistance = Math.abs((finishY - column) * 10);
				int distanceFromEnd = xDistance + yDistance;
				if(map[row][column] == 'O') {
					spotsAndDValues[row][column][0] = 99999;
				}
				else {
				spotsAndDValues[row][column][0] = distanceFromEnd;
				}
			}
			
		}
		mazeMove(spotsAndDValues, map); /* Because the 3 dimensional list with spots and their distance
		values is right here, and is a parameter of MazeMove, we just call mazeMove from within
		this function */
	}
}