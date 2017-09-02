class Appa{
	void test(){
		System.out.println("아빠");
	}
}

class Umma{
	void test(){
		출력("엄마");
	}

}

class Ddal{
	void test(){
		출력("딸");
	}

}

class Test extends Umma, {
	//상속은 하나 밖에 안됨. 무조건 단일 상속, 부모가 2명이상 올 수 없다.

	Ddal d = new Ddal();
	if(d instance of Umma)
	{
		출력("Ddal의 객체")
	}
}


엄마의 