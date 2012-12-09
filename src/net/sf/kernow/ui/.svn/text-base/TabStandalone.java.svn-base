/*
 * TabStandalone.java
 *
 * Created on August 9, 2007, 1:38 AM
 */

package net.sf.kernow.ui;

import net.sf.kernow.ui.util.ComboBoxUtil;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.xml.transform.Source;
import net.sf.kernow.Message;
import net.sf.kernow.ui.util.DisablingToggleButtonController;
import net.sf.kernow.ui.util.FileComboController;
import net.sf.kernow.util.FileUtil;
import net.sf.kernow.util.IOUtils;
import net.sf.kernow.util.NamedTemplateExtractor;
import net.sf.saxon.s9api.Serializer;

/**
 *
 * @author  Florent Georges
 */
public class TabStandalone extends javax.swing.JPanel implements Tab {
    
    private TabbedView view;
    private FileComboController action;
    private FileComboController output;
    private DisablingToggleButtonController disablingOutput;
    
    private final int splitPanePos = 220;
    
    private ResourceBundle bundle = ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView");
    
    public TabStandalone() {
        initComponents();
        
        // file controllers
        action = new FileComboController(actionCombo, actionButton, false);
        output = new FileComboController(outputCombo, outputButton, false);
        
        // disabling controller, for the "output" group
        disablingOutput = new DisablingToggleButtonController(sendToFile);
        disablingOutput.add(outputCombo);
        disablingOutput.add(outputButton);
        disablingOutput.add(outputLabel);
    }
    
    @Override
    public void init(TabbedView view) {
        this.view = view;
    }

    @Override
    public void insertInto(JTabbedPane pane, int pos) {
        String name = bundle.getString("Standalone");
        String tooltip = bundle.getString("Run_a_standalone_stylesheet_or_query");
        pane.insertTab(name, null, this, tooltip, pos);
    }

    @Override
    public void loadProperties(Properties props) {
        action.loadValues(props, "standaloneXSLTCmbBx");
        output.loadValues(props, "standaloneOutputFileCmbBx");
        sendToFile.setSelected(Boolean.parseBoolean(props.getProperty("standaloneSendOutputToFileChkBox", "false")));
    }

    @Override
    public void saveProperties(Properties props) {
        action.saveValues(props, "standaloneXSLTCmbBx");
        output.saveValues(props, "standaloneOutputFileCmbBx");
        props.setProperty("standaloneSendOutputToFileChkBox", Boolean.toString(sendToFile.isSelected()));
    }
    
    @Override
    public void setRunButtonsEnabled(boolean enable) {
        runButton.setEnabled(enable);
        paramsButton.setEnabled(enable);
        actionButton.setEnabled(enable);
        if ( sendToFile.isSelected()) {
            outputButton.setEnabled(enable);
        }
    }

    @Override
    public void setAllCaretPositions() {
        ComboBoxUtil.setCaretPosition(actionCombo);
        ComboBoxUtil.setCaretPosition(outputCombo);
    }

    @Override
    public int getSplitPanePos() {
        return splitPanePos;
    }
    
    private void setAvailableInitial(Set<String> names) {
        initialCombo.removeAllItems();
        if (names.size() > 0) {
            for (String s : names) {
                initialCombo.addItem(s);
            }
        } else {
            System.out.println(bundle.getString("No_named_templates_found!"));
        }
    }                                

    private void runStandaloneTransform() {
        // Make sure an initial template is specified
        String initialTemplate = null;
        
        if (initialCombo.getSelectedItem() != null
                && initialCombo.getSelectedItem().toString().length() > 0) {
            // > 0 checks user hasn't just deleted value with backspace
            initialTemplate = initialCombo.getSelectedItem().toString();
        } else {
            Message.error(bundle.getString("Initial_template_not_set!"));
            return;
        }
        
        final String it = initialTemplate;
        
        final SwingWorker worker = new SwingWorker() {
            @Override
            public Object doInBackground() {
                
                view.startIndeterminant(bundle.getString("Working..."));
                
                boolean success = false;
                
                try {

                    Source stylesheet = action.getSelectedSource();

                    Serializer serializer;
                    if (sendToFile.isSelected()) {
                        serializer = output.getSelectedDestination();
                    } else {
                        serializer = new Serializer();
                        serializer.setOutputStream(System.out);
                    }

                    success = view.getTransformController().runStandaloneTransform(stylesheet, serializer, it);
                // TODO: Better error reporting! -fg
                } catch (URISyntaxException e) {
                    System.out.println(e.toString());
                }
                
                view.endIndeterminant(success);
                
                return "standalone transform finished";
            }
        };
        
        view.executeService(worker);
    }
    
