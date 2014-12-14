package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Using Type to discriminate meanings from different sources
//Type 1:Baidu
//Type 2:Bing
//Type 3:Youdao

public class DictionaryManager {
	public static boolean SetMeaning(String word,String[] meaning,int type)
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			String sql = "select Word from Dictionary;";
			ResultSet result = statement.executeQuery(sql);
			boolean existence = false;
			while(result.next()){
				if(word.equals(result.getString("Word")))
						existence = true;
			}
			if(existence == false)
				return false;
			if(type == 1)
				statement.execute("update Dictionary set Baidu = '"+ meaning.toString() +"' where Word = '"+word+"';");
			else if(type == 2)
				statement.execute("update Dictionary set Bing = '"+ meaning.toString() +"' where Word = '"+word+"';");
			else
				statement.execute("update Dictionary set Youdao = '"+ meaning.toString() +"' where Word = '"+word+"';");
			change = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
	public static boolean AddWord(String word,String[] meaning, int type) 
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			String sql = "insert into Dictionary(Word) values('"
					+ word+"');";
			statement.execute(sql);
			change = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
	public static boolean AddPraise(String word)
	{
		boolean change = false;
		try {
			Statement statement = DataBase.connect().createStatement();
			String sql = "insert into Dictionary(Word,Baidu,Bing,Youdao) values('"
					+ word +"null,null,null";
			statement.execute(sql);
			change = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}
}
