package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import word.Word;

public class FromBing extends WordEngine{
	private static String url_prefix = "http://cn.bing.com/dict/search?q=";//the word that want to search
	public FromBing(){
		//need to do nothing
	}
	
	protected Word analyze(String text_HTML){//annalyze the word from html text
		//System.out.print(text_HTML);
		if(text_HTML == null || text_HTML.length() == 0)
			return null;//text error 
		//String wo = null;
		//String pron_EN_UK = null;
		//String pron_EN_US = null;
		
		Pattern wordPattern = Pattern.compile("(?<=<meta name=\"description\" content=\")[\u0000-\uFFFF]*(?=\"/><style type)");
		Matcher m = wordPattern.matcher(text_HTML);
		String wordString = null;
		Word theWord = new Word();
		if(m.find()){
			wordString = m.group();
			//System.out.println(wordString);
			Pattern word = Pattern.compile("(?<=蹇呭簲璇嶅吀涓烘偍鎻愪緵)[\u0000-\uFFFF]*(?=鐨勯噴涔夛紝)");
			Pattern pron_EN_UK = Pattern.compile("(?<=缇嶾\\[)[^\\[]*(?=\\]锛�");
			Pattern pron_EN_US = Pattern.compile("(?<=锛岃嫳\\[)[^\\[]*(?=\\]锛�");
			Pattern explains = Pattern.compile("(?<=锛�[^\\[]*(?=缃戠粶閲婁箟锛�");
			m = word.matcher(wordString);
			if(m.find())
				theWord.setWord(m.group());
			System.out.println(theWord.getWord());
			m = pron_EN_UK.matcher(wordString);
			if(m.find())
				theWord.setPron_EN_UK(m.group());
			System.out.println(theWord.getPron_EN_UK());
			m = pron_EN_US.matcher(wordString);
			if(m.find())
				theWord.setPron_EN_US(m.group());
			System.out.println(theWord.getPron_EN_US());
			m = explains.matcher(wordString);
			if(m.find()){
				//System.out.println(m.group());
				String[] ex = m.group().split("锛�");
				ArrayList<String> explain = new ArrayList<String>();
				for(String str:ex){
					System.out.println(str);
					if(str != null && str.length() != 0)
						explain.add(str);
				}
				theWord.setExplain(explain);
			}
		}else{
			
			return null;
		}
		//System.out.println( wordString);
	
		
		return theWord;
	}

	@Override
	protected URL getURL(String word) throws MalformedURLException{
		// TODO Auto-generated method stub
		return new URL(url_prefix + word.replaceAll(" ", "%20"));
	}
	public static void main(String[] args){
		WordEngine bing = new FromBing();
		bing.search("gggg");
	}
}
