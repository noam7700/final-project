package com.example.testingapp;

import java.io.File;
import java.util.ArrayList;

import Client_side.Client;
import request_response.ResponseObject;

public class User {
    private String username; // this details can be maintained is Client class as well (it will anyway contain so)
    private String password;

    private Client serverExecutor;

    public User(String username, String password) {
        serverExecutor = new Client(username, password);
        this.username = username;
        this.password = password;
    }

    public void register() {
        ResponseObject server_response = serverExecutor.register(username, password);
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case - username already exists
        } else { // user details added to the system
            ResponseObject updatedFileObject = serverExecutor.getData();
            File productsData = (File) updatedFileObject.getResponse();
            File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
            //TODO: update current file
        }
    }

    public void login() {
        ResponseObject server_response = serverExecutor.verifyUser(username, password);
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case - username/password are wrong
        } else { //
            ResponseObject updatedFileObject = serverExecutor.getData();
            File productsData = (File) updatedFileObject.getResponse();
            File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
            //TODO: update current file
        }
    }

    public void enterAsGuest() {
        ResponseObject updatedFileObject = serverExecutor.getData();
        File productsData = (File) updatedFileObject.getResponse();
        File currentFile = new File("TestingApp\\app\\src\\main\\assets\\ProductsTextData");
        //TODO: update current file
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
            return null;
        } else {
            for (Object basketObject : basketObjects) {
                savedBaskets.add((Basket) basketObject);
            }
        }
        return savedBaskets;
    }

    public void deleteBasket_byIndex(int position) { //this is a bit expensive in complexity compared to deleting by value
        ArrayList<Basket> userBaskets = getSavedBaskets();
        ResponseObject server_response = serverExecutor.removeBasket(userBaskets.get(position));
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

    public String getUsername() {
        return username;
    }
}
