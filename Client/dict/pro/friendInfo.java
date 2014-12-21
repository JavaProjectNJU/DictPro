package dict.pro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.Message.Message;
import dict.net.LinkToServer;
import System.UserInfo;
 
public class friendInfo extends JFrame{
	
	private JButton msgSend=new JButton("Send");
	private JTextField msgField=new JTextField(20);
	
	public friendInfo(final LinkToServer link,final UserInfo finfo){
		JLabel uidLabel=new JLabel("姓名 : "+finfo.getAccount());
		JLabel emailLabel=new JLabel("邮箱 : "+finfo.getEmail());
		JLabel sexLabel;
		if(finfo.isSex()){
			sexLabel=new JLabel("性别 ：Female");
		}
		else {
			sexLabel=new JLabel("性别 ：Male");
		}
		JButton chatFriend=new JButton("Chat");
		JButton delFriend=new JButton("Delet Friend");
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(chatFriend);
		buttonPanel.add(delFriend);
		
		JPanel fPanel=new JPanel();
		fPanel.setLayout(new GridLayout(4,1));
		fPanel.add(uidLabel,FlowLayout.LEFT);
		fPanel.add(emailLabel,FlowLayout.LEFT);
		fPanel.add(sexLabel,FlowLayout.LEFT);
		fPanel.add(buttonPanel,FlowLayout.LEFT);
		//+++++++++++++++++++++++++++++++++++++++++
		//JPanel 
		delFriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				link.delFriend(finfo.getAccount());
			}
		});
		
		chatFriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame msgFrame=new JFrame();

				JPanel msgPanel=new JPanel();
				msgPanel.setLayout(new GridLayout(2,1));
				
				msgPanel.add(new JLabel(" To: "+ finfo.getAccount()),FlowLayout.LEFT);
				
				JPanel msgbtmPanel=new JPanel();
				msgbtmPanel.setLayout(new FlowLayout());
				msgbtmPanel.add(msgField);
				msgbtmPanel.add(msgSend);
				msgPanel.add(msgbtmPanel,FlowLayout.LEFT);
				
				msgFrame.setTitle("Message");
				msgFrame.setSize(400,200);
				msgFrame.setLocationRelativeTo(null);
				msgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				msgFrame.setVisible(true);
			}
		});
	}
	
}
