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
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.PlainDocument;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.transform.ScheduledValidation;
import net.sf.kernow.transform.ScheduledXML;
import net.sf.kernow.util.XMLFormatter;
import net.sf.saxon.type.SchemaException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.xml.sax.SAXParseException;

/**
 * The XSD Sandbox tab 
 *
 * @author  Andrew Welch
 */
public class TabXMLSchemaSandbox extends javax.swing.JPanel implements Tab, XMLSandboxTab {
    
    private TabbedView view;
       
    private Timer timer;
    
    private Highlighter xsdHighlighter;
    private DefaultHighlightPainter xsdHighlightPainter;
    private List<Object> xsdHighlightTags = new ArrayList<Object>();
    
    private Highlighter xmlHighlighter;
    private DefaultHighlightPainter xmlHighlightPainter;    
    private List<Object> xmlHightlightTags = new ArrayList<Object>();
    
    private ScheduledXML scheduledXML;
    private ScheduledValidation scheduledXSD;
    
    private static final int splitPanePos = 420;
    
    private boolean checkXML = true;
    private boolean checkXSD = true;
    
    private ResourceBundle bundle = ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView");
    
    /**
     * Creates new form TabSandbox
     */
    public TabXMLSchemaSandbox() {
        initComponents();
        
        final RSyntaxTextArea xsdRsta = ((RSyntaxTextArea)xsdTextArea);
        xsdRsta.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        setTextAreaStyles(xsdRsta);

        setupPopup(xsdRsta);
        XMLSchemaEditorMouseAdapter.addMenuEntriesToPopup(xsdRsta);

        //DefaultCompletionProvider dc = new XSLTAutoCompletion().getCompletionProvider();
        //AutoCompletion ac = new AutoCompletion(dc);
        //ac.install(rsta);

        RSyntaxTextArea xmlRSTA = ((RSyntaxTextArea)xmlTextArea);
        xmlRSTA.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        setTextAreaStyles(xmlRSTA);
        setupPopup(xmlRSTA);

        // set the tab sizes
        xsdRsta.getDocument().putProperty(PlainDocument.tabSizeAttribute, Integer.valueOf(4));
        xmlRSTA.getDocument().putProperty(PlainDocument.tabSizeAttribute, Integer.valueOf(4));
        
        this.scheduledXML = new ScheduledXML(this);
        this.scheduledXSD = new ScheduledValidation(this);
        
        timer = new Timer();
        
        xmlHighlighter = xmlTextArea.getHighlighter();
        xmlHighlightPainter = new DefaultHighlightPainter(Color.YELLOW);
        
        xsdHighlighter = xsdTextArea.getHighlighter();
        xsdHighlightPainter = new DefaultHighlightPainter(Color.YELLOW);
        
        xsdTextArea.setText(getDefaultSchema());
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
        String name = "XML Schema Sandbox";
        String tooltip = "Try some validation code here";
        pane.insertTab(name, null, this, tooltip, pos);
    }

    @Override
    public void init(TabbedView view) {
        this.view = view;
    }
    
    @Override
    public void loadProperties(Properties props) {
        innerSplitPane.setDividerLocation(Integer.parseInt(props.getProperty("xmlSchemaSandboxInnerSplitPanePos", "100")));
        String savedXSLT = props.getProperty("xmlschemasandbox_xsd", "");
        if (savedXSLT.trim().equals("")) {
            savedXSLT = getDefaultSchema();
        }
        xsdTextArea.setText(savedXSLT);
        xmlTextArea.setText(props.getProperty("xmlschemasandbox_xml"));
    }

    @Override
    public void saveProperties(Properties props) {
        props.setProperty("xmlschemasandbox_xsd", xsdTextArea.getText());
        props.setProperty("xmlschemasandbox_xml", xmlTextArea.getText());
        props.setProperty("xmlSchemaSandboxInnerSplitPanePos", Integer.toString(innerSplitPane.getDividerLocation()));        
    }
    
    @Override
    public int getSplitPanePos() {
        return splitPanePos;
    }    

