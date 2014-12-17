package word;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class WordCard {
	String srcImageFile;
	BufferedImage bi;
	int srcWidth;
	int srcHeight;
	BufferedImage buffImage;
	public WordCard(Word word,String uid1,String uid2) throws IOException {
		// TODO Auto-generated method stub
		srcImageFile = "C:\\Users\\YourDream\\Desktop\\card2.jpg";
		bi = ImageIO.read(new File(srcImageFile));
        srcWidth = bi.getHeight(); // 源图宽度
        srcHeight = bi.getWidth(); // 源图高度
        //System.out.println(srcWidth+ "," +srcHeight);
        
        uid1 = "Roy";
        uid2 = "Guo";
        //WordEngine baidu = new FromBing();
        //Word word = baidu.search("give");
        try {     	
        	buffImage =  ImageIO.read(new File(srcImageFile));
			Graphics g = buffImage.getGraphics();
			Font font = new Font("Console",Font.PLAIN,30);
			Font pronounce = new Font("Console",Font.PLAIN,20);
			Font explain = new Font("微软雅黑",Font.BOLD,16);
			g.setFont(font);
			g.setColor(new Color(0,0,0));
			g.drawString(word.getWord(), 100, 130);
			g.setFont(pronounce);
			if(word.getPron_EN_UK() != null &&word.getPron_EN_US() != null)
				g.drawString("["+word.getPron_EN_UK()+"]   ["+ word.getPron_EN_US()+ "]", 120, 160);
			g.setFont(explain);
			int height = 165;
			for(int i = 0; i < word.getExplain().size(); i ++)
			{
				g.drawString(word.getExplain().get(i), 120, height += 20);
			}
			
			g.drawString(uid1 +" 发给  " + uid2, 350, 50);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void display() //Display Wordard in a Frame
	{
		JFrame frame = new ImageViewerFrame(buffImage,srcWidth, srcHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

	public String getSrcImageFile() {
		return srcImageFile;
	}

	public void setSrcImageFile(String srcImageFile) {
		this.srcImageFile = srcImageFile;
	}

	public BufferedImage getBi() {
		return bi;
	}

	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}

	public int getSrcWidth() {
		return srcWidth;
	}

	public void setSrcWidth(int srcWidth) {
		this.srcWidth = srcWidth;
	}

	public int getSrcHeight() {
		return srcHeight;
	}

	public void setSrcHeight(int srcHeight) {
		this.srcHeight = srcHeight;
	}

	public BufferedImage getBuffImage() {
		return buffImage;
	}

	public void setBuffImage(BufferedImage buffImage) {
		this.buffImage = buffImage;
	}
}



class ImageViewerFrame extends JFrame{
    public ImageViewerFrame(BufferedImage name,int width,int height){
        setTitle("WordCard");
        setSize(height,width);
        label = new JLabel();
        add(label);
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu menu = new JMenu("File");
        menubar.add(menu);
        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        JMenuItem exitItem = new JMenuItem("Close");
        menu.add(exitItem);
        label.setIcon(new ImageIcon(name));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
    }
    private JLabel label;
}

