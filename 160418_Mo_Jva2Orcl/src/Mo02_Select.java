import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mo02_Select {

	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String pass = "tiger";
		
		Connection con = null;
		
		try{
			//1. 드라이버 로딩
			Class.forName(driver);
			
			//2. 연결객체 얻어오기
			con = DriverManager.getConnection(url, user, pass); //con을 밖으로 선언. finally에서도 유효하기 위해.
			System.out.println("연결성공 함!");
			
			//3. sql 문장만들기		
			String sql = "SELECT * FROM ACCOUNT";
			//가독율 때문에 sql문장에 키워드를 죄다 대문자로 쓴거야
			//이렇게 안하면 나중에 찾기 너무 어려워
			
			//4. sql 전송객체 얻어오기
			Statement stmt = con.createStatement();
			
			//5. 전송. 
			ResultSet rs = stmt.executeQuery(sql); //정수형 등의 값 하나가 아니라
			//외부에서 접속하기 때문에 executeQuery자체는 실행이자 오토커밋임.
			//단, 오라클 내부에서 테이블을 commit안 시키면 자바가 blocking상태에 빠짐.
			
			//테이블에 관련된 데이터를 뭉탱이로 던져줌.
			//그걸 우리는 ResultSet으로 받아 반복문으로 읽어 내려가야 해.
			while(rs.next()){ //반복문으로 읽어 내려가야 해.
				String account_num = rs.getString("account_num");
				String customer = rs.getString("CUSTOMER"); //
				int amount = rs.getInt("AMOUNT");
				
				System.out.println(account_num +">"+customer +"님은"+ amount + "있음.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
			try {con.close(); }catch(Exception ex){}//실패를 해도 성공을 해도 죄다 정상적으로 닫아줘야 해. 그래서 finally에.
		}
		//6.닫기

	}

}
