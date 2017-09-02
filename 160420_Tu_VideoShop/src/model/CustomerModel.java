package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.dao.Customer;

public class CustomerModel {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	String user = "scott";
	String pass = "tiger";
	
	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	
	public CustomerModel() throws Exception{
		
		// 1. 드라이버로딩
		Class.forName(driver);
		// 2. Connection 연결객체 얻어오기
		con = DriverManager.getConnection(url, user, pass);
		// [지역변수 안되] Connection con = ...;
		
	}
	
	public void insertCustomer(Customer dao) throws Exception{
		
		// 3. sql 문장만들기
		String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";
		System.out.println(sql);
		
		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setString(1, dao.getCustTel1());
		ps.setString(2, dao.getCustName());
		ps.setString(3, dao.getCustTel2());
		ps.setString(4, dao.getCustAddr());
		ps.setString(5, dao.getCustEmail());

		// 5. sql 전송(executeUpdate()이용)
		int insResult = ps.executeUpdate();
		System.out.println(insResult + " 행이 수정되었어!.");
		
		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		ps.close();

	}
	
	public Customer selectByTel(String tel) throws Exception{
		Customer dao = new Customer();
		
		// 3. sql 문장만들기
		String sql = "SELECT * FROM customer WHERE TEL=?";
		System.out.println(sql);
		
		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		
		// 5. sql 전송(executeUpdate()이용)
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			dao.setCustTel1(rs.getString("TEL"));
			dao.setCustName(rs.getString("NAME"));
			dao.setCustTel2(rs.getString("SECOND_TEL"));
			dao.setCustAddr(rs.getString("ADDR"));
			dao.setCustEmail(rs.getString("EMAIL"));
			
		}else{
			JOptionPane.showMessageDialog(null, tel+"로 등록된 회원이 없습니다.");
		}
		
		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		ps.close();
		
		return dao;
		
	}
	
	public ArrayList<Customer> selectByName(String name) throws Exception{
		
		ArrayList<Customer> daoARL = new ArrayList<Customer>();
		Customer dao;
		
		// 3. sql 문장만들기
		String sql = "SELECT * FROM customer WHERE NAME=?";
		System.out.println(sql);
		
		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setString(1, name);
		
		// 5. sql 전송(executeUpdate()이용)
		ResultSet rs = ps.executeQuery();

			while(rs.next()){
				
					dao = null;
					dao = new Customer();
	
					dao.setCustTel1(rs.getString("TEL"));
					dao.setCustName(rs.getString("NAME"));
					dao.setCustTel2(rs.getString("SECOND_TEL"));
					dao.setCustAddr(rs.getString("ADDR"));
					dao.setCustEmail(rs.getString("EMAIL"));
					
					System.out.println(dao.getCustTel1());
					System.out.println(dao.getCustName());
					System.out.println(dao.getCustTel2());
					System.out.println(dao.getCustAddr());
					System.out.println(dao.getCustEmail());
					
					daoARL.add(dao);				
				}

		System.out.println("이제 리스트 출력!!");
			for(int i=0; i < daoARL.size(); i++){
				
//				if(returnedName.equals(ctmrlist.get(i).getCustName())){
				
				

				System.out.println(daoARL.get(i).getCustTel1());//tfCustTel
				System.out.println(daoARL.get(i).getCustName());//tfCustName
				System.out.println(daoARL.get(i).getCustTel2());//tfCustTelAid
				System.out.println(daoARL.get(i).getCustAddr());//tfCustAddr
				System.out.println(daoARL.get(i).getCustEmail());//tfCustEmail

			}

		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		ps.close();
		
		return daoARL;
		
	}
	
	public int updateCustomer(Customer dao) throws Exception{
		
		int udtResult = 0;//~행의 행 숫자 초기화.
		
		// 3. sql 문장만들기
		String sql = "UPDATE customer SET NAME=?, SECOND_TEL=?, ADDR=?, EMAIL=? WHERE TEL=?";
		System.out.println(sql);
		
		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setString(1, dao.getCustName());
		ps.setString(2, dao.getCustTel2());
		ps.setString(3, dao.getCustAddr());
		ps.setString(4, dao.getCustEmail());

		ps.setString(5, dao.getCustTel1());
		
		// 5. sql 전송(executeUpdate()이용)
		udtResult = ps.executeUpdate();
		System.out.println(udtResult + " 행이 수정되었어!.");
		
		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		ps.close();
		
		return udtResult;
	}
}
//import view.We01_MyDialog;
//We01_MyDialog helPDilg = new We01_MyDialog();
//helPDilg.UILayout();

/*TEL
NAME  
SECOND_TEL 
ADDR 
EMAIL*/