

package org.dom4j.io;

import org.dom4j.AbstractTestCase;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * DOCUMENT ME!
 *
 * @author Maarten
 */
public class DOMReaderTest extends AbstractTestCase {

    public void testBug972737() throws Exception {
        String xml = "<schema targetNamespace='http://SharedTest.org/xsd' "
                + "        xmlns='http://www.w3.org/2001/XMLSchema' "
                + "        xmlns:xsd='http://www.w3.org/2001/XMLSchema'>"
                + "    <complexType name='AllStruct'>" + "        <all>"
                + "            <element name='arString' type='xsd:string'/>"
                + "            <element name='varInt' type='xsd:int'/>"
                + "        </all>" + "    </complexType>" + "</schema>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

        DOMReader reader = new DOMReader();
        org.dom4j.Document dom4jDoc = reader.read(doc);

        List namespaces = dom4jDoc.getRootElement().declaredNamespaces();
        assertEquals(2, namespaces.size());

        System.out.println(dom4jDoc.asXML());
    }
}

