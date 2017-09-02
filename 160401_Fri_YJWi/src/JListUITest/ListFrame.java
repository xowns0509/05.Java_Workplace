package JListUITest;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListFrame extends JFrame
{

	private static final long serialVersionUID = 1L;


	private JList colorJList;


	private  final String colorNames[] = 
		{
				"Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", 
				"Light Gray", "Magenta", "Orange", "Pink", "Red",
				"White", "Yellow"
		};



	private final Color colors[] = {
			Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
			Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
	};





	public ListFrame()


	{


		super("ListControl Test");


		setLayout(new FlowLayout());


		// 색상이름으로 리스트 생성


		colorJList = new JList(colorNames);





		// 한번에 최대 5개를 보여줌


		colorJList.setVisibleRowCount(5);





		// setSelectionMode(): 리스트의 선택모드를 설정


		// SINGLE_SELECTION: 한번에 한 항목


		// SINGLE_INTERVAL_SELECTION: 여러 인접한 항목을 선택가능한 다중 선택


		// MULTIPLE_INTERVAL_SELECTION: 선택할 수 있는 항목의 제한이 없는 다중 선택


		// 한번에 한 항목만 선택할 수 있는 모드로 리스트를 사용


		colorJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);





		// JScrollPane: 스크롤을 사용하는 클래스


		// JList를 포함하는 JScrollPane을 프레임에 추가


		// 리스트는 스크롤이 지원되지 않기 때문에 스크롤을 추가해야 한다.


		add(new JScrollPane(colorJList));


		// 이벤트 핸들러 등록


		JListHandler handler = new JListHandler();


		colorJList.addListSelectionListener(handler);

	}





	/**


	 * 리스트 선택 이벤트 핸들러 클래스


	 * 선택한 색상으로 프레임의 배경색을 바꿈


	 */


	private class JListHandler implements ListSelectionListener


	{


		// 리스트의 항목이 선택이 되면


		public void valueChanged(ListSelectionEvent event)


		{


			// getContentPane(): GUI 컴포넌트의 참조를 반환


			// setBackground(): 컴포넌트의 배경을 설정


			// getSelectedIndex(): 선택된 항목의 인덱스 반환


			// 리스트에서 색상 항목이 선택되면 해당 색상으로 배경지정


			getContentPane().setBackground(colors[colorJList.getSelectedIndex()]);


			JOptionPane.showMessageDialog(null, colorNames[colorJList.getSelectedIndex()]);


		}


	}
}