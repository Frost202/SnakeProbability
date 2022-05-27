
public class Apple extends GameObjects implements Comparable <Apple> {
	
	int distance;
	
	public Apple(int row, int col, int d)
	{	
		super(row, col);
		distance = d;
	}
	
	public void setDistance(int d)
	{
		distance = d;
	}
	
	public int getDistance()
	{
		return distance;
	}
	
	public int compareTo (Apple otherApple)
	{	
		return distance - otherApple.getDistance();
	}

	

}
