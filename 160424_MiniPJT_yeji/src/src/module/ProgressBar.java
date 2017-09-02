package module;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import main.Database;
/**
 * ProgressBar표시 - 메인화면
 * @author 임일원
 */
public class ProgressBar extends JPanel{
	
	ArrayList pList = new ArrayList();
	ArrayList pName = new ArrayList();
	ArrayList pPercent = new ArrayList();
	
	public ProgressBar(){
		
		readProject();
		addLayout();
	}
	
	// ProgressBar에 표시할 비율 구하기
	int getPercent(int p_num) {
		int percent = 0;
		int listNum = 0;
		int checkNum = 0;
		
		// 프로젝트와 일치하는 항목의 수 구하기
		Connection con = null;
		try {
			con = Database.getConnection();
		} catch (Exception ex) {
			System.out.println("getPercent() con 생성에러");
		}
		
		// 3. (***) sql 문장 만들기
		String sql = "SELECT count(l.list_num) AS count "
				+ "FROM test_list l, test_category c "
				+ "WHERE l.cat_num=c.cat_num and c.p_num=?";
		
		System.out.println(sql);
		
		try{
		// 4. sql 전송 객체 얻어오기
		PreparedStatement ps = null;
		ps = con.prepareStatement(sql);
		ps.setInt(1, p_num);	// '  ' 붙여줌. 첫번째 - 1
		
		// 5. sql 전송하기
		ResultSet rs = ps.executeQuery();
		while( rs.next() ){
			listNum = rs.getInt("count");
		}
		System.out.println( "listNum 개수 : " + listNum );

		// 7. 닫기
		ps.close();
		
		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}

		// 프로젝트와 일치하는 항목의 상태가 '승인'인 경우의 수 구하기
		// 3. (***) sql 문장 만들기
		String sql2 = "SELECT count(l.list_num) AS checknum "
				+ "FROM test_list l, test_category c "
				+ "WHERE l.cat_num=c.cat_num and c.p_num=? and list_stat='승인'";

		System.out.println(sql2);

		try{
			// 4. sql 전송 객체 얻어오기
			PreparedStatement ps = null;
			ps = con.prepareStatement(sql2);
			ps.setInt(1, p_num);	// '  ' 붙여줌. 첫번째 - 1

			// 5. sql 전송하기
			ResultSet rs = ps.executeQuery();
			while( rs.next() ){
				checkNum = rs.getInt("checknum");
			}
			System.out.println( "checkNum 개수 : " + checkNum );

			// 7. 닫기
			ps.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}
		
		//percent 구하기
		if(listNum != 0){
			percent = (int)( ((double)checkNum / (double)listNum) * 100);
		}
		System.out.println("percent : " + percent);

		return percent;
	}
	
	// 프로젝트번호와 이름을 구한다
	void readProject(){
		
		Connection con = null;
		try {
			con = Database.getConnection();
		} catch (Exception ex) {
			System.out.println("readProject() con 생성에러");
		}
		
		// 3. sql 문장 만들기
		String sql = "SELECT p_num, p_name FROM test_project";
				
		System.out.println(sql);
		
		try{
		// 디비에서 데이터 읽어서 저장
		// 4. 전송객체 얻어오기 (Statement)
		Statement stmt = con.createStatement();

		// 5. 전송( executeQuery() )
		ResultSet rs = stmt.executeQuery(sql);

		// 6. 결과처리
		while( rs.next() ){
			// ArrayList에 한 행(레코드)이 각각의 정보를 저장
			// 그 ArrayList를 list(ArrayList) 저장

			int p_num = rs.getInt("p_num");
			String p_name = rs.getString("p_name");

			pList.add(p_num);
			pName.add(p_name);
		}

		// 7. 닫기
		stmt.close();

		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}
	}
	
	void addLayout(){
		//좌측: 하단 progressBar
		String status = "";
		
		JPanel p_left_center = new JPanel();
		// p_left_center.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_left_center.setLayout(new GridLayout(pList.size(), 1));
		System.out.println(pList.size() );
		System.out.println(pName.size() );
		
		for(int i=0; i < pName.size(); i++){
			System.out.println(pName.get(i));
			String name = (String) pName.get(i);
		
		JLabel project = new JLabel(name + "  ");
		
		JProgressBar progressBar = new JProgressBar();

		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		Dimension prefSize = progressBar.getPreferredSize();
		prefSize.height = 50;
		prefSize.width = 400;
		progressBar.setPreferredSize(prefSize);

		progressBar.setStringPainted(true);
		
		System.out.println("프로젝트 번호 : " + (int)pList.get(i));
		
		int percent = getPercent((int)pList.get(i));
		
		progressBar.setValue(percent);
		
		//System.out.println("percent :" + percent);
		
		if(percent == 100)
			status = "완료";
		else status = "진행중...";		
		
		TitledBorder border = BorderFactory.createTitledBorder(status);
		border.setTitleJustification(TitledBorder.CENTER);
		progressBar.setBorder(border);
		
		JPanel progressP = new JPanel();
		progressP.setLayout(new FlowLayout(FlowLayout.TRAILING));
		progressP.add(project); 
		progressP.add(progressBar);
		
		p_left_center.add(progressP);
		}

		add(p_left_center);
		setVisible(true);
	}
	
}
