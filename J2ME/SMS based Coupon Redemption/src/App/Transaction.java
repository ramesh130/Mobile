
package App;

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
import java.util.*;

/**
 *
 * @author ramesh
 */
public class Transaction extends Screen {

    Screen currentScreen = this;
    private static final int textBoxCount = 6;
    Form transactionPage = null;
    protected final Vector textBox = new Vector();

    public void show(Screen prev) {
        super.show(prev);
        show();
    }

    public void show() {
        transactionPage = new Form("HCRM UNNATI");
        transactionPage.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label buttonLabel = new Label("Send Coupons");
        transactionPage.addComponent(buttonLabel);

        for (int i = 0; i < textBoxCount; i++) {
            TextField textArea = new TextField();//1, 20, TextArea.ANY);
            textArea.setUseSoftkeys(false);
            transactionPage.addComponent(textArea);
            textBox.addElement(textArea);
        }

        transactionPage.addCommand(backCommand);
        transactionPage.addCommand(sendCommand);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Command cmd = evt.getCommand();
                switch (cmd.getId()) {
                    case BACK_COMMAND:
                        gotoPrevScreen();
                        break;
                    case SEND_COMMAND:
                        String msg = "";
                        for (int i = 0; i < textBox.size(); i++) {
                            TextField textArea = (TextField)textBox.elementAt(i);
                            msg += textArea.getText() + " ";
                        }
                        ComposedMessage.getInstance().setMessage(msg);
                        SMSPage sendSMS = new SMSPage();
                        sendSMS.show(currentScreen);
                        break;
                }
            }
        };

        transactionPage.addCommandListener(listener);
        transactionPage.show();
    }

    public void showExisting() {
        transactionPage.show();
    }
}
