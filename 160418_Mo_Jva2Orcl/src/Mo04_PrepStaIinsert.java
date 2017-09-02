import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mo04_PrepStaIinsert {
	public static void main(String[] args) {
		
		int amount = 50000;
		//금액이 위의 변수 50000 이상인 금액의 계좌 정보를 출력
		//preparedstatement 이용

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String pass = "tiger";

		Connection con = null; //초기화 안하면 에러
		Statement stmt = null;
		PreparedStatement ps = null;//명사형이라서 ed 붙음.

		try {
			//1.드라이버로딩
			Class.forName(driver); 		//arg안에 스트링으로 들어오는 거 경로로 찾아 메모리에 올리는 놈.

			//2.연결객체 얻어오기			
			con = DriverManager.getConnection(url, user, pass); //con을 밖으로 선언. finally에서도 유효하기 위해.
			System.out.println("연결성공 함!");

			//3. sql 문장만들기
			String sql = "SELECT * FROM account WHERE amount >= ?";
//			String sql = "SELECT account_num, amount FROM account WHERE amount >= ?";
			//이거 하면 안되. 의뢰 한건 2열인데 밑에서 출력하는건 3열이잖아. 그래서 에러 뜸.
			System.out.println(sql);
			
			//4. sql 전송객체 얻어오기
			ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
			ps.setInt(1, amount);		
			
			//5. sql 전송하기
			ResultSet rs = ps.executeQuery();
//			int result = ps.executeUpdate(); 이거 작성하면 밑의 콘솔에서 결과를 못내놔.
			
//			//6. 결과처리
//			System.out.println(result + " 행을 수행했어");
			
			while(rs.next()){ //반복문으로 읽어 내려가야 해.
				String account_num = rs.getString("account_num");
				String customer = rs.getString("CUSTOMER"); //
				int amount1 = rs.getInt("AMOUNT");
				
				System.out.println("여기");
				System.out.println(account_num +">"+customer +"님은"+ amount1 + "있음.");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
			try {
				//7. 닫기. 실패를 해도 성공을 해도 죄다 정상적으로 닫아줘야 해. 그래서 finally에 넣음. stmt도 지역말고 전역변수로 바꿔선언해줌.
				stmt.close();
				con.close(); }catch(Exception ex){}//
		}
	}
}