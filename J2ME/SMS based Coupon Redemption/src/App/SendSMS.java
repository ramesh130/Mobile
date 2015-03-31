
package App;

import javax.microedition.midlet.*;
import com.sun.lwuit.*;
import com.sun.lwuit.events.*;

import com.sun.lwuit.Button;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.impl.midp.VKBImplementationFactory;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.LookAndFeel;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import java.io.IOException;

/**
 *
 * @author ramesh
 */
public class SendSMS extends Screen implements OutgoingTextMessageListener {

    class SMSStatus {

        public static final int READY = 1;
        public static final int SENDING = 2;
        public static final int ERROR = 3;
        public static final int SUCCESS = 4;
    }
    private boolean status = false;
    Form SendSMSPage = null;
    int smsStatus = SMSStatus.READY;
    String msg = "Sending SMS";
    private static SmsHandler smsHandler = null;
    private String port = null;

    public void show(Screen prev) {
        super.show(prev);
        show();
    }

    public void show() {
        SendSMSPage = new Form("HCRM UNNATI");
        SendSMSPage.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label buttonLabel = new Label("SMS Report");
        SendSMSPage.addComponent(buttonLabel);

        SendSMSPage.addCommand(exitCommand);
        SendSMSPage.addCommand(backCommand);

        smsHandler = new WmaSmsHandler();
        port = "50000";//7500";//getAppProperty("Default-Port");
        smsHandler.init(this, port);
        //smsHandler.sendMessage("+918600907272", "Hello LWUIT!");
        smsHandler.sendMessage(ComposedMessage.getInstance().getAddress(),
                ComposedMessage.getInstance().getMessage());
        smsStatus = SMSStatus.SENDING;

        Label logo = new Label() {

            private long lastInvoke;
            private int count = 0;
            String msgDisp = null;

            public void initComponent() {
                SendSMSPage.registerAnimated(this);
            }

            public boolean animate() {
                if (smsStatus == SMSStatus.SENDING) {
                    long current = System.currentTimeMillis();
                    if (current - lastInvoke > 500) {
                        lastInvoke = current;
                        count++;
                        msgDisp = msg;
                        for (int i = 0; i < count; i++) {
                            msgDisp += ".";
                        }

                        setText(msgDisp);
                        if (count == 4) {
                            count = 0;
                        }
                        return true;
                    }
                    return false;
                } else if (smsStatus == SMSStatus.SUCCESS) {
                    msgDisp = "Message Sent successfully!";
                    setText(msgDisp);
                    return true;
                } else if (smsStatus == SMSStatus.ERROR) {
                    msgDisp = "Error! Try again.";
                    setText(msgDisp);
                    return true;
                }
                return false;
            }
        };

        logo.setText(msg);
        SendSMSPage.addComponent(logo);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Command cmd = evt.getCommand();
                if (cmd != null) {
                    if (smsStatus == SMSStatus.SUCCESS || smsStatus == SMSStatus.ERROR) {
                        stopSmsThread();
                        switch (cmd.getId()) {
                            case BACK_COMMAND:
                                gotoPrevScreen();
                                break;
                            case EXIT_COMMAND:
                                GetMidletHandle.getInstance().getHandle().notifyDestroyed();
                                break;
                        }
                    }
                }
            }
        };

        //send.addActionListener(listener);
        SendSMSPage.addCommandListener(listener);
        SendSMSPage.show();
    }

    private void stopSmsThread() {
        if (smsHandler != null) {
            smsHandler.abort();
        }
    }

    public void showExisting() {
        SendSMSPage.show();
    }

    //OutgoingTextMessageListenerinterfacemethods
    public void handleOutgoingOk(OutgoingTextMessage message) {
        smsStatus = SMSStatus.SUCCESS;
    }

    public void handleOutgoingCancel(OutgoingTextMessage message,
            String type) {
    }

    public void handleOutgoingError(OutgoingTextMessage message,
            String errorStr) {
        smsStatus = SMSStatus.ERROR;
    }
}
