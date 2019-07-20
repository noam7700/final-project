package communication.clientDataAccess;

public class UnexpectedResponseFromServer extends Exception {


    public UnexpectedResponseFromServer() {
        super();
    }

    public UnexpectedResponseFromServer(String msg) {
        super(msg);
    }
}