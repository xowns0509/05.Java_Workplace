import java.sql.*;
public class AccLogic 
{
	// 연결 객체 생성시 필요한 변수 선언
	String url;
	String user;
	String pass;

	//===============================================
	// 드라이버를 드라이버매니저에 등록
	public AccLogic() throws Exception{
		/////////////////////////////////////////////////////////
		// 1. 드라이버를 드라이버 매니저에 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		user = "scott";
		pass = "tiger";
	}


	//====================================================
	// 보내는 계좌번호와 받는 계좌번호와 계좌금액을 넘겨받아 
	//	보내는계좌에서 계좌금액을 빼고 받는계좌에서 계좌금액을 더한다
	public int moveAccount(String sendAcc, String recvAcc, int amount)
	{
		Connection con = null;

		///////////////////////////////////////////////////////////
		//	 1. Connection 객체 생성
		//@@ 2. Auto-commit을 해제
		//	 3. 출금계좌에서 이체금액을 뺀다. update.커넥션은 하나 갖고 쓸 꺼야.
		//	 4. 입금계좌에 이체금액을 더한다. 각각의 전송객체를 날려야 함. 하나 갖고 출금, 입금 둘다 쓰지 말고 각자.
		//@@ 5. commit을 전송한다
		//	 6. 객체 닫기
		//	 - 정상적인 경우는 0을 리턴, 도중에 잘못되었으면 트랜잭션을 롤백시키고 -1 리턴.
		
		try{
			//사용자에게 리턴값 알려주기위해 예외를 던지지 않고 여기서 잡아냄.
			con = DriverManager.getConnection(url, user, pass);
			con.setAutoCommit(false); //이게 오토커밋 취소하는 놈.
			
			//3. SQL문장 만들기. 보내는 계좌에서 이체금액만큼 빼기
			String sendSql = "UPDATE account SET amount = amount - ? WHERE ACCOUNT_NUM = ?";
			String recvSql = "UPDATE account SET amount = amount + ? WHERE ACCOUNT_NUM = ?";
			//
			// 프리페어 스테이트먼트는 setInt일 때는 그냥 값을 넣고
			// setString 시 자동으로 ''을 처주기 때문에
			//ACCOUNT_NUM = '?' 가 아니라 ACCOUNT_NUM = ?지
			//(현 ACCOUNT_NUM의 컬럼자료형은 varchar2)
			
			
			// 4. sql 전송객체 얻어오기(PreparedStatement)
			PreparedStatement sendPS = con.prepareStatement(sendSql);
			PreparedStatement recvPS = con.prepareStatement(recvSql);
			
			sendPS.setInt(1, amount);
			sendPS.setString(2, sendAcc);
			recvPS.setInt(1, amount);
			recvPS.setString(2, recvAcc);
			
			// 5. sql 전송(executeUpdate()이용)
			int sendResult = sendPS.executeUpdate();
			int recvResult = recvPS.executeUpdate();
			
			System.out.println();
			
			if(sendResult == 0){
				con.rollback();
				System.out.println("출금 실패");
				return -1;
			}else if(recvResult == 0){
				System.out.println("입금 실패");
				return -1;
			}else{
				System.out.println("트랜잭션 완료!!");
			}	
			//받는 처리 수행
			con.commit();
			
			System.out.println(sendAcc + "로부터 정상적으로 금액 " + amount + "을 인출했습니다.");
			System.out.println(recvAcc + "에게 정상적으로 금액 " + amount + "을 입금했습니다.");
			
		}catch(Exception ex){
			try {
				con.rollback(); //롤백 문장도 trycatch 잡아줘야 함.
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	//=======================================================
	//		보내는계좌번호와 받는계좌번호를 넘겨받아
	//		보내는계좌고객명과 보내는계좌의남은 금액을 얻어오고
	//		받는계좌고객명을 얻어와서
	//		얻은 정보를 ConfirmData객체에 넣고 리턴
	public ConfirmData confirmAccount(String sendAcc, String recvAcc) 
		throws Exception{

		
		String sendCust="", recvCust="";
		int gainMoney=0;
		ConfirmData  resultData=null;
		
		//	1. Connection 객체 생성
		//	2. 테이블에서, 넘겨받은 sendAcc와 같은 account_num필드에서 customer, amount를 얻어온다
		//	3. 테이블에서, 넘겨받은 recvAcc와 같은 account_num필드에서 customer를 얻어온다
		//  4. 2와 3에서 얻은 값을 ConfirmData 객체에 저장
		//	5. 4번의 객체를 리턴



		return resultData;
	}

}


//#################################################################
//	테이블명 : account
//	account_num		계좌번호		varchar2(20)
//	customer		고객명			varchar2(20)
//	amount			계좌금액		int
