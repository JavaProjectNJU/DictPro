package DataBase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Blob;

import javax.smartcardio.CardTerminals;
import javax.swing.JOptionPane;

import net.FromBaidu;
import net.FromBing;
import net.FromYoudao;
import net.WordEngine;

import com.sun.net.httpserver.Authenticator.Success;

import word.UnionWord;
import word.Word;

//Using Type to discriminate meanings from different sources
//Type 1:Baidu
//Type 2:Bing
//Type 3:Youdao

public class DictionaryManager {
	public static int BAIDU = 0;
	public static int BING = 1;
	public static int YOUDAO = 2;
	
	public static boolean AddWordandMeaning(UnionWord uword)
	{
		boolean success1 = DictionaryManager.AddWord(uword.getWordstr());
		boolean success2 = DictionaryManager.SetMeaning(uword.getWordBaidu().getWord(),uword.getWordBaidu(),BAIDU);
		boolean success3 = DictionaryManager.SetMeaning(uword.getWordYoudao().getWord(),uword.getWordYoudao(),YOUDAO);
		boolean success4 = DictionaryManager.SetMeaning(uword.getWordBing().getWord(),uword.getWordBing(),BING);
		return success1 & success2 & success3 & success4;
	}
	
	public static boolean SetMeaning(String word,Word meaning,int type)
	{
		boolean change = false;
		Connection conn = null;
		try {
			PreparedStatement statement;
			conn = DataBase.connect();
			if(type == BAIDU)
				statement = conn.prepareStatement("update Dictionary set Baidu = (?) where Word = '"+word+"';");
			else if(type == BING)
				statement = conn.prepareStatement("update Dictionary set Bing = (?) where Word = '"+word+"';");
			else
				statement = conn.prepareStatement("update Dictionary set Youdao = (?) where Word = '"+word+"';");
			statement.setObject(1, meaning);
			statement.execute();
			change = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	
	public static boolean AddWord(String word) //Add New Word into Dictionary
	{
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into Dictionary(Word,NumZanBaidu,NumZanYoudao,NumZanBing) values('"
					+ word +"',0,0,0);";
			//System.out.println(sql);
			statement.execute(sql);
			change = true;
		} catch (SQLException e) {
			System.out.println("Word Exists!");
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	
	@SuppressWarnings("finally")
	public static boolean AddPraise(String username, String word,int type)
	{
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = null;
			if(type == BAIDU)
				sql = "insert into BaiduPraise(username,Word) values('"+ username + "','"+ word + "');";
			else if(type == BING)
				sql = "insert into BingPraise(username,Word) values('"+ username + "','"+ word + "');";
			else 
				sql = "insert into YouDaoPraise(username,Word) values('"+ username + "','"+ word + "');";
			change = statement.execute(sql);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "你已经点过赞了，不要重复点击!", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			DataBase.close(conn);
			return change;
		}
		
	}
	
	@SuppressWarnings("finally")
	public static boolean DelPraise(String username, String word,int type)
	{
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = null;
			if(type == BAIDU)
				sql = "delete from BaiduPraise where username = '"+ username + "' and word = '"+ word + "';";
			else if(type == BING)
				sql = "delete from BingPraise where username = '"+ username + "' and word = '"+ word + "';";
			else 
				sql = "delete from YouDaoPraise where username = '"+ username + "' and word = '"+ word + "';";
			change = statement.execute(sql);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "你还没点过赞", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			DataBase.close(conn);
			return change;
		}
	}
	
	public static UnionWord SearchWord(String word) {	
		Statement statement;
		UnionWord unionWord = new UnionWord();
		Connection conn = null;
		boolean exist = false;
		try {
			conn = DataBase.connect();
			statement = conn.createStatement();
			String sql ="select Baidu,Bing,YouDao,NumZanBaidu,NumZanYoudao,NumZanBing from Dictionary where Word = '"+ word +"';";
			ResultSet result = statement.executeQuery(sql);
			if(result.next()){	
				exist = true;
				Blob inblobBaidu = result.getBlob("Baidu");
				//System.out.println(inblobBaidu);
				InputStream isBaidu = inblobBaidu.getBinaryStream();
				BufferedInputStream inputBaidu = new BufferedInputStream(isBaidu);
				
				byte[] buff = new byte[(int)inblobBaidu.length()];
				while(-1 != (inputBaidu.read(buff, 0, buff.length)));
				ObjectInputStream inBaidu = new ObjectInputStream(new ByteArrayInputStream(buff));
				Word baidu = (Word)inBaidu.readObject();
				System.out.println(baidu.getWord());
				unionWord.setwordBaidu(baidu);

				Blob inblobBing = result.getBlob("Bing");
				//System.out.println(inblobBing);
				InputStream isBing = inblobBing.getBinaryStream();
				BufferedInputStream inputBing = new BufferedInputStream(isBing);
				
				buff = new byte[(int)inblobBing.length()];
				while(-1 != (inputBing.read(buff, 0, buff.length)));
				ObjectInputStream inBing = new ObjectInputStream(new ByteArrayInputStream(buff));
				Word bing = (Word)inBing.readObject();
				System.out.println(bing.getWord());
				unionWord.setwordBing(bing);
				
				Blob inblobYouDao = result.getBlob("YouDao");
				//System.out.println(inblobYouDao);
				InputStream isYouDao = inblobYouDao.getBinaryStream();
				BufferedInputStream inputYouDao = new BufferedInputStream(isYouDao);
				
				buff = new byte[(int)inblobYouDao.length()];
				while(-1 != (inputYouDao.read(buff, 0, buff.length)));
				ObjectInputStream inYouDao = new ObjectInputStream(new ByteArrayInputStream(buff));
				Word youdao = (Word)inYouDao.readObject();
				System.out.println(youdao.getWord());
				unionWord.setwordYouDao(youdao);
				unionWord.setPariseBaidu(result.getInt("NumZanBaidu"));
				unionWord.setPariseBing(result.getInt("NumZanBing"));
				unionWord.setPariseYoudao(result.getInt("NumZanYoudao"));
				
			}
			//result.getBlob("Bing");
			//result.getBlob("Baidu");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "未知的错误！", "系统信息", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DataBase.close(conn);
		}
		if(!exist){//Serch Online
			WordEngine baidusearch = new FromBaidu();
	        WordEngine bingsearch = new FromBing();
	        WordEngine youdaosearch = new FromYoudao();	        
			Word baidu = baidusearch.search(word);
		    Word bing = bingsearch.search(word);
		    Word youdao = youdaosearch.search(word);
		    unionWord.setWordstr(word);
			unionWord.setwordBaidu(baidu);
			unionWord.setwordBing(bing);
			unionWord.setwordYouDao(youdao);
			AddWordandMeaning(unionWord);//Save into the database
		}
		return unionWord;
	}
	
	public static boolean saveWordCard(String sender,String receiver,byte[] img){
		boolean change = false;
		try {
			PreparedStatement statement;
			Connection conn = DataBase.connect();
			statement = conn.prepareStatement("insert into WordCard(sender,receiver,image) values((?),(?),(?))");
			statement.setString(1, sender);
			statement.setString(2, receiver);
			statement.setObject(3, img);
			statement.execute();
			DataBase.close(conn);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "未知的错误，存储单词卡失败", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		return change;
	}
	
	public static boolean saveUserImage(String user,byte[] img){
		boolean change = false;
		try {
			PreparedStatement statement;
			Connection conn = DataBase.connect();
			statement = conn.prepareStatement("update Dictionary set UserImg = (?) where user = '"+user+"';");
			statement.setObject(1, img);
			statement.execute();
			DataBase.close(conn);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "未知的错误，存储单词卡失败", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		return change;
	}
	
	public static byte[] getWordCard(String uid){
		boolean change = false;
		byte[] card = null;
		try {
			PreparedStatement statement;
			Connection conn = DataBase.connect();
			statement = conn.prepareStatement("insert into WordCard(sender,receiver,image) values((?),(?),(?))");
			//statement.setString(1, sender);
			//statement.setString(2, receiver);
			//statement.setObject(3, img);
			statement.execute();
			DataBase.close(conn);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "未知的错误，取出单词卡失败", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		return card;
	}
	
	public static void main(String[] args){
		Word word = new Word();
		word.setWord("China");
		//DictionaryManager.AddWord(word.getWord());
		DictionaryManager.SetMeaning(word.getWord(),word,BAIDU);
		DictionaryManager.SetMeaning(word.getWord(),word,YOUDAO);
		DictionaryManager.SetMeaning(word.getWord(),word,BING);
		DictionaryManager.SearchWord("China");
		//DictionaryManager.AddPraise("haohao", "insistence", BAIDU);
	}
}
