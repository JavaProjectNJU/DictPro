package word;

import java.io.Serializable;

public class UnionWord implements Serializable{
	String wordstr;
	Word wordBaidu;
	Word wordYoudao;
	Word wordBing;
	public void setwordBaidu(Word baidu)
	{
		wordBaidu = baidu;
	}
	public void setwordBing(Word bing) {
		// TODO Auto-generated method stub
		wordBing = bing;
	}
	public String getWordstr() {
		return wordstr;
	}
	public void setWordstr(String wordstr) {
		this.wordstr = wordstr;
	}
	public Word getWordBaidu() {
		return wordBaidu;
	}
	public Word getWordYoudao() {
		return wordYoudao;
	}
	public Word getWordBing() {
		return wordBing;
	}
	public void setwordYouDao(Word youdao) {
		// TODO Auto-generated method stub
		wordYoudao = youdao;
	}
}
