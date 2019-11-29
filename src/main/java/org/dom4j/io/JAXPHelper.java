

package org.dom4j.io;

import org.xml.sax.XMLReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * <code>JAXPHelper</code> contains some helper methods for working with JAXP.
 * These methods are kept in a seperate class to avoid class loading issues,
 * such that dom4j can work without JAXP on the CLASSPATH
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
class JAXPHelper {
    protected JAXPHelper() {
    }

    /**
     * This method attempts to use JAXP to locate the SAX2 XMLReader
     * implementation. This method uses reflection to avoid being dependent
     * directly on the JAXP classes.
     * 
     * @param validating
     *            DOCUMENT ME!
     * @param namespaceAware
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public static XMLReader createXMLReader(boolean validating,
            boolean namespaceAware) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(validating);
        factory.setNamespaceAware(namespaceAware);

        SAXParser parser = factory.newSAXParser();

        return parser.getXMLReader();
    }

    public static org.w3c.dom.Document createDocument(boolean validating,
            boolean namespaceAware) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);
        factory.setNamespaceAware(namespaceAware);

        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.newDocument();
    }
}

