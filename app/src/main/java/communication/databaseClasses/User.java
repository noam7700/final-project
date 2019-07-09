package communication.databaseClasses;

import java.io.Serializable;

/**
 * @author amit
 * 
 *         This class holds all the details about the client besides its private
 *         details such as password, credit card number etc.
 *
 */
public class User implements Serializable {

	 private static final long serialVersionUID = 1L;

	 String username;

	 public User(String username) throws IllegalArgumentException {
		  if (username == null) {
			   throw new IllegalArgumentException("Username cant be null");
		  }
		  
		  this.username = username;
	 }

	 public String getUsername() {
		  return username;
	 }

}
