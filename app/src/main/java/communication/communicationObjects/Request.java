package communication.communicationObjects;
import java.io.Serializable;

public class Request implements Serializable{
	static final long serialVersionUID = 0;
	ClientQuery query;
	Object objectToSend;
	String username, password;

	public Request(ClientQuery q, Object o, String un, String ps) {
		query = q;
		objectToSend = o;
		username = un;
		password = ps;

	}

	public Request(ClientQuery q, String un, String ps) {
		query = q;
		objectToSend = null;
		username = un;
		password = ps;
	}
	public ClientQuery getQuery() {
		return query;
	}
	public String getPass() {
		return password;
	}

	public String getUname() {
		return username;
	}

	public Object getObject() {
		return objectToSend;
	}
}
