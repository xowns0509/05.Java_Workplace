package Jva2Orcl_GUI;

public class Record2 implements java.io.Serializable{

	private String tel;
	private String name;
	private String jumin;
	private String age;
	private String gender;
	private String home;
	
	//constructor
	public Record2(String tel, String name, String jumin, String age, String gender, String home) {
		super();
		this.tel = tel;
		this.name = name;
		this.jumin = jumin;
		this.age = age;
		this.gender = gender;
		this.home = home;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}