public class Th01_Process_extProgram {
	
	public static void main(String [] args){
		
		Runtime rt = Runtime.getRuntime();	//자바가 런타임이 프로세스를 관리함
		//rt.exec("탐색기에서 실행하고 싶은 놈. 경로 찾아서 붙여넣음 역슬래시는 두개씩 적어야 함.");//		
		//프로세스 실행하는 놈
		try{
			rt.exec("C:\\Program Files\\Internet Explorer\\iexplore.exe");//자바는 자기 예외의 것(자바프로그램 말고 모든 것)을 건드리면 반드시 예외처리 필수
		}catch(Exception e){
			System.out.println("안됨");

		}
	}
}
