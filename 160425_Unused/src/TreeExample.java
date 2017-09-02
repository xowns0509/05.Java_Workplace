import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
//트리 예제
public class TreeExample extends JFrame {

	private JPanel contentPane;
	JTree tree = new JTree();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeExample frame = new TreeExample();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TreeExample() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		final ArrayList<String> sample = new ArrayList<String>();
		sample.add("Number");
		sample.add("Two");
		sample.add("Three");
		sample.add("Four");
		sample.add("Five");
		sample.add("Six");

		final ArrayList<String> sampleDigit = new ArrayList<String>();
		sampleDigit.add("Digit");
		sampleDigit.add("1");
		sampleDigit.add("2");
		sampleDigit.add("3");
		sampleDigit.add("4");
		sampleDigit.add("5");
		sampleDigit.add("6");

		DefaultMutableTreeNode obj = new DefaultMutableTreeNode("JTree") {
			{
				DefaultMutableTreeNode node_1;
				DefaultMutableTreeNode node_2;
				node_1 = new DefaultMutableTreeNode(sampleDigit.get(0));
				for (int i = 1; i < sampleDigit.size(); i++) {
					node_1.add(new DefaultMutableTreeNode(sampleDigit.get(i)));
				}

				add(node_1);
				node_2= new DefaultMutableTreeNode(sample.get(0));
				for (int i = 1; i < sample.size(); i++) {
					node_2.add(new DefaultMutableTreeNode(sample.get(i)));
				}

				add(node_2);
			}
		};
		tree.setModel(new DefaultTreeModel(obj));
		tree.addTreeSelectionListener(listener());
		panel.add(tree);
	}

	TreeSelectionListener listener() {
		TreeSelectionListener objTreeListener = new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if (node == null)
					// Nothing is selected.
					return;

				Object nodeInfo = node.getUserObject();
				if (node.isLeaf()) {
					System.out.println("THE ROOT NODE IS: "+node.getParent().toString()+" CHILD NODE IS: "+nodeInfo.toString());
				} else {

				}
			}
		};
		return objTreeListener;
	}
}

//output:
//
//	THE ROOT NODE IS: Number CHILD NODE IS: Four