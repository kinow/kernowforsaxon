/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.text.JTextComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author awelch
 */
public class XSLTEditorMouseAdapter {
    
    private static final String STYLESHEET = "<xsl:stylesheet version=\"2.0\"    \n" +
                "    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
                "    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "    xmlns:saxon=\"http://saxon.sf.net/\" \n" +
                "    exclude-result-prefixes=\"xs saxon\">\n" +
                "\n" +
                "<xsl:template match=\"/\">\n" +
                "\t\n" +
                "</xsl:template>\n" +
                "    \n" +
                "</xsl:stylesheet>";
    
    private static final String OUTPUT_INDENT = "<xsl:output indent=\"yes\"/>\n";
    private static final String CHOOSE_WHEN = "<xsl:choose>\n\t<xsl:when test=\"\"></xsl:when>\n\t<xsl:otherwise></xsl:otherwise>\n</xsl:choose>\n";
    private static final String IDENTITY_TEMPLATE = "<xsl:template match=\"@*|node()\">" +
        "\n\t<xsl:copy>" +
        "\n\t\t<xsl:apply-templates select=\"@*|node()\"/>" +
        "\n\t</xsl:copy>" +
        "\n</xsl:template>\n";
    
    private static final String IDENTITY_TEMPLATE_2 = "<xsl:template match=\"element()\">" +
        "\n\t<xsl:copy>" +
        "\n\t\t<xsl:apply-templates select=\"@*, node()\"/>" +
        "\n\t</xsl:copy>" +
        "\n</xsl:template>" + 
        "\n\n<xsl:template match=\"attribute()|text()|comment()|processing-instruction()\">" +
        "\n\t<xsl:copy/>" +
        "\n</xsl:template>\n";    
    
    private static final String XSL_TEMPLATE = "<xsl:template match=\"\">\n</xsl:template>";
    
    private static final String XSL_FOR_EACH = "<xsl:for-each select=\"\">\n</xsl:for-each>";
    
    private static final String XSL_FOR_EACH_GROUP = "<xsl:for-each-group select=\"\" group-by=\"\">\n</xsl:for-each-group>";
    
    
    public static void addMenuEntriesToPopup(final RSyntaxTextArea rSyntaxTextArea) {
                
        JMenu subMenu = new JMenu(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Insert"));
         
        addMenuItem(rSyntaxTextArea, subMenu, "xsl:stylesheet", STYLESHEET);
        addMenuItem(rSyntaxTextArea, subMenu, "xsl:output", OUTPUT_INDENT);
        addMenuItem(rSyntaxTextArea, subMenu, "xsl:template", XSL_TEMPLATE);
        addMenuItem(rSyntaxTextArea, subMenu, "xsl:for-each", XSL_FOR_EACH);
        addMenuItem(rSyntaxTextArea, subMenu, "xsl:for-each-group", XSL_FOR_EACH_GROUP);
        addMenuItem(rSyntaxTextArea, subMenu, "xsl:choose/xsl:when", CHOOSE_WHEN);
        addMenuItem(rSyntaxTextArea, subMenu, "identity template", IDENTITY_TEMPLATE);
        addMenuItem(rSyntaxTextArea, subMenu, "identity template 2.0", IDENTITY_TEMPLATE_2);

        rSyntaxTextArea.getPopupMenu().addSeparator();
        rSyntaxTextArea.getPopupMenu().add(subMenu);
    }   
    
    private static void addMenuItem(final RSyntaxTextArea textComp, JMenu subMenu, String title, final String codeToInsert) {
        subMenu.add(new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {                
                String txt = textComp.getText();
                int pos = textComp.getCaretPosition();
                if (pos != -1) {
                    String newStr = txt.substring(0, pos) + codeToInsert + txt.substring(pos);
                    textComp.setText(newStr);
                    textComp.setCaretPosition(pos);
                }
            }
        });        
    }
}
