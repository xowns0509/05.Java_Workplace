package module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.Database;
import record.EmpRecord;

/**
 * 로그인 하는 모듈, db 연결
 * @author yeji 
 */
public class Login {
	
	EmpRecord rec;
	Connection con;
	PreparedStatement ps;
	
	public Login(){
		rec = new EmpRecord();
		try {
			con = Database.getConnection();
			System.out.println("db 로그인 연결 성공");
		} catch (Exception e) {
			System.out.println("디비 연결 오류 " + e.getMessage());
		}
	}
	/*
	 * 로그인 하는 메소드 (한개만 반환) 
	 */
	public EmpRecord login(String id, String pw) throws Exception{
		//반환 할 사원 레코드 (후에 사용할것임)
		EmpRecord ret = new EmpRecord();
		String sql = "SELECT * FROM test_emp WHERE emp_id = ? AND emp_pw = ?";
		
		ps = con.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			ret.setEmpId(rs.getString("emp_id"));
			ret.setEmpPw(rs.getString("emp_pw"));
			ret.setEname(rs.getString("ename"));
			ret.setJob(rs.getString("job"));
			ret.setMgrId(rs.getString("mgr_id"));
			ret.setPermission(rs.getString("permission"));
			ret.setpNum(rs.getInt("p_num"));
			ret.setPosition(rs.getString("position"));
			ret.setTel(rs.getString("tel"));
		}
		ps.close();		
		return ret;
	}
	
}
