import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SQLUtility {
	/* Function taken from stack overflow: http://stackoverflow.com/questions/33085493/hash-a-password-with-sha-512-in-java 
	 * Just something to mess around with.
	 */
	public static String sha512_Encrpyt(String passwordToHash, String salt){
		String generatedPassword = null;
		    try {
		         MessageDigest md = MessageDigest.getInstance("SHA-512");
		         md.update(salt.getBytes("UTF-8"));
		         byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++){
		            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }
		         generatedPassword = sb.toString();
		        } 
		       catch (NoSuchAlgorithmException e){
		        e.printStackTrace();
		       }
		     	catch (UnsupportedEncodingException e) {
		     		e.printStackTrace();
		     	}
		    return generatedPassword;
	}

}
