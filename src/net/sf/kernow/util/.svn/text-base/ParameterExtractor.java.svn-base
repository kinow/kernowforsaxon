/*
 * ParamExtractor.java
 *
 * Created on 08 November 2005, 10:37
 *
 */


package net.sf.kernow.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Templates;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.Message;
import net.sf.saxon.Controller;
import net.sf.saxon.expr.instruct.Executable;
import net.sf.saxon.expr.instruct.GlobalParam;
import net.sf.saxon.PreparedStylesheet;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.Literal;
import net.sf.saxon.expr.StringLiteral;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import net.sf.saxon.trans.XPathException;
import org.xml.sax.InputSource;

/**
 * Extracts the top-level parameters from a given stylesheet
 * @author AWelch
 */
public class ParameterExtractor {

    public static HashMap<String, Object> getParametersForStylesheetAAAAA(URI stylesheetURI) {
        HashMap<String, Object> params = new HashMap<String, Object>();

        Processor processor = new Processor(false);
        XsltCompiler xsltCompiler = processor.newXsltCompiler();
        try {
            XsltExecutable xsltExecutable = xsltCompiler.compile(new SAXSource(new InputSource(stylesheetURI.toString())));
            XsltTransformer xsltTransformer = xsltExecutable.load();
            Controller controller = xsltTransformer.getUnderlyingController();
            for (Iterator it = controller.iterateParameters(); it.hasNext();) {
                Map.Entry entry = (Map.Entry)it.next();
                params.put((String)entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            Message.exception(e, true);
        }

        return params;
    }

    /** Compiles the stylesheet then extracts the global parameters */
    public static HashMap<String, Object> getParametersForStylesheet(URI stylesheetURI) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        
        SAXTransformerFactory stf = (SAXTransformerFactory)TransformerFactory.newInstance();
        Templates compiledStylesheet;
        
        try {                        
            compiledStylesheet = stf.newTemplates(new StreamSource(stylesheetURI.toString()));	
            Executable exec = ((PreparedStylesheet)compiledStylesheet).getExecutable();
            HashMap map = exec.getCompiledGlobalVariables();
            if (map != null) {
                Iterator iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    StructuredQName qname = (StructuredQName) iter.next();
                    Object var = map.get(qname);
                    if (var instanceof GlobalParam) {
                        String name = qname.getDisplayName();
                        String value = ((GlobalParam)var).getSelectExpression().toString();
                        Expression exp = ((GlobalParam)var).getSelectExpression();

                        if (exp instanceof StringLiteral) {
                            value = ((StringLiteral)exp).getStringValue();
                        } else if (exp instanceof Literal) {
                                try {
                                    value = ((Literal)exp).getValue().getStringValue();
                                } catch (XPathException ex) {
                                    ex.printStackTrace();
                                }
                        }

                       // Fixup the values so that they don't break the transform
                       // if the use does 'auto-populate' and leaves them in
                       if (value.contains("true()")) {
                           // remove the brackets
                           value= "true";
                       } else if (value.contains("false()")) {
                           // remove the brackets
                           value = "false";
                       } else if (value.startsWith("\"") && value.endsWith("\"")) {
                           // remove the quotes
                           value = value.substring(1, value.length() - 1);
                       }

                        params.put(name, value);
                    }
                }
            } else {
                Message.info("No global parameters found in " + stylesheetURI.toString());
            }       
        } catch (TransformerConfigurationException tce) {
            Message.error(tce.toString());
        }
        
        return params;
    }

}
