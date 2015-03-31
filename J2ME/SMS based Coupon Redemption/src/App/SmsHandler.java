
package App;

import java.io.*;
import java.util.*;


interface OutgoingTextMessageListener {

    public void handleOutgoingOk(OutgoingTextMessage message);

    public void handleOutgoingCancel(OutgoingTextMessage message, String type);

    public void handleOutgoingError(OutgoingTextMessage message, String errorStr);
}

abstract class SmsHandler implements Runnable {

    protected final Vector outgoingTextMessages = new Vector();
    //protected IncomingTextMessageListener incomingListener;
    protected OutgoingTextMessageListener outgoingListener;
    protected String port = null;
    protected volatile boolean aborting = false;
    protected volatile int incomingMessagesPending = 0;

    protected SmsHandler() {
    }

    void init(OutgoingTextMessageListener outgoingListener,
            String port) {
        //this.incomingListener = incomingListener;
        this.outgoingListener = outgoingListener;
        this.port = port;
        //Ifweareabletoopentheservermodeconnection,
        //thenstartthethreadthatdoeslistening+sending.
        if (openServerModeConnection()) {
            Thread t = new Thread(this);
            t.start();
        }
    }

    public void run() {
        while (!aborting) {
            //1.Waitifthereisnothingtosendorreceive.
            synchronized (this) {
                if ((outgoingTextMessages.size() == 0)) {
                    try {
                        wait();//releaseslock
                    } catch (InterruptedException e) {
                    }
                }
            }
            if (aborting) {
                break;
            }
            //2.Ifthereareanyoutgoingmessagesonthequeue,thensendone.
            while (outgoingTextMessages.size() > 0) {
                OutgoingTextMessage outgoing =
                        (OutgoingTextMessage) outgoingTextMessages.firstElement();
                doSend(outgoing);
                outgoingTextMessages.removeElementAt(0);
            }
        }
    }

    protected abstract boolean openServerModeConnection();

    protected abstract void doSend(OutgoingTextMessage outgoing);

    //protected abstract void doReceive();

    void sendMessage(String addrs, String content) {
        //for (int i = 0; i < addrs.length; i++) {
            if (addrs != null) {
                OutgoingTextMessage outgoing =
                        new OutgoingTextMessage(addrs, content);
                outgoingTextMessages.addElement(outgoing);
            }
        //}
        synchronized (this) {
            notify();//wakeupthesendingthread
        }
    }

    void abort() {
        aborting = true;
        synchronized (this) {
            notify();//wakeuptheSmsHandlerthread,tokillit
        }
    }
}
