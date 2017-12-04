package model;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String text;
	public static final String HELLO_MSG = "HELLO";
	public static final String BYE_MSG = "BYE";
	
	public Message() {
		
	}

	public Message(String nickName, String text) {
		this.nickName = nickName;
		this.text = text;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return nickName + ": " + text + "\n";
	}
}
