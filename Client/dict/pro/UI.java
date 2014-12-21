package dict.pro;

import dict.net.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.Document;
import javax.xml.ws.handler.MessageContext;

import net.Message.Message;
import net.Message.Message.Serach;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import com.mysql.jdbc.log.Jdk14Logger;

import System.UserInfo;
import word.UnionWord;
import wordcard.WordCard;


public class UI extends JFrame{

	private ArrayList<Message> msgBox=new ArrayList<Message>();
	
	private JList Flist = new JList<UserInfo>(); //flist for online friend

	private JButton messageButton=new JButton("No Message");
	private JList messageList=new JList();
	
	public LinkToServer link=Client.getLink(Flist,messageButton,messageList,msgBox);
	
	//private boolean online=link.isOnline();
	private boolean bdgood=false;
	private boolean ydgood=false;
	private boolean bygood=false;

	
	private JTextField inputField = new JTextField(30); // Input Field
	private JButton SearchButton = new JButton("search"); //Search Button
	
	private JButton Login=new JButton("Log in");//log in button

	
	private JButton Signin=new JButton("Sign in"); //sign in button
	
	private JCheckBox jchkBD=new JCheckBox("百度",true);
	private JCheckBox jchkYD=new JCheckBox("有道",true);
	private JCheckBox jchkBY=new JCheckBox("必应",true);

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
	
	private JPanel addFriendPanel=new JPanel();
	private JTextField addFriendField=new JTextField(10);
	private JButton addFriendButton=new JButton("Add Friend");
	

	private JLabel bdGoodLabel=new JLabel("Great number : ");
	private JLabel ydGoodLabel=new JLabel("Great number : ");
	private JLabel byGoodLabel=new JLabel("Great number : ");
	
	public UI(){
	
		bDJPanel.setLayout(new BorderLayout(5,0));
		bDJPanel.add(new JScrollPane(bDArea),BorderLayout.CENTER);
		JPanel bdp=new JPanel();
		bdp.setLayout(new FlowLayout(FlowLayout.LEFT));
		bdp.add(BDid);
		bdp.add(BDsend);
		bdp.add(BDgood);
		bdp.add(bdGoodLabel);
		
		bDJPanel.add(bdp,BorderLayout.SOUTH);
		
		yDJPanel.setLayout(new BorderLayout(5,0));
		yDJPanel.add(new JScrollPane(yDArea),BorderLayout.CENTER);
		JPanel ydp=new JPanel();
		ydp.setLayout(new FlowLayout(FlowLayout.LEFT));
		ydp.add(YDid);
		ydp.add(YDsend);
		ydp.add(YDgood);
		ydp.add(ydGoodLabel);
		
		yDJPanel.add(ydp,BorderLayout.SOUTH);		
	
		bYJPanel.setLayout(new BorderLayout(5,0));
		bYJPanel.add(new JScrollPane(bYArea),BorderLayout.CENTER);
		JPanel byp=new JPanel();
		byp.setLayout(new FlowLayout(FlowLayout.LEFT));
		byp.add(BYid);
		byp.add(BYsend);
		byp.add(BYgood);
		byp.add(byGoodLabel);
		
		bYJPanel.add(byp,BorderLayout.SOUTH);
		
		final JPanel body=new JPanel();
		body.setLayout(new GridLayout(3,1));
// add a sort function
		
		bDJPanel.setBorder(new TitledBorder("Baidu"));
		yDJPanel.setBorder(new TitledBorder("Youdao"));
		bYJPanel.setBorder(new TitledBorder("Bing"));
		
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
		final JLabel userinfoLabel =new JLabel(" ");
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottom.add(userinfoLabel);
		bottom.add(jchkBD);
		bottom.add(jchkYD);
		bottom.add(jchkBY);
		bottom.add(messageButton);
		
		headPanel.add(bar,BorderLayout.CENTER);
		headPanel.add(bottom,BorderLayout.SOUTH);
		
		addFriendButton.setLayout(new FlowLayout());
		addFriendPanel.add(addFriendField);
		addFriendPanel.add(addFriendButton);
		
		JPanel dictPro=new JPanel();
		dictPro.setLayout(new BorderLayout());
		
		Flist.setFixedCellWidth(150);
		dictPro.add(headPanel,BorderLayout.NORTH);
		dictPro.add(new JScrollPane(Flist),BorderLayout.WEST);
		dictPro.add(body,BorderLayout.CENTER);
		dictPro.add(addFriendPanel,BorderLayout.SOUTH);
		
		add(dictPro);
		//listen the online friend list

		Flist.addListSelectionListener(new ListSelectionListener(){ 

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(Flist.getSelectedValue()==null) return;
				//====
				String selectid = (String)Flist.getSelectedValue();
				BDid.setText(selectid);
				YDid.setText(selectid);
				BYid.setText(selectid);
				
				friendInfo fi=new friendInfo(link, (UserInfo)Flist.getSelectedValue());
				fi.setTitle("Friend Info");
				fi.setSize(100,300);
				fi.setLocationRelativeTo(null);
				fi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fi.setVisible(true);
			}
			
		});
		
