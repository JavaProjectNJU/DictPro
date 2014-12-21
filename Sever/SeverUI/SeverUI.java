package SeverUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.*;     

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import DataBase.UserManager;
import System.Sever;
import System.UserInfo;
import net.Message.Message;

import java.awt.*;
class LayoutUtil{
    public static void add(Container c,Component comp,int fill,int anchor,int weightx,int weighty, int x,int y,int width,int height){	
	    GridBagConstraints constraints=new GridBagConstraints();
		constraints.fill=fill;
		constraints.anchor=anchor;
		constraints.weightx=weightx;
		constraints.weighty=weighty;
		constraints.gridx=x;
		constraints.gridy=y;
		constraints.gridwidth=width;
		constraints.gridheight=height;
		c.add(comp,constraints);
	} 
	public static void add(Container c,Component comp,int fill,int anchor,int weightx,int weighty,int x,int y,int width,int height,Insets insets){	
	    GridBagConstraints constraints=new GridBagConstraints();
		constraints.insets=insets;
		constraints.fill=fill;
		constraints.anchor=anchor;
		constraints.weightx=weightx;
		constraints.weighty=weighty;
		constraints.gridx=x;
		constraints.gridy=y;
		constraints.gridwidth=width;
		constraints.gridheight=height;
		c.add(comp,constraints);
	}
}

class SeverUI extends JFrame {

	Sever server;

	/***************************** 以下组件 ********************************/
	Container contentPane; // 顶层容器

	JScrollPane PubMsgScroll; // 显示待发送公告的滚动条
	JTextArea PubMsgText; // 显示待发送公告的文本框

	JScrollPane SendedPubMsgScroll; // 显示已发送公告的滚动条
	JTextArea SendedPubMsgText; // 显示已发送公告的文本框

	JLabel OnlineState; // 显示当前连接数
	JLabel IP_Port; // 显示服务器端地址信息

	Vector OnlineUsers = new Vector();
	Vector OfflineUsers = new Vector();

	JScrollPane OnlineScroll; // 显示在线用户的列表
	JList<String> OnlineList;
	DefaultListModel JListOnlineModel;

	JScrollPane OfflineScroll; // 显示不在线用户的列表
	JList<String> OfflineList;
	DefaultListModel JListOfflineModel;

	JButton dispatchBtn; // 发送按钮
	JButton freshUserBtn; // 发送按钮

	/***************************** 以上组件 ********************************/
	public SeverUI(Sever server) {
		this();
		this.server = server;

		// 显示服务器端的IP和端口port
		// IP_Port.setText(server.getIPAddr()+":"+Integer.toString(server.getServerPort()));
		IP_Port.setText("192.168.0.1: 8000");
		// 这里有破坏界面与代码分开原则的嫌疑

	}// public ServerFrame

