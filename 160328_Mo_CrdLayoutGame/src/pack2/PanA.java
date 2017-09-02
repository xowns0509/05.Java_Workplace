package pack2;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class PanA extends JPanel{
	JButton forwad = new JButton("앞으로");
	
	PanA(Mo01_CardLayoutTest parent){
		setBackground(new Color(0, 30, 30));//RGB
		
		add(forwad);
		
		forwad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				parent.change1();				
			}
		});
	}
}