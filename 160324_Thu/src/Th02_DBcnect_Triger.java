//DB연결 트리거
public class Th02_DBcnect_Triger {
	
	public static void main(String [] args){
		Th02_DBUsrA a = new Th02_DBUsrA();
		a.use();
		
		Th01_DBUsrB b = new Th01_DBUsrB();
		b.use();
		
		Th01_DBUsrC c = new Th01_DBUsrC();
		c.use();
	}
}
//static 뭔가 공유하기 위해. 단 하나만 존재하기 위해.

