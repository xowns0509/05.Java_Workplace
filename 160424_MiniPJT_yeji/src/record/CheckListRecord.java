package record;

/**
 * 카테고리 정보 
 * @author user
 *
 */
public class CheckListRecord {
	private int catNum;		// 카테고리번호
	private int pNum; 		// 프로젝트 번호
	private String catTitle;// 프로젝트 명
	private int listNum; 	//항목번호
	private String empId; 	//사원ID
	private String content;	//내용
	private String listStat;//상태
	
	
	public int getCatNum() {
		return catNum;
	}
	public void setCatNum(int catNum) {
		this.catNum = catNum;
	}
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
	public String getCatTitle() {
		return catTitle;
	}
	public void setCatTitle(String catTitle) {
		this.catTitle = catTitle;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getListStat() {
		return listStat;
	}
	public void setListStat(String listStat) {
		this.listStat = listStat;
	}	
}
