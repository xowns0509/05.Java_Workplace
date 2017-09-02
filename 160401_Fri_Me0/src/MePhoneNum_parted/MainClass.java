package MePhoneNum_parted;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainClass extends JFrame {
  JList list = null;

  MainClass() {
    Container cp = getContentPane();
    cp.setLayout(new FlowLayout());
    ArrayList data = new ArrayList();
    data.add("Hi");
    data.add("Hello");
    data.add("Goodbye");
     list = new JList(data.toArray());
    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting())
          return;
        System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
      }
    });
    cp.add(new JScrollPane(list), BorderLayout.CENTER);
  }

  public static void main(String[] s) {
    MainClass l = new MainClass();
    l.pack();
    l.setVisible(true);
  }
}
          