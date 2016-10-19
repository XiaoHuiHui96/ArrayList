package com.transientTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * transient属性的例子
 * 被标记为transient的属性在对象被序列化的时候不会被保存。
 * @author Administrator
 *
 */
public class test implements Serializable  {

    private static final long serialVersionUID=996890129747019948L;
    
    private String name;
    
    private transient String pwd;
	
    public test(String name,String pwd){
    	this.name=name;
    	this.pwd=pwd;
    }
    
    public String toString(){
		return "name=" + name + ", psw=" + pwd;  
    	
    }
    
	public static void main(String[] args) {
		test userInfo=new test("张三","123123");
		System.out.println(userInfo);
		 // 序列化，被设置为transient的属性没有被序列化
		ObjectOutputStream o;
		try {
			o = new ObjectOutputStream(new FileOutputStream("test.out"));
			o.writeObject(userInfo);
			o.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try{
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("test.out"));
			test readUserInfo=(test) in.readObject();
			System.out.println(readUserInfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
