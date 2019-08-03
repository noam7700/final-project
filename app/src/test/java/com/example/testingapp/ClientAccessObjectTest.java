package com.example.testingapp;

import android.os.Parcel;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import communication.clientDataAccess.ClientDataAccessObject;
import communication.clientDataAccess.UnexpectedResponseFromServer;
import communication.communicationObjects.BasketsContent;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ClientAccessObjectTest {
    ClientDataAccessObject cda = new ClientDataAccessObject("noamnoam", "noamnoam");


    @Test
    public void saveBasketTest() throws IOException, UnexpectedResponseFromServer {
        Basket basket = new Basket("test", "test");
        basket.setBasketBuyables(Arrays.asList(new BasketProduct
                (new Product(
                        "a",
                        "1",
                        "b",
                        "c",
                        "1",
                        "google.com"), 1)));

        cda.saveBasket(basket);

        List<Basket> baskets = cda.getSavedBaskets();

        cda.removeBasket(basket);
        try {
            Basket basketReturned = baskets.get(0);
            assertEquals(basket.getName(), basketReturned.getName());
            assertEquals(basket.getAuthor(), basketReturned.getAuthor());
            assertEquals(basket.getBasketBuyables().get(0).getDesc(), basketReturned.getBasketBuyables().get(0).getDesc());
        } catch (Exception e){
            fail();
        }
    }
}