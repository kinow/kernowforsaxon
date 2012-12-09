/*
 * TextAreaPrintStream.java
 *
 * Created on 30 November 2005, 12:45
 */

package net.sf.kernow.ui;

import java.io.*;
import java.nio.charset.Charset;
import javax.swing.*;

/**
 * This class redirects the output sent to a PrintStream to a JEditorPane.
 * @author AWelch
 */
public class TextAreaPrintStream extends PrintStream {
    
    private JTextArea textArea;

    /**
     * Method TextAreaPrintStream
     * The constructor of the class.
     * @param area the JEditorPane to which the output stream will be redirected.
     * @param out a standard output stream (needed by super method)
     **/  
    public TextAreaPrintStream(JTextArea textArea, OutputStream out) {
        super(out);
        this.textArea = textArea;
    }        

    @Override
    public void println(String string) {
        //super.print(string);
        String str = string + "\n";
        textArea.append(str);
        updateCaretPos(str.length());        
    }
    
    @Override
    public void print(String string) {
        //super.print(string);        
        textArea.append(string);
        updateCaretPos(string.length());
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        //super.write(buf, off, len);
        String str = new String(buf, off, len, Charset.forName("UTF-8"));        
        textArea.append(str);
        updateCaretPos(str.length());
    }
    
    @Override
    public void write(byte[] b) throws IOException {
        //super.write(b);
        String str = new String(b, Charset.forName("UTF-8"));        
        textArea.append(str);
        updateCaretPos(str.length());
    }

    
    private void updateCaretPos(int addition) {
        //textArea.setCaretPosition(textArea.getCaretPosition() + addition);
        //textArea.insert("x", textArea.getCaretPosition());
    }
    
}

