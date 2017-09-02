package IO.objectstream;

public class Record implements java.io.Serializable//직렬화.객체가 Stream을 탈 수 있는 유일한 방법
{
	private String 	name;
	private int		age;
	private double	height;
	private char	bloodType;
	
	
	public Record( String _name, int _age, double _height, char _bloodType)
	{
		name 	= _name;
		age		= _age;
		height	= _height;
		bloodType = _bloodType;	
	}
	
	public String getName()
	{	return name;	}
	
	public int	getAge()
	{	return age;		}
	
	public double getHeight()
	{	return height;	}
	
	public char	getBloodType()
	{	return bloodType;}
		
			
}