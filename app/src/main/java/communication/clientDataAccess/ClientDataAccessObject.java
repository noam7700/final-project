package communication.clientDataAccess;

import com.example.testingapp.Basket;
import com.example.testingapp.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import java.lang.*;

import communication.databaseObjectsParse.*;
import communication.communicationObjects.Baskets;
import communication.communicationObjects.ClientQuery;
import communication.communicationObjects.Products;
import communication.communicationObjects.Request;
import communication.databaseClasses.User;
import communication.databaseClasses.ProductInfo;

public class ClientDataAccessObject {
    String serverHostname = " 10.0.2.2";//"10.0.2.2" "127.0.0.1"; // local host for now
    int port = 8080;
    String password = null;
    String username = null;

    DatabaseObjectParser databaseObjectParser = new DatabaseObjectParser();

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

        ClientDataAccessObject client = new ClientDataAccessObject("noam1234", "noam12", "127.0.0.1");

        try {
            boolean success = client.register();
            if (success)
                System.out.println("registeration completed succesfully");
            else
                System.out.println("registeration didn't complete succesfully");

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
        Request req = new Request(ClientQuery.GET_DATA_FROM_WEB, username, password);
        Object products;
        try {
            products = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }
        try {
            Products productsRetrieved = (Products) products;
            return productsRetrieved.getProductsInfo();
        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }
    }

    public List<ProductInfo> searchProductsByName() throws ConnectException, UnexpectedResponseFromServer {
        Request req = new Request(ClientQuery.SEARCH_PRODUCTS_BY_NAME, username, password);
        Object products;
        try {
            products = sendToServer(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException();
        }
        try {
            Products productsRetrieved = (Products) products;
            return databaseObjectParser.parseDbProducts(productsRetrieved);
        } catch (Exception e) {
            throw new UnexpectedResponseFromServer();
        }
    }

//	private void ListDetails(String un, String ps) {
//		username = un;
//		password = ps;
//	}

    private Object sendToServer(Request req) throws IOException, IllegalArgumentException, ClassNotFoundException {
        System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//		SSLSocket serverSocket = null;
        Socket serverSocket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        System.out.println("connecting to server: ");
        serverSocket = new Socket(serverHostname, port);
//			factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//			serverSocket = (SSLSocket) factory.createSocket(serverHostname, port);
//			serverSocket.startHandshake();
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
