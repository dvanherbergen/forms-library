package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.Vector;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.swing.operator.ComponentWrapper;

import sun.awt.AppContext;

/**
 * Operator for working with the main Oracle Forms Window Frame.
 */
@SuppressWarnings("restriction")
public class FrameOperator extends ContainerOperator implements ComponentWrapper {

    private static final String ORACLE_FORMS_FRAME_TITLE = "Oracle Fusion Middleware Forms Services";

    /**
     * Create a new frame operator for a provided Frame.
     */
    public FrameOperator(Container frame) {
        super(frame);
    }

    /**
     * Create a new default FrameOperator that targets the main
     * javax.swing.JFrame window used by the Oracle Forms application.
     */
    public FrameOperator() {
        this(findFrame(ORACLE_FORMS_FRAME_TITLE, true));
    }

    @SuppressWarnings("unchecked")
    private static Frame findFrame(String title, boolean failIfNotFound) {

        Vector<WeakReference<Window>> windowList = (Vector<WeakReference<Window>>) AppContext.getAppContext().get(Window.class);
        if (windowList != null) {
            for (int i = 0; i < windowList.size(); i++) {
                Window w = windowList.get(i).get();
                if (w != null) {
                    if (w instanceof Frame) {
                        Frame f = (Frame) w;
                        if (ORACLE_FORMS_FRAME_TITLE.equals(f.getTitle())) {
                            return f;
                        }
                    }
                }
            }
        }
        if (failIfNotFound) {
            throw new FormsLibraryException("Frame '" + ORACLE_FORMS_FRAME_TITLE
                    + "' not found in AppContext. Try restarting the application or checking the context first with isContextInvalid");
        }
        return null;
    }

    /**
     * Check if the main Oracle Forms Frame still contains the given component.
     */
    public boolean containsComponent(Component component) {
        return ComponentUtil.containsComponent(getSource(), component);
    }

    /**
     * Check if the Oracle Forms frame is part of the current AppContext. This
     * will not be the case if Java Web Start has downloaded a new version of
     * the jars. That download causes the frame to be included in a different
     * threads' appContext.
     */
    public static boolean isFrameInCurrentAppContext() {
        return findFrame(ORACLE_FORMS_FRAME_TITLE, false) != null;
    }

}
