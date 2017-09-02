package extraPlus;

import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSetUpdateTest {

	public static void main(String[] args) {
		
		//갖고 온놈들을 저장할시 반드시 배열을 사용해야 한다면
		//전체 갯수를 얻어온 후에 얻어와야 겠지
		//(배열은 갖고오는 놈들의 양을 정확히 알아야 쓰잖아.)
		
		//그래서 우리는 쿼리를 두번 날려야 해
		String previousSql = "select count(*) as cnt from emp";
		String sql = "select * from emp"
	
		//근데 리절트 셋을 잘 이용하면 String sql 한번 만으로도 됨.
		Statement stmt = con.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String sql = "select * from emp";
		ResultSet rs = stmt.executeQuery(sql);
		//다 갖고 왔음
		
		//이제 갖고 온 것들에서 커서를 맨 마지막으로 보내놓고 
		rs.last();
		
		//그 마지막이 몇 번째 행인지 알아 낸 후에 카운트에다 저장해 놓음
		//여기까지 우리는 위의 쿼리로 얼마만큼의 양을 갖고 왔는지 파악했다.
		int count = rs.getRow();
		System.out.println(count);
		
		//그런 후 커서를 다시 first로 올려놔.
		rs.first();
		
		//그래서 데이터를 읽어오기 시작.
		

	}

}
