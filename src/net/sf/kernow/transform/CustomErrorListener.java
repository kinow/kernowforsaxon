/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

/**
 *
 * @author andrew
 */
public class CustomErrorListener implements ErrorListener {

    private static CustomErrorListener instance = new CustomErrorListener();

    private CustomErrorListener() {
        
    }

    public static CustomErrorListener getInstance() {
        return instance;
    }
    
    @Override
    public void warning(TransformerException exception) throws TransformerException {
        System.err.println(exception);
    }

    @Override
    public void error(TransformerException exception) throws TransformerException {
        System.err.println(exception);
    }

    @Override
    public void fatalError(TransformerException exception) throws TransformerException {
        System.err.println(exception);
    }
 }