package dict.pro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import dict.net.LinkToServer;

public class signin extends JFrame{
	
	private boolean signEnable=true;
	private boolean issex=false;

	public signin(LinkToServer link){
		final JTextField uid =new JTextField(20); // Input Field
		final JTextField psw0=new JTextField(20); // input the psw for the first time
		final JTextField psw1=new JTextField(20); // input the psw for the second time
		final JTextField email=new JTextField(20); // input the email
		
		final ButtonGroup sex=new ButtonGroup();
		JRadioButton male=new JRadioButton("Male",true);
		JRadioButton female=new JRadioButton("Female");
		sex.add(male);
		sex.add(female);
		
		JButton submitButton=new JButton("Submit");
		JButton cancelButton=new JButton("Cancel");
		
		JPanel uidPanel=new JPanel();
		uidPanel.setLayout(new BorderLayout(5,10));
		uidPanel.add(new JLabel(" Enter Your UID: "), BorderLayout.NORTH);
		uidPanel.add(uid, BorderLayout.CENTER);
		
		JPanel pswPanel0=new JPanel();
		pswPanel0.setLayout(new BorderLayout(5,10));
		pswPanel0.add(new JLabel(" Enter Your PassWord: "), BorderLayout.NORTH);
		pswPanel0.add(psw0, BorderLayout.CENTER);
		
		JPanel pswPanel1=new JPanel();	
		pswPanel1.setLayout(new BorderLayout(5,10));
		pswPanel1.add(new JLabel(" Confirm Your PassWord: "), BorderLayout.NORTH);
		pswPanel1.add(psw1, BorderLayout.CENTER);	
		
		JPanel emailPanel=new JPanel();
		emailPanel.setLayout(new BorderLayout(5,10));
		emailPanel.add(new JLabel(" Enter Your Email: "), BorderLayout.NORTH);
		emailPanel.add(email, BorderLayout.CENTER);	
		
		JPanel sexPanel=new JPanel();
		sexPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,10));
		sexPanel.add(male);
		sexPanel.add(female);
		sexPanel.setBorder(new TitledBorder("Choose your sex:"));
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,10));
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		
		JPanel sinPanel=new JPanel();
		sinPanel.setLayout(new GridLayout(6,1));
		
		sinPanel.add(uidPanel,BorderLayout.WEST);
		sinPanel.add(pswPanel0,BorderLayout.WEST);
		sinPanel.add(pswPanel1,BorderLayout.WEST);
		sinPanel.add(emailPanel,BorderLayout.WEST);
		sinPanel.add(sexPanel,BorderLayout.WEST);
		
		sinPanel.add(buttonPanel,BorderLayout.WEST);
		add(sinPanel);
		
		male.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				issex=true;
				
			}
		});
		
		female.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				issex=false;
			}
		});
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String pswStr0=psw0.getText();
				String pswStr1=psw1.getText();
				signEnable=pswStr0.equals(pswStr1);
				if(signEnable){
					String uidStr=uid.getText();
					String emailStr=email.getText();
					
					//send to server
					
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
    

}