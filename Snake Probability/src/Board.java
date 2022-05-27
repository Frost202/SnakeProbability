import java.util.ArrayList;
import java.util.Arrays;

public class Board {


		private GameObjects[][] board;
		
		private int appleCount;
		

		
		private Apple [] appleOrder = new Apple [5];
		
		private int [] smallCol = {7, 5, 5, 9, 9};
		private int [] smallRow = {4, 2, 6, 2, 6};
		private int [] standardCol = {12, 10, 10, 14, 14};
		private int [] standardRow = {7, 5, 9, 5, 9};
		private int [] largeCol = {18, 16, 16, 20, 20};
		private int [] largeRow = {10, 8, 12, 8, 12};
		private int [] spawnCol;
		private int [] spawnRow;
		
		//Stores the direction of the snake: 1 is up, 2 is right, 3 is down, 4 is left
		private int direction;
		
		private boolean up;
		private boolean left;

		private ArrayList<Snake> Tail = new ArrayList<Snake>(1000);
		private ArrayList<EmptySquare> Squares = new ArrayList<EmptySquare>(1000);
		
		private int moveCount = 0;
		private int score = 0;
		
		private int targetScore = 0;
		
		private int reactionTime;
		
		
		private int test = 0;
		private int t1; private int t2; private int t3; private int t4; 


		
		
		
		public Board(int rows, int cols, int appleAmount, double speed) {
			
			board = new GameObjects[rows][cols];
			appleCount = appleAmount;
			
			if (speed == 89.1)
			{
				reactionTime = 3;
			}
			
			else if (speed == 135)
			{
				reactionTime = 2;
			}
			
			else 
			{
				reactionTime = 1;
			}
			
			int snakeRow; 
			
			if (cols <= 10) //Small board
			{
				snakeRow = 3; spawnCol = smallCol; spawnRow = smallRow;
				
				if(appleAmount >= 4)
					spawnCol = Shift(smallCol, -1);
			}
			
			else if (cols <= 17)//Standard board
			{
				snakeRow = 4; spawnCol = standardCol; spawnRow = standardRow;
				
				if(appleAmount >= 4)
					spawnCol = Shift(standardCol, -1);
			}
				
			else //Large board
			{
				snakeRow = 6; spawnCol = largeCol; spawnRow = largeRow;
				
				if(appleAmount >= 4)
					spawnCol = Shift(largeCol, -2);
			} 
			
			direction = 2; //Snake starts by facing right
			
			Snake a = new Snake (rows/2, snakeRow); Snake b = new Snake (rows/2, snakeRow - 1);	Snake c = new Snake (rows/2, snakeRow - 2); Snake d = new Snake (rows/2, snakeRow - 3);
			Tail.add(a); Tail.add(b); Tail.add(c); Tail.add(d); 
			board[rows/2][snakeRow] = a; board[rows/2][snakeRow - 1] = b; board[rows/2][snakeRow - 2] = c; board[rows/2][snakeRow - 3] = d;

			

			//Spawns the apples and adds them to the array board
			for(int i = 0; i < appleAmount; i++)
			{	
				Apple x = new Apple(spawnRow[i], spawnCol[i], 0);
				board[spawnRow[i]][spawnCol[i]] = x;
				appleOrder[i] = x;
				
				//System.out.println(board[spawnRow[i]][spawnCol[i]]);
			} 
			
			//Spawns EmptySquare objects where there are no Snake or Apple objects
			for(int row = 0; row < board.length; row++)
			{
				for(int col = 0; col < board[0].length; col++)
				{
					if (board[row][col] == null) 
					{
						EmptySquare x = new EmptySquare(row, col);
						board[row][col] = x;
						Squares.add(x);
					}
											
				}
				
			}
			
			
		} 
		
		//Prints out the board
		public void Display()
		{	
			System.out.println();
		
			for(int row = 0; row < board.length; row++)
			{
				for(int col = 0; col < board[0].length; col++)
				{
					if (board[row][col].getClass().getName() == "EmptySquare") 
						System.out.print("x ");	
					else if (board[row][col].getClass().getName() == "Apple")
						System.out.print("o ");	
					else if (row == Tail.get(0).getRow() && col == Tail.get(0).getCol())
						System.out.print("H ");
					else if (board[row][col].getClass().getName() == "Snake")
					{
						if (direction == 1)
							System.out.print("^ ");
						if (direction == 2)
							System.out.print("> ");
						if (direction == 3)
							System.out.print("v ");
						if (direction == 4)
							System.out.print("< ");
					}
					

							
				}
				
				System.out.println();
			}
			
			System.out.println(score);
		}
		
