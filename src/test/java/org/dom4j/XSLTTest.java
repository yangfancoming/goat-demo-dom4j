

package org.dom4j;

import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.testng.annotations.BeforeClass;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.util.List;

/**
 * Tests that XSLT works correctly
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class XSLTTest extends AbstractTestCase {

    public void testTransform() throws Exception {
        Document transformedDoc = transform("/xml/nitf/ashtml.xsl");

        // log( transformedDoc.asXML() );
        assertTrue("Transformed Document is not null", transformedDoc != null);

        List h1List = transformedDoc.selectNodes("/html//h1");

        assertTrue("At least one <h1>", h1List.size() > 0);

        List pList = transformedDoc.selectNodes("//p");

        assertTrue("At least one <p>", pList.size() > 0);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        document = getDocument("/xml/nitf/sample.xml");
    }

    protected Document transform(String xsl) throws Exception {
        assertTrue("Document is not null", document != null);

        // load the transformer
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(
                getFile(xsl)));

        // now lets create the TrAX source and result
        // objects and do the transformation
        Source source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);

        return result.getDocument();
    }
}

