package net.sf.kernow.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.PlainDocument;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.transform.ScheduledXML;
import net.sf.kernow.transform.ScheduledXSLT;
import net.sf.kernow.ui.util.XSLTAutoCompletion;
import net.sf.kernow.util.XMLFormatter;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.xml.sax.SAXParseException;

/**
 * The XSLT Sandbox tab 
 *
 * @author  Andrew Welch
 */
public class TabXSLTSandbox extends javax.swing.JPanel implements Tab, XMLSandboxTab {
    
    private TabbedView view;
       
    private Timer timer;
    
    private Highlighter xsltHighlighter;
    private DefaultHighlightPainter xsltHighlightPainter;
    private List<Object> xsltHighlightTags = new ArrayList<Object>();
    
    private Highlighter xmlHighlighter;
    private DefaultHighlightPainter xmlHighlightPainter;    
    private List<Object> xmlHighlightTags = new ArrayList<Object>();    
    
    private ScheduledXML scheduledXML;
    private ScheduledXSLT scheduledXSLT;
    
    private final int splitPanePos = 420;
    
    private boolean checkXML = true;
    private boolean checkXSLT = true;
    
    private ResourceBundle bundle = ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView");
    
    /**
     * Creates new form TabSandbox
     */
    public TabXSLTSandbox() {
        initComponents();

        final RSyntaxTextArea rsta = ((RSyntaxTextArea)xsltTextArea);
        rsta.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        setTextAreaStyles(rsta);

        setupPopup(rsta);
        XSLTEditorMouseAdapter.addMenuEntriesToPopup(rsta);

        DefaultCompletionProvider dc = new XSLTAutoCompletion().getCompletionProvider();
        AutoCompletion ac = new AutoCompletion(dc);
        ac.install(rsta);
                
        RSyntaxTextArea xmlRSTA = ((RSyntaxTextArea)xmlTextArea);
        xmlRSTA.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        setTextAreaStyles(xmlRSTA);
        setupPopup(xmlRSTA);

        // set the tab sizes
        rsta.getDocument().putProperty(PlainDocument.tabSizeAttribute, Integer.valueOf(4));
        xmlRSTA.getDocument().putProperty(PlainDocument.tabSizeAttribute, Integer.valueOf(4));

        xsltScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        xmlScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.scheduledXML = new ScheduledXML(this);
        this.scheduledXSLT = new ScheduledXSLT(this);
        
        timer = new Timer();
        
        xmlHighlighter = xmlTextArea.getHighlighter();
        xmlHighlightPainter = new DefaultHighlightPainter(Color.YELLOW);
        
        xsltHighlighter = xsltTextArea.getHighlighter();
        xsltHighlightPainter = new DefaultHighlightPainter(Color.YELLOW);
        
        xsltTextArea.setText(getIdentityTemplate());
        
    }

    private void setTextAreaStyles(RSyntaxTextArea textArea) {
        // Set the font for all token types.
        setFont(textArea, new Font("Courier New", Font.PLAIN, 12), new Color(0, 0, 0));

        // Change a few things here and there.
        SyntaxScheme scheme = textArea.getSyntaxScheme();
        scheme.styles[Token.MARKUP_TAG_NAME].font = new Font("Courier New", Font.PLAIN, 12);
        scheme.styles[Token.MARKUP_TAG_NAME].foreground = new Color(0, 0, 150);
        scheme.styles[Token.MARKUP_TAG_ATTRIBUTE].foreground = new Color(245, 132, 76);

        scheme.styles[Token.LITERAL_STRING_DOUBLE_QUOTE].font = new Font("Courier New", Font.PLAIN, 12);
        scheme.styles[Token.LITERAL_STRING_DOUBLE_QUOTE].foreground = new Color(153, 51, 0);

        scheme.styles[Token.IDENTIFIER].foreground = Color.black;

        textArea.setMatchedBracketBorderColor(Color.orange);
        
        textArea.setCaretColor(Color.black);
        textArea.setCurrentLineHighlightColor(new Color(249, 249, 255));
    }

