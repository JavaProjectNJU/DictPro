package net.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {  
    //定义成静态的好处  
    private  boolean first;  
  
    /** 
     * 初始化静态文件对象，并返回类对象 
     * @param file 文件对象，用于初始化静态文件对象 
     * @param out 输出流 
     * @return MyObjectOutputStream 
     * @throws SecurityException 
     * @throws IOException 
     */  
    public MyObjectOutputStream() throws SecurityException, IOException{
    	super();
    	
    	first = true;
    }

    public MyObjectOutputStream(OutputStream out) throws IOException{
    	super(out);
    	first = true;
    }
    @Override  
    protected void writeStreamHeader() throws IOException {  
        if (first) {  
            super.writeStreamHeader();  
            first = false;
        } else {  
            super.reset();  
        }  
  
    }  
  
  
}  