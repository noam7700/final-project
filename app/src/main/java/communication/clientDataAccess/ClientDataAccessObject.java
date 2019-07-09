package communication.clientDataAccess;

import com.example.testingapp.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.lang.*;

import communication.databaseObjectsParse.*;
import communication.communicationObjects.Baskets;
import communication.communicationObjects.ClientQuery;
import communication.communicationObjects.Products;
import communication.communicationObjects.Request;
import communication.databaseClasses.DbBasket;
import communication.databaseClasses.DbProduct;
import communication.databaseClasses.User;

public class ClientDataAccessObject {
    String serverHostname = "127.0.0.1";// "127.0.0.1"; // local host for now
    int port = 8080;
    String password = null;
    String username = null;

    DatabaseObjectParser databaseObjectParser;

    public ClientDataAccessObject(String username, String password, String host, int port) {
        this.serverHostname = host;
        this.port = port;
        this.username = username;
        this.password = password;
        databaseObjectParser = new DatabaseObjectParser();
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

        ClientDataAccessObject client = new ClientDataAccessObject("noam1234", "noam12", "127.0.0.1");

        try {
            boolean success = client.register();
            if (success)
                System.out.println("registeration completed succesfully");
            else
                System.out.println("registeration didn't complete succesfully");
    throw new java.lang.E
        } catch ( e) {
            e.printStackTrace();
        }
    }

    //	/**
//	 * ----Explanation---- the functions returns true on success, false on failure.
//	 * message sent by server written in {@msgReceived}
//	 */
    public boolean register() throws UnexpectedException {
        Request req = new Request(ClientQuery.REGISTER, new User(username), username, password);
        System.out.println("registering " + username + " " + password);

        Object registerStatus;
        try {
            registerStatus = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException("Connection to server encountered a problem");
        }

        try {
            return (boolean) registerStatus;

        } catch (Exception e) {
            throw new UnexpectedException("Unexpected response from server");
        }

    }

    public User login() throws UnexpectedException {
        Request req = new Request(ClientQuery.LOGIN, username, password);
        Object user;
        try {
            user = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException("Connection to server encountered a problem");
        }

        try {
            return (User) user;
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected response from server");
        }

    }

    public List<DbBasket> getSavedBaskets() throws UnexpectedException {
        Request req = new Request(ClientQuery.GET_BASKET, username, password);
        Object baskets;
        try {
            baskets = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException("Connection to server encountered a problem");
        }
        try {
            Baskets basketsRetrieval = (Baskets) baskets;
            return basketsRetrieval.getBaskets();
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected response from server");
        }
    }

    public void saveBasket(Object basket) throws UnexpectedException {

        Request req = new Request(ClientQuery.SAVE_BASKET, basket, username, password);
        try {
            sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBasket(Object basket) throws UnexpectedException {

        Request req = new Request(ClientQuery.REMOVE_BASKET, basket, username, password);
        try {
            sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAllBaskets() throws UnexpectedException {

        Request req = new Request(ClientQuery.REMOVE_ALL_BASKETS, username, password);
        try {
            sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProductsData() throws UnexpectedException {
        Request req = new Request(ClientQuery.GET_DATA_FROM_WEB, username, password);
        Object products;
        try {
            products = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException("Connection to server encountered a problem");
        }
        try {
            Products productsRetrieved = (Products) products;
            return databaseObjectParser.parseDbProducts(productsRetrieved);
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected response from server");
        }
    }

    public List<Product> searchProductsByName() throws UnexpectedException {
        Request req = new Request(ClientQuery.SEARCH_PRODUCTS_BY_NAME, username, password);
        Object products;
        try {
            products = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException("Connection to server encountered a problem");
        }
        try {
            Products productsRetrieved = (Products) products;
            return databaseObjectParser.parseDbProducts(productsRetrieved);
        } catch (Exception e) {
            throw new UnexpectedException("Unexpected response from server");
        }
    }

//	private void ListDetails(String un, String ps) {
//		username = un;
//		password = ps;
//	}

    private Object sendToServer(Request req) {
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
            serverSocket = new Socket(serverHostname, port);
//			factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//			serverSocket = (SSLSocket) factory.createSocket(serverHostname, port);
//			serverSocket.startHandshake();
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverHostname);
            System.err.println(e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Unable to get streams from server");
            System.err.println(e.getMessage());
            return null;
        }

        System.out.println("Sending data to server..");
        try {
            out.writeObject(req);
        } catch (IOException e1) {
            System.err.println("error in sending data to server");
            System.err.println(e1.getMessage());
            return null;
        }
        System.out.println("data sent. receiving data:");
        Object res = null;
        try {
            res = in.readObject();
        } catch (ClassNotFoundException | IOException e1) {
            System.err.println("error in reading data from server");
            System.err.println(e1.getMessage());
        } finally {

            /** Closing all the resources */
            try {
                out.close();
                in.close();
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources");
                System.err.println(e.getMessage());
                return null;
            }
        }
        return res;

    }

}
