
public class GameObjects {
	
	private int row;
	private int col;
	
	public GameObjects (int theRow, int theCol)
	{
		row = theRow;
		col = theCol;
	}
	
	public void moveObj(int rowChange, int colChange)
	{
		row += rowChange;
		col += colChange;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public String toString()
	{
		return row + " " + col;
	}

}