	public SeverUI() {
		super("Server of Communication");
		// 创建容器
		contentPane = this.getContentPane();
		contentPane.setLayout(new GridBagLayout());

		PubMsgText = new JTextArea();
		PubMsgText.setLineWrap(true);
		PubMsgText.setBorder(BorderFactory
				.createTitledBorder("Public Message be to Users:"));
		PubMsgScroll = new JScrollPane(PubMsgText);
		// PubMsgScroll.setBorder(BorderFactory.createTitledBorder("Public Message be to Users:"));

		SendedPubMsgText = new JTextArea();
		SendedPubMsgText.setEditable(false);
		SendedPubMsgText.setBorder(BorderFactory
				.createTitledBorder("Public Message has been send to Users:"));
		SendedPubMsgScroll = new JScrollPane(SendedPubMsgText);

		OnlineState = new JLabel("0                                          ");
		OnlineState.setBorder(BorderFactory
				.createTitledBorder("Actived Connections:"));
		// OnlineState.setIcon(new ImageIcon(".\\pics\\lzpaul12.jpg"));
		OnlineState.setVerticalTextPosition(SwingConstants.BOTTOM);

		IP_Port = new JLabel(); // "127.0.0.0:3000"
		IP_Port.setBorder(BorderFactory.createTitledBorder("Cur IP+Port:"));
		// IP_Port.setIcon(new ImageIcon(".\\pics\\lzpaul21.jpg"));
		IP_Port.setVerticalTextPosition(SwingConstants.BOTTOM);

		ArrayList<UserInfo> onlineUserInfo = UserManager.getOnlineUser();
		String[] onlineuser = new String[onlineUserInfo.size()];
		ArrayList<String> onlineString = new ArrayList<String>();
		for(int i = 0;i < onlineuser.length; i ++)
		{
			onlineuser[i] = onlineUserInfo.get(i).getAccount();
			onlineString.add(onlineuser[i]);
		}
		OnlineList = new JList(onlineuser);
		OnlineList.setBorder(BorderFactory.createTitledBorder("Online Users:"));
		// OnlineList.setCellRenderer(new OnlineIconCellRenderer());
		OnlineScroll = new JScrollPane(OnlineList);
		
		ArrayList<UserInfo> alllineUserInfo = UserManager.getUserList();
		final String[] offlineuser = new String[alllineUserInfo.size()];
		int j = 0;
		for(int i = 0;i < offlineuser.length; i ++)
		{
			if(!onlineString.contains(alllineUserInfo.get(i).getAccount()))
			{	
				offlineuser[j] = alllineUserInfo.get(i).getAccount();
				j ++;
			}
		}
			
		OfflineList = new JList<String>(offlineuser);
		OfflineList.setBorder(BorderFactory
				.createTitledBorder("Offline Users:"));
		// OfflineList.setCellRenderer(new OfflineIconCellRenderer());
		OfflineScroll = new JScrollPane(OfflineList);

		OfflineList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index;
				UserInfo tempuser;
				if (e.getClickCount() == 2) {
					int indexofSelect = OfflineList.getSelectedIndex();
					String searchString = offlineuser[indexofSelect];
					UserInfoWindow subFrame = new UserInfoWindow(UserManager.getUserInfo(searchString));
					subFrame.setSize(300, 400);
					subFrame.setTitle("About Dictionary");
					subFrame.setLocationRelativeTo(null);//Center the Frame
					subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					subFrame.setVisible(true);
				}// if(e.getClickCount()==2)

			}

		});

		dispatchBtn = new JButton("Dispatch");
		dispatchBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		freshUserBtn  = new JButton("Fresh User");
		dispatchBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		freshUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<UserInfo> onlineUserInfo = UserManager.getOnlineUser();
				String[] onlineuser = new String[onlineUserInfo.size()];
				ArrayList<String> onlineString = new ArrayList<String>();
				for(int i = 0;i < onlineuser.length; i ++)
				{
					onlineuser[i] = onlineUserInfo.get(i).getAccount();
					onlineString.add(onlineuser[i]);
				}
				
				OnlineList.setListData(onlineuser);
				
				ArrayList<UserInfo> alllineUserInfo = UserManager.getUserList();
				String[] offlineuser = new String[alllineUserInfo.size()];
				int j = 0;
				for(int i = 0;i < offlineuser.length; i ++)
				{
					if(!onlineString.contains(alllineUserInfo.get(i).getAccount()))
					{	
						offlineuser[j] = alllineUserInfo.get(i).getAccount();
						j ++;
					}
				}
					
				OfflineList.setListData(offlineuser);
			}// public void actionPerformed(ActionEvent e)
		});
		
		dispatchBtn.addActionListener(new ActionListener() {
			Message tempPubMsg;

			public void actionPerformed(ActionEvent e) {
				ArrayList<UserInfo>onlineFriend = new ArrayList<UserInfo>();
				ArrayList<UserInfo> temp = UserManager.getOnlineUser();
				for(int i = 0;i < temp.size();i ++)
				{
					temp.get(i).display();
					//System.out.println(temp.get(i).getAccount()+" "+ this.account +" "+ temp.get(i).isFriend(this.account));
					System.out.println("Hello");
					if(UserManager.friendJudge("zhangry", temp.get(i).getAccount()))
					{	
						onlineFriend.add(temp.get(i));
						System.out.println("Is Friend!!!");
					}
				}
			}// public void actionPerformed(ActionEvent e)
		});

		LayoutUtil.add(contentPane, SendedPubMsgScroll,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1, 0, 0,
				4, 4);// 5,4);
		LayoutUtil.add(contentPane, PubMsgScroll, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1, 1, 0, 4, 4, 4);// 5,4);

		LayoutUtil.add(contentPane, OnlineScroll, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1, 1, 4, 0, 3, 3);
		LayoutUtil.add(contentPane, OfflineScroll, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1, 1, 4, 4, 3, 3);

		LayoutUtil.add(contentPane, OnlineState, GridBagConstraints.NONE,
				GridBagConstraints.CENTER, 1, 1, 0, 8, 1, 1);
		LayoutUtil.add(contentPane, IP_Port, GridBagConstraints.NONE,
				GridBagConstraints.CENTER, 1, 1, 2, 8, 1, 1);
		LayoutUtil.add(contentPane, dispatchBtn, GridBagConstraints.NONE,
				GridBagConstraints.CENTER, 1, 1, 4, 8, 1, 1);
		LayoutUtil.add(contentPane, freshUserBtn, GridBagConstraints.NONE,
				GridBagConstraints.CENTER, 1, 1, 6, 8, 1, 1);

		// 窗体居中设置
		setSize(500, 500);
		setResizable(false);
		// setVisible(true);

		// CurConNum(0);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}// public void windowClosing(WindowEvent e)
		});// addWindowListener(new WindowAdapter(){

	}// public ServerFrame()

	public void CurConNum(int num) {

		OnlineState.setText(String.valueOf(num)
				+ "                                           ");
		RfreshList();

	}// public void CurConNum(int num)//将连接数显示出来

	public void RfreshList() {

		
	}

	public static void main(String args[]) {
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
		new SeverUI().setVisible(true);
		Sever sever = new Sever();
		System.out.println("server is starting");
		sever.start();
		
	}// main()

}// class ServerFrame
