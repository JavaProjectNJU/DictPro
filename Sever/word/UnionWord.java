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
}
