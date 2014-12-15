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

import DataBase.DictionaryManager;
import word.Word;

public class FromYoudao extends WordEngine{
	private static String url_prefix = "http://dict.youdao.com/search?q=";//the word that want to search
	public FromYoudao(){
		//need to do nothing
	}
	
	protected Word analyze(String text_HTML){//annalyze the word from html text
		//System.out.print(text_HTML);
		if(text_HTML == null || text_HTML.length() == 0)
			return null;//text error 
		//String wo = null;
		//String pron_EN_UK = null;
		//String pron_EN_US = null;
				
		Pattern wordPattern = Pattern.compile("(?<=<div id=\"phrsListTab\" class=\"trans-wrapper clearfix\">)[\u0000-\uFFFF]*(?=</div>\\s*</div>\\s*<div id=\"webTrans\" class=\"trans-wrapper trans-tab\">)");
		Matcher m = wordPattern.matcher(text_HTML);
		String wordString = null;
		Word theWord = new Word();
		if(m.find()){
			wordString = m.group();
			//System.out.println(wordString);
			Pattern word = Pattern.compile("(?<=<span class=\"keyword\">)[^<]*(?=</span>)"); 
			Pattern pron_EN_UK = Pattern.compile("(?<=<span class=\"pronounce\">英\\s{37}<span class=\"phonetic\">)[^<]*(?=</span>)");
			Pattern pron_EN_US = Pattern.compile("(?<=<span class=\"pronounce\">美\\s{37}<span class=\"phonetic\">)[^<]*(?=</span>)");
			Pattern explains = Pattern.compile("(?<=<ul>)[\u0000-\uFFFF]*(?=</ul>)");
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
			//	System.out.println(m.group());
				String ex = m.group();
				Pattern pex = Pattern.compile("(?<=<li>)[^<]*(?=</li>)");
				Matcher mathcer = pex.matcher(ex);
				ArrayList<String> explain = new ArrayList<String>();
				while(mathcer.find()){
					
					System.out.println(mathcer.group());
					explain.add(mathcer.group());
				}
				theWord.setExplain(explain.toString(),DictionaryManager.YOUDAO);
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
		return new URL(url_prefix + word.replaceAll(" ", "%20") + "&keyfrom=dict.index");
	}
	public static void main(String[] args){
		WordEngine youdao = new FromYoudao();
		youdao.search("give");
	}
}
