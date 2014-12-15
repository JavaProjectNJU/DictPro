package word;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable{
	private String word;//the word
	private String pron_EN_UK;//is a url for an mp3 file
	private String pron_EN_US;
	private String[] explain;//explains, the length is 3
	public Word(){
		word = null;
		pron_EN_UK = null;
		pron_EN_US = null;
		explain = new String[3];
	}
	
	public Word(String word){
		this.word = word;
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
	public String[] getExplain() {
		return explain;
	}
	public void setExplain(String explain,int type) {
		this.explain[type] = explain;
	}
	
	public void addExplain(String exp,int type){
		if(explain == null){
			explain = new String[3];
		}
	}
}
