package dict.pro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.Message.Message;
import dict.net.LinkToServer;
import System.UserInfo;
 
public class friendInfo extends JFrame{
	
	//new added
	private Font msgFont=new Font("微软雅黑",Font.BOLD,16);
	
	private JButton msgSend=new JButton("Send");
	
	private JTextField msgField=new JTextField(20);
	
	public friendInfo(final LinkToServer link,final UserInfo finfo){
		
		JLabel iconLabel =new JLabel(" "+ link.getDetail().getImge());
		
		JLabel uidLabel=new JLabel("姓名 : "+finfo.getAccount());
		JLabel emailLabel=new JLabel("邮箱 : "+finfo.getEmail());
		JLabel sexLabel;
		if(!finfo.isSex()){
			sexLabel=new JLabel("性别 ：Female");
		}
		else {
			sexLabel=new JLabel("性别 ：Male");
		}
		
		JButton chatFriend=new JButton("Chat");
		JButton delFriend=new JButton("Delet Friend");

		uidLabel.setFont(msgFont);
		emailLabel.setFont(msgFont);
		sexLabel.setFont(msgFont);
		chatFriend.setFont(msgFont);
		delFriend.setFont(msgFont);
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(chatFriend);
		buttonPanel.add(delFriend);
		
		JPanel fPanel=new JPanel();
		fPanel.setLayout(new GridLayout(5,1));
		fPanel.add(buttonPanel,FlowLayout.LEFT);
		fPanel.add(sexLabel,FlowLayout.LEFT);	
		fPanel.add(emailLabel,FlowLayout.LEFT);
		fPanel.add(uidLabel,FlowLayout.LEFT);
		fPanel.add(iconLabel,FlowLayout.LEFT);
		
		this.add(fPanel);
		//+++++++++++++++++++++++++++++++++++++++++
		//JPanel 
		delFriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				link.delFriend(finfo.getAccount());
				dispose();
			}
		});
		
		chatFriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame msgFrame=new JFrame();

				JPanel msgPanel=new JPanel();
				msgPanel.setLayout(new GridLayout(2,1));
			
				
				JPanel msgbtmPanel=new JPanel();
				msgbtmPanel.setLayout(new FlowLayout());
				msgbtmPanel.add(msgField);
				msgbtmPanel.add(msgSend);
				msgPanel.add(msgbtmPanel,FlowLayout.LEFT);
				
				msgFrame.add(msgPanel);
				msgPanel.add(new JLabel(" To: "+ finfo.getAccount()),FlowLayout.LEFT);
				
				msgFrame.setTitle("Message");
				msgFrame.setSize(400,200);
				msgFrame.setLocationRelativeTo(null);
				msgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				msgFrame.setVisible(true);
			}
		});
		
		msgSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean issu=false;
				if(msgField.getText()!=null && msgField.getText().length()!=0){
					issu=link.sendText(msgField.getText(), finfo.getAccount());
					if(issu){
						JOptionPane.showMessageDialog(null,"Send successed!", "Successed", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else{
						JOptionPane.showMessageDialog(null,"Send fail!", "Error", JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
		
		msgField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean issu=false;
				if(msgField.getText()!=null && msgField.getText().length()!=0){
					issu=link.sendText(msgField.getText(), finfo.getAccount());
					if(issu){
						JOptionPane.showMessageDialog(null,"Send successed!", "Successed", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else{
						JOptionPane.showMessageDialog(null,"Send fail!", "Error", JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
	}
	
}
