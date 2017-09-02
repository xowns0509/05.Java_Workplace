import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mo03_PrepStaIinsert {
	public static void main(String[] args) {

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
			
//			String account_num = "666-666-6666";
//			String customer = "오가네";
//			int amount = 80000;
			//String sql = "INSERT INTO account VALUES('"+ account_num +"', '"+customer+"', "+amount+")";
			//아예 들어가는 내용들을 미완성처리. (?,?,?)
			//물음표를 갖고 있는 미완성 문장.
//			String sql = "INSERT INTO account VALUES(?,?,?)";
			
			//String sql = "666-666-6666 계좌 정보를 박길동, 금액도 98900으로 변경하는 sql"
			String account_num = "666-666-6666";
			String customer = "박길동";
			int amount = 98900;			
			String sql = "UPDATE account SET customer = ?, amount=? where account_num=?";
			
			System.out.println(sql);
			
			//4. sql 전송객체 얻어오기
			//stmt = con.createStatement();//java.sql.statement 임포트임
//			ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
//			ps.setString(1, account_num);//1번째 물음표. 데이터베이스이기 때문에 1번째임. 0번째가 아니고
//			ps.setString(2, customer);
//			ps.setInt(3, amount);

			ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
			ps.setString(1, customer);//1번째 물음표. 데이터베이스이기 때문에 1번째임. 0번째가 아니고
			ps.setInt(2, amount);
			ps.setString(3, account_num);		
			
			//5. sql 전송하기
			//int result = stmt.executeUpdate(sql);
			int result = ps.executeUpdate();//위에서 sql끌고 들어갔으니 여기서는 그냥 공백으로.
					
			//6. 결과처리
			System.out.println(result + " 행을 수행했어");



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
			try {
				//7. 닫기. 실패를 해도 성공을 해도 죄다 정상적으로 닫아줘야 해. 그래서 finally에 넣음. stmt도 지역말고 전역변수로 바꿔선언해줌.
				stmt.close();
				con.close(); }catch(Exception ex){}//
		}
	}
}
/*
		try {
			//1.드라이버로딩
			Class.forName(driver); 		//arg안에 스트링으로 들어오는 거 경로로 찾아 메모리에 올리는 놈.

			//2.연결객체 얻어오기			
			con = DriverManager.getConnection(url, user, pass); //con을 밖으로 선언. finally에서도 유효하기 위해.
			System.out.println("연결성공 함!");

			//3. sql 문장만들기
			String account_num = "777-777-7777";
			String customer = "김가네";
			int amount = 50000;
			
			//String sql = "INSERT INTO account VALUES('"+ account_num +"', '"+customer+"', "+amount+")";
			//아예 들어가는 내용들을 미완성처리
			//(?,?,?)
			//물음표를 갖고 있는 미완성 문장.
			String sql = "INSERT INTO account VALUES(?,?,?)";
			
			System.out.println(sql);
			
			//4. sql 전송객체 얻어오기
			//stmt = con.createStatement();//java.sql.statement 임포트임
			ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
			ps.setString(1, account_num);//1번째 물음표. 데이터베이스이기 때문에 1번째임. 0번째가 아니고
			ps.setString(2, customer);
			ps.setInt(3, amount);

			//5. sql 전송하기
			//int result = stmt.executeUpdate(sql);
			int result = ps.executeUpdate();//위에서 sql끌고 들어갔으니 여기서는 그냥 공백으로.
					
			//6. 결과처리
			System.out.println(result + " 행을 수행했어");



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
			try {
				//7. 닫기. 실패를 해도 성공을 해도 죄다 정상적으로 닫아줘야 해. 그래서 finally에 넣음. stmt도 지역말고 전역변수로 바꿔선언해줌.
				stmt.close();
				con.close(); }catch(Exception ex){}//
		}
	}
}
*/