		//Returns an array with val added to each element
		private static int[] Shift(int[] arr, int val)
		{
			for(int i = 0; i < arr.length; i++)
			{
				arr[i] += val;
			}
			
			return arr;
		}
		
		
		//Returns the index position of the first occurrence of a Snake object with a given row and column
		private int findTail(int row, int col)
		{
			for(int i = 0; i < Tail.size(); i++)
			{
				if(row == Tail.get(i).getRow() && col == Tail.get(i).getCol())
					return i;
			}
			
			return -1;
		}
		
		//Returns the index position of the first occurrence of an EmptySquare object with a given row and column
		private int findEmptySquare(int row, int col)
		{
			for(int i = 0; i < Squares.size(); i++)
			{
				if(row == Squares.get(i).getRow() && col == Squares.get(i).getCol())
					return i;			
			}
			
			return -1;

		}
		
		private int findApple(int row, int col)
		{
			for(int i = 0; i < appleCount; i++)
			{
				if(row == appleOrder[i].getRow() && col == appleOrder[i].getCol())
					return i;			
			}
			
			return -1;

		}
		
		private void Move(int row, int col)
		{	
			if (row < 0)
				row = board.length - 1;
			if (row >= board.length)
				row = 0;
			if (col < 0)
				col = board[0].length - 1;
			if (col >= board[0].length)
				col = 0;
			
			moveCount ++;
			
			boolean isApple;
			if (board[row][col].getClass().getName() == "Apple")
				isApple = true;
			else
				isApple = false;
			
			Snake x = new Snake(row, col); 
			board[row][col] = x; //Adds a Snake object to the board at row, col
			Tail.add(0, x); //Adds a Snake object to the first index position of the ArrayList Tails
			
			
			

			
			if(isApple) //Checks if the square contained an Apple object
			{	
				score++;
				int ind = findApple(row, col);
				spawnApple(ind);
			}
			
			else 
			{
				Snake y = Tail.remove(Tail.size() - 1); //Removes the Snake object at the last index position of the ArrayList Tails and sets it equal to y
				
				if (!((findTail(y.getRow(), y.getCol())) >= 0)) //Checks if there is a tail object with the same row and column as y
					{
						EmptySquare z = new EmptySquare(y.getRow(), y.getCol());
						board[y.getRow()][y.getCol()] = z; //Adds an EmptySquare object to the board at y's row and y's col
						if (findEmptySquare(row, col) < 0)
							Squares.add(0, z); //Adds an EmptySquare object to the ArrayList Squares with y's row and y's col
					}
			}
			
			
		}
		
		private void spawnApple(int index)
		{
			int x = (int)(Math.random()*Squares.size()); //Randomly selects the index position of an element in the ArrayList Squares
			
		    while (findTail(Squares.get(x).getRow(), Squares.get(x).getCol()) >= 0 || findApple(Squares.get(x).getRow(), Squares.get(x).getCol()) >= 0) {
				
		    	 Squares.remove(x); x = (int)(Math.random()*Squares.size());
			}
				
		
			Apple y = new Apple(Squares.get(x).getRow(), Squares.get(x).getCol(), 0); //A new Apple object is created with the row and column of the randomly selected EmptySquare
			board[y.getRow()][y.getCol()] = y; //The new Apple object is added to the board
			appleOrder[index] = y;
			Squares.remove(x); //The empty square is removed from the ArrayList Squares
			
			if (appleCount == 1)
			{
				
			}
			
			test = 0; t1 = y.getRow(); t2 = y.getCol(); t3 = Tail.get(0).getRow(); t4 = Tail.get(0).getCol();
			
		}
		
		private int findRowDifference(int row1, int row2)
		{
			
			int rowDifference = row1 - row2; 
			if (rowDifference < board.length/2 * -1)
			{
				rowDifference = board.length - rowDifference * -1;
				up = true;
			}

			else if (rowDifference > board.length/2)
			{
				rowDifference = board.length - rowDifference;
				up = false;
			}
			
			else if (rowDifference < 0)
			{
				rowDifference *= -1;
				up = false;
			}
			
			else
				up = true;
			
			return rowDifference;
		}  
		
		private int findColDifference(int col1, int col2)
		{	

			int colDifference = col1 - col2;
			
			if (colDifference < board[0].length/2 * -1)
			{
				colDifference = board[0].length - colDifference * -1;	
				left = true;
			}
			
			else if (colDifference > board[0].length)
			{
				colDifference = board[0].length - colDifference;
				left = false;
			}
			
			else if (colDifference < 0)
			{
				colDifference *= -1;
				left = false;
			}
			
			else 
				left = true;
			
			return colDifference;
		}
		
		
		private void findMove()
		{
			if (appleCount == 1)
				findMoveOneApple();
			else if (appleCount == 3)
				findMoveThreeApples();
			else if(appleCount == 5)
				findMoveFiveApples();
		}
		
