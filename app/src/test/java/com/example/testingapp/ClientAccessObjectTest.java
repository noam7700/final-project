package com.example.testingapp;

import android.os.Parcel;

import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import communication.clientDataAccess.ClientDataAccessObject;
import communication.clientDataAccess.UnexpectedResponseFromServer;

import communicationObjects.ProductInfo;

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
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getDiscountTest() throws UnexpectedResponseFromServer, ConnectException {
        List<ProductInfo> products = ClientDataAccessObject.getProductsData();
        ProductInfo productInfo = products.get(0);
        int qty = 10;
        double discount = ClientDataAccessObject.getProductDiscount(productInfo.getId_str(), qty);
        System.out.println(discount);
        assertTrue(discount >= 0);
    }
}