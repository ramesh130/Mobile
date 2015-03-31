
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

/**
 *
 * @author ramesh
 */
public class SMSPage extends Screen {

    Screen currentScreen = this;
    Form SMSReportPage = null;
    TextField textArea = null;

    public void show(Screen prev) {
        super.show(prev);
        show();
    }

    public void show() {
        SMSReportPage = new Form("HCRM UNNATI");
        SMSReportPage.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label buttonLabel = new Label("Send SMS");
        SMSReportPage.addComponent(buttonLabel);

        Label numberLabel = new Label("Number to send");
        SMSReportPage.addComponent(numberLabel);

        textArea = new TextField("", 10);
        textArea.setConstraint(TextArea.NUMERIC);
        textArea.setInputModeOrder(new String[]{"123"});
        textArea.setUseSoftkeys(false);
        SMSReportPage.addComponent(textArea);
        SMSReportPage.addCommand(backCommand);
        SMSReportPage.addCommand(sendCommand);

        /*final Button send = new Button("Send");
        send.setAlignment(Label.CENTER);
        SMSReportPage.addComponent(send);*/

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Command cmd = evt.getCommand();
                if (cmd != null) {
                    switch (cmd.getId()) {
                        case BACK_COMMAND:
                            gotoPrevScreen();
                            break;
                        case SEND_COMMAND:
                            ComposedMessage.getInstance().setAddress(
                                    textArea.getText());
                            SendSMS sendSMS = new SendSMS();
                            sendSMS.show(currentScreen);
                            break;
                    }
                }

                /*Object source = evt.getSource();
                if (source != null) {
                if (source == send) {

                return;
                }
                }*/
            }
        };

        //send.addActionListener(listener);
        SMSReportPage.addCommandListener(listener);
        SMSReportPage.show();
    }

    public void showExisting() {
        SMSReportPage.show();
    }
}
