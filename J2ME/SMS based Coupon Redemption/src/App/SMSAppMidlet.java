
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
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.LookAndFeel;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import java.io.IOException;
import java.util.Hashtable;

public class SMSAppMidlet extends MIDlet implements /*OutgoingTextMessageListener,*/
        ActionListener {
    private Resources res;
    //private static SmsHandler smsHandler = null;
    //private String port = null;

    public void startApp() {
        try {
            //By using the VKBImplementationFactory.init() we automatically
            //bundle the LWUIT Virtual Keyboard.
            //If your application is not aimed to touch screen devices,
            //this line of code should be removed.
            VKBImplementationFactory.init();
            Display.init(this);
            //set the theme
            Resources theme = Resources.open("/LWUITtheme.res");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
            //open the resources file that contains all the icons
            res = Resources.open("/resources.res");
            //although calling directly to setMainForm(res) will work on
            //most devices, a good coding practice will be to allow the midp
            //thread to return and to do all the UI on the EDT.
            Display.getInstance().callSerially(new Runnable() {

                public void run() {
                    if (!setSmsHandler())//check and show dialog
                    {
                        setErrorForm();
                    } else {
                        setMainForm(res);
                    }
                }
            });

        } catch (Throwable ex) {
            ex.printStackTrace();
            Dialog.show("Exception", ex.getMessage(), "OK", null);
        }
    }

    void setMainForm(Resources r) {
        //smsHandler.init(this, port);
        //smsHandler.sendMessage("+918600907272", "Hello LWUIT!");
        UIManager.getInstance().setResourceBundle(r.getL10N("localize", "en"));

        GetMidletHandle.getInstance().setMidletHandle(this);

        final Form mainMenu = new Form("");
        try {
            Resources images = SMSAppMidlet.getResource("images");
            Image a = images.getImage("hcrm.png");

            Label logo = new Label() {

                private long lastInvoke;
                private long count=0;

                public void initComponent() {
                    mainMenu.registerAnimated(this);
                }

                public boolean animate() {
                    long current = System.currentTimeMillis();
                    if (current - lastInvoke > 100) {
                        count++;
                        if(count > 50){
                        lastInvoke = current;
                        LandingPage newPage = new LandingPage();
                        newPage.show(null);
                        return true;
                        }
                    }
                    return false;
                }
            };
            logo.setAlignment(mainMenu.CENTER);
            logo.setIcon(a);
            mainMenu.setLayout(new BorderLayout());
            mainMenu.addComponent(BorderLayout.CENTER, logo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mainMenu.show();
    }

    void setErrorForm() {
        final Form errorMenu = new Form("HCRM");

        Label buttonLabel = new Label("Unsupported Handset!");
        errorMenu.addComponent(buttonLabel);
        errorMenu.addCommand(new Command("Exit") {

            public void actionPerformed(ActionEvent evt) {
                notifyDestroyed();
            }
        });
        errorMenu.show();
    }

    /**
     * Used instead of using the Resources API to allow us to fetch locally downloaded
     * resources
     *
     * @param name the name of the resource
     * @return a resources object
     */
    public static Resources getResource(String name) throws IOException {
        return Resources.open("/" + name + ".res");
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        /*if (smsHandler != null) {
            smsHandler.abort();
        }*/
        notifyDestroyed();
    }

    public void actionPerformed(ActionEvent ae) {
        notifyDestroyed();
    }

    private boolean setSmsHandler() {
        boolean jsrTest = true;
        //port = "7500";//getAppProperty("Default-Port");
        try {
            Class.forName("javax.wireless.messaging.Message");
            //smsHandler = (SmsHandler) Class.forName("App.WmaSmsHandler").newInstance();
        } catch (Exception e) {
            jsrTest = false;
        }
        return jsrTest;
       /* if (smsHandler == null) {
            //Native Handler
        }
        return (smsHandler != null);*/
    }

}
