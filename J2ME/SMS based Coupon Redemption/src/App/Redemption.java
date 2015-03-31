
package App;

/**
 *
 * @author ramesh
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Redemption extends Screen {
    Screen currentScreen = this;
    Form RedemptionPage = null;
    TextField productCodetextArea = null;
    TextField QuantitytextArea = null;

    public void show(Screen prev) {
        super.show(prev);
        show();
    }

    public void show() {
        RedemptionPage = new Form("HCRM UNNATI");
        RedemptionPage.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label buttonLabel = new Label("Request Redemption");
        RedemptionPage.addComponent(buttonLabel);

        Label productCodeLabel = new Label("Product Code");
        Label QuantityLabel = new Label("Quantity");


/*        TextArea productCodetextArea = new TextArea(1, 20, TextArea.ANY);
        RedemptionPage.addComponent(productCodeLabel);
        RedemptionPage.addComponent(productCodetextArea);

        TextArea QuantitytextArea = new TextArea(1, 20, TextArea.ANY);
        RedemptionPage.addComponent(QuantityLabel);
        RedemptionPage.addComponent(QuantitytextArea);*/

        productCodetextArea = new TextField("");
        //productCodetextArea.setConstraint(TextField.INITIAL_CAPS_SENTENCE);
        //productCodetextArea.setInputModeOrder(new String[]{"ABC"});
        productCodetextArea.setInputMode("ABC");
        productCodetextArea.setUseSoftkeys(false);
        //productCodetextArea.setQwertyAutoDetect(true);
        RedemptionPage.addComponent(productCodeLabel);
        RedemptionPage.addComponent(productCodetextArea);

        QuantitytextArea = new TextField("");
        QuantitytextArea.setConstraint(TextArea.NUMERIC);
        QuantitytextArea.setInputModeOrder(new String[]{"123"});
        QuantitytextArea.setUseSoftkeys(false);
        //QuantitytextArea.setQwertyAutoDetect(true);
        RedemptionPage.addComponent(QuantityLabel);
        RedemptionPage.addComponent(QuantitytextArea);


        RedemptionPage.addCommand(backCommand);
        RedemptionPage.addCommand(sendCommand);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Command cmd = evt.getCommand();
                switch (cmd.getId()) {
                    case BACK_COMMAND:
                        gotoPrevScreen();
                        break;
                    case SEND_COMMAND:
                        ComposedMessage.getInstance().setMessage(
                                productCodetextArea.getText() + " " +
                                QuantitytextArea.getText());
                        SMSPage sendSMS = new SMSPage();
                        sendSMS.show(currentScreen);
                        break;
                }
            }
        };

        RedemptionPage.addCommandListener(listener);
        RedemptionPage.show();
    }

     public void showExisting()
     {
         RedemptionPage.show();
     }
}
