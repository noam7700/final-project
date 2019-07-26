package communication.parse;

import android.net.ParseException;

import com.example.testingapp.Basket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import communication.communicationObjects.BasketContent;
import communication.communicationObjects.BasketsContent;


public class ObjectParser {

    public List<Basket> parseToBaskets(BasketsContent basketsContent) throws IOException, ClassNotFoundException {
        List<Basket> baskets = new ArrayList<>();
        for (BasketContent basketContent : basketsContent.getBaskets()) {
            baskets.add(parseToBasket(basketContent));
        }
        return baskets;
    }

    public Basket parseToBasket(BasketContent basketContent) throws IOException, ClassNotFoundException {
        byte[] rawContent = basketContent.getRawContent();
        Object object = getObject(rawContent);
        return (Basket) object;
    }

    public static byte[] getBytes(Object object) throws IOException {
        byte[] objectBytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.close();
        bos.close();
        objectBytes = bos.toByteArray();
        return objectBytes;
    }

    public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais;
        ObjectInputStream ins;
        bais = new ByteArrayInputStream(bytes);
        ins = new ObjectInputStream(bais);
        Object object = ins.readObject();
        ins.close();
        return object;
    }
}
