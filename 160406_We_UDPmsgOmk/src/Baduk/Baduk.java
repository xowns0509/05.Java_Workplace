package Baduk;
//================================================
//	바둑알 하나에 대한 정보를 저장하는 클래스
//================================================

public class Baduk implements java.io.Serializable
{
	public static int			BLACK_BADUK = 0;
	public static int      		WHITE_BADUK = 1;
	public static int      		NONE_BADUK = -1;

    int row;
    int col;
    int color;

    public Baduk(){
      this(0,0,0);
    }
    public Baduk(int color, int row, int col ){
      this.row = row;
      this.col = col;
      this.color = color;
    }

    public int getRow(){
      return row;
    }
    public int getCol(){
      return col;
    }
    public int getColor(){
      return color;
    }
    public void setValue(int row, int col, int color){
      this.row = row;
      this.col = col;
      this.color = color;
    }
}