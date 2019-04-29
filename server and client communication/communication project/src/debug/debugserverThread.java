package debug;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import request_response.RequestObject;
import request_response.ResponseObject;

public class debugserverThread extends Thread {
	protected Socket socket;

	public debugserverThread(Socket socket) {
		this.socket = socket;
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		start();
	}

	public void run() {
		System.out.println("I'm in thread: " + this.getName());
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Connection is set");
			Object clientReq = in.readObject();
			System.out.println(clientReq instanceof debug_class);
			if (clientReq instanceof debug_class) {
				System.out.println("debug_class field is: " + ((debug_class) clientReq).field);
			}
			if (clientReq instanceof RequestObject) {
				System.out.println("un, ps from RequestObject are: " + ((RequestObject) clientReq).getUname()
						+ ((RequestObject) clientReq).getPass());
				if (((RequestObject) clientReq).getObject() != null)
					System.out.println("Object toString: " + ((RequestObject) clientReq).getObject().toString());
			}
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			ObjectOutput oout = new ObjectOutputStream(bos);
//			oout.writeObject(clientReq);
//			oout.flush();
//			bos.toByteArray();
			System.out.println("client's object toString(): " + clientReq.toString());
			out.writeObject(new ResponseObject("hello client"));
			// BufferedReader br = new BufferedReader(new InputStreamReader(in));
		} catch (IOException e) {
			System.err.println("error receiving input from client");
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("error receiving input from client");
			e.printStackTrace();
		}

	}
}
