public class We02_ObjectStudent /*extends Object 이거 안줘도 들어가는 거임. */{
	
	//1.Field
	private String hakbun, name;
	
	//2.Constructors
	public We02_ObjectStudent(){}
	public We02_ObjectStudent(String hakbun, String name){
		this.hakbun = hakbun;
		this.name = name;
		
	}
	//3.Methods
	public boolean equals(Object obj){
		//equals 메소드의 기능을 바꾸려고(재정의하려고, 오버라이딩하려고) Object 클래스로부터 equals를 오버라이딩.
		//각각의 이름을 비교하여 같으면 true, 다르면 false를 반환하도록 재정의 하려함.
		
		We02_ObjectStudent s2 = (We02_ObjectStudent)obj;
		//일단 들어온 놈은 Object형으로 들어왔으니 그놈을 받을 또 하나의 객체를 선언생성 한 후 다운케스팅.
		if( name.equals(s2.name))
		{
			return true;
		}
		return false;
	}
	
	/*public String toString(Object obj){
	String mg = "[" +haknun+ "]" +name+ "님";
	return mg
	}	원래는 이와 같이 작성해야 하지만 아래로 한 줄로 정리함.*/
	public String toString(Object obj){
		return "[" +hakbun+ "]" +name+ "님";
	}
}
