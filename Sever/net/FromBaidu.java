package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import word.Word;

public class FromBaidu extends WordEngine{
	private static String url_prefix = "http://dict.baidu.com/s?wd=";//the word that want to search
	public FromBaidu(){
		//need to do nothing
	}
	
	protected Word analyze(String text_HTML){//annalyze the word from html text
		//System.out.print(text_HTML);
		if(text_HTML == null || text_HTML.length() == 0)
			return null;//text error 
		//String wo = null;
		//String pron_EN_UK = null;
		//String pron_EN_US = null;
		
		Pattern wordPattern = Pattern.compile("(?<=cidianData = \\{\\s{4}word: word,\\s{4}// )[\u0000-\uFFFF]*(?= \\+ netExplain \\+ zhExplain(\\s*)\\};(\\s*)window\\.cidianData = cidianData;)");
		Matcher m = wordPattern.matcher(text_HTML);
		String wordString = null;
		Word theWord = new Word();
		if(m.find()){
			wordString = m.group();
			Pattern word = Pattern.compile("(?<=head: \\{\"word\":\")[\u0000-\uFFFF]*(?=\",\"head\":\\{\"en\":\\{\"punc\":\")");
			Pattern pron_EN_UK = Pattern.compile("(?<=\"head\":\\{\"en\":\\{\"punc\":\")[^\"]*(?=\",\"mp3\":\")");
			Pattern pron_EN_US = Pattern.compile("(?<=\\},\"am\":\\{\"punc\":\")[^\"]*(?=\",\"mp3\":\")");
			Pattern explains = Pattern.compile("(?<=phonetic: phonetic,\\s{4}explain: \")[\u0000-\uFFFF]*(?=\")");
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
				String[] ex = m.group().split("<br />");
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
	protected URL getURL(String word) throws MalformedURLException {
		// TODO Auto-generated method stub
		return new URL(url_prefix + word.replaceAll(" ", "%20"));//闇镐紶杩涙潵鐨勫瓧绗︿覆杞寲涓哄悎娉曠殑URL鐮�
	}
	public static void main(String[] args){
		WordEngine baidu = new FromBaidu();
		baidu.search("give");
	}
}
