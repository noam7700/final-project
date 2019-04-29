package Client_side;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.invoke.MethodHandle;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.google.api.client.http.UrlEncodedParser;

import debug.debug_class.Data;
import request_response.AcceptedQuery;
import request_response.RequestObject;
import request_response.ResponseObject;

public class Client {
	static final String serverHostname = "127.0.0.1"; // local host for now
	static final int port = 8080;
	static String clientPass = null;
	static String clientUsername = null;

	public static void main(String args[]) {

		ResponseObject ro = verifyUser("blakjn1", "blajhb2");
		if (ro.Error())
			System.out.println("error. " + ro.getError());
		else
			System.out.println(ro.getResponse().toString());

		Data basket = new Data();
		basket.setData("sdsc");
		ro = saveBasket(basket);
		if (ro.Error())
			System.out.println("error. " + ro.getError());
		else {
			System.out.println(ro.getResponse().toString());
		}
		ro = getSavedBaskets();
		if (ro.Error())
			System.out.println("error. " + ro.getError());
		else {
			Object[] o = (Object[]) ro.getResponse();
			List<Data> dArr = new ArrayList<>();
			for (Object ob : o) {
				Data d = (Data) ob;
				dArr.add(d);
				System.out.println(d.getData());
			}
		}
	}

	public static boolean isSigned() {
		return clientUsername != null && clientPass != null;
	}

	/**
	 * ----Explanation---- the functions returns true on success, false on failure.
	 * message sent by server written in {@msgReceived}
	 */
	public static ResponseObject register(String un, String ps) {
		setDetails(un, ps);
		RequestObject req = new RequestObject(AcceptedQuery.register, un, ps);
		return send(req);

	}

	public static ResponseObject verifyUser(String un, String ps) {
		setDetails(un, ps);
		RequestObject req = new RequestObject(AcceptedQuery.verifyUser, un, ps);
		return send(req);
	}

	public static ResponseObject getSavedBaskets() {
//		if (!isSigned())
//			return false;
		RequestObject req = new RequestObject(AcceptedQuery.getBaskets, clientUsername, clientPass);
		return send(req);
	}

	public static ResponseObject saveBasket(Object basket) {
//		if (!isSigned())
//			return false;
		RequestObject req = new RequestObject(AcceptedQuery.saveBasket, basket, clientUsername, clientPass);
		return send(req);
	}

	public static ResponseObject removeBasket(Object basket) {
//		if (!isSigned())
//			return null;
		RequestObject req = new RequestObject(AcceptedQuery.removeBasket, basket, clientUsername, clientPass);
		return send(req);
	}

	public static ResponseObject removeAllBaskets() {
//		if (!isSigned())
//			return false;
		RequestObject req = new RequestObject(AcceptedQuery.removeAllBaskets, clientUsername, clientPass);
		return send(req);
	}

	public static ResponseObject getData() {
		RequestObject req = new RequestObject(AcceptedQuery.getData, clientUsername, clientPass);
		return send(req);
	}

	private static void setDetails(String un, String ps) {
		clientUsername = un;
		clientPass = ps;
	}

	private static ResponseObject send(RequestObject req) { // false for error, true otherwise
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//		SSLSocketFactory factory =
//                (SSLSocketFactory)SSLSocketFactory.getDefault();
//            SSLSocket serverSocket = null;
		Socket serverSocket = null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		if (req == null) {
			System.err.println("Error! no request sent.");
			return null;
		}
		try {
			System.out.println("connecting to server: ");
			serverSocket = new Socket(serverHostname, port);
//			serverSocket = (SSLSocket)factory.createSocket(serverHostname, port);
//			serverSocket.startHandshake();
			out = new ObjectOutputStream(serverSocket.getOutputStream());
			in = new ObjectInputStream(serverSocket.getInputStream());

		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + serverHostname);
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to get streams from server");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("Sending data to server..");
		try {
			out.writeObject(req);
		} catch (IOException e1) {
			System.err.println("error in sending data to server");
			System.err.println(e1.getMessage());
		}
		System.out.println("data sent. receiving data:");
		Object res = null;
		try {
			res = in.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			System.err.println("error in reading data from server");
			System.err.println(e1.getMessage());
		}
		if (!(res instanceof ResponseObject)) {
			System.err.println("Error! unknown response from server.");
			return null;
		}
		ResponseObject resObject = (ResponseObject) res;

		/** Closing all the resources */
		try {
			out.close();
			in.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing resources");
			System.err.println(e.getMessage());
		}
		return resObject;
	}
}
