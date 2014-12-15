package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Using Type to discriminate meanings from different sources
//Type 1:Baidu
//Type 2:Bing
//Type 3:Youdao

public class DictionaryManager {
	public static int BAIDU = 0;
	public static int BING = 1;
	public static int YOUDAO = 2;
	public static boolean SetMeaning(String word,String meaning,int type)
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			/*String sql = "select Word from Dictionary;";
			ResultSet result = statement.executeQuery(sql);
			boolean existence = false;
			while(result.next()){
				if(word.equals(result.getString("Word")))
						existence = true;
			}
			if(existence == false)
				return false;*/
			if(type == 1)
				statement.execute("update Dictionary set Baidu = '"+ meaning +"' where Word = '"+word+"';");
			else if(type == 2)
				statement.execute("update Dictionary set Bing = '"+ meaning +"' where Word = '"+word+"';");
			else
				statement.execute("update Dictionary set Youdao = '"+ meaning +"' where Word = '"+word+"';");
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
	public static void OnlineSearchWord(String word) {
		
	}
	public static boolean SearchWord(String word) {	
		Statement statement;
		try {
			statement = DataBase.connect().createStatement();
			String sql = "select Word,Baidu,Bing,Youdao from Dictionary where Word='" + word + "';";
			ResultSet result = statement.executeQuery(sql);
			result.getString("Word");
			result.getString("YouDao");
			result.getString("Bing");
			result.getString("Baidu");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		//DictionaryManager.AddWord("insistence");
		//DictionaryManager.SetMeaning("insistence", "坚持", BAIDU);
		//DictionaryManager.AddPraise("haohao", "insistence", BAIDU);
	}
}
