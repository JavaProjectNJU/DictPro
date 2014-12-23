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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import net.Message.Message;
import dict.net.LinkToServer;
import System.UserInfo;
 
@SuppressWarnings("serial")
public class myInfo extends JFrame{
	
	//new added
	private Font msgFont=new Font("微软雅黑",Font.BOLD,16);
	
	JButton configButton=new JButton("Config");
	JButton cancelButton=new JButton("Cancel");
	
	JPasswordField oldField=new JPasswordField(20);
	JPasswordField newField0=new JPasswordField(20);
	JPasswordField newField1=new JPasswordField(20);
	
	public myInfo(final LinkToServer link,final UserInfo finfo){
		JLabel uidLabel=new JLabel("姓名 : "+finfo.getAccount());
		JLabel emailLabel=new JLabel("邮箱 : "+finfo.getEmail());
		JLabel sexLabel;
		if(!finfo.isSex()){
			sexLabel=new JLabel("性别 ：Female");
		}
		else {
			sexLabel=new JLabel("性别 ：Male");
		}
		
		JButton chgpsw=new JButton("Change Psw");
		JButton cancel=new JButton("Return");

		uidLabel.setFont(msgFont);
		emailLabel.setFont(msgFont);
		sexLabel.setFont(msgFont);
		
		chgpsw.setFont(msgFont);
		cancel.setFont(msgFont);
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(chgpsw);
		buttonPanel.add(cancel);
		
		JPanel fPanel=new JPanel();
		fPanel.setLayout(new GridLayout(4,1));
		fPanel.add(buttonPanel,FlowLayout.LEFT);
		fPanel.add(sexLabel,FlowLayout.LEFT);	
		fPanel.add(emailLabel,FlowLayout.LEFT);
		fPanel.add(uidLabel,FlowLayout.LEFT);
		
		this.add(fPanel);
		//+++++++++++++++++++++++++++++++++++++++++
		//JPanel 
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		chgpsw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame pswFrame=new JFrame();

				JPanel pswPanel=new JPanel();
				pswPanel.setLayout(new BorderLayout());
				
				JPanel oldPswPanel=new JPanel();
				JPanel newPswPanel=new JPanel();
		
				
				oldPswPanel.add(oldField);
				
				newPswPanel.setLayout(new GridLayout(2,1));
				newPswPanel.add(newField0);
				newPswPanel.add(newField1);
				
				pswPanel.add(oldPswPanel,BorderLayout.NORTH);
				pswPanel.add(newPswPanel,BorderLayout.CENTER);
				

				JPanel buttonPanel=new JPanel();
				buttonPanel.setLayout(new GridLayout(1,2));
				buttonPanel.add(configButton);
				buttonPanel.add(cancelButton);
				
				pswPanel.add(buttonPanel,BorderLayout.SOUTH);
				
				
				pswFrame.setTitle("Change Psw");
				pswFrame.setSize(300,400);
				pswFrame.setLocationRelativeTo(null);
				pswFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pswFrame.setVisible(true);
			}
		});
		
		
		
		configButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String oldpswstr=new String(oldField.getPassword());
				String newpswStr0=new String(newField0.getPassword());
				String newpswStr1=new String(newField1.getPassword());
				
				boolean issu=false;
				if(oldpswstr!=null && newpswStr0!=null && newpswStr1!=null && oldpswstr.length()!=0 && newpswStr0.length()!=0 && newpswStr1.length()!=0 ){
					if(newpswStr1.equals(newpswStr0)){
						issu=link.changePsw(newpswStr0);
						if(issu){
							JOptionPane.showMessageDialog(null,"success !", "Success", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
						else{
							JOptionPane.showMessageDialog(null,"未知网络问题 !", "Fail", JOptionPane.ERROR_MESSAGE);
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"密码不匹配 !", "Fail", JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"密码不能为空 !", "Fail", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
}
