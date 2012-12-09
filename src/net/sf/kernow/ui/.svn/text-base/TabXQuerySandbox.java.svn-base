package net.sf.kernow.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import net.sf.kernow.xquery.ScheduledXQuery;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * The XQuery Sandbox tab 
 *
 * @author  Andrew Welch
 * @author  Florent Georges
 */
public class TabXQuerySandbox extends javax.swing.JPanel implements Tab {
    
    private NamespacesDiag nsDiag;
    private TabbedView view;
       
    private Timer timer;
    private ScheduledXQuery scheduledXQuery;
    private Highlighter highlighter;
    private DefaultHighlightPainter highlightPainter;
    private List<Object> tags = new ArrayList<Object>(); //used to maintain a pointer to highlight so it can be removed 
    private final int splitPanePos = 420;
    
    private ResourceBundle bundle = ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView");
    
    /**
     * Creates new form TabXQuerySandbox
     */
    public TabXQuerySandbox() {
        initComponents();
        
        RSyntaxTextArea rsta = ((RSyntaxTextArea)xqueryTextArea);
        rsta.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        setTextAreaStyles(rsta);

        rsta.setBracketMatchingEnabled(true);
        rsta.setAnimateBracketMatching(true);
        rsta.setMarkOccurrences(true);
        rsta.setCloseCurlyBraces(true);

        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        cp.addCompletion(new BasicCompletion(cp, "xsl:apply-templates"));
        cp.addCompletion(new BasicCompletion(cp, "declare variable $foo as xs:string := 'abcd';"));
        AutoCompletion ac = new AutoCompletion(cp);
        ac.install(rsta);

        scheduledXQuery = new ScheduledXQuery(this);
        timer = new Timer();
        highlighter = xqueryTextArea.getHighlighter();
        highlightPainter = new DefaultHighlightPainter(Color.YELLOW);
        
        //new UndoHelper().addUndoSupport(xqueryTextArea);
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
        String name = bundle.getString("XQuery_Sandbox");
        String tooltip = bundle.getString("Run_a_query_written_directly_within_Kernow");
        pane.insertTab(name, null, this, tooltip, pos);
    }

    @Override
    public void init(TabbedView view) {
        this.view = view;
        nsDiag = new NamespacesDiag(view.getConfig().getNamespaceBindings());
    }
    
    @Override
    public void loadProperties(Properties props) {
        xqueryTextArea.setText(props.getProperty("quickquery", bundle.getString("[_Enter_query_here_]")));
    }

    @Override
    public void saveProperties(Properties props) {
        props.setProperty("quickquery", xqueryTextArea.getText());
    }

    @Override
    public int getSplitPanePos() {
        return splitPanePos;
    }
    
    public String getXQuery() {
        return xqueryTextArea.getText();
    }
    
    public void processError(SaxonApiException sae) {
        //errorTextArea.setText(sae.getCause().getMessageAndLocation());
        //errorTextArea.setToolTipText(sae.getMessageAndLocation());
        
        //int line = sae.getLocator().getLineNumber();
        //int col = sae.getLocator().getColumnNumber();
        int line = 2;
        int col = 5;
        highlightErrors(line, col, sae.getMessage());
    }
    
    /**
     * Highlights errors in the edit pane by working out the position
     * of the text using the line and col number from the error message
     */
    public void highlightErrors(int line, int col, String message) {   
        String txt = xqueryTextArea.getText();
        String[] lines = txt.split("\n");
        
        int start = 0;
        
        // Add up all the characters in the line before the error
        for (int i = 0; i < line - 1; i++) {
            start += lines[i].length();
        }
        
        // Add 1 per new line that gets removed in the call to split()
        start += (line - 2);
        
        // Add the col value
        start += col;
        
        // Work out the end value - defaults to 5 wide
        int end = start + 5;
        
        // Attempts to improve the highlighting by pattern matching the 
        // quoted value in the error message
        if (message.contains("\"")) {
            Pattern pattern = Pattern.compile("\".+\"");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                end = start + matcher.group().length();
            }
        }    
        
        try {
            tags.add(highlighter.addHighlight(start, end, highlightPainter));
            xqueryTextArea.repaint(); // this is needed to force the highlighting to be drawn
        } catch (BadLocationException ex) {
            System.out.println("bad loc: " + ex.toString());
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        runButton = new javax.swing.JButton();
        namespacesButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();
        xqueryScrollPane = new RTextScrollPane();
        xqueryTextArea = new RSyntaxTextArea();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView"); // NOI18N
        runButton.setText(bundle.getString("Execute_Query")); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        namespacesButton.setText(bundle.getString("Namespaces")); // NOI18N
        namespacesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namespacesButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        errorTextArea.setBackground(new java.awt.Color(236, 233, 216));
        errorTextArea.setColumns(20);
        errorTextArea.setEditable(false);
        errorTextArea.setFont(new java.awt.Font("Monospaced", 0, 12));
        errorTextArea.setForeground(new java.awt.Color(204, 0, 0));
        errorTextArea.setLineWrap(true);
        errorTextArea.setRows(2);
        errorTextArea.setWrapStyleWord(true);
        errorTextArea.setBorder(null);
        jScrollPane2.setViewportView(errorTextArea);

        xqueryTextArea.setColumns(20);
        xqueryTextArea.setRows(5);
        xqueryTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                xqueryTextAreaKeyReleased(evt);
            }
        });
        xqueryScrollPane.setViewportView(xqueryTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(xqueryScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(namespacesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(runButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xqueryScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(namespacesButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void namespacesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namespacesButtonActionPerformed
        nsDiag.showDiag();
    }//GEN-LAST:event_namespacesButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        final String q = xqueryTextArea.getText();
        final SwingWorker worker = new SwingWorker() {
            public Object doInBackground() {
                
                view.startIndeterminant(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView").getString("Working..."));
                
                boolean success = false;

                Serializer serializer = new Serializer();
                serializer.setOutputStream(System.out);
                
                try {
                    success = view.getTransformController().runStandaloneXQuery(q, serializer, null);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                
                view.endIndeterminant(success);
                
                return "Quick Query finished.";
            }
        };
        
        view.executeService(worker);
    }//GEN-LAST:event_runButtonActionPerformed

    private void xqueryTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xqueryTextAreaKeyReleased
        errorTextArea.setText("");

        timer.cancel();
        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
               for (Object tag : tags) {
                   highlighter.removeHighlight(tag);
               }
               tags.clear(); // clear the list of highlights
               scheduledXQuery.run();
            }
        }, 2000);
    }//GEN-LAST:event_xqueryTextAreaKeyReleased
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea errorTextArea;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton namespacesButton;
    private javax.swing.JButton runButton;
    private javax.swing.JScrollPane xqueryScrollPane;
    private javax.swing.JTextArea xqueryTextArea;
    // End of variables declaration//GEN-END:variables
}
