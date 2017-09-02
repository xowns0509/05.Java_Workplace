package extraPlus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class imgtest {

	public imgtest() throws Exception{
	}

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String pass = "tiger";

		Connection con = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		
		try {
			//1. 드라이버로딩
			Class.forName(driver);


			//2. 연결객체 얻어오기
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("연결성공!");

			//2.1 파일 얻어오기
			File file = new File("9.png");
			FileInputStream fis;
			fis = new FileInputStream(file);

			// 3. sql 문장만들기
			String sql = "INSERT INTO imgtest VALUES(?, ?)";
			System.out.println(sql);

			//4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, file.toString());
			ps.setBinaryStream(2, fis, (int)file.length());//통로를 통으로 지정.
			// file.length()는 파일을 읽었을 때 그 길이값을 알기위해. length가 long형임

			//5.전송
			ps.executeUpdate();

			//닫기
			fis.close();
			ps.close();
			con.close();

		}catch (FileNotFoundException e) {			
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