		private void findMoveOneApple()
		{	
			test++; 

			//Display();
			//System.out.println("Head row: " + Tail.get(0).getRow() + " Head col: " + Tail.get(0).getCol());
			
			
			int rowDifference = findRowDifference(Tail.get(0).getRow(), appleOrder[0].getRow());
			int colDifference = findColDifference(Tail.get(0).getCol(), appleOrder[0].getCol());
			
			
			if (test > 3)
			{
				Display(); System.out.println("Apple spawn row: " + t1 + " Apple spawn col: " + t2 + " Head when spawn row: " + t3 + " Head when spawn col: " + t4);
				
				for (int i = 0; i < appleCount; i++)
				{
					System.out.println(appleOrder[i]);
				}
				System.exit(0);
			}
	
			
			if (up == true && direction != 3 && rowDifference > 0)
			{	
				direction = 1;
				
				for (int i = 0; i < rowDifference; i++)
				{
					Move(Tail.get(0).getRow() - 1, Tail.get(0).getCol());
				}
				
				rowDifference = 0;
			}

			else if (up == false && direction != 1 && rowDifference > 0)
			{	
				direction = 3;
				
				for (int i = 0; i < rowDifference; i++)
				{
					Move(Tail.get(0).getRow() + 1, Tail.get(0).getCol()); 
				}
				
				rowDifference = 0;
			}
			
			if (left == true && direction != 2 && colDifference > 0)
			{	
				direction = 4;
				
				for (int i = 0; i < colDifference; i++)
				{
					Move(Tail.get(0).getRow(), Tail.get(0).getCol() - 1);
				}
				
				colDifference = 0;
			}

			else if (left == false && direction != 4 && colDifference > 0)
			{	
				direction = 2;
				
				for (int i = 0; i < colDifference; i++)
				{
					Move(Tail.get(0).getRow(), Tail.get(0).getCol() + 1); 
				}
				
				colDifference = 0;
			}
			
			if (up == true && direction != 3 && rowDifference > 0)
			{	
				direction = 1;
				
				for (int i = 0; i < rowDifference; i++)
				{
					Move(Tail.get(0).getRow() - 1, Tail.get(0).getCol()); 
				}
				
				rowDifference = 0;
			}

			else if (up == false && direction != 1 && rowDifference > 0)
			{	
				direction = 3;
				
				for (int i = 0; i < rowDifference; i++)
				{
					Move(Tail.get(0).getRow() + 1, Tail.get(0).getCol()); 
				} 
				
				rowDifference = 0;
			}
			
			if (rowDifference != 0)
			{
				Move(Tail.get(0).getRow(), Tail.get(0).getCol() - 1);	
				direction = 3; 
				findMoveOneApple();
			}
			
			if (colDifference != 0)
			{
				Move(Tail.get(0).getRow() - 1, Tail.get(0).getCol());
				direction = 1; 
				findMoveOneApple();
			}
			
		}
		
		private void findMoveThreeApples()
		{
			appleOrder[0].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[0]), 0));
			appleOrder[1].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[1]), 1));
			appleOrder[2].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[2]), 2));

			Arrays.sort(appleOrder, 0, 2);
			
			
			findMoveOneApple();
			
		}
		
		private void findMoveFiveApples()
		{
			appleOrder[0].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[0]), 0));
			appleOrder[1].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[1]), 1));
			appleOrder[2].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[2]), 2));
			appleOrder[3].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[3]), 3));
			appleOrder[4].setDistance(addDistance(findDifference(Tail.get(0), appleOrder[4]), 4));

			Arrays.sort(appleOrder);
			
			findMoveOneApple();
			
		}
		
		private int addDistance(int distance, int appleIndex)
		{
			if (appleOrder[appleIndex].getRow() == Tail.get(0).getRow())
			{
				if (left && direction == 2)
				{
					distance += 2;
				}
				
				else if (!left && direction == 4)
				{
					distance += 2;
				}
			}
			
			else if (appleOrder[appleIndex].getCol() == Tail.get(0).getCol())
			{
				if (up && direction == 3)
				{
					distance += 2;
				}
				
				else if (!up && direction == 1)
				{
					distance += 2;
				}
			}
			
			return distance;
		}
		
		private int findDifference(GameObjects one, GameObjects two)
		{
			return findRowDifference(one.getRow(), two.getRow()) + findRowDifference(one.getCol(), two.getCol());
		}
		
		
		private void appleSwap(int index1, int index2)
		{
			Apple temp = appleOrder[index1];
			appleOrder[index1] = appleOrder[index2];
			appleOrder[index2] = temp;
		}
		
		
		public int Play(int tScore)
		{		
			targetScore = tScore;
			
			while (score < tScore)
			{
				findMove();	
			}
					
			return moveCount;
		}
		
		

}
