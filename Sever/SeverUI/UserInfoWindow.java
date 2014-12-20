package SeverUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import DataBase.UserManager;
import System.UserInfo;

public class UserInfoWindow extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2215166237228509322L;
	JPanel explanationPanel;
	JTextArea WordAera;
	JButton jbtAbout,jbtDel;
	JScrollPane textArea;
	JPanel ButtonArea = new JPanel();
	//protected MessagePanel messagePanel;
	public UserInfoWindow(final UserInfo userinfo)
	{
		explanationPanel = new JPanel(new BorderLayout(5,0));	
		WordAera = new JTextArea("Username:" + userinfo.getAccount() 
		+ "\nSex:" 	+(userinfo.isSex() == true?("male"):("female"))
		+ "\nEmail:" + userinfo.getEmail()
		+ "\nState:"+ (userinfo.isOn() == true?("online"):("offline"))
		+"\nLast Login:"+userinfo.getDate()
		+"\n");
		WordAera.setLineWrap(true);
		WordAera.setFont(new Font("Consola",Font.BOLD,16));
		WordAera.setBackground(new Color(120, 145, 230));
		textArea = new JScrollPane(WordAera);
		explanationPanel.add(textArea,BorderLayout.CENTER);
		add(explanationPanel, BorderLayout.CENTER);
		
		jbtAbout = new JButton("Close");
		jbtAbout.setMnemonic('S');
		jbtAbout.setToolTipText("Close Window");
		
		jbtAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		jbtDel = new JButton("Delete");
		jbtDel.setMnemonic('S');
		jbtDel.setToolTipText("Delete User");
		
		jbtDel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				UserManager.delUser(userinfo.getAccount());
			}
		});
		
		ButtonArea.add(jbtAbout);
		ButtonArea.add(jbtDel);
		add(ButtonArea, BorderLayout.SOUTH);
		
	}
}