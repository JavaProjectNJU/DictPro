package dict.pro;

import javax.swing.JFrame;


import javax.swing.*;

import javax.swing.JFrame;

import java.awt.event.*;
import java.awt.*;
import java.io.*;

import javax.swing.event.*;
import javax.swing.text.Document;


public class UI extends JFrame{
	
	private JTextField inputField = new JTextField(30); // Input Field
	private JButton SearchButton = new JButton("search"); //Search Button
	
	private JButton Login=new JButton("Log in");//log in button
	private JButton Signin=new JButton("Sign in"); //sign in button
	
	private JCheckBox jchkBD=new JCheckBox("百度",true);
	private JCheckBox jchkYD=new JCheckBox("有道",true);
	private JCheckBox jchkBY=new JCheckBox("必应",true);
	
	private String[] FriendList =new String[100];  	//online friend list
	private JList Flist = new JList(FriendList); //flist for online friend
	
	private JTextArea bDArea=new JTextArea("百度");
	private JTextArea yDArea=new JTextArea("有道");
	private JTextArea bYArea=new JTextArea("必应");
	
	private JTextField BDid=new JTextField(10);
	private JTextField YDid=new JTextField(10);
	private JTextField BYid=new JTextField(10);
	
	private JButton BDsend=new JButton("send");
	private JButton YDsend=new JButton("send");
	private JButton BYsend=new JButton("send");
	
	private JButton BDgood=new JButton("good");
	private JButton YDgood=new JButton("good");
	private JButton BYgood=new JButton("good");
	 
	private JPanel bDJPanel=new JPanel();
	private JPanel yDJPanel=new JPanel();
	private JPanel bYJPanel=new JPanel();
	
	public UI(){
		bDJPanel.setLayout(new BorderLayout(5,0));
		bDJPanel.add(bDArea,BorderLayout.CENTER);
		JPanel bdp=new JPanel();
		bdp.setLayout(new FlowLayout(FlowLayout.LEFT));
		bdp.add(BDid);
		bdp.add(BDsend);
		bdp.add(BDgood);
		bDJPanel.add(bdp,BorderLayout.SOUTH);
		
		yDJPanel.setLayout(new BorderLayout(5,0));
		yDJPanel.add(yDArea,BorderLayout.CENTER);
		JPanel ydp=new JPanel();
		ydp.setLayout(new FlowLayout(FlowLayout.LEFT));
		ydp.add(YDid);
		ydp.add(YDsend);
		ydp.add(YDgood);
		yDJPanel.add(ydp,BorderLayout.SOUTH);		
	
		bYJPanel.setLayout(new BorderLayout(5,0));
		bYJPanel.add(bYArea,BorderLayout.CENTER);
		JPanel byp=new JPanel();
		byp.setLayout(new FlowLayout(FlowLayout.LEFT));
		byp.add(BYid);
		byp.add(BYsend);
		byp.add(BYgood);
		bYJPanel.add(byp,BorderLayout.SOUTH);
		
		JPanel body=new JPanel();
		body.setLayout(new GridLayout(3,1));
		body.add(bDJPanel);
		body.add(yDJPanel);
		body.add(bYJPanel);
		
		JPanel headPanel=new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		JPanel bar=new JPanel();
		bar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bar.add(inputField);
		bar.add(SearchButton);
		bar.add(Login);
		bar.add(Signin);
		
		JPanel bottom=new JPanel();
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottom.add(jchkBD);
		bottom.add(jchkYD);
		bottom.add(jchkBY);
		
		headPanel.add(bar,BorderLayout.CENTER);
		headPanel.add(bottom,BorderLayout.SOUTH);
		
		JPanel dictPro=new JPanel();
		dictPro.setLayout(new BorderLayout());
		
		Flist.setFixedCellWidth(150);
		
		dictPro.add(headPanel,BorderLayout.NORTH);
		dictPro.add(new JScrollPane(Flist),BorderLayout.WEST);
		dictPro.add(body,BorderLayout.CENTER);
		
		add(dictPro);
	}
    
	public static void main(String[] args){
		
		UI frame=new UI();
		frame.setTitle("DictPro");
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