    @Override
    public String getXML() {
        return xmlTextArea.getText();
    }    
    
    public String getXSD() {
        return xsdTextArea.getText();
    }       
    
    public void processXMLSchemaError(SAXParseException spe) {                        
        int line = spe.getLineNumber();
        int col = spe.getColumnNumber();
        String msg = "Line " + line + ", Col " + col + " " + spe.getMessage();
        
        errorTextArea.setText(msg);
        errorTextArea.setToolTipText(msg);
        
        highlightErrors(line, col, xsdTextArea, xsdHighlighter, xsdHighlightPainter, xsdHighlightTags);
    }    

    public void processXMLSchemaError(SchemaException se) {                        
        int line = se.getLocator().getLineNumber();
        int col = se.getLocator().getColumnNumber();
        String msg = "Line " + line + ", Col " + col + " " + se.getMessage();
        
        errorTextArea.setText(msg);
        errorTextArea.setToolTipText(msg);
        
        highlightErrors(line, col, xsdTextArea, xsdHighlighter, xsdHighlightPainter, xsdHighlightTags);
    }    

    @Override
    public void processXMLError(SAXParseException spe) {                        
        int line = spe.getLineNumber();
        int col = spe.getColumnNumber();
        String msg = "Line " + line + ", Col " + col + " " + spe.getMessage();
        
        errorTextArea.setText(msg);
        errorTextArea.setToolTipText(msg);
        
        // sub 1 off the line as the message is 1 based and the highlighter is 0 based
        highlightErrors(line, col - 1, xmlTextArea, xmlHighlighter, xmlHighlightPainter, xmlHightlightTags);
    }    
    
