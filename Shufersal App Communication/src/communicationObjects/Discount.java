package communicationObjects;

import java.io.Serializable;

public class Discount implements Serializable{
	 private static final long serialVersionUID = 1L;
	 private double discount;

    public Discount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
