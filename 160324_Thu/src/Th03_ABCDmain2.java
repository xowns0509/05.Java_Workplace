import java.util.*;

public class Th03_ABCDmain2 {

	public static void main(String [] args){

		Th03_item i = null; //부모 자료형 선언

		System.out.println("원하는 아이템을 선택하세요. 1.책, 2.DVD, 3.CD");
		Scanner input = new Scanner(System.in);
		int sel = input.nextInt();
		switch (sel) {
		case 1 : i = new Th03_Book("0001", "몰라" , "일본인"); break; //부모클래스 변수에 자식인스턴스 객체 대입
		case 2 : i = new Th03_DVD("9999","검사외전","강동원","몰라"); break;
		case 3 : i = new Th03_CD("1000","유명한노래","마마무"); break;
		}

		 /* Item i = new Book();
		  * * i.output();하면 Book의 output 실행.*/
		
		i.output();
		/* switch(sel){					오버라이딩이 없으면
		case 1 : b.output() ; break;	이걸 다 써야 되잖아.
		case 2 : d.output() ; break;
		case 3 : c.output() ; break;
		}*/
		
		/* 다형성
		 * 
		 * 똑같은걸 물려받아서 다양한 기능을 오버라이딩 하면
		 * 원본은 동일하지만 기능이 여러 개가 되는거잖아.
		 * 그걸 형태가 다양해 진다 해서 다형성이라 하는거지.
		 * 
		 * 오버라이딩
		 * 부모클래스랑 자식클래스랑 똑같은 메소드를 갖고 있을 때.
		 * 이것을 오버라이딩이라 하며
		 * 부모의 메소드를 부르면 오버라이딩 된 자식의 메소드를 부름.
		 * 
		 * 오버라이딩 - 기각 무시. 규칙을 무시하고 관리자의 권한으로 강제 한다.
		 * 원래는 부모의 메소드를 실행해야 하지.
		 * 근데 상속받는 자식의 메소드를 실행하니
		 * 감히 부모의 메소드를 무시기각하므로 오버라이딩.
		 * 
		 * 오버라이딩의 조건
		 * 1. 부모/자식으로 상속관계에 있어야 하고,
		 * 2. 상속관계에 있는 부모/자식 간 동일한 이름의 함수들.
		 * (리턴형, 메소드명, 인자 동일 할 때)
		 * 
		 * 이걸 왜 하냐면
		 * 변수가 부모라 하더라도 자식의 메소드를 실행하기 위해서
		 * 그래야 다형성이 될꺼 아냐. 부모 것을 그대로 하면 다형이 되냐.
		 * 
		 * 오버라이딩이 되어 있는 상태함. */
	}
}