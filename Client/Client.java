import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Client extends JFrame{
	public Client(){
		
		JPanel pHead0=new JPanel();
		
		pHead0.setLayout(new FlowLayout(FlowLayout.LEFT,5,20));
		pHead0.add(new JButton("Login"));
		pHead0.add(new JButton("Signin"));
		pHead0.add(new JTextField("DictPro"));
		
		JPanel pHead1=new JPanel();
		pHead1.setLayout(new BorderLayout(5,10));
		pHead1.add(new JTextField("Input"),BorderLayout.WEST);
		pHead1.add(new JTextArea(),BorderLayout.CENTER);
		pHead1.add(new JButton("Search"),BorderLayout.EAST);
		
		JPanel pHead2=new JPanel();
		pHead2.setLayout(new GridLayout(1, 3));
		
		JCheckBox dictSrcBaidu=new JCheckBox("百度",true);
		JCheckBox dictSrcYoudao=new JCheckBox("有道",true);
		JCheckBox dictSrcBing=new JCheckBox("必应",true);
		
		pHead2.add(dictSrcBaidu);
		pHead2.add(dictSrcYoudao);
		pHead2.add(dictSrcBing);
		
		JPanel pHead=new JPanel();
		pHead.setLayout(new BorderLayout(5,5));
		pHead.add(pHead0,BorderLayout.NORTH);
		pHead.add(pHead1,BorderLayout.CENTER);
		pHead.add(pHead2,BorderLayout.SOUTH);
		
		JPanel pFriendlist=new JPanel();
		pFriendlist.add(new JTextArea());
	
		JPanel pDict0=new JPanel();
		pDict0.setLayout(new BorderLayout(2,2));
		pDict0.add(new JTextArea(),BorderLayout.NORTH);
		JPanel pD0=new JPanel();
		pD0.setLayout(new GridLayout(1,3));
		pD0.add(new JTextArea("UID"));
		pD0.add(new JButton("Send"));
		pD0.add(new JButton("赞"));
		pDict0.add(pD0,BorderLayout.SOUTH);
		pDict0.setBorder(new TitledBorder("百度"));
		
		JPanel pDict1=new JPanel();
		pDict1.setLayout(new BorderLayout(2,2));
		pDict1.add(new JTextArea(),BorderLayout.NORTH);
		JPanel pD1=new JPanel();
		pD1.setLayout(new GridLayout(1,3));
		pD1.add(new JTextArea("UID"));
		pD1.add(new JButton("Send"));
		pD1.add(new JButton("赞"));
		pDict1.add(pD1,BorderLayout.SOUTH);
		pDict1.setBorder(new TitledBorder("有道"));
	
		JPanel pDict2=new JPanel();
		pDict2.setLayout(new BorderLayout(2,2));
		pDict2.add(new JTextArea(),BorderLayout.NORTH);
		JPanel pD2=new JPanel();
		pD2.setLayout(new GridLayout(1,3));
		pD2.add(new JTextArea("UID"));
		pD2.add(new JButton("Send"));
		pD2.add(new JButton("赞"));
		pDict2.add(pD2,BorderLayout.SOUTH);
		pDict2.setBorder(new TitledBorder("必应"));
		
		JPanel pDict=new JPanel();
		pDict.setLayout(new BorderLayout());
		pDict.add(pDict0,BorderLayout.NORTH);
		pDict.add(pDict1,BorderLayout.CENTER);
		pDict.add(pDict2,BorderLayout.SOUTH);
		
		JPanel pBody=new JPanel();
		pBody.setLayout(new BorderLayout(2,5));
		pBody.add(pFriendlist,BorderLayout.WEST);
		pBody.add(pDict,BorderLayout.CENTER);
		
		setLayout(new BorderLayout(5,5));
		add(pHead,BorderLayout.NORTH);
		add(pBody,BorderLayout.CENTER);
	}
	
	public static void main(String[] args){
		Client frame=new Client();
		
		frame.setTitle("DictPro");
		frame.setSize(400,250);
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
}