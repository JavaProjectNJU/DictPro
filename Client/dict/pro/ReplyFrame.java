package dict.pro;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dict.net.LinkToServer;
import net.Message.Message;

public class ReplyFrame extends JFrame{
	private Message msg;


	private JButton msgSend;
	private JTextField msgField;
	
	public ReplyFrame(final LinkToServer link, Message msg){
		this.msg = msg;
		msgSend=new JButton("Send");
		msgField=new JTextField(20);
		
		final Message.Send_Message data=(Message.Send_Message)(msg.data);
		
		JPanel msgPanel=new JPanel();
		msgPanel.setLayout(new GridLayout(2,1));
		msgPanel.add(new JLabel(" From : "+ data.uid+"     "+ data.dialoge));
		JPanel msgbtmPanel=new JPanel();
		msgbtmPanel.setLayout(new BorderLayout());
		msgbtmPanel.add(msgField,BorderLayout.CENTER);
		msgbtmPanel.add(msgSend,BorderLayout.EAST);
		msgPanel.add(msgbtmPanel);
		
		this.add(msgPanel);
		
		//JButton msgSend=new JButton("Send");
		msgSend.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(msgField.getText()!=null && msgField.getText().length()!=0){
					boolean issuccess=link.sendText(msgField.getText(),data.uid);
					if(issuccess){
						JOptionPane.showMessageDialog(null,"send sussefully! ", "Message Reminder!", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "cannot send!","Message Remainder",JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				}
			}
		});
		msgField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(msgField.getText()!=null && msgField.getText().length()!=0){
					boolean issuccess=link.sendText(msgField.getText(),data.uid);
					if(issuccess){
						JOptionPane.showMessageDialog(null,"send sussefully! ", "Message Reminder!", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "cannot send!","Message Remainder",JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				}
			}
		});
	
		
	}
	
	
	
	
}
