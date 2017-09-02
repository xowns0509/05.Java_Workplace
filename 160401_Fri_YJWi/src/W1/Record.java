package W1;

public class Record implements java.io.Serializable{
	private String name;
	private String id;
	private String tel;
	private String sex;
	private String age;
	private String addr;
	
	public Record(String name, String id, String tel, String sex, String age, String addr ){
		this.name = name;
		this.id = id;
		this.tel = tel;
		this.sex = sex;
		this.age = age;
		this.addr = addr;
	}
	//getter method
	public String getName(){
		return name;
	}
	public String getId(){
		return id;
	}
	public String getTel(){
		return tel; 
	}
	public String getSex(){
		return sex;
	}
	public String getAge(){
		return age;
	}
	public String getAddr(){
		return addr;
	}
	//setter method
	public void setName(String name){
		this.name = name;
	}
	public void setId(String id){
		this.id = id;
	}
	public void setTel(String tel){
		this.tel = tel;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public void setAge(String age){
		this.age = age;
	}
	public void setAddr(String addr){
		this.addr = addr;
	}
}

