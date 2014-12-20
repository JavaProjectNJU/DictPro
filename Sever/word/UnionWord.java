package word;

import java.io.Serializable;

public class UnionWord implements Serializable{
	String wordstr;
	Word wordBaidu;
	Word wordYoudao;
	Word wordBing;
	int pariseBaidu;
	int pariseYoudao;
	public int getPariseBaidu() {
		return pariseBaidu;
	}
	public void setPariseBaidu(int pariseBaidu) {
		this.pariseBaidu = pariseBaidu;
	}
	public int getPariseYoudao() {
		return pariseYoudao;
	}
	public void setPariseYoudao(int pariseYoudao) {
		this.pariseYoudao = pariseYoudao;
	}
	public int getPariseBing() {
		return pariseBing;
	}
	public void setPariseBing(int pariseBing) {
		this.pariseBing = pariseBing;
	}
	int pariseBing;
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
