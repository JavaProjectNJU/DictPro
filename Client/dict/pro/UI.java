package dict.pro;

import dict.net.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.*;

import javax.swing.event.*;
import javax.swing.text.Document;

import net.Message.Message.Serach;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import System.UserInfo;
import word.UnionWord;
import wordcard.WordCard;


public class UI extends JFrame{

	
	private JList Flist = new JList<UserInfo>(); //flist for online friend

	public LinkToServer link=Client.getLink(Flist);
	
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
	
	
	
	public UI(){
	
		
		bDJPanel.setLayout(new BorderLayout(5,0));
		bDJPanel.add(new JScrollPane(bDArea),BorderLayout.CENTER);
		JPanel bdp=new JPanel();
		bdp.setLayout(new FlowLayout(FlowLayout.LEFT));
		bdp.add(BDid);
		bdp.add(BDsend);
		bdp.add(BDgood);
		bDJPanel.add(bdp,BorderLayout.SOUTH);
		
		yDJPanel.setLayout(new BorderLayout(5,0));
		yDJPanel.add(new JScrollPane(yDArea),BorderLayout.CENTER);
		JPanel ydp=new JPanel();
		ydp.setLayout(new FlowLayout(FlowLayout.LEFT));
		ydp.add(YDid);
		ydp.add(YDsend);
		ydp.add(YDgood);
		yDJPanel.add(ydp,BorderLayout.SOUTH);		
	
		bYJPanel.setLayout(new BorderLayout(5,0));
		bYJPanel.add(new JScrollPane(bYArea),BorderLayout.CENTER);
		JPanel byp=new JPanel();
		byp.setLayout(new FlowLayout(FlowLayout.LEFT));
		byp.add(BYid);
		byp.add(BYsend);
		byp.add(BYgood);
		bYJPanel.add(byp,BorderLayout.SOUTH);
		
		JPanel body=new JPanel();
		body.setLayout(new GridLayout(3,1));
// add a sort function
		
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
		bottom.add(userinfoLabel,FlowLayout.LEFT);
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottom.add(jchkBD);
		bottom.add(jchkYD);
		bottom.add(jchkBY);
		
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
				
				friendInfo fi=new friendInfo((UserInfo)Flist.getSelectedValue());
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
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				String getString=inputField.getText();
				
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
			}
			}	
		});
		
		//click the search button
	
		SearchButton.addActionListener(new ActionListener(){   

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					String getString=inputField.getText();
					
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
				 if(link.isOnline() == true){
					 //send
					 if(inputField.getText()!=null && BDid.getText()!=null){
						 WordCard wcCard=new WordCard(link.serach(inputField.getText()).getWordBaidu(), link.getDetail().getNickname(), BDid.getText());
						 link.sendCard(wcCard.getBuffImage(), BDid.getText());
					 }
				 }
			}
		});
		BDgood.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(link.isOnline() == true){
					 //give good comment
					 if(!bdgood){
						 link.addPrise(inputField.getText(), 0);
						 bdgood=true;
					 }
					 else{
						 link.delPrise(inputField.getText(), 0);
						 bdgood=false;
					 }
					
				 }
			}
		});
		
		YDsend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(link.isOnline() == true){
					 //send
					 if(inputField.getText()!=null && YDid.getText()!=null){
						 WordCard wcCard=new WordCard(link.serach(inputField.getText()).getWordYoudao(), link.getDetail().getNickname(), BDid.getText());
						 link.sendCard(wcCard.getBuffImage(), YDid.getText());
					 }
				 }
			}
		});
		YDgood.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(link.isOnline() == true){
					 //give good comment
					 if(!ydgood){
						 link.addPrise(inputField.getText(), 1);
						 ydgood=true;
					 }
					 else{
						 link.delPrise(inputField.getText(), 1);
						 ydgood=false;
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
					 if(inputField.getText()!=null && YDid.getText()!=null){
						 WordCard wcCard=new WordCard(link.serach(inputField.getText()).getWordBing(), link.getDetail().getNickname(), BDid.getText());
						 link.sendCard(wcCard.getBuffImage(), BYid.getText());
					 }
				 }
			}
		});
		BYgood.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(link.isOnline() == true){
					 //give good comment
					 if(!bygood){
						 link.addPrise(inputField.getText(), 2);
						 bygood=true;
					 }
					 else{
						 link.delPrise(inputField.getText(), 2);
						 bygood=false;
					 }
					
				 }
			}
		});

		addFriendField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String uname=addFriendField.getText();
				if(link.isOnline() && uname!=null){
					link.addFriend(uname);
				}
			}
		});
		
		addFriendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String uname=addFriendField.getText();
				if(link.isOnline() && uname!=null){
					link.addFriend(uname);
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

