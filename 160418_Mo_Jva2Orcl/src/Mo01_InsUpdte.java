import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mo01_InsUpdte {

	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String pass = "tiger";
		
		Connection con = null; //초기화 안하면 에러
		
		try {
			//1.드라이버로딩
			Class.forName(driver); 		//arg안에 스트링으로 들어오는 거 경로로 찾아 메모리에 올리는 놈.
			
			//2.연결객체 얻어오기			
			con = DriverManager.getConnection(url, user, pass); //con을 밖으로 선언. finally에서도 유효하기 위해.
			System.out.println("연결성공 함!");
			
			//3. sql 문장만들기
			//String sql = "INSERT INTO account VALUES('999-99-9999', '홍가네', 100000)"; //뒤에 세미콜론 찍으면 안되.
			//String sql = "INSERT INTO account VALUES('999-99-9999', '홍길동', 100000)"; //홍가네 실행 후 이걸 실행시 
			//SQLexception발생.ORA-00001: 무결성 제약 조건(SCOTT.PK_ACC_ACCOUNT_NUM)에 위배됩니다 라고 뜸.
			String sql = "UPDATE account set customer = '홍길동' where ACCOUNT_NUM = '999-99-9999'"; //오라클 문장 뒤에 세미콜론 찍으면 안되.
			//4. sql 전송객체 얻어오기
			Statement stmt = con.createStatement();//java.sql.statement 임포트임
			
			//5. sql 전송하기
			int result = stmt.executeUpdate(sql);
			
			//6. 결과처리
			System.out.println(result + " 행을 수행했어");
			
			//7. 닫기
			stmt.close();
			//con.close();

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
		
	}
}
