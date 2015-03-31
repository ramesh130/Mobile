
package App;

import java.io.*;
import javax.microedition.io.*;
import javax.wireless.messaging.*;

public class WmaSmsHandler extends SmsHandler implements MessageListener {

    private final static String PROTOCOL = "sms://";
    private MessageConnection conn;

    WmaSmsHandler() {
    }

    protected void doSend(OutgoingTextMessage outgoing) {
        try {
            TextMessage message = (TextMessage) conn.newMessage(MessageConnection.TEXT_MESSAGE);
            String outgoingAddress = outgoing.getAddress();
            if (outgoingAddress.indexOf(':') == -1) {
                outgoingAddress += ":" + port;
            }
            message.setAddress(PROTOCOL + outgoingAddress);
            message.setPayloadText(outgoing.getMessage());
            conn.send(message);
            outgoingListener.handleOutgoingOk(outgoing);
        } catch (SecurityException e) {
            outgoingListener.handleOutgoingCancel(outgoing, "Security");
        } catch (InterruptedIOException e) {
            outgoingListener.handleOutgoingCancel(outgoing, "Interrupted");
        } catch (Exception e) {
            outgoingListener.handleOutgoingError(outgoing,
                    "OutgoingError:" + e);
        }
    }

//thismethodisfromMessageListener
    public synchronized void notifyIncomingMessage(MessageConnection conn) {
        incomingMessagesPending++;
        synchronized (this) {
            notify();//wakeuptheSmsHandlerthread
        }
    }

    protected boolean openServerModeConnection() {
        try {
            conn = (MessageConnection) Connector.open(PROTOCOL + ":" + port);
            conn.setMessageListener(this);
        } catch (Exception e) {
            conn = null;
        }
        return (conn != null);
    }

    void abort() {
        if (conn != null) {
            try {
                conn.setMessageListener(null);
                conn.close();
            } catch (Exception e) {
                //Sinceweareaborting,noerrorhandlingneeded.
            }
            conn = null;
        }
        super.abort();
    }
}