    /**
     * Highlights errors in the edit pane by working out the position
     * of the text using the line and col number from the error message
     */
    public void highlightErrors(int line, int col, JTextArea textArea,
            Highlighter highlighter, DefaultHighlightPainter highlighterPainter, List<Object> tagList) {   
        
        try {
            String txt = textArea.getText();
            String[] lines = txt.split("\n");

            int start = 0;

            // Add up all the characters in the line before the error
            for (int i = 0; i < line - 1; i++) {
                start += lines[i].length();
            }

            // Add 1 per new line that gets removed in the call to split()
            start += (line - 2) > 0?line - 2:0;

            // Add the col value
            col = (col == -1)?0:col;  // make col = 0 if col = -1 so it doesnt go back a line
            start += col;

            if (line == -1) { 
                line = 0;  // fix it where the line isnt known
            }
            
            // make the end the end of the line
            int end = start + (lines[line - 1].length() - col);

            // adjust the values when start and end are close to ensure the highlighting is visible
            if (end - start < 5) {
                start -= 5;
            }        
        
            tagList.add(highlighter.addHighlight(start, end, highlighterPainter));
            textArea.repaint(); // this is needed to force the highlighting to be drawn
            
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

        runValidationButton = new javax.swing.JButton();
        innerSplitPane = new javax.swing.JSplitPane();
        xmlScrollPane = new RTextScrollPane();
        xmlTextArea = new RSyntaxTextArea();
        xsdScrollPane = new RTextScrollPane();
        xsdTextArea = new RSyntaxTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        syntaxCheckXMLChkBx = new javax.swing.JCheckBox();
        syntaxCheckXSDChkBx = new javax.swing.JCheckBox();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/XSLTSandboxTab"); // NOI18N
        runValidationButton.setText(bundle.getString("Run_Transform")); // NOI18N
        runValidationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runValidationButtonActionPerformed(evt);
            }
        });

        xmlTextArea.setColumns(20);
        xmlTextArea.setRows(5);
        xmlTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                xmlTextAreaKeyReleased(evt);
            }
        });
        xmlScrollPane.setViewportView(xmlTextArea);

        innerSplitPane.setLeftComponent(xmlScrollPane);

        xsdTextArea.setColumns(20);
        xsdTextArea.setRows(5);
        xsdTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                xsdTextAreaKeyReleased(evt);
            }
        });
        xsdScrollPane.setViewportView(xsdTextArea);

        innerSplitPane.setRightComponent(xsdScrollPane);

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

        syntaxCheckXSDChkBx.setSelected(true);
        syntaxCheckXSDChkBx.setText("XSD");
        syntaxCheckXSDChkBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syntaxCheckXSDChkBxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(innerSplitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(syntaxCheckXMLChkBx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(syntaxCheckXSDChkBx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(runValidationButton))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 363, Short.MAX_VALUE))
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
                    .addComponent(runValidationButton)
                    .addComponent(jLabel1)
                    .addComponent(syntaxCheckXMLChkBx)
                    .addComponent(syntaxCheckXSDChkBx))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void runValidationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runValidationButtonActionPerformed
        xsdHighlighter.removeAllHighlights();        
        errorTextArea.setText("");
        
        // recreate scheduledValidation 
        this.scheduledXSD = new ScheduledValidation(this);
        
        final SwingWorker worker = new SwingWorker() {
            @Override
            public Object doInBackground() {
                
                view.startIndeterminant(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView").getString("Working..."));
                
                boolean success = false;
                
                final Source input = new StreamSource(new StringReader(getXML()));
                final Source xsd = new StreamSource(new StringReader(getXSD()));
                
                input.setSystemId("XSDSandbox_XML");
                xsd.setSystemId("XSDSandbox_XSD");
                                        
                success = scheduledXSD.validate();
                
                view.setProgressText("Done");
                
                view.endIndeterminant(success);
                                
                return "XML Schema Sandbox finished.";
            }
        };
        
        view.executeService(worker);
}//GEN-LAST:event_runValidationButtonActionPerformed

    private void syntaxCheckXMLChkBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syntaxCheckXMLChkBxActionPerformed
        xmlHighlighter.removeAllHighlights();
        errorTextArea.setText("");
        errorTextArea.setToolTipText("");
        setCheckXML(syntaxCheckXMLChkBx.isSelected());
}//GEN-LAST:event_syntaxCheckXMLChkBxActionPerformed

    private void syntaxCheckXSDChkBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syntaxCheckXSDChkBxActionPerformed
        xsdHighlighter.removeAllHighlights();
        errorTextArea.setText("");
        errorTextArea.setToolTipText("");
        setCheckXSD(syntaxCheckXSDChkBx.isSelected());
}//GEN-LAST:event_syntaxCheckXSDChkBxActionPerformed

    private void xmlTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xmlTextAreaKeyReleased
        if (checkXML) {
            runTimer(scheduledXML, xmlHighlighter, xmlHightlightTags);
        }
    }//GEN-LAST:event_xmlTextAreaKeyReleased

    private void xsdTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xsdTextAreaKeyReleased
        if (checkXSD) {
            runTimer(scheduledXSD, xsdHighlighter, xsdHighlightTags);
        }
    }//GEN-LAST:event_xsdTextAreaKeyReleased
    
    private String getDefaultSchema() {
        String identity = "<xs:schema \n" +
                "    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "    elementFormDefault=\"qualified\">\n" +
                "\n" +
                "<xs:element name=\"foo\" type=\"xs:string\"/>\n" +
                "    \n" +
                "</xs:schema>";
        return identity;
    }

    public boolean isCheckXML() {
        return checkXML;
    }

    public void setCheckXML(boolean checkXML) {
        this.checkXML = checkXML;
    }

    public boolean isCheckXSD() {
        return checkXSD;
    }

    public void setCheckXSD(boolean checkXSD) {
        this.checkXSD = checkXSD;
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea errorTextArea;
    private javax.swing.JSplitPane innerSplitPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton runValidationButton;
    private javax.swing.JCheckBox syntaxCheckXMLChkBx;
    private javax.swing.JCheckBox syntaxCheckXSDChkBx;
    private javax.swing.JScrollPane xmlScrollPane;
    private javax.swing.JTextArea xmlTextArea;
    private javax.swing.JScrollPane xsdScrollPane;
    private javax.swing.JTextArea xsdTextArea;
    // End of variables declaration//GEN-END:variables
}
