package DataBase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Blob;

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
	public static boolean SetMeaning(String word,Word meaning,int type)
	{
		boolean change = false;
		try {
			PreparedStatement statement;
			if(type == BAIDU)
				statement = DataBase.connect().prepareStatement("update Dictionary set Baidu = (?) where Word = '"+word+"';");
			else if(type == BING)
				statement = DataBase.connect().prepareStatement("update Dictionary set Bing = (?) where Word = '"+word+"';");
			else
				statement = DataBase.connect().prepareStatement("update Dictionary set Youdao = (?) where Word = '"+word+"';");
			statement.setObject(1, meaning);
			statement.execute();
			change = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
	
	public static boolean AddWord(String word) 
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			String sql = "insert into Dictionary(Word,NumZanBaidu,NumZanYoudao,NumZanBing) values('"
					+ word +"',0,0,0);";
			//System.out.println(sql);
			statement.execute(sql);
			change = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
	
	public static boolean AddPraise(String username, String word,int type)
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			String sql = null;
			if(type == BAIDU)
				sql = "insert into BaiduPraise(username,Word) values('"+ username + "','"+ word + "');";
			else if(type == BING)
				sql = "insert into BingPraise(username,Word) values('"+ username + "','"+ word + "');";
			else 
				sql = "insert into YouDaoPraise(username,Word) values('"+ username + "','"+ word + "');";
			change = statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
	
	public static boolean DelPraise(String username, String word,int type)
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			String sql = null;
			if(type == BAIDU)
				sql = "delete from BaiduPraise where username = '"+ username + "' and word = '"+ word + "';";
			else if(type == BING)
				sql = "delete from BingPraise where username = '"+ username + "' and word = '"+ word + "';";
			else 
				sql = "delete from YouDaoPraise where username = '"+ username + "' and word = '"+ word + "';";
			change = statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
	
	public static UnionWord SearchWord(String word) {	
		Statement statement;
		UnionWord unionWord = new UnionWord();
		try {
			statement = DataBase.connect().createStatement();
			String sql ="select Baidu from Dictionary where Word = '"+ word +"';";
			ResultSet result = statement.executeQuery(sql);
			if(result.next()){		
				Blob inblobBaidu = result.getBlob("Baidu");
				System.out.println(inblobBaidu);
				InputStream isBaidu = inblobBaidu.getBinaryStream();
				BufferedInputStream inputBaidu = new BufferedInputStream(isBaidu);
				
				byte[] buff = new byte[(int)inblobBaidu.length()];
				while(-1 != (inputBaidu.read(buff, 0, buff.length)));
				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
				Word baidu = (Word)in.readObject();
				System.out.println(baidu.getWord());
				unionWord.setwordBaidu(baidu);
			}
			//result.getBlob("Bing");
			//result.getBlob("Baidu");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return unionWord;
	}
	
	public static void main(String[] args)
	{
		Word word = new Word();
		word.setWord("China");
		//DictionaryManager.AddWord(word.getWord());
		DictionaryManager.SetMeaning(word.getWord(),word,BAIDU);
		DictionaryManager.SearchWord("China");
		//DictionaryManager.AddPraise("haohao", "insistence", BAIDU);
	}
}
