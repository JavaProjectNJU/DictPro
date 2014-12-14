package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import word.Word;

public abstract class WordEngine {
	protected abstract Word analyze(String HTML);
	protected abstract URL getURL(String word) throws MalformedURLException;
	public Word search(String word){
		InputStream is = null;
		try {
			URL url = getURL(word);
			is = url.openStream();
			BufferedReader infile = new BufferedReader(new InputStreamReader(is));
			String str = null;
			StringBuilder text = new StringBuilder();
			while((str = infile.readLine()) != null){
				text.append("\n" + str);
			}
			
			return analyze(text.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(is != null)
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
