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

public class DispatchWindow extends JFrame
{
	private static final long serialVersionUID = -2215166237228509322L;
	JPanel explanationPanel;
	JTextArea WordAera;
	JButton jbtAbout,jbtDel;
	JScrollPane textArea;
	JPanel ButtonArea = new JPanel();
	//protected MessagePanel messagePanel;
	public DispatchWindow()
	{
		explanationPanel = new JPanel(new BorderLayout(5,0));	
		
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
				
			}
		});
		
		ButtonArea.add(jbtAbout);
		ButtonArea.add(jbtDel);
		add(ButtonArea, BorderLayout.SOUTH);
		
	}
	
	void public static main(String[] args)
	{
		UserInfoWindow subFrame = new UserInfoWindow(UserManager.getOtherOnlineUser(searchString));
		subFrame.setSize(300, 400);
		subFrame.setTitle("About Dictionary");
		subFrame.setLocationRelativeTo(null);//Center the Frame
		subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		subFrame.setVisible(true);
	}
}