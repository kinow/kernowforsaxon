/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import javax.xml.transform.SourceLocator;
import net.sf.saxon.s9api.MessageListener;
import net.sf.saxon.s9api.XdmNode;

/**
 *
 * @author andrew
 */
public class CustomMessageListener implements MessageListener {

    private static CustomMessageListener instance = new CustomMessageListener();

    private CustomMessageListener() {

    }
    
    public static CustomMessageListener getInstance() {
        return instance;
    }

    @Override
    public void message(XdmNode xn, boolean bln, SourceLocator sl) {
        System.out.println(xn.getStringValue());
    }
}
