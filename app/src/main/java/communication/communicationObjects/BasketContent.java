package communication.communicationObjects;

public class BasketContent {

    public byte[] getRawContent() {
        return rawContent;
    }

    public BasketContent(byte[] rawContent) {
        this.rawContent = rawContent;
    }

    private byte[] rawContent;

}
