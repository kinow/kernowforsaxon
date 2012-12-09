
package net.sf.kernow.ui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

/**
 *
 * @author welcha
 */
public class MouseListener implements MouseMotionListener {

    private JFrame jframe;
    private int cursorType;
    
    public MouseListener(JFrame jframe, int cursorType) {
        this.jframe = jframe;
        this.cursorType = cursorType;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseHand(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseHand(e);
    }

    public void mouseHand(MouseEvent event) {
        if (!jframe.getCursor().equals(Cursor.getPredefinedCursor(cursorType))) {
            jframe.setCursor(new Cursor(cursorType));
        }
    }

}
