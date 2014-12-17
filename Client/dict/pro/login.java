package dict.pro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class login extends JFrame{

	public login(){
		JTextField uid =new JTextField(20); // Input Field
		JTextField psw=new JTextField(20); // input the psw for the first time

		JButton loginButton=new JButton("Log In");
		JButton cancelButton=new JButton("Cancel");
		
		JPanel uidPanel=new JPanel();
		uidPanel.setLayout(new BorderLayout(5,10));
		uidPanel.add(new JLabel(" Enter Your UID: "), BorderLayout.NORTH);
		uidPanel.add(uid, BorderLayout.CENTER);
		
		JPanel pswPanel=new JPanel();
		pswPanel.setLayout(new BorderLayout(5,10));
		pswPanel.add(new JLabel(" Enter Your PassWord: "), BorderLayout.NORTH);
		pswPanel.add(psw, BorderLayout.CENTER);
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,10));
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);

		JPanel linPanel=new JPanel();
		linPanel.setLayout(new GridLayout(3,1));
		
		linPanel.add(uidPanel,BorderLayout.WEST);
		linPanel.add(pswPanel,BorderLayout.WEST);
		linPanel.add(buttonPanel,BorderLayout.WEST);
		add(linPanel);
	}
    
	public static void main(String[] args){
		
		login frame=new login();
		frame.setTitle("DictPro-LogIn");
		frame.setSize(250,200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);   //test, when click the sign in button, show this window
	}
}
