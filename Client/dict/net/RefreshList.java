package dict.net;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import System.UserInfo;
import word.Word;

public class RefreshList implements Runnable {

	private JList list;
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
			if(link.isOnline()){
				ArrayList<UserInfo> online = link.getOnlineFriend();
				//找到了新的单词列表,那么更新list中的单词
			
				
				if(online != null){
					for(UserInfo info:online){
						dlist.addElement(info);
						System.out.println("refresh friend list : friend:" + info.getAccount());
					}
				}else{
					System.out.println("no friend online");
				}
				list.setModel(dlist);//更新列表中的元素
				list.repaint();
			}else{
				list.setModel(dlist);//更新列表中的元素
				list.repaint();
				System.out.println("refresh friend -----self not online");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
