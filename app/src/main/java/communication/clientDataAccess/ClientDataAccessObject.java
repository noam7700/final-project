package communication.clientDataAccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.testingapp.Basket;

import java.net.ConnectException;
import java.util.List;
import java.lang.*;

import communication.communicationObjects.Baskets;
import communicationObjects.ClientQuery;
import communicationObjects.Products;
import communicationObjects.Request;
import communicationObjects.ProductInfo;
import communicationObjects.User;

public class ClientDataAccessObject {
    static public String serverHostname = " 10.0.2.2";
    static public int port = 8080;
    static public String password = null;
    static public String username = null;

    public ClientDataAccessObject(String username, String password, String host, int port) {
        this.serverHostname = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public ClientDataAccessObject(String username, String password, String host) {
        this.serverHostname = host;
        this.username = username;
        this.password = password;
    }

    public ClientDataAccessObject(String username, String password) {
        this.username = username;
        this.password = password;
//		this.serverHostname = "127.0.0.1";
        this.port = 8080;
    }

    public ClientDataAccessObject() {
        this.username = "";
        this.password = "";
        this.serverHostname = "127.0.0.1";
        this.port = 8080;
    }

    public static void main(String args[]) {

        ClientDataAccessObject client = new ClientDataAccessObject("noam1a23345", "noam12", "127.0.0.1");

        try {
            boolean success = client.register();
            if (success)
                System.out.println("registeration completed succesfully");
            else
                System.out.println("registeration didn't complete succesfully");

            User user = client.login();
            if (user != null)
                System.out.println("user: " + user.getUsername() + " verified successfully");
            else
                System.out.println("wrong details");

            List<ProductInfo> productInfos = client.searchProducts("חלב");
            productInfos = client.getProductsData();

        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (UnexpectedResponseFromServer e) {
            e.printStackTrace();
        }
    }

    //	/**
//	 * ----Explanation---- the functions returns true on success, false on failure.
//	 * message sent by server written in {@msgReceived}
//	 */
    public boolean register() throws ConnectException, UnexpectedResponseFromServer {
        Request req = new Request(ClientQuery.REGISTER, new User(username), username, password);
        System.out.println("registering " + username + " " + password);

        Object registerStatus;
        try {
            registerStatus = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }

        try {
            return (boolean) registerStatus;

        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }

    }

    public User login() throws ConnectException, UnexpectedResponseFromServer {
        Request req = new Request(ClientQuery.LOGIN, username, password);
        Object user;
        try {
            user = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }

        try {
            return (User) user;
        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }

    }

    public List<Basket> getSavedBaskets() throws ConnectException, UnexpectedResponseFromServer {
        Request req = new Request(ClientQuery.GET_BASKET, username, password);
        Object baskets;
        try {
            baskets = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }
        try {
            Baskets basketsRetrieval = (Baskets) baskets;
            return basketsRetrieval.getBaskets();
        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }
    }

    public void saveBasket(Object basket) throws ConnectException {

        Request req = new Request(ClientQuery.SAVE_BASKET, basket, username, password);
        try {
            sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBasket(Object basket) throws ConnectException {

        Request req = new Request(ClientQuery.REMOVE_BASKET, basket, username, password);
        try {
            sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAllBaskets() throws ConnectException {

        Request req = new Request(ClientQuery.REMOVE_ALL_BASKETS, username, password);
        try {
            sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductInfo> getProductsData() throws ConnectException, UnexpectedResponseFromServer {
        Request req = new Request(ClientQuery.GET_PRODUCTS_DATA, username, password);
        Object object;
        try {
            object = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }
        try {
            Products products = (Products) object;
            return products.getProducts();
        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }
    }

    public List<ProductInfo> searchProducts(String productDescription) throws ConnectException, UnexpectedResponseFromServer {
        Request req = new Request(ClientQuery.SEARCH_PRODUCTS, productDescription, username, password);
        Object object;
        try {
            object = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }
        try {
            Products productsRetrieved = (Products) object;
            return productsRetrieved.getProducts();
        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }
    }

//	private void ListDetails(String un, String ps) {
//		username = un;
//		password = ps;
//	}


    private Object sendToServer(Request request) {
        ClientThread clientThread = new ClientThread(request);
        Thread thread = new Thread(clientThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientThread.getObjectReceived();
    }
//	private void setDetails(String un, String ps) {
//		username = un;
//		password = ps;
//	}


}

class ClientThread implements Runnable {
    private Request request;
    private Object objectReceived;
    private String serverHostname = ClientDataAccessObject.serverHostname;
    private int port = ClientDataAccessObject.port;

    public ClientThread(Request requestObject) {
        this.request = requestObject;
    }

    @Override
    public void run() {

        try {
            objectReceived = sendToServer(request);
        } catch (IOException e1) {

        } catch (IllegalArgumentException e2) {

        } catch (ClassNotFoundException e3) {

        }
    }

    public Object getObjectReceived() {
        return objectReceived;
    }

    private Object sendToServer(Request req) throws IOException, IllegalArgumentException, ClassNotFoundException {
        System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
        Socket serverSocket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        System.out.println("connecting to server: ");
        serverSocket = new Socket(serverHostname, port);
        out = new ObjectOutputStream(serverSocket.getOutputStream());
        in = new ObjectInputStream(serverSocket.getInputStream());


        System.out.println("Sending data to server..");
        out.writeObject(req);

        System.out.println("data sent. receiving data:");
        Object res = null;
        res = in.readObject();

        /** Closing all the resources */
        out.close();
        in.close();
        serverSocket.close();

        return res;

    }
}

