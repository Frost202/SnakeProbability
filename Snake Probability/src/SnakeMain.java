import java.util.Scanner;
public class SnakeMain {
	

	
	public static void playManyTimes(int playTimes, int targetScore, double targetTime, int rows, int cols, int appleCount, double speed, double reactionTime)
	{	
		int averageMoveCount = 0;
		int successfulRuns = 0;
		int targetMoveCount = (int)(targetTime*1000/speed);	
	
		for (int i = 0; i < playTimes; i++)
		{
			Board x = new Board(rows, cols, appleCount, speed);
			int y = x.Play(targetScore);
			
			if (appleCount == 0)
			y += (int)(targetScore*reactionTime);
			
			averageMoveCount += y;
			
			if (y <= targetMoveCount)
				successfulRuns++;
		}
		
		System.out.println("The average run time was " + averageMoveCount/playTimes*speed/1000 + " seconds");
		System.out.println("The number of runs below or equal to the target time of " + targetTime + " seconds is " + successfulRuns + " out of " + playTimes + " total play times" + "\t" + successfulRuns + "/" + playTimes);
	}


	public static void main(String[] args) {
			
		int boardSize;
		int appleCount;
		double speed;
		int rows; 
		int cols;
		int playTimes;
		double targetTime;
		int targetScore;
		double reactionTime;
	
		Scanner scan = new Scanner(System.in);
			
		System.out.println("Enter the board size");
		boardSize = scan.nextInt();
		System.out.println("Enter the apple count");		
		appleCount = scan.nextInt();
		System.out.println("Enter the speed");
		speed = scan.nextInt();
		System.out.println("Enter the target score");
		targetScore = scan.nextInt();
		System.out.println("Enter the target time");
		targetTime = scan.nextDouble();
		System.out.println("Enter the amount of play times");
		playTimes = scan.nextInt();
		
		if (boardSize == 1)
		{
			rows = 9; cols = 10; reactionTime = (4 + speed)/10;
		}
		else if (boardSize == 2)
		{
			rows = 15; cols = 17; reactionTime = (8 + speed)/17;
		}
		else
		{
			rows = 21; cols = 24; reactionTime = (11 + speed)/24;
		}
		
		
		if (speed == 3)
		{
			speed = 89.1; reactionTime *= 3;
		}
		
		else if (speed == 2)
		{
			speed = 135; reactionTime *= 2;
		}
		else
		{
			speed = 179.55; 
		}
		

	
	

	
		playManyTimes(playTimes, targetScore, targetTime, rows, cols, appleCount, speed, reactionTime);
	
	
		
		
	/*	for (int x = 0; x < 25; x++)
		{	
			Board test = new Board(9, 10, 3);
			test.Play(25);
		}
	*/
		
	}

}
