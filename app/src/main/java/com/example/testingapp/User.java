package com.example.testingapp;

import android.util.Pair;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import communication.clientDataAccess.ClientDataAccessObject;
import communication.clientDataAccess.UnexpectedResponseFromServer;
import communicationObjects.ProductInfo;

public class User {
    private String username; // this details can be maintained is Client class as well (it will anyway contain so)
    private String password;

    private ClientDataAccessObject serverExecutor;


    public static void main(String[] args) {
        User user = new User("NOAM1234", "NOAM");
        user.register();
    }


    public User(String username, String password) {

        serverExecutor = new ClientDataAccessObject(username, password, "10.100.102.4", 8080);
//        Client c = new Client(username, password);
//        c.register();
        this.username = username;
        this.password = password;
    }

    public String register() {

        try {
            boolean success = serverExecutor.register();
            if (!success) {
                // TODO failure
                System.out.println("registeration failed");
                return "registeration failed";
            } else
                return "";
        } catch (ConnectException e1) {
            return e1.getMessage();
        } catch (UnexpectedResponseFromServer e2) {
            return e2.getMessage();
        }

//        else { // user details added to the system
//            ResponseObject updatedFileObject = serverExecutor.getData();
//            File productsData = (File) updatedFileObject.getResponse();
//            File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
//            //TODO: update current file
//        }
    }

    public String login() {
        communicationObjects.User userLogged;
        try {
            userLogged = serverExecutor.login();
        } catch (ConnectException e1) {
            return e1.getMessage();
        } catch (communication.clientDataAccess.UnexpectedResponseFromServer e2) {
            return e2.getMessage();
        }
        if (userLogged == null) {
            //TODO: error case - username/password are wrong  --call AlertDialog
            String error_message = "wrong details";
            this.username = error_message;
            return this.username; //maybe I'll show it to user on the screen
        }
        return ""; //No error
//        else { //
//            ResponseObject updatedFileObject = serverExecutor.getData();
//            File productsData = (File) updatedFileObject.getResponse();
//            File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
//            //TODO: update current file
//        }
    }

    public List<ProductInfo> getProductsData() {
        try {
            List<ProductInfo> productsData = serverExecutor.getProductsData();
            return productsData;
        } catch (ConnectException e1) {
            e1.printStackTrace();
            return new ArrayList<>();
        } catch (communication.clientDataAccess.UnexpectedResponseFromServer e2) {
            e2.printStackTrace();
            return new ArrayList<>();
        }
    }

    // set dest = source (update dest to have source's content)
    public void updateProductsTextData(File source) {
        /*try {
            InputStream source_stream = new FileInputStream(source);
            Path dest = Paths.get("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
            Files.copy(source_stream, dest);
        } catch(IOException ex){
            ex.printStackTrace();
        }*/
    }

    public void addBasket(Basket basket) {
        try {
            serverExecutor.saveBasket(basket);
        } catch (ConnectException e) {
            // TODO
        }
    }

    public List<Basket> getSavedBaskets() {
        try {
            List<Basket> savedBaskets = serverExecutor.getSavedBaskets();
            return savedBaskets;
        } catch (ConnectException e1) {
            return new ArrayList<>();
        } catch (UnexpectedResponseFromServer e2) {
            return new ArrayList<>();
        }
    }

    public void deleteBasket_byIndex(int position) { //this is a bit expensive in complexity compared to deleting by value
        List<Basket> userBaskets = getSavedBaskets();

        Basket basket = null;
        try {
            basket = userBaskets.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO handle exception
        }
        try {
            serverExecutor.removeBasket(basket);
        } catch (ConnectException e) {
            //TODO
        }

    }

    public void removeAllBaskets() {
        try {
            serverExecutor.removeAllBaskets();
        } catch (ConnectException e) {
        }
    }

    public int findBasketIndex_byName(ArrayList<Basket> baskets, String name) {
        for (int i = 0; i < baskets.size(); i++)
            if (baskets.get(i).getName() == name)
                return i;
        return -1;
    }

    public static ArrayList<Product> searchWanteditem(Pair<String, Double> wanteditem) {
        User tmp = new User("default", "default");
        List<ProductInfo> searchResults;
        try {
            searchResults = tmp.serverExecutor.searchProducts(wanteditem.first);
        } catch(ConnectException ex){
            //TODO: tell the user about this error --AlertDialog
            searchResults = new ArrayList<ProductInfo>(); //empty result
        } catch(UnexpectedResponseFromServer ex){
            //TODO: tell the user about this error --AlertDialog
            searchResults = new ArrayList<ProductInfo>(); //empty result
        }

        ArrayList<Product> searchResults_parsed = new ArrayList<>();
        Product parsedCurr;
        for(int i=0; i<searchResults.size(); i++){
            parsedCurr = Product.parseProductInfo(searchResults.get(i));
            searchResults_parsed.add(parsedCurr);
        }

        //parse ArrayList<ProductInfo> to ArrayList<Product>

        return searchResults_parsed;
    }

    public String getUsername() {
        return username;
    }
}
