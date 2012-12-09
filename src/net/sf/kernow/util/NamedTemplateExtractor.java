/*
 * NamedTemplateExtractor.java
 *
 * Created on 28 November 2005, 19:33
 */

package net.sf.kernow.util;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Templates;
import net.sf.saxon.Configuration;
import net.sf.saxon.PreparedStylesheet;
import net.sf.saxon.event.PipelineConfiguration;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.expr.instruct.Executable;
import net.sf.saxon.expr.instruct.Template;
import net.sf.saxon.trace.ExpressionPresenter;
import net.sf.saxon.trans.XPathException;

/**
 * Extract the named templates from a stylesheet.
 *
 * @author ajwelch
 * @author Florent Georges
 */
public class NamedTemplateExtractor {

    /**
     * Extract the names of the named templates from a stylesheet.
     *
     * <p><b>TODO</b>: Template names are QName, not NCName.  The method should
     * then return a {@code Set&lt;QName&gt;}.  Anyway, that seems to be
     * impossible with Saxon 9.</p>
     *
     * <p><b>TODO</b>: Receive a stylesheet URI, compile it to extract the
     * template names, then throw it away.  That would be worth getting a
     * compiled yet stylesheet, allowing the caller to compile itself the
     * stylesheet and to reuse it later for its own purpose.</p>
     */
    public static Set<String> getAvailableNamedTemplates(String stylesheetPath) {
        try {            
            Templates compiledStylesheet = TransformerFactory.newInstance().newTemplates(new StreamSource(new File(stylesheetPath)));

            HashSet<String> names = new HashSet<String>();

            PreparedStylesheet ps = (PreparedStylesheet)compiledStylesheet;
            Iterator<Template> tempIter = ps.iterateNamedTemplates();
            while (tempIter.hasNext()) {
                Template t = tempIter.next();
                String name = t.getTemplateName().getDisplayName();
                if (!"".equals(name)) {
                    names.add(name);
                }
            }

            return names;

        } catch (TransformerConfigurationException tce) {
            System.err.println("Unable to extract available named templates: " + tce.toString());
            return new HashSet<String>();
        }
    }

    /**
     * An {@link ExpressionPresenter} that collects template names.
     *
     * <p>In version 9 of Saxon, {@code Executable.getNamedTemplateTable()}
     * disappeared.  Here is a not-so-pretty workaround.  See
     * <a href="http://sf.net/mailarchive/forum.php?thread_name=814097.75783.qm%40web23001.mail.ird.yahoo.com&forum_name=saxon-help">this
     * thread</a> on the Saxon's mailing list.  Will be fixed in a future
     * version maybe?</p>
     *
     * <p>Just create a new instance, pass it to
     * {@link Executable#explainNamedTemplates(ExpressionPresenter)}, then get
     * the name set with {@link #getTemplateNames()}.</p>
     */
    private static class TemplateNamesPresenter
            extends ExpressionPresenter
    {
        public TemplateNamesPresenter(Configuration config) {
            super(config, new NullReceiver());
            myNames = new HashSet<String>();
        }

        public Set<String> getTemplateNames() {
            return myNames;
        }

        @Override
        public void close() {
        }

        @Override
        public void emitAttribute(String name, String value) {
            if ( myInTemplate && "name".equals(name) ) {
                myNames.add(value);
                myInTemplate = false;
            }
        }

        @Override
        public int endElement() {
            myInTemplate = false;
            return 0;
        }

        @Override
        public void endSubsidiaryElement() {
            myInTemplate = false;
        }

        @Override
        public int startElement(String name) {
            if ( "template".equals(name) ) {
                myInTemplate = true;
            }
            return 0;
        }

        @Override
        public void startSubsidiaryElement(String name) {
            myInTemplate = false;
        }

        private Set<String> myNames;
        private boolean myInTemplate = false;
    }

    /**
     * A receiver that does nothing.  Used by {@link TemplateNamesPresenter}.
     */
    private static class NullReceiver
            implements Receiver
    {
        @Override
        public void setPipelineConfiguration(PipelineConfiguration config) {}
        @Override
        public PipelineConfiguration getPipelineConfiguration() { return null; }
        @Override
        public void setSystemId(String systemId) {}
        @Override
        public void open() throws XPathException {}
        @Override
        public void startDocument(int properties) throws XPathException {}
        @Override
        public void endDocument() throws XPathException {}
        @Override
        public void setUnparsedEntity(String name, String systemID, String publicID) throws XPathException {}
        @Override
        public void startElement(int nameCode, int typeCode, int locationId, int properties) throws XPathException {}
        @Override
        public void namespace(int namespaceCode, int properties) throws XPathException {}
        @Override
        public void attribute(int nameCode, int typeCode, CharSequence value, int locationId, int properties) throws XPathException {}
        @Override
        public void startContent() throws XPathException {}
        @Override
        public void endElement() throws XPathException {}
        @Override
        public void characters(CharSequence chars, int locationId, int properties) throws XPathException {}
        @Override
        public void processingInstruction(String name, CharSequence data, int locationId, int properties) throws XPathException {}
        @Override
        public void comment(CharSequence content, int locationId, int properties) throws XPathException {}
        @Override
        public void close() throws XPathException {}
        @Override
        public String getSystemId() { return null; }

        @Override
        public boolean usesTypeAnnotations() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
