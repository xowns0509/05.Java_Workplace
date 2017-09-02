import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class We07_Properties {

	public static void main(String [] args){

		Properties sp = System.getProperties();//시스템 속성조회
		sp.list(System.out);
		//=을 중심으로 앞이 key 뒤가 value

		Properties prop = new Properties();

		try{
			prop.load(new FileInputStream("src\\db.properties"));//로드로 파일을 읽어서 prop에 심는다.
			/*예외처리가 안榮鳴?
			파일에 아무런 내용이 없을 수도 있고
			파일 자체가 안읽힐 수도 있고
			파일이 없을 수도 있다. 그래서 예외처리 함.*/	
		}catch(FileNotFoundException e){
			System.out.println("해당하는 파일이 없습니다.");
		}catch(IOException e){
			System.out.println("읽는 도중에 오류발생.");
		}
		System.out.println("DB연결에 필요한 정보");
		System.out.println(prop.getProperty("user")+"와"+prop.getProperty("pass")+"로 연결.");
	}
}
