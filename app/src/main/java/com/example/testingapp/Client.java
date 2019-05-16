package com.example.testingapp;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import request_response.AcceptedQuery;
import request_response.RequestObject;
import request_response.ResponseObject;

public class Client {
    static String serverHostname = "192.168.1.210";// "127.0.0.1"; // local host for now
    static int port = 8080;
    String password = null;
    String username = null;

    public Client(String username, String password, String host, int port) {
        this.serverHostname = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public Client(String username, String password, String host) {
        this.serverHostname = host;
        this.username = username;
        this.password = password;
    }

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
//		this.serverHostname = "127.0.0.1";
        this.port = 8080;
    }

    public Client() {
        this.username = "";
        this.password = "";
        this.serverHostname = "127.0.0.1";
        this.port = 8080;
    }

    public static void main(String args[]) {
//		Client c = new Client();
//		ResponseObject responseObject = c.register();
//		if (responseObject.Error())
//			System.out.println("error. " + responseObject.getError());
//		else
//			System.out.println(responseObject.getResponse().toString());
//		System.exit(1);
        Client client = new Client("noam1234", "noam12", "192.168.1.210");

        ResponseObject ro = client.register();
        if (ro.Error())
            System.out.println("error. " + ro.getError());
        else
            System.out.println(ro.getResponse().toString());

//		ResponseObject ro = verifyUser("blakjn1", "blajhb2");
//		if (ro.Error())
//			System.out.println("error. " + ro.getError());
//		else
//			System.out.println(ro.getResponse().toString());

//		Data basket = new Data();
//		basket.setData("sdsc");
//		ro = saveBasket(basket);
//		if (ro.Error())
//			System.out.println("error. " + ro.getError());
//		else {
//			System.out.println(ro.getResponse().toString());
//		}
//		ro = getSavedBaskets();
//		if (ro.Error())
//			System.out.println("error. " + ro.getError());
//		else {
//			Object[] o = (Object[]) ro.getResponse();
//			List<Data> dArr = new ArrayList<>();
//			for (Object ob : o) {
//				Data d = (Data) ob;
//				dArr.add(d);
//				System.out.println(d.getData());
//			}
//		}
    }

    //
//	public boolean isSigned() {
//		return clientUsername != null && clientPass != null;
//	}
//
//	/**
//	 * ----Explanation---- the functions returns true on success, false on failure.
//	 * message sent by server written in {@msgReceived}
//	 */
    public ResponseObject register() {
//		setDetails(username, password);
        RequestObject req = new RequestObject(AcceptedQuery.register, username, password);
        return sendObject(req);
    }

    public ResponseObject verifyUser() {
//		setDetails(username, password);
        RequestObject req = new RequestObject(AcceptedQuery.verifyUser, username, password);
        return sendObject(req);
    }

    public ResponseObject getSavedBaskets() {
//		if (!isSigned())
//			return false;
        RequestObject req = new RequestObject(AcceptedQuery.getBaskets, username, password);
        return sendObject(req);
    }

    public ResponseObject saveBasket(Object basket) {
//		if (!isSigned())
//			return false;
        RequestObject req = new RequestObject(AcceptedQuery.saveBasket, basket, username, password);
        return sendObject(req);
    }

    public ResponseObject removeBasket(Object basket) {
//		if (!isSigned())
//			return null;
        RequestObject req = new RequestObject(AcceptedQuery.removeBasket, basket, username, password);
        return sendObject(req);
    }

    public ResponseObject removeAllBaskets() {
//		if (!isSigned())
//			return false;
        RequestObject req = new RequestObject(AcceptedQuery.removeAllBaskets, username, password);
        return sendObject(req);
    }

    public ResponseObject getData() {
        RequestObject req = new RequestObject(AcceptedQuery.getData, username, password);
        return sendObject(req);
    }
    private ResponseObject sendObject(RequestObject requestObject){
        ClientThread clientThread = new ClientThread(requestObject);
        Thread thread = new Thread(clientThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientThread.getResponseObject();
    }
//	private void setDetails(String un, String ps) {
//		username = un;
//		password = ps;
//	}


}

class ClientThread implements Runnable {
    private RequestObject requestObject;
    private ResponseObject responseObject;
    private String serverHostname = Client.serverHostname;
    private int port = Client.port;

    public ClientThread(RequestObject requestObject) {
        this.requestObject = requestObject;
//        Thread t = new Thread(this);
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
        responseObject = send(requestObject);
    }

    public ResponseObject getResponseObject() {
        return responseObject;
    }

    private ResponseObject send(RequestObject req) { // false for error, true otherwise
        System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//		SSLSocket serverSocket = null;
        Socket serverSocket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        if (req == null) {
            System.err.println("Error! no request sent.");
            return null;
        }
        try {
            System.out.println("connecting to server: ");
//            Log.d("CLIENTDEBUG", "starting connection to server\n server host=" + serverHostname + ", port=" + port);
            serverSocket = new Socket(serverHostname, port);
//			factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//			serverSocket = (SSLSocket) factory.createSocket(serverHostname, port);
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