    public static void setFont(RSyntaxTextArea textArea, Font font, Color color) {
        if (font!=null) {
            SyntaxScheme ss = textArea.getSyntaxScheme();
            ss = (SyntaxScheme)ss.clone();
            for (int i=0; i<ss.styles.length; i++) {
                if (ss.styles[i]!=null) {
                    ss.styles[i].font = font;
                    ss.styles[i].foreground = color;
                }
            }
            textArea.setSyntaxScheme(ss);
            textArea.setFont(font);
        }
    }

    private void setupPopup(final RSyntaxTextArea textarea) {
        JPopupMenu popup = textarea.getPopupMenu();
        popup.addSeparator();
        popup.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Format_XML")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formatted = XMLFormatter.formatXML(textarea.getText());

                if (formatted != null) {
                    textarea.setText(formatted);
                }
            }
        });
    }
    
    @Override
    public void setRunButtonsEnabled(boolean enable) {
        // empty
    }

    @Override
    public void setAllCaretPositions() {
        // empty
    }

    @Override
    public void insertInto(JTabbedPane pane, int pos) {        
        String name = java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/XSLTSandboxTab").getString("XSLT_Sandbox");
        String tooltip = java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/XSLTSandboxTab").getString("Try_out_a_transform_by_coding_the_XML_and_XSLT_here");
        pane.insertTab(name, null, this, tooltip, pos);
    }

    @Override
    public void init(TabbedView view) {
        this.view = view;
    }
    
    @Override
    public void loadProperties(Properties props) {
        innerSplitPane.setDividerLocation(Integer.parseInt(props.getProperty("xsltSandboxInnerSplitPanePos", "100")));
        String savedXSLT = props.getProperty("xsltsandbox_xslt", "");
        if (savedXSLT.trim().equals("")) {
            savedXSLT = getIdentityTemplate();
        }
        xsltTextArea.setText(savedXSLT);
        xmlTextArea.setText(props.getProperty("xsltsandbox_xml"));
    }

    @Override
    public void saveProperties(Properties props) {
        props.setProperty("xsltsandbox_xslt", xsltTextArea.getText());
        props.setProperty("xsltsandbox_xml", xmlTextArea.getText());
        props.setProperty("xsltSandboxInnerSplitPanePos", Integer.toString(innerSplitPane.getDividerLocation()));        
    }
    
    @Override
    public int getSplitPanePos() {
        return splitPanePos;
    }    

    @Override
    public String getXML() {
        return xmlTextArea.getText();
    }    
    
    public String getXSLT() {
        return xsltTextArea.getText();
    }
    
    public void processXSLTError(TransformerException tce) {        
        errorTextArea.setText(tce.getMessageAndLocation());
        errorTextArea.setToolTipText(tce.getMessageAndLocation());
        if (tce.getLocator() != null) {
            int line = tce.getLocator().getLineNumber();
            int col = tce.getLocator().getColumnNumber();
            highlightErrors(line, col, xsltTextArea, xsltHighlighter, xsltHighlightPainter, xsltHighlightTags);
        }
    }
    
    public void processXSLTError(SAXParseException spe) {                        
        int line = spe.getLineNumber();
        int col = spe.getColumnNumber();
        String msg = "Line " + line + ", Col " + col + " " + spe.getMessage();
        
        errorTextArea.setText(msg);
        errorTextArea.setToolTipText(msg);
        
        highlightErrors(line, col, xsltTextArea, xsltHighlighter, xsltHighlightPainter, xsltHighlightTags);
    }    
    
    @Override
    public void processXMLError(SAXParseException spe) {                        
        int line = spe.getLineNumber();
        int col = spe.getColumnNumber();
        String msg = "Line " + line + ", Col " + col + " " + spe.getMessage();
        
        errorTextArea.setText(msg);
        errorTextArea.setToolTipText(msg);
        
        // sub 1 off the line as the message is 1 based and the highlighter is 0 based
        highlightErrors(line, col - 1, xmlTextArea, xmlHighlighter, xmlHighlightPainter, xmlHighlightTags);
    }    
    
    /**
     * Highlights errors in the edit pane by working out the position
     * of the text using the line and col number from the error message
     */
    public void highlightErrors(int lineNumber, int col, JTextArea textarea,
        
        Highlighter highlighter, DefaultHighlightPainter highlighterPainter, List<Object> tagList) {   
        
        try {
            String txt = textarea.getText();
            String[] lines = txt.split("\n");

            int start = 0;

            if (lineNumber <= lines.length) {
                // Add up all the characters in the line before the error
                for (int i = 0; i < lineNumber - 1; i++) {
                    start += lines[i].length();
                }            
            
                // Add the col value
                col = (col == -1)?0:col;  // make col = 0 if col = -1 so it doesnt go back a line
                start += col;

                int end = start;
                start -= 8;

                tagList.add(highlighter.addHighlight(start, end, highlighterPainter));
                textarea.repaint(); // this is needed to force the highlighting to be drawn
            }
        } catch (BadLocationException ex) {
            System.out.println("bad loc: " + ex.toString());
        } catch (Exception e) {
            // do nothing
        }
    }
    
    private void runTimer(final TimerTask task, final Highlighter highlighter, final List<Object> tagList) {
        errorTextArea.setText("");
        errorTextArea.setToolTipText("");      
        
        timer.cancel();
        timer = new Timer();
                
        timer.schedule(new TimerTask() {
            @Override
            public void run() {  
               for (Object tag : tagList) {
                   highlighter.removeHighlight(tag);
               }
               tagList.clear();
               task.run();
            }
        }, 2000);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        innerSplitPane = new javax.swing.JSplitPane();
        xmlScrollPane = new RTextScrollPane();
        xmlTextArea = new RSyntaxTextArea();
        xsltScrollPane = new RTextScrollPane();
        xsltTextArea = new RSyntaxTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        syntaxCheckXMLChkBx = new javax.swing.JCheckBox();
        syntaxCheckXSLTChkBx = new javax.swing.JCheckBox();
        runTransformButton = new javax.swing.JButton();

        xmlTextArea.setColumns(20);
        xmlTextArea.setRows(5);
        xmlTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                xmlTextAreaKeyReleased(evt);
            }
        });
        xmlScrollPane.setViewportView(xmlTextArea);

        innerSplitPane.setLeftComponent(xmlScrollPane);

        xsltTextArea.setColumns(20);
        xsltTextArea.setRows(5);
        xsltTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                xsltTextAreaKeyReleased(evt);
            }
        });
        xsltScrollPane.setViewportView(xsltTextArea);

        innerSplitPane.setRightComponent(xsltScrollPane);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        errorTextArea.setBackground(new java.awt.Color(236, 233, 216));
        errorTextArea.setColumns(20);
        errorTextArea.setEditable(false);
        errorTextArea.setFont(new java.awt.Font("Monospaced", 0, 12));
        errorTextArea.setForeground(new java.awt.Color(204, 0, 0));
        errorTextArea.setLineWrap(true);
        errorTextArea.setRows(2);
        errorTextArea.setWrapStyleWord(true);
        errorTextArea.setBorder(null);
        errorTextArea.setSelectionColor(new java.awt.Color(204, 204, 204));
        jScrollPane3.setViewportView(errorTextArea);

        jLabel1.setText("Check:");

        syntaxCheckXMLChkBx.setSelected(true);
        syntaxCheckXMLChkBx.setText("XML");
        syntaxCheckXMLChkBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syntaxCheckXMLChkBxActionPerformed(evt);
            }
        });

        syntaxCheckXSLTChkBx.setSelected(true);
        syntaxCheckXSLTChkBx.setText("XSLT");
        syntaxCheckXSLTChkBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syntaxCheckXSLTChkBxActionPerformed(evt);
            }
        });

        runTransformButton.setText("Run Transform");
        runTransformButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runTransformButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(syntaxCheckXMLChkBx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(syntaxCheckXSLTChkBx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(runTransformButton))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(innerSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(innerSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(syntaxCheckXMLChkBx)
                    .addComponent(syntaxCheckXSLTChkBx)
                    .addComponent(runTransformButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void syntaxCheckXMLChkBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syntaxCheckXMLChkBxActionPerformed
        xmlHighlighter.removeAllHighlights();
        errorTextArea.setText("");
        errorTextArea.setToolTipText("");
        setCheckXML(syntaxCheckXMLChkBx.isSelected());
}//GEN-LAST:event_syntaxCheckXMLChkBxActionPerformed

    private void syntaxCheckXSLTChkBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syntaxCheckXSLTChkBxActionPerformed
        xsltHighlighter.removeAllHighlights();
        errorTextArea.setText("");
        errorTextArea.setToolTipText("");
        setCheckXSLT(syntaxCheckXSLTChkBx.isSelected());
    }//GEN-LAST:event_syntaxCheckXSLTChkBxActionPerformed

private void runTransformButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runTransformButtonActionPerformed
    xsltHighlighter.removeAllHighlights();
        errorTextArea.setText("");
        
        // recreate scheduledXSLT to force it to create a new TransformerFactory
        // just in case the user has switched between basic and SA processors
        this.scheduledXSLT = new ScheduledXSLT(this);
        
        final SwingWorker worker = new SwingWorker() {
            @Override
            public Object doInBackground() {
                
                view.startIndeterminant(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView").getString("Working..."));
                
                boolean success = false;
                
                final Source input = new StreamSource(new StringReader(getXML()));
                final Source stylesheet = new StreamSource(new StringReader(getXSLT()));
                
                input.setSystemId("XSLTSandbox_XML");
                stylesheet.setSystemId("XSLTSandbox_XSLT");
                                        
                success = scheduledXSLT.transform();
                
                view.setProgressText("Done");
                
                view.endIndeterminant(success);
                                
                return "Quick Query finished.";
            }
        };
        
        view.executeService(worker);
}//GEN-LAST:event_runTransformButtonActionPerformed

private void xsltTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xsltTextAreaKeyReleased
    if (checkXSLT) {
        runTimer(scheduledXSLT, xsltHighlighter, xsltHighlightTags);
    }
}//GEN-LAST:event_xsltTextAreaKeyReleased

private void xmlTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xmlTextAreaKeyReleased
    if (checkXML) {
        runTimer(scheduledXML, xmlHighlighter, xmlHighlightTags);
    }
}//GEN-LAST:event_xmlTextAreaKeyReleased
    
    private String getIdentityTemplate() {
        String identity = "<xsl:stylesheet \n" +
                "    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"2.0\"\n" +
                "    exclude-result-prefixes=\"xs\">\n" +
                "\n" +
                "<xsl:template match=\"@*|node()\">\n" +
                "    <xsl:copy>\n" +
                "        <xsl:apply-templates select=\"@*|node()\"/>\n" +
                "    </xsl:copy>\n" +
                "</xsl:template>\n" +
                "    \n" +
                "</xsl:stylesheet>";
        return identity;
    }

    public boolean isCheckXML() {
        return checkXML;
    }

    public void setCheckXML(boolean checkXML) {
        this.checkXML = checkXML;
    }

    public boolean isCheckXSLT() {
        return checkXSLT;
    }

    public void setCheckXSLT(boolean checkXSLT) {
        this.checkXSLT = checkXSLT;
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea errorTextArea;
    private javax.swing.JSplitPane innerSplitPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton runTransformButton;
    private javax.swing.JCheckBox syntaxCheckXMLChkBx;
    private javax.swing.JCheckBox syntaxCheckXSLTChkBx;
    private javax.swing.JScrollPane xmlScrollPane;
    private javax.swing.JTextArea xmlTextArea;
    private javax.swing.JScrollPane xsltScrollPane;
    private javax.swing.JTextArea xsltTextArea;
    // End of variables declaration//GEN-END:variables
}
