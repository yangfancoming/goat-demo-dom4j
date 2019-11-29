

package org.dom4j;

import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * A test harness to test the DocumentFactory.getQNames() method
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class GetQNamesTest extends AbstractTestCase {

    public void testQNames() throws Exception {
        DocumentFactory factory = new DocumentFactory();

        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);
        getDocument("/xml/test/soap2.xml", reader);

        List qnames = factory.getQNames();
        assertEquals("Number of QNames not correct", 15, qnames.size());
    }

    /**
     * Test the element rename functionality which was lacking as spotted by Rob
     * Lebowitz
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testRename() throws Exception {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("foo");

        assertEquals("named correctly", "foo", root.getName());

        root.setName("bar");

        assertEquals("named correctly", "bar", root.getName());

        QName xyz = root.getQName("xyz");

        root.setQName(xyz);

        assertEquals("QNamed correctly", xyz, root.getQName());
    }
}

