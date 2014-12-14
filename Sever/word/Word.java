package word;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable{
	private String word;//the word
	private String pron_EN_UK;//is a url for an mp3 file
	private String pron_EN_US;
	private ArrayList<String> explain;//explains, the length is the counter
	public Word(){
		word = null;
		pron_EN_UK = null;
		pron_EN_US = null;
		explain = null;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPron_EN_UK() {
		return pron_EN_UK;
	}
	public void setPron_EN_UK(String pron_EN_UK) {
		this.pron_EN_UK = pron_EN_UK;
	}
	public String getPron_EN_US() {
		return pron_EN_US;
	}
	public void setPron_EN_US(String pron_EN_US) {
		this.pron_EN_US = pron_EN_US;
	}
	public ArrayList<String> getExplain() {
		return explain;
	}
	public void setExplain(ArrayList<String> explain) {
		this.explain = explain;
	}
	
	public void addExplain(String exp){
		if(explain == null){
			explain = new ArrayList<String>();
		}
		explain.add(exp);
	}
}
