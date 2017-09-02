//DB커넥트 유저들.
class Th02_DBUsrA {
	 Th02_DBConnect_Genrtor con; //커넥션 객체를 위한 준비, 선언.

	public Th02_DBUsrA() {
		
//		con = new Th01_DBConnect(); 이렇게 하면 직접 접근을 못하므로
//		con = 객체명.getInstance 이렇게 해야 할텐데,
//		우리가 객체를 못만들게 했어. 생성자를 private로 지정했잖아.
//		하지만 연결은 만들어야해. 그렇다면,
//		클래스명.getInstance로 실행 시 가능하게 하면 되잖. 따라서

		con =  Th02_DBConnect_Genrtor.getInstance(); // 이걸로 커넥션 ....
		/*cf) public Th01_DBConnect(String[] args)
		이 String[] args 안에 있다면 생성자 undefined에러 뜸 */
	}
	
	public void use(){
		System.out.println("DB 작업중");
	}
}

class Th01_DBUsrB {
	Th02_DBConnect_Genrtor con;

	public Th01_DBUsrB() {
		con =  Th02_DBConnect_Genrtor.getInstance();
		//public Th01_DBConnect(String[] args) 이 String[] args 안에 있다면 생성자 undefined에러 뜸
	}
	
	public void use(){
		System.out.println("DB 작업중");
	}
}

class Th01_DBUsrC {
	Th02_DBConnect_Genrtor con;

	public Th01_DBUsrC() {
		con =  Th02_DBConnect_Genrtor.getInstance();
		//public Th01_DBConnect(String[] args) 이 String[] args 안에 있다면 생성자 undefined에러 뜸
	}
	
	public void use(){
		System.out.println("DB 작업중");
	}
}