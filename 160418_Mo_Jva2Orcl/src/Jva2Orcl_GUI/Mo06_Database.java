package Jva2Orcl_GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Mo06_Database {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	String user = "scott";
	String pass = "tiger";

	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	public Mo06_Database() throws Exception{

		//1. 드라이버로딩
		Class.forName(driver);

		//2. 연결객체 얻어오기
		con = DriverManager.getConnection(url, user, pass);
		System.out.println("연결성공!");

	}

	public int insert (Record2 rec) throws Exception{

		//3. sql문장 만들기 (INSERT 문장 만들기)
		String sql = "INSERT INTO info VALUES(?,?,?,?,?,?)";
		System.out.println("sql문장실행: "+sql);

		//4. sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		ps.setString(1, rec.getName());
		ps.setString(2, rec.getTel());
		ps.setString(3, rec.getJumin());
		ps.setString(4, rec.getGender());
		ps.setInt(5, Integer.parseInt(rec.getAge()));
		ps.setString(6, rec.getHome());

		//5. 전송(executeUpdate())
		ResultSet rs = ps.executeQuery();
		int result = 0;
		//6. 결과 처리

		//7. 닫기 (ps만 닫아야 해. connection 닫으면 안 됨.)
		ps.close();

		return result;
	}
	public ArrayList search(Record2 rec) throws Exception{
		//갖고 오는게 어레이리스트로. 얼마나 갖고오는지 모르니까.
		
		//3. sql문장 만들기 (select 문장 만들기)
		String sql = "SELECT * FROM info";
//		String sql = "SELECT account_num, amount FROM account WHERE amount >= ?";
		
		//4. sql 전송객체 얻어오기
		ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		
		//5. sql 전송하기
		ResultSet rs = ps.executeQuery();//위에서 넣었으니 여기서 안넣어도 됨.
		
		//6. 결과처리
		while(rs.next()){ //반복문으로 읽어 내려가야 해.
			String getName = rs.getString("NAME");
			String getTel = rs.getString("TEL");
			String getJumin = rs.getString("MINBUN");
			String getGender = rs.getString("GENDER");
			int getAge = rs.getInt("AGE");
			String.valueOf(getAge);
			String getHome = rs.getString("DOSI");
			
			System.out.println("여기");
			System.out.println(getName +">"+getTel +">"+ getJumin  +">"+ getGender +">"+ getAge +">"+ getHome);
		}

/*NAME	rec.getName();
 TEL	rec.getTel();
MINBUN	rec.getJumin();
GENDER	rec.getGender();
AGE		rec.getAge();
DOSI	rec.getHome();*/
//		Integer.parseInt(ageS)
		
		return;
	}
}
