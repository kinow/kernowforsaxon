/*
 * TabbedViewComponentListener.java
 *
 * Created on 10 November 2005, 11:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.ui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * The component listener for the et.
 * @author AWelch
 */
public class TabbedViewComponentListener implements ComponentListener {
    
    private TabbedView et;
    
    public TabbedViewComponentListener(TabbedView et) {
        this.et = et;
    }
    
    // Methods from ComponentListener
    public void componentResized(ComponentEvent e) {

        int width = et.getWidth();
        int height = et.getHeight();

        //we check if either the width
        //or the height are below minimum

        boolean resize = false;

        if (width < et.MIN_WIDTH) {
            resize = true;
            width = et.MIN_WIDTH;
        }
        if (height < et.MIN_HEIGHT) {
            resize = true;
            height = et.MIN_HEIGHT;
        }
        if (resize) {
            et.setSize(width, height);
        }
        
        et.setAllCaretPositions();
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}
