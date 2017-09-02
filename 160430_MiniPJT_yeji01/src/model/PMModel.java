package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.Database;
import record.EmpRecord;

public class PMModel {

	// Connection con = null;
	Connection con;

	PreparedStatement ps = null;

	public PMModel() throws Exception{
		con = Database.getConnection();	

	}


	/**
	 * 해당 하는 아이디의 업무를 반환하는 메소드 
	 * @param empId 게시물 작성자 아이디 
	 * @return 아이디의 업무 
	 * @throws Exception 아이디의 업무를 가져오며 발생하는 모든 예외를 던짐 
	 */
	public String getJob(String empId) throws Exception{
		String job = "";

		//sql 
		String sql = "SELECT job FROM test_emp WHERE emp_id = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, empId);

		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			job = rs.getString("job");
		}		
		return job;
	}
	public void uploadBoard(EmpRecord rec, String title){
		// 3. (***) sql 문장 만들기
		int pNum = rec.getpNum();
		String filename = "filename";
		String empId = rec.getEmpId();
		String job = rec.getJob();
		String post_type = null;

		System.out.println(" job -> " + job);

		if(job.equals("PM")){
			post_type = "업무지시";
		}else if(job.equals("PL")){
			post_type = "업무보고";
		}

		// post - post_num, p_num, file_name, emp_id, post_time, post_type
		//		,post_stat ,post_title
		String sql = "INSERT INTO test_post VALUES(post_num_seq.nextval, ?, ?, "
				+ "?, sysdate, ?, null, ?)";

		System.out.println(sql);

		try{
			// 4. sql 전송 객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setInt(1, pNum);	// '  ' 붙여줌. 첫번째 - 1
			ps.setString(2, filename);
			ps.setString(3, empId);
			ps.setString(4, post_type);
			ps.setString(5, title);

			// 5. sql 전송하기
			// int result = stmt.executeUpdate(sql);
			int result = ps.executeUpdate();

			// 6. 결과 처리
			System.out.println( result + "행을 수행하였습니다.");

			// 7. 닫기
			ps.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}
	}

	public void deleteBoard(int post_num){
		PreparedStatement ps = null;

		// 3. (***) sql 문장 만들기
		// String emp_id = rec.getEmpId();

		String sql = "DELETE FROM test_post WHERE post_num=?";

		System.out.println(sql);
		try{
			// 4. sql 전송 객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setInt(1, post_num);

			// 5. sql 전송하기
			int result = ps.executeUpdate();

			// 6. 결과 처리
			System.out.println( result + "행을 수행하였습니다.");

			// 7. 닫기
			ps.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}
	}

	public void setPostState(String appr, int post_num){

		PreparedStatement ps = null;

		// 3. (***) sql 문장 만들기
		// String emp_id = rec.getEmpId();

		String sql = "UPDATE test_post SET post_stat=? WHERE post_num=?";

		System.out.println(sql);
		try{
			// 4. sql 전송 객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, appr);		// '  ' 붙여줌. 첫번째 - 1
			ps.setInt(2, post_num);

			// 5. sql 전송하기
			int result = ps.executeUpdate();

			// 6. 결과 처리
			System.out.println( result + "행을 수행하였습니다.");

			// 7. 닫기
			ps.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}
	}

	public EmpRecord searchByEmpId(String empId){

		PreparedStatement ps = null;
		EmpRecord rec = new EmpRecord();

		rec.setEmpId(empId);

		// 3. (***) sql 문장 만들기
		String sql = "SELECT test_job FROM emp WHERE emp_id = ?";

		System.out.println(sql);

		try{
			// 4. sql 전송 객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, empId);		// '  ' 붙여줌. 첫번째 - 1

			// 5. sql 전송하기
			ResultSet rs = ps.executeQuery();
			while( rs.next() ){
				String job = rs.getString("job");
				rec.setJob(job);

				System.out.println( ">> " + job );
			}
			System.out.println( rs + "행을 수행하였습니다.");

			ps.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}

		return rec;
	}

	public ArrayList searchBoard(){

		System.out.println("searchBorad() 실행");
		//"게시번호","종류", "상태", "제목", "작성자", "작성시간"
		// 3. sql 문장 만들기 ( "게시번호","글제목","작성자","작성시간","승인여부" )
		String sql = "SELECT post_num, post_type, post_title , emp_id, post_time, post_stat" 
				+ " FROM test_post WHERE post_type='업무지시' or post_type='업무보고'"
				+ " ORDER BY post_time DESC";

		System.out.println(sql);

		ArrayList list = new ArrayList();

		try{
			// 디비에서 데이터 읽어서 저장
			// 4. 전송객체 얻어오기 (Statement)
			Statement stmt = con.createStatement();

			// 5. 전송( executeQuery() )
			ResultSet rs = stmt.executeQuery(sql);

			System.out.println("stmt.executeQuery(sql) 실행됨");

			// 6. 결과처리
			while( rs.next() ){
				// ArrayList에 한 행(레코드)이 각각의 정보를 저장
				// 그 ArrayList를 list(ArrayList) 저장

				ArrayList temp = new ArrayList();

				String post_num = rs.getString("post_num");
				String post_type = rs.getString("post_type");
				String post_title = rs.getString("post_title");
				String emp_id = rs.getString("emp_id");
				String post_time = rs.getString("post_time");
				String post_stat = rs.getString("post_stat");

				temp.add(post_num);
				temp.add(post_type);
				temp.add(post_stat);
				temp.add(post_title);
				temp.add(emp_id);
				temp.add(post_time);

				list.add(temp);
			}

			stmt.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}

		return list;
	}

}
