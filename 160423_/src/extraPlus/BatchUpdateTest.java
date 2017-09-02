package extraPlus;

import java.sql.Statement;

public class BatchUpdateTest {

	public static void main(String[] args) {
		
		Statement stmt = con.createStatement();
//		stmt.executeUpdate("insert 문장");
//		stmt.executeUpdate("insert 문장");
//		stmt.executeUpdate("insert 문장");
//		...300개의 insert문장이 있으면 어떻게 하나하나마다 쿼리를 날려... 존나 비효율적! 그래서
		//배치에 모아 한 번에 날림
		
		stmt.addBatch("insert 문장");
		stmt.addBatch("insert into account values('111-11-9999', '김개똥', 100)");
		stmt.addBatch("insert into account values('152-11-9999', '김개투', 300)");
		stmt.addBatch("insert into account values('111-12-9999', '김개삼', 200)");
		
		int [] results = stmt.executeBatch();//executeUpdate()를 한 번에 수행
		for(int i = 0 ; i<results.length; i++)
		{
			System.out.println(results[i] + "행을 수행하였어.");
		}

	}

}
