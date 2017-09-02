package view;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class We01_MyDialog extends JDialog{
	//1.필드선언
	JLabel test1, test2;
	JButton confirm;

	//2.생성자
	public We01_MyDialog(){
		
		test1 = new JLabel("능력자 토끼놈이 만든 무늬만 파워고급프로그램", JLabel.CENTER);
		test2 = new JLabel("이건 두 번째 줄", JLabel.CENTER);
		confirm = new JButton("확인");
	}

	//3.메소드: 레이아웃
	public void UILayout(){
		
		setVisible(true);
		setSize(300,100);
		setTitle("다이얼로그의 제목");
		
		setLayout(new BorderLayout());
		add(test1, BorderLayout.NORTH);
		add(test2, BorderLayout.CENTER);
		add(confirm, BorderLayout.SOUTH);
		
		//setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);//JDialog와 JFrame을 구문하라. JDial
		
		confirm.addActionListener(new ActionListener(){//여기서 ActionListener하려면 위에서 implement빼야 함
			public void actionPerformed(ActionEvent e){
				setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
			}
		});
	}	
}
