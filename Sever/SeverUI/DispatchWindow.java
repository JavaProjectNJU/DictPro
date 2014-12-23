package SeverUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.Message.Message;
import net.Message.Message.Send_Message;
import DataBase.UserManager;
import System.SendTask;
import System.UserInfo;

public class DispatchWindow extends JFrame
{
	private static final long serialVersionUID = -2215166237228509322L;
	Map<String,ArrayList<Message>> mailbox;
	JPanel explanationPanel;
	JTextArea WordAera;
	JButton jbtAbout,jbtDel;
	JScrollPane textArea;
	JPanel ButtonArea = new JPanel();
	//protected MessagePanel messagePanel;
	public DispatchWindow(Map<String,ArrayList<Message>> _mailbox)
	{
		mailbox = _mailbox;
		explanationPanel = new JPanel(new BorderLayout(5,0));	
		WordAera = new JTextArea();
		WordAera.setLineWrap(true);
		WordAera.setFont(new Font("Consola",Font.BOLD,16));
		//WordAera.setBackground(new Color(120, 145, 230));
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
		
		jbtDel = new JButton("Dipatch");
		jbtDel.setMnemonic('S');
		jbtDel.setToolTipText("Dipatch to Users");
		
		jbtDel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String uid = null;
				System.out.println("Test IN");
				ArrayList<UserInfo> onlineUserInfo = UserManager.getOnlineUser();
				for(int i = 0;i < onlineUserInfo.size(); i ++)
				{
					uid =  onlineUserInfo.get(i).getAccount();
					Message msg = new Message();
					msg.id = 0;
					msg.reply = false;
					msg.type = Message.SEND_MESSAGE;
					Message.Send_Message data = msg.new Send_Message();
					msg.data = data;
					data.uid = "Server";
					data.dialoge = WordAera.getText();
					System.out.println("Test:" + data.dialoge);
					ArrayList<Message> list = mailbox.get(uid);
					list.add(msg);
				}
			}
		});
		
		ButtonArea.add(jbtAbout);
		ButtonArea.add(jbtDel);
		add(ButtonArea, BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args)
	{
		DispatchWindow subFrame = new DispatchWindow(null);
		subFrame.setSize(300, 200);
		subFrame.setTitle("About Dictionary");
		subFrame.setLocationRelativeTo(null);//Center the Frame
		subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		subFrame.setVisible(true);
	}
}