		//listen the inputField
		inputField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String getString=inputField.getText();
				if(getString.length() == 0)
					return;
				
				bDArea.removeAll();
				yDArea.removeAll();
				bYArea.removeAll();
				
				//search getString
				final UnionWord uWord=link.serach(getString);
				
				if(uWord==null){
					bDArea.setText("The word cannot be found! ");
					yDArea.setText("The word cannot be found! ");
					bYArea.setText("The word cannot be found! ");
				}
				else{
					
					//++++++++++++++++++++++++++++++++++++++++++
					
					body.remove(bDJPanel);
					body.remove(yDJPanel);
					body.remove(bYJPanel);
					
					int bdP=uWord.getPariseBaidu();
					int ydP=uWord.getPariseYoudao();
					int byP=uWord.getPariseBing();
					
					if(bdP>=ydP && ydP>=byP){
						if(jchkBD.isSelected())
							body.add(bDJPanel);
						if(jchkYD.isSelected())
							body.add(yDJPanel);
						if(jchkBY.isSelected())
							body.add(bYJPanel);
					}else if(bdP>=byP && byP >=ydP){
						if(jchkBD.isSelected())
							body.add(bDJPanel);
						if(jchkBY.isSelected())
							body.add(bYJPanel);
						if(jchkYD.isSelected())
							body.add(yDJPanel);
					}else if(ydP>=bdP && bdP>=byP){
						if(jchkYD.isSelected())
							body.add(yDJPanel);
						if(jchkBD.isSelected())
							body.add(bDJPanel);
						if(jchkBY.isSelected())
							body.add(bYJPanel);
					}else if(ydP>=byP && byP >=bdP){
						if(jchkYD.isSelected())
							body.add(yDJPanel);
						if(jchkBY.isSelected())
							body.add(bYJPanel);
						if(jchkBD.isSelected())
							body.add(bDJPanel);
					}else if(byP>=bdP && bdP>=ydP){
						if(jchkBY.isSelected())
							body.add(bYJPanel);
						if(jchkBD.isSelected())
							body.add(bDJPanel);
						if(jchkYD.isSelected())
							body.add(yDJPanel);
					}else if(byP>=ydP && ydP >=bdP){
						if(jchkBY.isSelected())
							body.add(bYJPanel);
						if(jchkYD.isSelected())
							body.add(yDJPanel);
						if(jchkBD.isSelected())
							body.add(bDJPanel);
					}
					
					//++++++++++++++++++++++++++++++++++++++++++
					
					StringBuilder bdsb=new StringBuilder();
					if(uWord.getWordBaidu()!=null){
						bdsb.append(" UK :"+uWord.getWordBaidu().getPron_EN_UK());
						bdsb.append(" US :"+uWord.getWordBaidu().getPron_EN_US()+'\n');
						for(int i=0;i<uWord.getWordBaidu().getExplain().size();i++){
							bdsb.append(uWord.getWordBaidu().getExplain().get(i)+'\n');
						}
					}
					else{
						bdsb.append("BaiDu Dict cannot find this word! ");
					}
					bDArea.setText(bdsb.toString());
					bdGoodLabel.setText("Great number : "+ uWord.getPariseBaidu());
					if(uWord.isHasPraisedBaidu())
						BDgood.setText("cancel");
					else{
						BDgood.setText("good");
					}
					
					StringBuilder ydsb=new StringBuilder();
					if(uWord.getWordYoudao()!=null){
						ydsb.append(" UK :"+uWord.getWordYoudao().getPron_EN_UK());
						ydsb.append(" US :"+uWord.getWordYoudao().getPron_EN_US()+'\n');
						for(int i=0;i<uWord.getWordYoudao().getExplain().size();i++){
							ydsb.append(uWord.getWordYoudao().getExplain().get(i)+'\n');
						}
					}
					else{
						ydsb.append("YouDao Dict cannot find this word! ");
					}
				
					yDArea.setText(ydsb.toString());
					ydGoodLabel.setText("Great number : "+ uWord.getPariseYoudao());
					if(uWord.isHasPraisedYoudao())
						YDgood.setText("cancel");
					else{
						YDgood.setText("good");
					}

					StringBuilder bysb=new StringBuilder();
					if(uWord.getWordBing()!=null){
						bysb.append(" UK :"+uWord.getWordBing().getPron_EN_UK());
						bysb.append(" US :"+uWord.getWordBing().getPron_EN_US()+'\n');
						for(int i=0;i<uWord.getWordBing().getExplain().size();i++){
							bysb.append(uWord.getWordBing().getExplain().get(i)+'\n');
						}
					}
					else{
						bysb.append("Bing Dict cannot find this word!");
					}
					bYArea.setText(bysb.toString());
					byGoodLabel.setText("Great number : "+ uWord.getPariseBing());
					if(uWord.isHasPraisedBing())
						BYgood.setText("cancel");
					else{
						BYgood.setText("good");
					}
				}
			}	
		});
		
		//click the search button
	
		SearchButton.addActionListener(new ActionListener(){   

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					String getString=inputField.getText();
					if(getString.length() == 0)
						return;
					
					bDArea.removeAll();
					yDArea.removeAll();
					bYArea.removeAll();
					
					//search getString
					final UnionWord uWord=link.serach(getString);
					
					if(uWord==null){
						bDArea.setText("The word cannot be found! ");
						yDArea.setText("The word cannot be found! ");
						bYArea.setText("The word cannot be found! ");
					}
					else{
						
						//++++++++++++++++++++++++++++++++++++++++++
						
						body.remove(bDJPanel);
						body.remove(yDJPanel);
						body.remove(bYJPanel);
						
						int bdP=uWord.getPariseBaidu();
						int ydP=uWord.getPariseYoudao();
						int byP=uWord.getPariseBing();
						
						if(bdP>=ydP && ydP>=byP){
							if(jchkBD.isSelected())
								body.add(bDJPanel);
							if(jchkYD.isSelected())
								body.add(yDJPanel);
							if(jchkBY.isSelected())
								body.add(bYJPanel);
						}else if(bdP>=byP && byP >=ydP){
							if(jchkBD.isSelected())
								body.add(bDJPanel);
							if(jchkBY.isSelected())
								body.add(bYJPanel);
							if(jchkYD.isSelected())
								body.add(yDJPanel);
						}else if(ydP>=bdP && bdP>=byP){
							if(jchkYD.isSelected())
								body.add(yDJPanel);
							if(jchkBD.isSelected())
								body.add(bDJPanel);
							if(jchkBY.isSelected())
								body.add(bYJPanel);
						}else if(ydP>=byP && byP >=bdP){
							if(jchkYD.isSelected())
								body.add(yDJPanel);
							if(jchkBY.isSelected())
								body.add(bYJPanel);
							if(jchkBD.isSelected())
								body.add(bDJPanel);
						}else if(byP>=bdP && bdP>=ydP){
							if(jchkBY.isSelected())
								body.add(bYJPanel);
							if(jchkBD.isSelected())
								body.add(bDJPanel);
							if(jchkYD.isSelected())
								body.add(yDJPanel);
						}else if(byP>=ydP && ydP >=bdP){
							if(jchkBY.isSelected())
								body.add(bYJPanel);
							if(jchkYD.isSelected())
								body.add(yDJPanel);
							if(jchkBD.isSelected())
								body.add(bDJPanel);
						}
						
						//++++++++++++++++++++++++++++++++++++++++++
						
						StringBuilder bdsb=new StringBuilder();
						if(uWord.getWordBaidu()!=null){
							bdsb.append(" UK :"+uWord.getWordBaidu().getPron_EN_UK());
							bdsb.append(" US :"+uWord.getWordBaidu().getPron_EN_US()+'\n');
							for(int i=0;i<uWord.getWordBaidu().getExplain().size();i++){
								bdsb.append(uWord.getWordBaidu().getExplain().get(i)+'\n');
							}
						}
						else{
							bdsb.append("BaiDu Dict cannot find this word! ");
						}
						bDArea.setText(bdsb.toString());
						bdGoodLabel.setText("Great number : "+ uWord.getPariseBaidu());
						if(uWord.isHasPraisedBaidu())
							BDgood.setText("cancel");
						else{
							BDgood.setText("good");
						}
						
						StringBuilder ydsb=new StringBuilder();
						if(uWord.getWordYoudao()!=null){
							ydsb.append(" UK :"+uWord.getWordYoudao().getPron_EN_UK());
							ydsb.append(" US :"+uWord.getWordYoudao().getPron_EN_US()+'\n');
							for(int i=0;i<uWord.getWordYoudao().getExplain().size();i++){
								ydsb.append(uWord.getWordYoudao().getExplain().get(i)+'\n');
							}
						}
						else{
							ydsb.append("YouDao Dict cannot find this word! ");
						}
					
						yDArea.setText(ydsb.toString());
						ydGoodLabel.setText("Great number : "+ uWord.getPariseYoudao());
						if(uWord.isHasPraisedYoudao())
							YDgood.setText("cancel");
						else{
							YDgood.setText("good");
						}

						StringBuilder bysb=new StringBuilder();
						if(uWord.getWordBing()!=null){
							bysb.append(" UK :"+uWord.getWordBing().getPron_EN_UK());
							bysb.append(" US :"+uWord.getWordBing().getPron_EN_US()+'\n');
							for(int i=0;i<uWord.getWordBing().getExplain().size();i++){
								bysb.append(uWord.getWordBing().getExplain().get(i)+'\n');
							}
						}
						else{
							bysb.append("Bing Dict cannot find this word!");
						}
						bYArea.setText(bysb.toString());
						byGoodLabel.setText("Great number : "+ uWord.getPariseBing());
						if(uWord.isHasPraisedBing())
							BYgood.setText("cancel");
						else{
							BYgood.setText("good");
						}
					}
			}
		});
		
		//click the add button
		Login.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(Login.getText().equals("Log in")){
					login lwin=new login(link,Login,userinfoLabel);
					lwin.setTitle("DictPro-LogIn");
					lwin.setSize(250,200);
					lwin.setLocationRelativeTo(null);
					lwin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					lwin.setVisible(true); 
					
				 }
				 else{
					 //log out
					 if(link.logout()){
						 Login.setText("Log in");
						 BDgood.setText("good");
						 YDgood.setText("good");
						 BYgood.setText("good");
						 userinfoLabel.setText(" ");
					 }
					 
				 }
			}
		});
		
		Signin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				signin swin=new signin(link);
				swin.setTitle("DictPro-SignIn");
				swin.setSize(250,400);
				swin.setLocationRelativeTo(null);
				swin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				swin.setVisible(true); 
			}
		});
	
		BDsend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean success = false;
				 if(link.isOnline()){
					 //send
					 if(inputField.getText().length() != 0 && BDid.getText().length() != 0 ){
						 if(link.serach(inputField.getText())== null)
						 {
							 JOptionPane.showMessageDialog(null,"Can't get the meaning of word from baidu!", "Error", JOptionPane.ERROR_MESSAGE);
							 return;
						 }
						 WordCard wcCard=new WordCard(link.serach(inputField.getText()).getWordBaidu(), link.getDetail().getAccount(), BDid.getText());
						 success = link.sendCard(wcCard.getBuffImage(), BDid.getText());
						 if(success)
							 JOptionPane.showMessageDialog(null,"Send Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
						 else {
							 JOptionPane.showMessageDialog(null,"Send Fail", "Fail", JOptionPane.ERROR_MESSAGE);
						}
					 }
				 }
			}
		});
		BDgood.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				if(link.isOnline()){
					String getString=inputField.getText();
				
					if(getString==null || getString.length() == 0)
						return;
				
					final UnionWord uWord=link.serach(getString);
				
					if(uWord==null)
						return;
				
					
					if(uWord.getWordBaidu()!=null){
						if(!uWord.isHasPraisedBaidu()){
							 link.addPrise(inputField.getText(), 0);
							 bdGoodLabel.setText("Great number : "+ uWord.getPariseBaidu());
							 BDgood.setText("cancel");
						}
						else{
							 link.delPrise(inputField.getText(), 0);
							 bdGoodLabel.setText("Great number : "+ uWord.getPariseBaidu());
							 BDgood.setText("good");
						}
					}
				}
			}
		});
		
		YDsend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean success = false;
				 if(link.isOnline()){
					 //send
					 if(inputField.getText().length() != 0 && YDid.getText().length() != 0 ){
						 if(link.serach(inputField.getText())== null)
						 {
							 JOptionPane.showMessageDialog(null,"Can't get the meaning of word from youdao!", "Error", JOptionPane.ERROR_MESSAGE);
							 return;
						 }
						 WordCard wcCard=new WordCard(link.serach(inputField.getText()).getWordYoudao(),link.getDetail().getAccount(), YDid.getText());
						 success = link.sendCard(wcCard.getBuffImage(), YDid.getText());
						 if(success)
							 JOptionPane.showMessageDialog(null,"Send Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
						 else {
							 JOptionPane.showMessageDialog(null,"Send Fail", "Fail", JOptionPane.ERROR_MESSAGE);
						}
					 }
				 }
			}
		});
		YDgood.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(link.isOnline()){
					String getString=inputField.getText();
				
					if(getString==null || getString.length() == 0)
						return;
				
					final UnionWord uWord=link.serach(getString);
				
					if(uWord==null)
						return;
				
					
					if(uWord.getWordYoudao()!=null){
						if(!uWord.isHasPraisedYoudao()){
							 link.addPrise(inputField.getText(), 1);
							 YDgood.setText("cancel");
							 ydGoodLabel.setText("Great number : "+ uWord.getPariseYoudao());
						}
						else{
							 link.delPrise(inputField.getText(), 1);
							 YDgood.setText("good");
							 ydGoodLabel.setText("Great number : "+ uWord.getPariseYoudao());
						}
					}
				}
			}
		});
		
		BYsend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(link.isOnline() == true){
					 //send
					 if(inputField.getText()!=null && BYid.getText()!=null){
						 WordCard wcCard=new WordCard(link.serach(inputField.getText()).getWordBing(), link.getDetail().getAccount(), BYid.getText());
						 link.sendCard(wcCard.getBuffImage(), BYid.getText());
					 }
				 }
			}
		});
		BYgood.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(link.isOnline()){
					String getString=inputField.getText();
				
					if(getString==null || getString.length() == 0)
						return;
				
					final UnionWord uWord=link.serach(getString);
				
					if(uWord==null)
						return;
				
					
					if(uWord.getWordBing()!=null){
						if(!uWord.isHasPraisedBing()){
							 link.addPrise(inputField.getText(), 2);
							 BYgood.setText("cancel");
							 byGoodLabel.setText("Great number : "+ uWord.getPariseBing());
						}
						else{
							 link.delPrise(inputField.getText(), 2);
							 BYgood.setText("good");
							 byGoodLabel.setText("Great number : "+ uWord.getPariseBing());
						}
					}
				}
			}
		});

		addFriendField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String uname=addFriendField.getText();
				if(uname.length() == 0)
					return;
				boolean isadd=false;
				if(link.isOnline() && uname!=null){
					isadd=link.addFriend(uname);
					if(isadd){
						JOptionPane.showMessageDialog(null,"success! ", "Add friend reminder!", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null,"can not be found! ", "Add friend reminder!", JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"fail! ", "Add friend reminder!", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		addFriendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String uname=addFriendField.getText();
				if(uname.length() == 0)
					return;
				boolean isadd=false;
				if(link.isOnline() && uname!=null){
					isadd=link.addFriend(uname);
					if(isadd){
						JOptionPane.showMessageDialog(null,"success! ", "Add friend reminder!", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null,"can not be found! ", "Add friend reminder!", JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"fail! ", "Add friend reminder!", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		// private JButton messageButton=new JButton("No Message");
		// private JList messageList=new JList();
		
		messageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!messageButton.getText().equals("No Message")){
					//++++++++++++++++++++++++++++++++++++++++++++++++++
					JFrame messageFrame=new JFrame();
					JPanel messagePanel=new JPanel();
					messagePanel.add(new JScrollPane(messageList));
					messageFrame.add(messagePanel);
					
					messageFrame.setTitle("Message List");
					messageFrame.setSize(100,500);
					messageFrame.setLocationRelativeTo(null);
					messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					messageFrame.setVisible(true);
				}
			}
		});

		messageList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				Message msg=(Message)messageList.getSelectedValue();
				if(msg.type==Message.SEND_MESSAGE){
					//send the message
					ReplyFrame msgFrame=new ReplyFrame(link,msg);

					msgFrame.setTitle("Message");
					msgFrame.setSize(400,200);
					msgFrame.setLocationRelativeTo(null);
					msgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					msgFrame.setVisible(true);
					
				}
				else if(msg.type==Message.SEND_CARD){
					//send the card
					WordCard card=new WordCard(((Message.Send_Card)msg.data).card);
					card.display();
				}
				
				synchronized(msgBox){
					msgBox.remove(msg);
				}
			}
		});
	
	}
    
	public static void main(String[] args){
		
		try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
          

          
          SubstanceLookAndFeel.setSkin(new OfficeBlue2007Skin());
          SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
          SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
          SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
            SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
            //SubstanceLookAndFeel.setCurrentTitlePainter(new FlatTitePainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }
		
		
		UI frame=new UI();
		frame.setTitle("DictPro");
		frame.setSize(600,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

