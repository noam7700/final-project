package communicationObjects;

import java.io.Serializable;

public class DiscountRequest implements Serializable{
	private static final long serialVersionUID = 1L;
    private final String prodID;
    private final int qty;

    public DiscountRequest(String prodID, int qty) {
        this.prodID = prodID;
        this.qty = qty;
    }


    public int getQty() {
        return qty;
    }

    public String getProdID() {
        return prodID;
    }
}
