package exam.access1;
/* 자기 자신의 경로를 쓰는 거임. 
 * 패키지를 명시 안하면(= 이 소스파일을 따로 폴더 만들어서 거기다 처넣하지 않으면) 안붙음.
 * 그러니 폴더에 없고 프로젝트 최상위 폴더에 이 소스파일이 위치해 있다면 이 package 구문이 안 붙지. */

import exam.access1.sub.Access;

//public class AccessTest {
public class AccessTest extends Access{
	
	public static void main(String []args){
//		Access me = new Access();
		AccessTest me = new AccessTest(); //상속관계를 맺었다면 자기자신을 인스턴스하여 부모의 속성들을 사용
//		me.a = "프라이빗 접근"; // 프라이빗은 다른 클래스에서 접근이 안되므로 컴파일 전부터 에러뜸
		me.b = "디폴트 접근"; 
		me.c = "프로? 접근";  //상속관계를 맺었을 때
		me.d = "퍼블릭 접근";
		
	}
}