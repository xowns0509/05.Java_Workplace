package session;

import java.io.IOException;
import java.io.InputStream;

import model.Comment;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.HashMap;
import java.util.List;

public class CommentRepository {
	
	String namespace = "mapper.CommentMapper";
	
	private SqlSessionFactory getSqlSessionFactory()
	{
		String resource = "config.xml"; // 이걸 읽을 꺼야. 오픈세션을 읽어 오겠어. 근데 컨피그는 연결을 담당하는 놈이므로 sqlsession은 커넥션과 같은 개념.
		InputStream in = null;
		
		try{
			in = Resources.getResourceAsStream(resource);
			
		}catch(IOException e){
			System.out.println("연결실패: "+ e.getMessage());			
			
		}
		
		return new SqlSessionFactoryBuilder().build(in);
	}
	
//	public List<Comment> select()
//	{
//		SqlSession sess = getSqlSessionFactory().openSession();//근데 컨피그는 연결을 담당하는 놈이므로 sqlsession은 커넥션과 같은 개념.
//		try{
//			String statement = namespace + ".selecComment";
//			List temp = sess.selectList(statement);//프라이머리키 넣을 껀 아니니까 , cNo삭제
//			//또한, 하나만 던지는게 아니므로 one을 리스트로 바꾼 후 반환형도 list로
//			//어떤 리스트? 하나의 행을 Comment로 담기로 했으므로
//			System.out.println("여기 감지");
//			return temp;
////		}catch(Exception e){
////			System.out.println("여기"+e.getMessage());
//			
//		}finally{
//			sess.close();
//		}
//	}
	
	public List<Comment> select(HashMap<String, String> condition)
	{
		SqlSession sess = getSqlSessionFactory().openSession();//근데 컨피그는 연결을 담당하는 놈이므로 sqlsession은 커넥션과 같은 개념.
		try{
			String statement = namespace + ".selecComment";
			List temp = sess.selectList(statement, condition);//프라이머리키 넣을 껀 아니니까 , cNo삭제
			//또한, 하나만 던지는게 아니므로 one을 리스트로 바꾼 후 반환형도 list로
			//어떤 리스트? 하나의 행을 Comment로 담기로 했으므로
			System.out.println("여기 감지");
			return temp;
//		}catch(Exception e){
//			System.out.println("여기"+e.getMessage());
			
		}finally{
			sess.close();
		}
	}
	
	public Comment selectByPK(long cNo){
		
		SqlSession sess = getSqlSessionFactory().openSession();//근데 컨피그는 연결을 담당하는 놈이므로 sqlsession은 커넥션과 같은 개념.
		System.out.println("여기는?");
		
		try{
			String statement = namespace + ".selectByPK";
			Comment temp = sess.selectOne(statement, cNo);
			System.out.println("여기 감지");
			return temp;
//		}catch(Exception e){
//			System.out.println("여기"+e.getMessage());
			
		}finally{
			sess.close();
		}
	}
	
	public int insert(Comment c){
	
		SqlSession sess = getSqlSessionFactory().openSession();
		try{
			String statement = namespace + ".insertComment";
			int result = sess.insert(statement, c);
			if(result > 0){
				sess.commit();
			}else{
				sess.rollback();
			}
			return result;
		}finally{
			sess.close();
		}
	}
}
