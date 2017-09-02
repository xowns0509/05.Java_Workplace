package extraPlus;

import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSetTest {

	public static void main(String[] args) {
		
		Statement stmt = con.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("select * from dept");
		
		while(rs.next()){
			System.out.println(rs.getInt("empno"));
			System.out.println(rs.getInt("ename"));
			
			int sal = rs.getInt("sal");
			rs.updateInt("sal", sal+1000);
			rs.updateRow();// 변경된 결과집합을 실행.resultSET수행.
			
			//오라클은 CONCUR_UPDATABLE이 기능을 안함. 즉 DB서버가 오라클을 사용한다면 이거 불가능
			//마이 에스큐엘은 CONCUR_UPDATABLE 가능해.
		
		}
		
	}

}
