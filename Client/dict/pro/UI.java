package dict.pro;

import dict.net.*;
import javafx.scene.layout.Border;

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

import word.UnionWord;
import word.WordCard;


public class UI extends JFrame{
	
	public LinkToServer link=Client.getLink();
	
	private boolean online=false;
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
	
	private String[] FriendList =new String[100];  	//online friend list
	private JList Flist = new JList(FriendList); //flist for online friend
	
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
	
	public UI(){
		
		bDJPanel.setLayout(new BorderLayout(5,0));
		bDJPanel.add(bDArea,BorderLayout.CENTER);
		JPanel bdp=new JPanel();
		bdp.setLayout(new FlowLayout(FlowLayout.LEFT));
		bdp.add(BDid);
		bdp.add(BDsend);
		bdp.add(BDgood);
		bDJPanel.add(bdp,BorderLayout.SOUTH);
		
		yDJPanel.setLayout(new BorderLayout(5,0));
		yDJPanel.add(yDArea,BorderLayout.CENTER);
		JPanel ydp=new JPanel();
		ydp.setLayout(new FlowLayout(FlowLayout.LEFT));
		ydp.add(YDid);
		ydp.add(YDsend);
		ydp.add(YDgood);
		yDJPanel.add(ydp,BorderLayout.SOUTH);		
	
		bYJPanel.setLayout(new BorderLayout(5,0));
		bYJPanel.add(bYArea,BorderLayout.CENTER);
		JPanel byp=new JPanel();
		byp.setLayout(new FlowLayout(FlowLayout.LEFT));
		byp.add(BYid);
		byp.add(BYsend);
		byp.add(BYgood);
		bYJPanel.add(byp,BorderLayout.SOUTH);
		
		JPanel body=new JPanel();
		body.setLayout(new GridLayout(3,1));
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
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottom.add(jchkBD);
		bottom.add(jchkYD);
		bottom.add(jchkBY);
		
		headPanel.add(bar,BorderLayout.CENTER);
		headPanel.add(bottom,BorderLayout.SOUTH);
		
		JPanel dictPro=new JPanel();
		dictPro.setLayout(new BorderLayout());
		
		Flist.setFixedCellWidth(150);
		
		dictPro.add(headPanel,BorderLayout.NORTH);
		dictPro.add(new JScrollPane(Flist),BorderLayout.WEST);
		dictPro.add(body,BorderLayout.CENTER);
		
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
			}
			
		});
		
		//listen the inputField
		inputField.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String getString=inputField.getText();
					
					bDArea.removeAll();
					yDArea.removeAll();
					bYArea.removeAll();
					
					//search getString
					final UnionWord uWord=link.serach(getString);
					
					JPanel BDUK = new JPanel() {
					    @Override
					    public void paint(Graphics g) {
					    	if(uWord.getWordBaidu().getPron_EN_UK()!=null)
					    		g.drawString(uWord.getWordBaidu().getPron_EN_UK(),10,50);
					    }
					};
					
					JPanel BDUS = new JPanel() {
					    @Override
					    public void paint(Graphics g) {
					    	if(uWord.getWordBaidu().getPron_EN_US()!=null)
					    		g.drawString(uWord.getWordBaidu().getPron_EN_US(),10,50);
					    }
					};	
					
					JPanel BDExplain=new JPanel(){
					    public void paint(Graphics g) {
					    	if(uWord.getWordBaidu().getExplain()!=null){
					    		for(int i=0;i<uWord.getWordBaidu().getExplain().size();i++)
					    			g.drawString(uWord.getWordBaidu().getExplain().get(i)+"\n",10,50);
					    	}
					    }
						
					}
					;
	
					JPanel BDHeader=new JPanel();
					BDHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
					BDHeader.add(BDUK);
					BDHeader.add(BDUS);
					
					JPanel BDBody=new JPanel();
					BDBody.setLayout(new BorderLayout());
					BDBody.add(BDHeader,BorderLayout.NORTH);
					BDBody.add(BDExplain,BorderLayout.CENTER);
					
					bDArea.add(BDBody);
					
					//====================YOUDAO
					JPanel YDUK = new JPanel() {
					    @Override
					    public void paint(Graphics g) {
					    	if(uWord.getWordYoudao().getPron_EN_UK()!=null)
					    		g.drawString(uWord.getWordYoudao().getPron_EN_UK(),10,50);
					    }
					};
					
					JPanel YDUS = new JPanel() {
					    @Override
					    public void paint(Graphics g) {
					    	if(uWord.getWordYoudao().getPron_EN_US()!=null)
					    		g.drawString(uWord.getWordYoudao().getPron_EN_US(),10,50);
					    }
					};	
					
					JPanel YDExplain=new JPanel(){
					    public void paint(Graphics g) {
					    	if(uWord.getWordYoudao().getExplain()!=null){
					    		for(int i=0;i<uWord.getWordYoudao().getExplain().size();i++)
					    			g.drawString(uWord.getWordYoudao().getExplain().get(i)+"\n",10,50);
					    	}
					    }
						
					};
	
					JPanel YDHeader=new JPanel();
					YDHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
					YDHeader.add(YDUK);
					YDHeader.add(YDUS);
					
					JPanel YDBody=new JPanel();
					YDBody.setLayout(new BorderLayout());
					YDBody.add(YDHeader,BorderLayout.NORTH);
					YDBody.add(YDExplain,BorderLayout.CENTER);
					
					yDArea.add(YDBody);
					
					
					//===============BING
					JPanel BYUK = new JPanel() {
					    @Override
					    public void paint(Graphics g) {
					    	if(uWord.getWordBing().getPron_EN_UK()!=null)
					    		g.drawString(uWord.getWordBing().getPron_EN_UK(),10,50);
					    }
					};
					
					JPanel BYUS = new JPanel() {
					    @Override
					    public void paint(Graphics g) {
					    	if(uWord.getWordBing().getPron_EN_US()!=null)
					    		g.drawString(uWord.getWordBing().getPron_EN_US(),10,50);
					    }
					};	
					
					JPanel BYExplain=new JPanel(){
					    public void paint(Graphics g) {
					    	if(uWord.getWordBing().getExplain()!=null){
					    		for(int i=0;i<uWord.getWordBing().getExplain().size();i++)
					    			g.drawString(uWord.getWordBing().getExplain().get(i)+"\n",10,50);
					    	}
					    }
						
					};
	
					JPanel BYHeader=new JPanel();
					BYHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
					BYHeader.add(BYUK);
					BYHeader.add(BYUS);
					
					JPanel BYBody=new JPanel();
					BYBody.setLayout(new BorderLayout());
					BYBody.add(BYHeader,BorderLayout.NORTH);
					BYBody.add(BYExplain,BorderLayout.CENTER);
					
					bYArea.add(BYBody);
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

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
				
				JPanel BDUK = new JPanel() {
				    @Override
				    public void paint(Graphics g) {
				    	if(uWord.getWordBaidu().getPron_EN_UK()!=null)
				    		g.drawString(uWord.getWordBaidu().getPron_EN_UK(),10,50);
				    }
				};
				
				JPanel BDUS = new JPanel() {
				    @Override
				    public void paint(Graphics g) {
				    	if(uWord.getWordBaidu().getPron_EN_US()!=null)
				    		g.drawString(uWord.getWordBaidu().getPron_EN_US(),10,50);
				    }
				};	
				
				JPanel BDExplain=new JPanel(){
				    public void paint(Graphics g) {
				    	if(uWord.getWordBaidu().getExplain()!=null){
				    		for(int i=0;i<uWord.getWordBaidu().getExplain().size();i++)
				    			g.drawString(uWord.getWordBaidu().getExplain().get(i)+"\n",10,50);
				    	}
				    }
					
				};

				JPanel BDHeader=new JPanel();
				BDHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
				BDHeader.add(BDUK);
				BDHeader.add(BDUS);
				
				JPanel BDBody=new JPanel();
				BDBody.setLayout(new BorderLayout());
				BDBody.add(BDHeader,BorderLayout.NORTH);
				BDBody.add(BDExplain,BorderLayout.CENTER);
				
				bDArea.add(BDBody);
				
				//====================YOUDAO
				JPanel YDUK = new JPanel() {
				    @Override
				    public void paint(Graphics g) {
				    	if(uWord.getWordYoudao().getPron_EN_UK()!=null)
				    		g.drawString(uWord.getWordYoudao().getPron_EN_UK(),10,50);
				    }
				};
				
				JPanel YDUS = new JPanel() {
				    @Override
				    public void paint(Graphics g) {
				    	if(uWord.getWordYoudao().getPron_EN_US()!=null)
				    		g.drawString(uWord.getWordYoudao().getPron_EN_US(),10,50);
				    }
				};	
				
				JPanel YDExplain=new JPanel(){
				    public void paint(Graphics g) {
				    	if(uWord.getWordYoudao().getExplain()!=null){
				    		for(int i=0;i<uWord.getWordYoudao().getExplain().size();i++)
				    			g.drawString(uWord.getWordYoudao().getExplain().get(i)+"\n",10,50);
				    	}
				    }
					
				};

				JPanel YDHeader=new JPanel();
				YDHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
				YDHeader.add(YDUK);
				YDHeader.add(YDUS);
				
				JPanel YDBody=new JPanel();
				YDBody.setLayout(new BorderLayout());
				YDBody.add(YDHeader,BorderLayout.NORTH);
				YDBody.add(YDExplain,BorderLayout.CENTER);
				
				yDArea.add(YDBody);
				
				
				//===============BING
				JPanel BYUK = new JPanel() {
				    @Override
				    public void paint(Graphics g) {
				    	if(uWord.getWordBing().getPron_EN_UK()!=null)
				    		g.drawString(uWord.getWordBing().getPron_EN_UK(),10,50);
				    }
				};
				
				JPanel BYUS = new JPanel() {
				    @Override
				    public void paint(Graphics g) {
				    	if(uWord.getWordBing().getPron_EN_US()!=null)
				    		g.drawString(uWord.getWordBing().getPron_EN_US(),10,50);
				    }
				};	
				
				JPanel BYExplain=new JPanel(){
				    public void paint(Graphics g) {
				    	if(uWord.getWordBing().getExplain()!=null){
				    		for(int i=0;i<uWord.getWordBing().getExplain().size();i++)
				    			g.drawString(uWord.getWordBing().getExplain().get(i)+"\n",10,50);
				    	}
				    }
					
				};

				JPanel BYHeader=new JPanel();
				BYHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
				BYHeader.add(BYUK);
				BYHeader.add(BYUS);
				
				JPanel BYBody=new JPanel();
				BYBody.setLayout(new BorderLayout());
				BYBody.add(BYHeader,BorderLayout.NORTH);
				BYBody.add(BYExplain,BorderLayout.CENTER);
				
				bYArea.add(BYBody);
			}
		});
		
		//click the add button
		Login.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(online == false){
					login lwin=new login();
					lwin.setTitle("DictPro-LogIn");
					lwin.setSize(250,200);
					lwin.setLocationRelativeTo(null);
					lwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					lwin.setVisible(true); 
				 }
			}
		});
		
		Signin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				signin swin=new signin();
				swin.setTitle("DictPro-SignIn");
				swin.setSize(250,400);
				swin.setLocationRelativeTo(null);
				swin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				swin.setVisible(true); 
			}
		});
	
		BDsend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if(online == true){
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
				 if(online == true){
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
				 if(online == true){
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
				 if(online == true){
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
				 if(online == true){
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
				 if(online == true){
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
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

