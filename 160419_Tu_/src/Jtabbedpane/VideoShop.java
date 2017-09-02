package Jtabbedpane;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Jtabbedpane.panels.CustomerView;
import Jtabbedpane.panels.RentView;
import Jtabbedpane.panels.VideoView;

public class VideoShop extends JFrame{
	
	CustomerView customer;
	VideoView video;
	RentView rent;
	
	VideoShop(){
		
		customer = new CustomerView();
		video = new VideoView();
		rent = new RentView();
		
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("고객관리", customer);
		pane.addTab("비디오관리", video);
		pane.addTab("대여관리", rent);
		
		pane.setSelectedIndex(2);//대여관리가 항상 뜨게 하고 싶다면 이렇게 기본값 지정해줌
		
		//이제 JTabbedPane을 프레임에다 붙여.
		add(pane);
		setSize(500, 350);
		setVisible(true);
	}

	public static void main(String[] args) {
		new VideoShop();

	}

}
