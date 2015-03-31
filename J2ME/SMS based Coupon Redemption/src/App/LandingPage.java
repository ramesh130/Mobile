
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
public class LandingPage extends Screen {

    Screen currentScreen = this;
    Form landingPage = null;

    public void show(Screen prev) {
        show();
    }

    public void show() {
        landingPage = new Form("HCRM UNNATI");
        landingPage.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label buttonLabel = new Label("Select Task");
        landingPage.addComponent(buttonLabel);
        final Button transaction = new Button("Transaction");
        transaction.setAlignment(Label.LEFT);
        final Button redemption = new Button("Redemption");
        redemption.setAlignment(Label.LEFT);
        final Button balance = new Button("Balance Enquiry");
        balance.setAlignment(Label.LEFT);

        landingPage.addComponent(transaction);
        landingPage.addComponent(redemption);
        landingPage.addComponent(balance);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Command cmd = evt.getCommand();
                if (cmd != null) {
                    switch (cmd.getId()) {
                        case EXIT_COMMAND:
                            GetMidletHandle.getInstance().getHandle().notifyDestroyed();
                            break;
                    }
                }

                Object source = evt.getSource();
                if (source != null) {
                    if (source == transaction) {
                        Transaction transaction = new Transaction();
                        transaction.show(currentScreen);
                        return;
                    }

                    if (source == redemption) {
                        Redemption redemption = new Redemption();
                        redemption.show(currentScreen);
                        return;
                    }

                    if (source == balance) {
                        ComposedMessage.getInstance().setMessage("BAL");
                        SMSPage sendSMS = new SMSPage();
                        sendSMS.show(currentScreen);
                        return;
                    }
                }
            }
        };
        transaction.addActionListener(listener);
        redemption.addActionListener(listener);
        balance.addActionListener(listener);
        landingPage.addCommand(exitCommand);
        landingPage.addCommandListener(listener);
        landingPage.show();
    }

    public void showExisting() {
        landingPage.show();
    }
}
