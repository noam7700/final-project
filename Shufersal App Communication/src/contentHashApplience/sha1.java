package contentHashApplience;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha1 {

	 /**
	  * Sha1 is a classic hash function. with this function the plain content will be
	  * unreachable to a malicious observer.
	  * 
	  * @param input 
	  * 			plain to apply the hash to
	  * @return <code>input</code> after sha1 hash has been applied
	  */
	 static String applySha1(String input) {
		  MessageDigest mDigest = null;
		  try {
			   mDigest = MessageDigest.getInstance("SHA1");
		  } catch (NoSuchAlgorithmException e) {
			   System.err.println("Error retreiving SHA1 hash");
			   System.err.println(e.getMessage());
			   System.exit(1);
		  }
		  byte[] result = mDigest.digest(input.getBytes());
		  StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < result.length; i++) {
			   sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		  }

		  return sb.toString();
	 }
}
