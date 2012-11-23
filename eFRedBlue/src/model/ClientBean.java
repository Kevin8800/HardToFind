/**
 * 
 */
package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 
 *
 */
public class ClientBean{

	private int clientNumber;
	private String clientName;
	private String password;
	private String rating; 
	
	/**
	 * 
	 */
	public ClientBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ClientBean(int clientNumber, String clientName, String password,
			String rating) {
		super();
		this.clientNumber = clientNumber;
		this.clientName = clientName;
		this.password = password;
		this.rating = rating;
	}

	@XmlAttribute(name="account")
	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	@XmlElement(name="name")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlTransient
	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}



}
