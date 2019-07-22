package handleMessageFromClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import communicationObjects.Request;

/**
 * @author amit
 * 
 *         The rule of this thread is to handle a client request. each request
 *         is handled by a separate instance of {@link ServerThread}, and by
 *         this the server can handle multiple clients at once.
 */
public class ServerThread extends Thread {
	 protected Socket socket;

	 public ServerThread(Socket socket) {
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
			   Request ro;
			   try {
					ro = (Request) in.readObject();
					ClientRequestManager.handle(ro, out);
			   } catch (ClassNotFoundException e) {
					e.printStackTrace();
			   }
		  } catch (IOException ex) {
			   System.err.println("Unable to get streams from client");
			   ex.printStackTrace();
		  } finally {
			   try {
					in.close();
					out.close();
					socket.close();
			   } catch (IOException ex) {
					System.err.println("Error closing resources");
					System.err.println(ex.getMessage());
			   }
		  }
	 }

}
