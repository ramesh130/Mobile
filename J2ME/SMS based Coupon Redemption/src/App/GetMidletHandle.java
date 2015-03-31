
package App;

import javax.microedition.midlet.*;

/**
 *
 * @author ramesh
 */
public class GetMidletHandle {

    private GetMidletHandle() {
    }

    public static GetMidletHandle getInstance() {
        if (ref == null) // it's ok, we can call this constructor
        {
            ref = new GetMidletHandle();
        }
        return ref;
    }

    public static void setMidletHandle(MIDlet midlet) {
        midletHandle = midlet;
    }

    public static MIDlet getHandle() {
        return midletHandle;
    }

    private static GetMidletHandle ref;
    private static MIDlet midletHandle;
}
