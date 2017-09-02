/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	static Connection con = null;  //con을 하나만 만들려고 static!
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	String user = "scott";
	String pass = "tiger";
	
	Database(){
		
		try {
			
			// 1. 드라이버로딩
			Class.forName(driver);
			
			// 2. Connection 연결객체 얻어오기
			con = DriverManager.getConnection(url, user, pass);
			// [지역변수 안되] Connection con = ...;
			
			System.out.println("실제로 DB에 연결을 얻어옵.");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	
	
	static public Connection getConnection() throws Exception{
		
		if(con == null){
			new Database();
		}
		
		return con;
	}
}
 public Th01_DBConnect getInstance(){ 메소드의 리턴형이 클래스(인스턴스)임 그래서 void대신 Th01_DBConnect.
 * 유저 a, b, c로 부터 이것을 클래스명.getInstance로 실행하려면 static을 붙이면 되지. 

 보안을 위해 생성자를 private. 동시에 아무나 만들수 없게 끔 하려는 의도도 존재.(하나만 있어야 하니까)
 * 근데 생성자를 private 하려니 유저들이 객체를 생성못하지. 객체는 생성해야되. 커넥션해야 하니.
 * 그래서 각 유저들이 클래스명.getInstance가 가능하도록(이걸로 DB연결을 할 수 있도록) 앞에 static을 붙임.
 * 외부에서 접근 가능하게 public을 붙여서 public Th01_DBConnect getInstance 자기 자신을 인스턴스.
 * 
 * 그럴때 static을 사용하지. 그래서 앞에 static 붙임.
 * static public Th01_DBConnect getInstance()
 * 이제 유저들은 이 방식으로 연결 생성 가능  con = Th01_DBConnect.getInstance(); 

DB연결은 우리가 알고 있는 상시 인터넷 연결(물리적 네트워크) 등의 지속적 연결 같은 게 아님.
 * 조회나 수정 등 볼일만 딱 보고 끝나는 거임. 대기시간도 오래걸리지 않음.
 * 즉 소수의 커넥션 제한으로 다수를 상대 할 수 있다.
 * 그래서 여기서는 한 개만 만들 수 있게 한거고 이를 static의 특성을 빌린 거임.

//package model;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
///* DB커넥트 생성기.
// * 생성자를 private했을 때 어떻게 할 것인가.
// * 타 클래스에서 (DBconnect클래스)
// * 이 클래스로 인스턴스 생성 시 메모리에 단 하나만 생성하고 싶다면?*/
//public class DBConnect_Genrtor {
//
//	static DBConnect_Genrtor con = null;  //con을 하나만 만들려고 static!
//	
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//	String user = "scott";
//	String pass = "tiger";
//	//생성자가 private이기 때문에 
//	//위의 4개 항목을 그냥 단순히 접근제한자를 default로 줘도 불러올 수 없다.?(==보안에 충족한다?) 틀림.
//	//위의 4개가 죄다 디폴트라서 같은 패키지면 접근이 가능해....
//	
//	//DBConnect_Genrtor dbcon = new DBConnect_Genrtor()할 경우
//	//dbcon.user로 접근이 가능한데 이걸 막기 위해서 private을 지정했잖아. 그럼 static으로 해야 할 텐데
//	//DBConnect_Genrtor.
//	
////	Statement stmt = null;
////	PreparedStatement ps = null;
//	
//	private DBConnect_Genrtor() { //생성자를 public 아닌 private로.
//		//아무도 이 생성자를 사용하지 못하도록(DBConnect_Genrtor 객체 생성못하도록) 조치.
//		
//		try {
//			
//			// 1. 드라이버로딩
//			Class.forName(driver);
//			
//			// 2. Connection 연결객체 얻어오기
//			con = DriverManager.getConnection(url, user, pass);
//			// [지역변수 안되] Connection con = ...;
//			
//			System.out.println("실제로 DB에 연결을 얻어옵.");
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}	
//	
//	static public DBConnect_Genrtor getInstance(){
//		/* 리턴형이 클래스(자기 자신).
//		 * 또한 외부에서 이걸 불러야 하기 때문에 public.
//		 * 외부에서 이 메소드로 커넥션 활성화 하기 때문에 public
//		 * 어떻게 커넥션 활성화? getInstance()로 이 메소드 실행 = DBConnect_Genrtor 객체 생성.
//		 * '이 메소드로 커넥션 활성화' =
//		 * 
//		 * 이게 왜 static이야? 이걸로 생성할 객체의 접근제한자가 static이잖.
//		 * 또한 외부에서 이걸 불러야 하기 때문에 public.
//		 * 
//		 * 참고. 객체를 리턴할 필요가 없고 커넥션만 리턴.*/
//		
//		if(con == null){ //유저 b가 들어왔을 때 con이 null? 그럼
//			con = new DBConnect_Genrtor();
//		}
//		
//		//이렇게 되면
//		//DBConnect_Genrtor가 만들어질 때
//		//메모리에 단 하나만 생성되며
//		//아무나 생성을 못해.
//		
//		return con;
//	}
//}