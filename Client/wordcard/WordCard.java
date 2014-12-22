package wordcard;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import net.FromBaidu;
import net.FromBing;
import net.WordEngine;

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

import word.Word;
import net.FromBing;
import net.WordEngine;
import net.Message.Message;

public class WordCard {
	String srcImageFile;
	BufferedImage bi;
	int srcWidth;
	int srcHeight;
	BufferedImage buffImage;
	static final Font font = new Font("Consola",Font.PLAIN,30);
	static final Font pronounce = new Font("Consola",Font.PLAIN,20);
	static final Font explain = new Font("微软雅黑",Font.BOLD,16);
	public WordCard(byte[] image){
		buffImage = Message.bytesToImage(image);
		srcWidth = buffImage.getHeight();
		srcHeight = buffImage.getWidth();
	}
	public WordCard(Word word,String uid1,String uid2){
		// TODO Auto-generated method stub
		srcImageFile = "Client\\card2.jpg";
		try {
			bi = ImageIO.read(new File(srcImageFile));
		} catch (IOException e1) {
			System.out.println("File Not Found!");
		}
        srcWidth = bi.getHeight(); // 源图宽度
        srcHeight = bi.getWidth(); // 源图高度
        //System.out.println(srcWidth+ "," +srcHeight);
        
        //uid1 = "Roy";
       // uid2 = "Guo";
        //WordEngine baidu = new FromBing();
        //Word word = baidu.search("give");
        try {     	
        	buffImage =  ImageIO.read(new File(srcImageFile));
			Graphics g = buffImage.getGraphics();
			g.setFont(font);
			g.setColor(new Color(0,0,0));
			g.drawString(word.getWord(), 100, 130);
			g.setFont(pronounce);
			if(word.getPron_EN_UK() != null &&word.getPron_EN_US() != null)
				g.drawString(" UK : ["+word.getPron_EN_UK()+"]   US : ["+ word.getPron_EN_US()+ "]", 120, 160);
			g.setFont(explain);
			int height = 165;
			for(int i = 0; i < word.getExplain().size(); i ++)
			{
				g.drawString(word.getExplain().get(i), 120, height += 20);
			}
			
			g.drawString(uid1 +" 发给  " + uid2, 450, 50);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void display() //Display Wordard in a Frame
	{
		JFrame frame = new ImageViewerFrame(buffImage,srcWidth, srcHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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
	
	public static void main(String[] args)
	{
		WordEngine baidu = new FromBing();
        Word word = baidu.search("give");
		WordCard wCard = new WordCard(word,"Roy","Guo");

		wCard.display();
	}
}


class ImageViewerFrame extends JFrame{
    public ImageViewerFrame(BufferedImage name,int width,int height){
        setTitle("WordCard");
        setSize(height,width);
        label = new JLabel();
        add(label);
        label.setIcon(new ImageIcon(name));
    }
    private JLabel label;
}