    private void runStandaloneXQuery() {
        final SwingWorker worker = new SwingWorker() {
            @Override
            public Object doInBackground() {
                
                view.startIndeterminant(bundle.getString("Working..."));
                
                boolean success = false;
                InputStream query = null;

                try {

                    query = new FileInputStream(action.getSelectedFile());
                    String query_base = action.getSelectedURI().toString();

                    Serializer serializer;
                    if (sendToFile.isSelected()) {
                        serializer = output.getSelectedDestination();
                    } else {
                        serializer = new Serializer();
                        serializer.setOutputStream(System.out);
                    }

                    success = view.getTransformController().runStandaloneXQuery(query, serializer, query_base);
                } catch (Exception e) {
                    Message.error(e.getMessage());
                } finally {
                    IOUtils.closeStream(query);
                }

                view.endIndeterminant(success);
                
                return "standalone transform finished";
            }
        };
        
        view.executeService(worker);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        titleSeparator = new javax.swing.JSeparator();
        actionLabel = new javax.swing.JLabel();
        actionCombo = new javax.swing.JComboBox();
        actionButton = new javax.swing.JButton();
        initialLabel = new javax.swing.JLabel();
        initialCombo = new javax.swing.JComboBox();
        initialButton = new javax.swing.JButton();
        outputLabel = new javax.swing.JLabel();
        outputCombo = new javax.swing.JComboBox();
        outputButton = new javax.swing.JButton();
        sendToFile = new javax.swing.JCheckBox();
        runButton = new javax.swing.JButton();
        paramsButton = new javax.swing.JButton();

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView"); // NOI18N
        titleLabel.setText(bundle.getString("Standalone_XSLT_/_XQuery")); // NOI18N

        actionLabel.setText(bundle.getString("XSLT_/_XQuery")); // NOI18N

        actionCombo.setEditable(true);

        actionButton.setText("...");
        actionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonActionPerformed(evt);
            }
        });

        initialLabel.setText(bundle.getString("Initial_Template_(XSLT_only)")); // NOI18N

        initialButton.setText(bundle.getString("auto")); // NOI18N
        initialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialButtonActionPerformed(evt);
            }
        });

        outputLabel.setText(bundle.getString("Output")); // NOI18N

        outputCombo.setEditable(true);

        outputButton.setText("...");

        sendToFile.setText(bundle.getString("Send_output_to_file")); // NOI18N
        sendToFile.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sendToFile.setMargin(new java.awt.Insets(0, 0, 0, 0));

        runButton.setText(bundle.getString("Run")); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        paramsButton.setText(bundle.getString("Params")); // NOI18N
        paramsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramsButtonActionPerformed(evt);
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
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(actionLabel)
                            .addComponent(outputLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(initialLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(initialCombo, 0, 178, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(initialButton))
                            .addComponent(actionCombo, 0, 375, Short.MAX_VALUE)
                            .addComponent(outputCombo, 0, 375, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(outputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sendToFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                        .addComponent(paramsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(runButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titleSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actionLabel)
                    .addComponent(actionButton)
                    .addComponent(actionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(initialButton)
                    .addComponent(initialLabel)
                    .addComponent(initialCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputButton)
                    .addComponent(outputLabel)
                    .addComponent(outputCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(paramsButton)
                    .addComponent(sendToFile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        view.clearOutputWindow();
//        String file = helper.chooseFile(actionCombo, false);
//        helper.updateComboBoxNoDuplicate(actionCombo, file);
    }//GEN-LAST:event_actionButtonActionPerformed

    /**
     * Populate the initial template combo box with the available initial templates.
     */
    private void initialButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initialButtonActionPerformed
        String stylesheet = action.getSelectedString();
        Set<String> names = NamedTemplateExtractor.getAvailableNamedTemplates(stylesheet);
        setAvailableInitial(names);
    }//GEN-LAST:event_initialButtonActionPerformed

    private void paramsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramsButtonActionPerformed
        String stylesheet = action.getSelectedString();
        view.getParamsDiag().showParamsForStylesheet(stylesheet);
    }//GEN-LAST:event_paramsButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        String file = action.getSelectedString();
        if (FileUtil.isXqueryFile(file)) {
            runStandaloneXQuery();
        } else {
            runStandaloneTransform();
        }
    }//GEN-LAST:event_runButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionButton;
    private javax.swing.JComboBox actionCombo;
    private javax.swing.JLabel actionLabel;
    private javax.swing.JButton initialButton;
    private javax.swing.JComboBox initialCombo;
    private javax.swing.JLabel initialLabel;
    private javax.swing.JButton outputButton;
    private javax.swing.JComboBox outputCombo;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JButton paramsButton;
    private javax.swing.JButton runButton;
    private javax.swing.JCheckBox sendToFile;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JSeparator titleSeparator;
    // End of variables declaration//GEN-END:variables
    
}
