package model;

import java.io.Serializable;
/**
 * A class representing the messages to send to the client and
 * to receive from the client.
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see java.io.Serializable
 */
public class Message implements Serializable {
	
	/**
	 * A unique serial version identifier.
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String constant for server disconnection message.
	 */
	public final static String DISCON_MSG = "Server disconnected";
	/**
	 * String constant for server nickname in messages.
	 */
	public final static String SERVER_NICK = "SERVER";
	/**
	 * Nickname for messages.
	 */
	private String nickName;
	/**
	 * Message body.
	 */
	private String text;
	/**
	 * String constant for the message when connection starts.
	 */
	public static final String HELLO_MSG = "HELLO";
	/**
	 * String constant for the message when connection ends.
	 */
	public static final String BYE_MSG = "BYE";
	
	/**
	 * Constructs a new <code>Message</code> object.
	 */
	public Message() {
		
	}

	/**
	 * Constructs a new <code>Message</code> object and
	 * initializes <code>nickName</code> and 
	 * <code>text</code> fields.
	 * 
	 * @param user nickname.
	 * @param message body text.
	 */
	public Message(String nickName, String text) {
		this.nickName = nickName;
		this.text = text;
	}
	
	/**
	 * Returns the nickname of a given message. 
	 * 
	 * @return the message's nickname.
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Register's the nickname of the message.
	 * 
	 * @param nickName 	the nickname to be displayed
	 * 					along with the message.
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Returns the text of the message.
	 * 
	 * @return the message's body text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Registers the text of the message.
	 * 
	 * @param text	the text message to be displayed.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * String format to display the message.
	 */
	@Override
	public String toString() {
		return nickName + ": " + text + "\n";
	}
}
