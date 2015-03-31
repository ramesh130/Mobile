
package App;

/**
 *
 * @author ramesh
 */
public class ComposedMessage {

    private ComposedMessage() {
    }

    public static ComposedMessage getInstance() {
        if (ref == null) // it's ok, we can call this constructor
        {
            ref = new ComposedMessage();
        }
        return ref;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public void setAddress(String addr) {
        address = addr;
    }

    public String getAddress() {
        return address;
    }

    public String getMessage() {
        return message;
    }
    private static ComposedMessage ref;
    private String address = null;
    private String message = null;
}
