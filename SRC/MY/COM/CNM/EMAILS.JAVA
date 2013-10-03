package my.com.cnm;

import java.io.Serializable;

public class Emails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String Name;
	private  String Email;
	
	Emails(String name, String email){
		Name = name;
		Email=email;
	}
	public String getName() {
		return Name;
	}
	public String getEmail() {
		return Email;
	}
	
	

}
