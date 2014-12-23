package dict.pro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import sun.security.util.Password;
import dict.net.LinkToServer;

public class signin extends JFrame{
	
	private boolean signEnable=true;
	private boolean issex=false;
	
	private File files = null; // files for the icon

	private BufferedImage image=null;
	
	public signin(final LinkToServer link){
		final JButton loadIconButton=new JButton("Browse"); //upload the icon
		final JTextField uid =new JTextField(20); // Input Field
		final JPasswordField psw0=new JPasswordField(20); // input the psw for the first time
		final JPasswordField psw1=new JPasswordField(20); // input the psw for the second time
		final JTextField email=new JTextField(20); // input the email
		

		
		final ButtonGroup sex=new ButtonGroup();
		JRadioButton male=new JRadioButton("Male",true);
		JRadioButton female=new JRadioButton("Female");
		sex.add(male);
		sex.add(female);
		
		JButton submitButton=new JButton("Submit");
		JButton cancelButton=new JButton("Cancel");
		
		
		JPanel iconPanel=new JPanel();
		final JLabel iconlocationLabel=new JLabel("文件位置 ：");
		iconPanel.setLayout(new BorderLayout(5,10));
		iconPanel.add(new JLabel(" Upload Your icon here :  "), BorderLayout.NORTH);
		iconPanel.add(loadIconButton, BorderLayout.CENTER);
		iconPanel.add(iconlocationLabel,BorderLayout.SOUTH);

	
		JPanel uidPanel=new JPanel();
		uidPanel.setLayout(new BorderLayout(5,10));
		uidPanel.add(new JLabel(" Enter Your UID: "), BorderLayout.NORTH);
		uidPanel.add(uid, BorderLayout.CENTER);
		
		JPanel pswPanel0=new JPanel();
		pswPanel0.setLayout(new BorderLayout(5,10));
		pswPanel0.add(new JLabel(" Enter Your PassWord: "), BorderLayout.NORTH);
		pswPanel0.add(psw0, BorderLayout.CENTER);
		
		JPanel pswPanel1=new JPanel();	
		pswPanel1.setLayout(new BorderLayout(5,10));
		pswPanel1.add(new JLabel(" Confirm Your PassWord: "), BorderLayout.NORTH);
		pswPanel1.add(psw1, BorderLayout.CENTER);	
		
		JPanel emailPanel=new JPanel();
		emailPanel.setLayout(new BorderLayout(5,10));
		emailPanel.add(new JLabel(" Enter Your Email: "), BorderLayout.NORTH);
		emailPanel.add(email, BorderLayout.CENTER);	
		
		JPanel sexPanel=new JPanel();
		sexPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,10));
		sexPanel.add(male);
		sexPanel.add(female);
		sexPanel.setBorder(new TitledBorder("Choose your sex:"));
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,10));
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		
		JPanel sinPanel=new JPanel();
		sinPanel.setLayout(new GridLayout(7,1));
		
		sinPanel.add(iconPanel,BorderLayout.WEST);
		sinPanel.add(uidPanel,BorderLayout.WEST);
		sinPanel.add(pswPanel0,BorderLayout.WEST);
		sinPanel.add(pswPanel1,BorderLayout.WEST);
		sinPanel.add(emailPanel,BorderLayout.WEST);
		sinPanel.add(sexPanel,BorderLayout.WEST);
		sinPanel.add(buttonPanel,BorderLayout.WEST);
		add(sinPanel);
		
		loadIconButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
				chooser.setFileFilter(filter); //set the type of the picture
				chooser.setMultiSelectionEnabled(false); //judge whether it can be multiply-selected
				int returnVal = chooser.showOpenDialog(getContentPane());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					files = chooser.getSelectedFile();
					try {
						image = ImageIO.read(files);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					iconlocationLabel.setText("文件位置 ：" + files.getAbsolutePath());
				}	
			}
		});
		
		
		male.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				issex=true;
			}
		});
		
		female.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				issex=false;
			}
		});
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String pswStr0= new String(psw0.getPassword());
				String pswStr1= new String(psw1.getPassword());

				
				if(pswStr0!=null && pswStr1!=null &&pswStr0.equals(pswStr1)){
					String uidStr=uid.getText();
					String emailStr=email.getText();
					if(uidStr!=null && emailStr!=null){
						link.register(uidStr, pswStr0, emailStr, issex,image);
						JOptionPane.showMessageDialog(null,"Success!", "sign in reminder!", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null,"fail!", "sign reminder!", JOptionPane.ERROR_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null,"fail!", "sign reminder!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
    

}