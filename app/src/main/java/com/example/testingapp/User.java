package com.example.testingapp;

import java.io.File;
import java.util.ArrayList;

import Client_side.Client;
import request_response.ResponseObject;

public class User {
    private String username; // this details can be maintained is Client class as well (it will anyway contain so)
    private String password;

    private ArrayList<Basket> mBaskets; // there is no need in mBaskets, besides maybe the use of removing basket by index
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
        mBaskets.add(basket);
        ResponseObject server_response = serverExecutor.saveBasket(basket);
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
        }
    }

    public void deleteBasket_byIndex(int position) {
        mBaskets.remove(position);
        ResponseObject server_response = serverExecutor.removeBasket(mBaskets.get(position));
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
        }
    }

    public void removeAllBaskets() {
        mBaskets.clear();
        ResponseObject server_response = serverExecutor.removeAllBaskets();
        if (server_response.Error()) {
            String error_message = server_response.getError();
            //TODO: error case (unexpected problem)
        }
    }

    public int findBasketIndex_byName(String name) {
        for (int i = 0; i < mBaskets.size(); i++)
            if (mBaskets.get(i).getName() == name)
                return i;
        return -1;
    }

    public ArrayList<Basket> getmBaskets() {
        return mBaskets;
    }

    public String getUsername() {
        return username;
    }
}
