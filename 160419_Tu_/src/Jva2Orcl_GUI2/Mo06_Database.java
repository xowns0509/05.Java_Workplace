package Jva2Orcl_GUI2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Mo06_Database {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	String user = "scott";
	String pass = "tiger";

	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	private Mo06_Database() throws Exception{
		//생성자를 프라이빗으로 막음.아무나 호출 할 수 없도록.
		//또한 단 한번만 호출 되도록 static.
		
		//1. 드라이버로딩
		Class.forName(driver);

		//2. 연결객체 얻어오기
		con = DriverManager.getConnection(url, user, pass);
		System.out.println("연결성공!");

	}
	
	static Mo06_Database db = null;
	private Record2 rec;
	
	public static Mo06_Database getInstance() throws Exception
	{
		if(db == null){//언제만 객체 생성할래? DB가 널일때만
			db = new Mo06_Database();
		}
		return db;
		
	}

	public int insert (Record2 rec) throws Exception{

		//3. sql문장 만들기 (INSERT 문장 만들기)
		String sql = "INSERT INTO memberTemp VALUES(?,?,?,?,?,?)";
		System.out.println("sql문장실행: "+sql);

		//4. sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		ps.setString(1, rec.getName());
		ps.setString(2, rec.getTel());
		ps.setString(3, rec.getJumin());
		ps.setString(4, rec.getGender());
		ps.setInt(5, Integer.parseInt(rec.getAge()));
		ps.setString(6, rec.getHome());

		//5. 전송(executeUpdate())
		int result = ps.executeUpdate();
		
		//6. 결과 처리
		System.out.println(result + " 행을 수행했어.(Database클래스단)");

		//7. 닫기 (ps만 닫아야 해. connection 닫으면 안 됨.)
		ps.close();

		return result;
	}
	
	public int modify (Record2 rec) throws Exception{

		//3. sql문장 만들기 (INSERT 문장 만들기)
		String sql = "UPDATE info SET NAME=?, MINBUN=?, GENDER=?, AGE=?, DOSI=? WHERE TEL=?";
		System.out.println("sql문장실행: "+sql);
		//UPDATE info
		//set NAME='이름', MINBUN='33334', GENDER='여자', AGE=43, DOSI='서남'
		//WHERE TEL = '0001'; --sql문장 테스트

		//4. sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		ps.setString(1, rec.getName());
		ps.setString(2, rec.getJumin());
		ps.setString(3, rec.getGender());
		ps.setInt(4, Integer.parseInt(rec.getAge()));
		ps.setString(5, rec.getHome());
		
		ps.setString(6, rec.getTel());
		
		//5. 전송(executeUpdate())
		int result = ps.executeUpdate();
		
		//6. 결과 처리
		System.out.println(result + " 행을 수행했어.(Database클래스단)");
		
		//7. 닫기 (ps만 닫아야 해. connection 닫으면 안 됨.)
		ps.close();
		return result;
	}
	
	public Record2 searchByTel(String tel) throws Exception
	{
		//rec = new Record2();//객체 생성도 안하고 밑에서 함수불러오려 했냐??!!!!!
		//게다가, 지금 Record2엔 인자 없는 생성자가 없잖아.
				
		//3. sql문장 만들기 (select 문장 만들기)
		String sql = "SELECT * FROM info WHERE tel = ?";

		//4. sql 전송객체 얻어오기
		ps = con.prepareStatement(sql);//그리고 여기서 sql끌고감.
		ps.setString(1, tel);
		
		//5. sql 전송하기
		ResultSet rs = ps.executeQuery();//위에서 sql 넣었으니 여기서 안넣어도 됨.
		
		//System.out.println(rs.next());// 처음엔 true.
		//System.out.println(rs.next());// 그다음은 false. 위에서 한번 출력했으니까! 그다음은false야.
		//지금 pk로 검색해서 하나의 결과만 갖고 오는거잖아. 그러니까 바로 한번 출력하면 false지!!
		
		
		//6. 결과얻어오기.
		if(rs.next()){ //각 컬럼값 얻어오기
			
			//ResultSet rs이 값들을 받았을 때
			//원래 처음에는 가저온 값들의 첫번째...
			//의 이전 값을 가리키고 있다. 거기는 값이 아무것도 없는 공간이지.
			//그러니까 거기서부터 값을 가져오라고 하지 않고
			//rs.next()로 포인터를 밑으로 내려서 그 다음부터 갖고 오라고 해야지.
		
/*			rec.setName(rs.getString("NAME"));
			rec.setTel(rs.getString("TEL"));
			rec.setJumin(rs.getString("MINBUN"));
			rec.setGender(rs.getString("GENDER"));
			rec.setAge(String.valueOf(rs.getInt("AGE")));
			rec.setHome(rs.getString("DOSI"));		
			뭣하러 이렇게 다 일일이 넣어. 생성자에다 처넣하면 되는데 */

			//테스트용. 제대로 갖고왔는가.
			//System.out.println(rs.getString("NAME") +">"+rs.getString("TEL") +">"+ rs.getString("MINBUN")  +">"+ rs.getString("GENDER") +">"+ String.valueOf(rs.getInt("AGE")) +">"+ rs.getString("DOSI"));			

			//레코드의 생성자가 인자가 있을 경우.
			//public Record2(String tel, String name, String jumin, String age, String gender, String home) {
			rec = new Record2(rs.getString("TEL"), rs.getString("NAME"), rs.getString("MINBUN"), String.valueOf(rs.getInt("AGE")), rs.getString("GENDER"), rs.getString("DOSI"));

			//테스트용. 제대로 Record2에 넣었는가.
			//System.out.println(rec.getName() +">"+rec.getTel() +">"+ rec.getJumin() +">"+ rec.getGender() +">"+ rec.getAge() +">"+ rec.getHome());

		}
		
		return rec;
	}
	
	
	//미구현
	public ArrayList<Record2> seeAll(Record2 rec) throws Exception{
		//갖고 오는걸 어레이리스트로! 얼마나 갖고오는지 모르니까.
		ArrayList<Record2> recARL = new ArrayList<Record2>(); 
		
		//3. sql문장 만들기 (select 문장 만들기)
		String sql = "SELECT * FROM info";
		//String sql = "SELECT account_num, amount FROM account WHERE amount >= ?";
		
		//4. sql 전송객체 얻어오기
		ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		
		//5. sql 전송하기
		ResultSet rs = ps.executeQuery();//위에서 넣었으니 여기서 안넣어도 됨.
		
		//6. 결과처리
		while(rs.next()){ //반복문으로 읽어 내려가야 해.
			
			rec = new Record2(rs.getString("TEL"), rs.getString("NAME"), rs.getString("MINBUN"), String.valueOf(rs.getInt("AGE")), rs.getString("GENDER"), rs.getString("DOSI"));
			recARL.add(rec);
			
		}
		return recARL;
	}
}

/*NAME	rec.getName();
TEL	rec.getTel();
MINBUN	rec.getJumin();
GENDER	rec.getGender();
AGE		rec.getAge();
DOSI	rec.getHome();*/
//		Integer.parseInt(ageS)