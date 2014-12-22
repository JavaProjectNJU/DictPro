package dict.net;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import System.UserInfo;
import word.Word;

public class RefreshList implements Runnable {

	private JList<UserInfo> list;
	private LinkToServer link;
	public RefreshList(final LinkToServer link, JList list){
		this.list = list;
		this.link = link;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			DefaultListModel<UserInfo> dlist = new DefaultListModel<UserInfo>();
			dlist.removeAllElements();
			
			System.out.println("try to refresh list $$$$$$$$$$$$$$$$$$$");
				//找到了新的单词列表,那么更新list中的单词
			if(link.isOnline()){
				ArrayList<UserInfo> online = link.getOnlineFriend();
				if(online != null){
					System.out.println("********************online is not null");
					for(UserInfo info:online){
						dlist.addElement(info);
						info.display();
						//System.out.println("refresh friend list : friend:" + info.getAccount());
					}
				}else{
					System.out.println("no friend online");
				}
				list.setModel(dlist);//更新列表中的元素
				list.repaint();
			}
	
			try {
				list.setModel(dlist);//更新列表中的元素
				list.repaint();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
