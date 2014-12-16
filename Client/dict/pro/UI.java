package dict.pro;

import javax.swing.JFrame;

public class UI {
	public static void main(String[] args){
		Client frame=new Client();
		
		frame.setTitle("DictPro");
		frame.setSize(400,250);
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		}
}
