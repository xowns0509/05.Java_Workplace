/* 사용자 정의 예외클래스 */
public class T03_UsrEcption extends Exception{//T03_UsrEcption클래스는 Exception클래스 기능을 물려받겠다.

	public String getMessage(){
		return "특별한 예외가 발생하였습니다.";
		
	}
}
