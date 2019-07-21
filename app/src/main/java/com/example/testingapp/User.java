package com.example.testingapp;

import android.util.Pair;

import java.io.File;
import java.util.ArrayList;

import request_response.ResponseObject;

//import Client_side.Client;

public class User {
    private String username; // this details can be maintained is Client class as well (it will anyway contain so)
    private String password;

    private Client serverExecutor;


    public static void main(String[] args) {
        User user = new User("NOAM1234", "NOAM");
        user.register();
    }


    public User(String username, String password) {
        serverExecutor = new Client(username, password, "10.100.102.4", 8080);
//        Client c = new Client(username, password);
//        c.register();
        this.username = username;
        this.password = password;
    }

    public String register() {
        ResponseObject server_response = serverExecutor.register();
        if (server_response.Error()) {
            System.out.println(server_response.getError());
            String error_message = server_response.getError();
            return error_message;
        }
        return "";
//        else { // user details added to the system
//            ResponseObject updatedFileObject = serverExecutor.getData();
//            File productsData = (File) updatedFileObject.getResponse();
//            File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
//            //TODO: update current file
//        }
    }

    public String login() {
        ResponseObject server_response = serverExecutor.verifyUser();
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case - username/password are wrong
            this.username = server_response.getError();
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

    public void enterAsGuest() {
        ResponseObject updatedFileObject = serverExecutor.getData();
        File productsData = (File) updatedFileObject.getResponse();
        File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
        //TODO: update current file
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
        ResponseObject server_response = serverExecutor.saveBasket(basket);
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
        }
    }

    public ArrayList<Basket> getSavedBaskets() {
        ResponseObject server_response = serverExecutor.getSavedBaskets();
        Object[] basketObjects = (Object[]) server_response.getResponse();
        ArrayList<Basket> savedBaskets = new ArrayList<>();
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
            return savedBaskets;
        } else {
            for (Object basketObject : basketObjects) {
                savedBaskets.add((Basket) basketObject);
            }
        }
        return savedBaskets;
    }

    public void deleteBasket_byIndex(int position) { //this is a bit expensive in complexity compared to deleting by value
        ArrayList<Basket> userBaskets = getSavedBaskets();

        Basket basket = null;
        try{
            basket = userBaskets.get(position);
        }catch (Exception e){
            e.printStackTrace();
            //TODO handle exception
        }
        ResponseObject server_response = serverExecutor.removeBasket(basket);
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
        }
    }

    public void removeAllBaskets() {
        ResponseObject server_response = serverExecutor.removeAllBaskets();
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
        }
    }

    public int findBasketIndex_byName(ArrayList<Basket> baskets, String name) {
        for (int i = 0; i < baskets.size(); i++)
            if (baskets.get(i).getName() == name)
                return i;
        return -1;
    }

    public ArrayList<Product> searchWanteditem(Pair<String, Double> wanteditem){
        return null;
    }

    public String getUsername() {
        return username;
    }
}
