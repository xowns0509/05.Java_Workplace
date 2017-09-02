import java.util.HashMap;
import java.util.List;

import model.Comment;
import session.CommentRepository;

public class Test {
	
	CommentRepository repository = new CommentRepository();
	
	void selectByPK(){
		
		long cNo = 1234L;  //실제 db의 pk값
		Comment cmt = repository.selectByPK(cNo);
		
		System.out.println(cmt.getUserId());
		System.out.println(cmt.getCommentContent());
		System.out.println(cmt.getRegDate());
	}
	
	void insert(){
		Comment c = new Comment();
		c.setCommentNo(123);
		c.setUserId("xxodj");
		c.setCommentContent("큼요일");
		c.setRegDate("99/12/12");
		
		int result = repository.insert(c);
		System.out.println(result + "행을 입력했어.");
	}
	
	void select(){
		
		HashMap<String, String> condition = new HashMap<String, String>();
		condition.put("userId", "홍길동");

		List<Comment> list = repository.select(condition);
		for(Comment c :list){//향상된 for문
			
			System.out.println(c.getCommentNo() + ">" + c.getUserId() + ":" + c.getCommentContent());
		}
	}
	
//	void select(){
//
//		List<Comment> list = repository.select();
//		for(Comment c :list){//향상된 for문
//			
//			System.out.println(c.getCommentNo() + ">" + c.getUserId() + ":" + c.getCommentContent());
//		}
//	}

	public static void main(String[] args) {
		
		Test t = new Test();
		t.selectByPK();

	}

}
