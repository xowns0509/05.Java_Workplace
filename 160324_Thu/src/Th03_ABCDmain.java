public class Th03_ABCDmain{

	public static void main(String [] args){

//		Th03_Book b1 = new Th03_Book("0001", "그것이 자바", "난자바");
//		b1.output();
		
//		Th03_Book b = new Th03_Book(); //자동으로 삽입되는 super에 의해 atem의 인자 없는 놈이 불려
//		b.output();
		Th03_Book b1 = new Th03_Book(); //이렇게 인자를 줘도 book의 각 생성자마다 자동으로 삽입되는 super에 의해 부모의 인자 없는 atem이 불린다고.
		b1.output();		

//		Th03_Atem i1 = new Th03_Atem();
//		i1.output();
//		Th03_Atem i2 = new Th03_Atem("0001", "오늘은 졸면 안됨");
//		i2.output();
		
	}
}
/* 부모변수 - 자식객체가 가능한 이유는
 * 자식객체를 생성할 때 생성자에서 슈퍼로 부모의 객체가 생긴 후 자식이 비로소 생기는 거지,
 * 부모객체가 생성瑛?때 자동으로 자식객체를 생성하진 않지.
 * 
 * 참고로 Th03_Atem i2 = new Th03_Book();의 경우
 * i2는 i2밖에 모름. 부모객체는 부모객체 밖에 몰라.
 * 자신보다 더 큰 자식객체에 따로 생성된 변수나 메소드 등은 실행이 불가*/