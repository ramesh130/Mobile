
package App;

/**
 *
 * @author ramesh
 */
public class OutgoingTextMessage {

    private final String address;
    private final String message;

    OutgoingTextMessage(String address, String message) {
        this.address = address;
        this.message = message;
    }

    String getAddress() {
        return address;
    }

    String getMessage() {
        return message;
    }
}
