import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class df {

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String pass = "tiger";

		Connection con = null;
		CallableStatement cs = null;

		try {
			// 1. 드라이버로딩
			Class.forName(driver);

			// 2. Connection 연결객체 얻어오기
			con = DriverManager.getConnection(url, user, pass);
			// [지역변수 안되] Connection con = ...;

			//3. sql 문장만들기
			//String sql = "{ call ins_temp() }"; {와 "의 공백을 주면 안 되. 인식못해
			//프로시져, 함수 호출시
			String sql = "{call ins_temp(?,?,?)}";
			
			//4. sql 전송객체 얻어오기(Statement, PreparedStatement, CallableStatement)
			cs = con.prepareCall(sql);//콜러블은 프리페어 스테이트 먼트의 자식임
			cs.setString(1, "333-2334-243");
			cs.setString(2, "박가네");
			cs.setInt(3, 30000);

			//5. sql 전송
			cs.executeUpdate();
			//프로시져 실행해봤자 결과집합을 받을 필요가 없잖아. 셀렉트처럼.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
