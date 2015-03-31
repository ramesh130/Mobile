
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
public abstract class Screen {
    public static final int BACK_COMMAND = 1;
    public static final int SEND_COMMAND = 2;
    public static final int EXIT_COMMAND = 3;
    public static final Command exitCommand = new Command("Exit", EXIT_COMMAND);
    public static final Command backCommand = new Command("Back", BACK_COMMAND);
    public static final Command sendCommand = new Command("Send", SEND_COMMAND);

    Screen prevScreen = null;
    public void show(Screen prev)
    {
        prevScreen = prev;
    };

    public abstract void show();

    public final void gotoPrevScreen()
    {
        prevScreen.showExisting();
    };

    public abstract void showExisting();
}
