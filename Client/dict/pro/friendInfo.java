package dict.pro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import System.UserInfo;
 
public class friendInfo extends JFrame{
	
	public friendInfo(UserInfo finfo){
		JLabel uidLabel=new JLabel("姓名 : "+finfo.getAccount());
//		JLabel emailLabel=new JLabel("邮箱 : "+finfo.getEmail());
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
		fPanel.setLayout(new GridLayout(3,1));
		fPanel.add(uidLabel,FlowLayout.LEFT);
		fPanel.add(sexLabel,FlowLayout.LEFT);
		fPanel.add(buttonPanel,FlowLayout.LEFT);
		//+++++++++++++++++++++++++++++++++++++++++
		//JPanel 
	
	}
	
}
