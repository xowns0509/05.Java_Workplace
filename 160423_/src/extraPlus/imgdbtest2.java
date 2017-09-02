package extraPlus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.omg.CORBA.portable.InputStream;

public class imgdbtest2 {

	public imgdbtest2() throws Exception{
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
			FileOutputStream fos = new FileOutputStream("copy.gif");
			
			InputStream in = null;
			byte [] buffer = new byte[1024];
			int size = 0;
			
			//1. 드라이버로딩
			Class.forName(driver);

			//2. 연결객체 얻어오기
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("연결성공!");

			// 3. sql 문장만들기
			String sql = "SELECT img_binary FROM imgtest";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				in = rs.getBinaryStream("img_binary")//x통로만 갖고 와 그래서 통로에 연결. 여기까지가 통로만 서로 연결해 놓은 거고
						// copy.gif ======= 자바파일 ======= DB(자바파일에서 서로를 연결.)
			}
			while((size = in.read(buffer)) != -1){//이 아닌동안만 쭉 읽어라)//버퍼를 인자로 넣어주면 읽은 내용을 버퍼에 저장
				fos.write(buffer, 0, size);
			}
			//copy.gif가 만들어지고 db에 저장된 그림이 거기에 담긴다.
			//닫기
			
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
