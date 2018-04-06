import java.util.ArrayList;

public class PathFinder {
    private int count = 0;
    // Creating a maze

    public PathFinder() {
    }

    public PathFinder(int numOfRows, int numOfColumns, Point start, Point end, Point[] obstacles) {
        char[][] map = makeMaze(numOfRows, numOfColumns, start, end, obstacles);
        int[] startSpot = this.findS(map);
        int[] finishSpot = this.findX(map);
        this.mazeMath(map, finishSpot, startSpot);
        /* Because the move function is called recursively within mazeMath, calling this
         * handles assigning the distance values for every spot on the map AND moving the
         * S character */
    }

    public char[][] makeMaze(int numOfRows, int numOfColumns, Point start, Point end, Point[] obstacles) {
        char[][] maze = new char[numOfRows][numOfColumns];
        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfColumns; column++) {
                maze[row][column] = '.';
            }
            maze[start.getY()][start.getX()] = 'S';
            maze[end.getY()][end.getX()] = 'X';
            try {
                for (int i = 0; i < obstacles.length; i++) {
                    maze[obstacles[i].getY()][obstacles[i].getX()] = 'O';
                }
            } catch (Exception e) {

            }
        }

        return maze;
    }


    public ArrayList<Point> moves = new ArrayList<Point>();

    /* This updates the maze and prints it out.
     * It'll be called on a loop within the main function after every move */
    public char[][] mazeUpdate(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            //System.out.println(map[row]);
        }
        return map;
    }

    // Loops through the maze to find S
    public int[] findS(char[][] map) {
        int locationX = 0;
        int locationY = 0;
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                if (map[row][column] == 'S') {
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
    public int[] findX(char[][] map) {
        int locationX = 0;
        int locationY = 0;
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                if (map[row][column] == 'X') {
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

    public int findDupeLowestIndex(int[] list, int target) {
	/* This checks to see if the lowest number in the D values surrounding S is the same on two
	sides of S. If so, it returns the index of second occurance - and if not, it just returns the number 8 */
        int[] returns = new int[2];
        int count = 0;
        int index = 8;
        int ogIndex = 0;
        for (int i = 0; i < list.length; i++) {
            int num = list[i];
            if (num == target) {
                count += 1;
                if (count == 2) {
                    index = i;
                }
            }
        }
        for (int j = 0; j < list.length; j++) {
            int number = list[j];
            if (number == target) {
                ogIndex = j;
            }
        }
        returns[0] = ogIndex;
        returns[1] = index;
        return index;
    }

    public int[] findLowest(int[] nums) {
        /*  This overrides findLowest, and is used as a helper function for findLowest
         */
        int lowest = nums[0];
        int[] returns = new int[2];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < lowest) {
                lowest = nums[i];
                index = i;
            }

        }
        returns[0] = index;
        returns[1] = lowest;
        return returns;
    }

    public int[] findLowest(int[] nums, int currentDValue, char[][] map, int[][][] mazeAndDValues) {
        /* This takes in a list of D Values for spots around where S currently is. It returns
         * the smallest value from that list. Based the index of the smallest number, S moves accordingly.
         * It also takes in the D Value of the spot S is currently on, and, if that spot is 0 raises our flag
         * (We check if D Value is 0 because the distance from the spot X is on from itself is 0)*/
        int lowest = nums[0];
        int index = 0;

        int rightLowest = 0;
        int leftLowest = 0;
        int upLowest = 0;
        int downLowest = 0;

        int[] sSpot = this.findS(map);
        int[] rightDValues = new int[4];
        int[] leftDValues = new int[4];
        int[] upDValues = new int[4];
        int[] downDValues = new int[4];

        int[] returns = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < lowest) {
                lowest = nums[i];
                index = i;
            }
        }

        if (currentDValue == 0) {
            index = 7; // 7 is just an arbitrarily set flag value.
        }
        //System.out.println(lowest);
        returns[0] = index;
        returns[1] = lowest;


        int dupeLowestIndex = this.findDupeLowestIndex(nums, lowest);

        if (dupeLowestIndex != 8) { // Checks to see if lowest number is in list twice, returns second index
            if (index == 0) { // Checking right side
                switch (dupeLowestIndex) {
                    case 1:
                        rightDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 2][0];
                        rightDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        rightDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        rightDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        rightLowest = this.findLowest(rightDValues)[1];

                        leftDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        leftDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 2][0];
                        leftDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        leftDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        leftLowest = this.findLowest(leftDValues)[1];

                        if (rightLowest < leftLowest) {
                            returns[0] = 0;
                            returns[1] = rightLowest;
                            return returns;
                        } else if (leftLowest < rightLowest) {
                            returns[0] = 1;
                            returns[1] = leftLowest;
                            return returns;
                        }

                        break;
                    case 2:
                        rightDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 2][0];
                        rightDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        rightDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        rightDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        rightLowest = this.findLowest(rightDValues)[1];

                        upDValues[0] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        upDValues[1] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        upDValues[2] = mazeAndDValues[sSpot[0] - 2][sSpot[1]][0];
                        upDValues[3] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        upLowest = this.findLowest(upDValues)[1];

                        if (rightLowest < upLowest) {
                            returns[0] = 0;
                            returns[1] = rightLowest;
                            return returns;
                        } else if (upLowest < rightLowest) {
                            returns[0] = 2;
                            returns[1] = upLowest;
                            return returns;
                        }

                        break;
                    case 3:
                        rightDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 2][0];
                        rightDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        rightDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        rightDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        rightLowest = this.findLowest(rightDValues)[1];

                        downDValues[0] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        downDValues[1] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        downDValues[2] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        downDValues[3] = mazeAndDValues[sSpot[0] + 2][sSpot[1]][0];
                        downLowest = this.findLowest(downDValues)[1];

                        if (rightLowest < downLowest) {
                            returns[0] = 0;
                            returns[1] = rightLowest;
                            return returns;
                        } else if (downLowest < rightLowest) {
                            returns[0] = 3;
                            returns[1] = downLowest;
                            return returns;
                        }
                        break;
                }
            }
            if (index == 1) {
                switch (dupeLowestIndex) {
                    case 0:
                        leftDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        leftDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 2][0];
                        leftDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        leftDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        leftLowest = this.findLowest(leftDValues)[1];

                        rightDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 2][0];
                        rightDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        rightDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        rightDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        rightLowest = this.findLowest(rightDValues)[1];

                        if (leftLowest < rightLowest) {
                            returns[0] = 1;
                            returns[1] = leftLowest;
                            return returns;
                        } else if (rightLowest < leftLowest) {
                            returns[0] = 0;
                            returns[1] = rightLowest;
                            return returns;
                        }

                        break;

                    case 2:
                        leftDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        leftDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 2][0];
                        leftDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        leftDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        leftLowest = this.findLowest(leftDValues)[1];

                        upDValues[0] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        upDValues[1] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        upDValues[2] = mazeAndDValues[sSpot[0] - 2][sSpot[1]][0];
                        upDValues[3] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        upLowest = this.findLowest(upDValues)[1];

                        if (leftLowest < upLowest) {
                            returns[0] = 1;
                            returns[1] = leftLowest;
                            return returns;
                        } else if (upLowest < leftLowest) {
                            returns[0] = 2;
                            returns[1] = upLowest;
                            return returns;
                        }
                        break;

                    case 3:
                        leftDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        leftDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 2][0];
                        leftDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        leftDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        leftLowest = this.findLowest(leftDValues)[1];

                        downDValues[0] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        downDValues[1] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        downDValues[2] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        downDValues[3] = mazeAndDValues[sSpot[0] + 2][sSpot[1]][0];
                        downLowest = this.findLowest(downDValues)[1];

                        if (leftLowest < downLowest) {
                            returns[0] = 1;
                            returns[1] = leftLowest;
                            return returns;
                        } else if (downLowest < leftLowest) {
                            returns[0] = 3;
                            returns[1] = downLowest;
                            return returns;
                        }

                        break;
                }
            }
            if (index == 2) {
                switch (dupeLowestIndex) {
                    case 0:
                        upDValues[0] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        upDValues[1] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        upDValues[2] = mazeAndDValues[sSpot[0] - 2][sSpot[1]][0];
                        upDValues[3] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        upLowest = this.findLowest(upDValues)[1];

                        rightDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 2][0];
                        rightDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        rightDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        rightDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        rightLowest = this.findLowest(rightDValues)[1];

                        if (upLowest < rightLowest) {
                            returns[0] = 2;
                            returns[1] = upLowest;
                            return returns;
                        } else if (rightLowest < upLowest) {
                            returns[0] = 0;
                            returns[1] = rightLowest;
                            return returns;
                        }


                        break;
                    case 1:
                        upDValues[0] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        upDValues[1] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        upDValues[2] = mazeAndDValues[sSpot[0] - 2][sSpot[1]][0];
                        upDValues[3] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        upLowest = this.findLowest(upDValues)[1];

                        leftDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        leftDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 2][0];
                        leftDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        leftDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        leftLowest = this.findLowest(leftDValues)[1];

                        if (upLowest < leftLowest) {
                            returns[0] = 2;
                            returns[1] = upLowest;
                            return returns;
                        } else if (leftLowest < upLowest) {
                            returns[0] = 1;
                            returns[1] = leftLowest;
                            return returns;
                        }

                        break;
                    case 3:
                        upDValues[0] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        upDValues[1] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        upDValues[2] = mazeAndDValues[sSpot[0] - 2][sSpot[1]][0];
                        upDValues[3] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        upLowest = this.findLowest(upDValues)[1];

                        downDValues[0] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        downDValues[1] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        downDValues[2] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        downDValues[3] = mazeAndDValues[sSpot[0] + 2][sSpot[1]][0];
                        downLowest = this.findLowest(downDValues)[1];

                        if (upLowest < downLowest) {
                            returns[0] = 2;
                            returns[1] = upLowest;
                            return returns;
                        } else if (downLowest < upLowest) {
                            returns[0] = 3;
                            returns[1] = downLowest;
                            return returns;
                        }

                        break;
                }
            }
            if (index == 3) {
                switch (dupeLowestIndex) {
                    case 0:
                        downDValues[0] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        downDValues[1] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        downDValues[2] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        downDValues[3] = mazeAndDValues[sSpot[0] + 2][sSpot[1]][0];
                        downLowest = this.findLowest(downDValues)[1];

                        rightDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 2][0];
                        rightDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        rightDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        rightDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        rightLowest = this.findLowest(rightDValues)[1];

                        if (downLowest < rightLowest) {
                            returns[0] = 3;
                            returns[1] = downLowest;
                            return returns;
                        } else if (rightLowest < downLowest) {
                            returns[0] = 0;
                            returns[1] = rightLowest;
                            return returns;
                        }
                        break;
                    case 1:
                        downDValues[0] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        downDValues[1] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        downDValues[2] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        downDValues[3] = mazeAndDValues[sSpot[0] + 2][sSpot[1]][0];
                        downLowest = this.findLowest(downDValues)[1];

                        leftDValues[0] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        leftDValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 2][0];
                        leftDValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        leftDValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        leftLowest = this.findLowest(leftDValues)[1];

                        if (downLowest < leftLowest) {
                            returns[0] = 3;
                            returns[1] = downLowest;
                            return returns;
                        } else if (leftLowest < downLowest) {
                            returns[0] = 1;
                            returns[1] = leftLowest;
                            return returns;
                        }


                        break;
                    case 2:
                        downDValues[0] = mazeAndDValues[sSpot[0] + 1][sSpot[1] + 1][0];
                        downDValues[1] = mazeAndDValues[sSpot[0] + 1][sSpot[1] - 1][0];
                        downDValues[2] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        downDValues[3] = mazeAndDValues[sSpot[0] + 2][sSpot[1]][0];
                        downLowest = this.findLowest(downDValues)[1];

                        upDValues[0] = mazeAndDValues[sSpot[0] - 1][sSpot[1] + 1][0];
                        upDValues[1] = mazeAndDValues[sSpot[0] - 1][sSpot[1] - 1][0];
                        upDValues[2] = mazeAndDValues[sSpot[0] - 2][sSpot[1]][0];
                        upDValues[3] = mazeAndDValues[sSpot[0]][sSpot[1]][0];
                        upLowest = this.findLowest(upDValues)[1];


                        if (downLowest < upLowest) {
                            returns[0] = 3;
                            returns[1] = downLowest;
                            return returns;
                        } else if (upLowest < downLowest) {
                            returns[0] = 2;
                            returns[1] = upLowest;
                            return returns;
                        }


                        break;
                }
            }
        }

        return returns;
    }


    public void movesTracker(char[][] map) {
        int sY = this.findS(map)[0];
        int sX = this.findS(map)[1];
        Point sSpot = new Point(sY, sX);
        this.moves.add(sSpot);
    }

    public char[][] mazeMove(int[][][] mazeAndDValues, char[][] map) throws ArrayIndexOutOfBoundsException, StackOverflowError{
        /* This is the actual method that makes our S character move. It does so by checking
         * the D Values around where S currently is, and moving in that direction.
         * */
        int[] sSpot = this.findS(map);

        int atS = mazeAndDValues[sSpot[0]][sSpot[1]][0];
        int[] dValues = new int[4];
        dValues[0] = mazeAndDValues[sSpot[0]][sSpot[1] + 1][0]; // right
        dValues[1] = mazeAndDValues[sSpot[0]][sSpot[1] - 1][0]; // left
        dValues[2] = mazeAndDValues[sSpot[0] - 1][sSpot[1]][0]; // up
        dValues[3] = mazeAndDValues[sSpot[0] + 1][sSpot[1]][0]; // down

        if (findLowest(dValues, atS, map, mazeAndDValues)[0] != 7) {


            if (findLowest(dValues, atS, map, mazeAndDValues)[0] == 0) {
                //System.out.println(mazeAndDValues[sSpot[0]][sSpot[1]][0]);
                map[sSpot[0]][sSpot[1]] = '.';
                map[sSpot[0]][sSpot[1] + 1] = 'S';
                mazeAndDValues[sSpot[0]][sSpot[1]][0] += 10;
                this.count += 1;
                movesTracker(map);
                //System.out.println();
                mazeUpdate(map);
                mazeMove(mazeAndDValues, map);
            } else if (findLowest(dValues, atS, map, mazeAndDValues)[0] == 1) {
                //System.out.println(mazeAndDValues[sSpot[0]][sSpot[1]][0]);
                map[sSpot[0]][sSpot[1]] = '.';
                map[sSpot[0]][sSpot[1] - 1] = 'S';
                mazeAndDValues[sSpot[0]][sSpot[1]][0] += 10;
                this.count += 1;
                movesTracker(map);
                //System.out.println();
                mazeUpdate(map);
                mazeMove(mazeAndDValues, map);
            } else if (findLowest(dValues, atS, map, mazeAndDValues)[0] == 2) {
                //System.out.println(mazeAndDValues[sSpot[0]][sSpot[1]][0]);
                map[sSpot[0]][sSpot[1]] = '.';
                map[sSpot[0] - 1][sSpot[1]] = 'S';
                mazeAndDValues[sSpot[0]][sSpot[1]][0] += 10;
                this.count += 1;
                movesTracker(map);
                //System.out.println();
                mazeUpdate(map);
                mazeMove(mazeAndDValues, map);
            } else if (findLowest(dValues, atS, map, mazeAndDValues)[0] == 3) {
                //System.out.println(mazeAndDValues[sSpot[0]][sSpot[1]][0]);
                map[sSpot[0]][sSpot[1]] = '.';
                map[sSpot[0] + 1][sSpot[1]] = 'S';
                mazeAndDValues[sSpot[0]][sSpot[1]][0] += 10;
                this.count += 1;
                movesTracker(map);
                //System.out.println();
                mazeUpdate(map);
                mazeMove(mazeAndDValues, map);
            }
        } else {
            this.count += 1;
            movesTracker(map);

        }
        return map;

    }


    // Actual A* calculations
    public void mazeMath(char[][] map, int[] finishCoords, int[] startCoords) {
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

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                xDistance = Math.abs((finishX - row) * 10);
                yDistance = Math.abs((finishY - column) * 10);
                int distanceFromEnd = xDistance + yDistance;
                if (map[row][column] == 'O') {
                    spotsAndDValues[row][column][0] = 99999;
                } else {
                    spotsAndDValues[row][column][0] = distanceFromEnd;
                }
            }

        }
        mazeMove(spotsAndDValues, map); /* Because the 3 dimensional list with spots and their distance
		values is right here, and is a parameter of MazeMove, we just call mazeMove from within
		this function */
    }
}
