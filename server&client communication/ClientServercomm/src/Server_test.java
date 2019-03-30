import java.io.IOException;
import java.net.ServerSocket;

public class Server_test {
    public static final int PORT_NUMBER = 8085;
    
    public static void main(String[] args) {
        System.out.println("server example");
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT_NUMBER);
            new DataUpdateThread(); // updates products data every 00:00
            
            while (true) { // always listening to client requests
                new serverThread(server.accept()); // creating thread to handle client's request (thus able to handle multiple requests)
            }
            
        } catch (IOException ex) {
            System.out.println("Unable to start server.");
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}