/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author awelch
 */
public class XMLSchemaEditorMouseAdapter {
    
    private static final String XML_SCHEMA = "<xs:schema\n" +
                "   xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "   elementFormDefault=\"qualified\">\n" +
                "\n" +
                "\t<xs:element name=\"el1\" type=\"el1\"/>\n" +
                "\t<xs:element name=\"el2\" type=\"xs:string\"/>\n" +
                "\n" +
                "\t<xs:complexType name=\"el1\">\n" +
                "\t\t<xs:sequence>\n" +
                "\t\t\t<xs:element ref=\"el2\"/>\n" +
                "\t\t</xs:sequence>\n" +
                "\t</xs:complexType>\n" +
                "\n" +
                "</xs:schema>";     
    
    private static final String ELEMENT_TYPE = "<xs:element name=\" \" type=\" \"/>"; 
    private static final String ELEMENT_REF = "<xs:element ref=\" \"/>";
    
    private static final String COMPLEX_TYPE = "<xs:complexType name=\" \">\n" +
                "</xs:complexType>"; 
    
    private static final String NON_EMPTY_STRING = "<xs:simpleType name=\"non-empty-string\">\n" +
                "\t<xs:restriction base=\"xs:string\">\n" +
                "\t\t<xs:whiteSpace value=\"collapse\"/>\n" + 
                "\t\t<xs:minLength value=\"1\"/>\n" +
                "\t</xs:restriction>\n" +
                "</xs:simpleType>";
        
    private static final String ENUMERATION = "<xs:simpleType name=\" \">\n" +
                "\t<xs:restriction base=\"xs:string\">\n" +
                "\t\t<xs:enumeration value=\"value1\"/>\n" + 
                "\t\t<xs:enumeration value=\"value2\"/>\n" + 
                "\t\t<xs:enumeration value=\"value3\"/>\n" +
                "\t</xs:restriction>\n" + 
                "</xs:simpleType>";
            
    private static final String ELEMENT_WITH_ATTS_AND_ELEMENT_CHILDREN = "<xs:element name=\"el\" type=\"el\"/>\n" +
            "<xs:complexType name=\"el\">\n" +                
            "\t<xs:sequence>\n" +
            "\t\t<xs:element ref=\"el2\">\n" +            
            "\t</xs:sequence>\n" +
            "\t<xs:attribute name=\"att\" type=\"xs:string\"/>\n" +
            "</xs:complexType>"; 
    
    private static final String ELEMENT_WITH_ATTS_AND_TEXT_ONLY_CHILD = "<xs:element name=\"el\" type=\"el\"/>\n" +
            "<xs:complexType name=\"el\">\n" +                
            "\t<xs:simpleContent>\n" +
            "\t\t<xs:extension base=\"xs:string\">\n" +
            "\t\t\t<xs:attribute name=\"att\" type=\"xs:string\"/>\n" +
            "\t\t</xs:extension>\n" +
            "\t</xs:simpleContent>\n" +                               
            "</xs:complexType>"; 
    
    public static void addMenuEntriesToPopup(final RSyntaxTextArea rSyntaxTextArea) {
        
        JMenu subMenu = new JMenu(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Insert"));
         
        addMenuItem(rSyntaxTextArea, subMenu, "xs:schema", XML_SCHEMA);
        addMenuItem(rSyntaxTextArea, subMenu, "xs:element (type)", ELEMENT_TYPE);
        addMenuItem(rSyntaxTextArea, subMenu, "xs:element (ref)", ELEMENT_REF);
        addMenuItem(rSyntaxTextArea, subMenu, "xs:complexType", COMPLEX_TYPE);
        addMenuItem(rSyntaxTextArea, subMenu, "Simple Type: non-empty-string", NON_EMPTY_STRING);
        addMenuItem(rSyntaxTextArea, subMenu, "Simple Type: enumeration", ENUMERATION);
        addMenuItem(rSyntaxTextArea, subMenu, "Element with attributes and element child", ELEMENT_WITH_ATTS_AND_ELEMENT_CHILDREN);
        addMenuItem(rSyntaxTextArea, subMenu, "Element with attributes and text child", ELEMENT_WITH_ATTS_AND_TEXT_ONLY_CHILD);
        
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
