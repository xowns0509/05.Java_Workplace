package record;

public class PostRecord {
	private int postNum; //게시물 번호
	private int pNum;	//프로젝트 번호
	private String fileName;	//파일명
	private String empId;	//사원ID
	private String postTime;	//작성시간
	private String postType;	//게시물종류
	private String postStat;	//상태
	private String postTitle;	//게시물제목


	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getPostStat() {
		return postStat;
	}
	public void setPostStat(String postStat) {
		this.postStat = postStat;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

}
