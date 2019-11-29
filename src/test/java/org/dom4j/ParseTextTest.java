

package org.dom4j;

import org.testng.annotations.BeforeClass;

/**
 * Tests the {@link DocumentHelper#parseText(String)}method.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class ParseTextTest extends AbstractTestCase {
    protected String xmlText =  "<root><author name='James'><location>Paris</location></author></root>";


    public void testDocument()  {
        assertTrue("Document is not null", document != null);

        Element root = document.getRootElement();

        assertTrue("Root element is not null", root != null);

        Element author = root.element("author");

        assertTrue("Author element is not null", author != null);

        String name = author.attributeValue("name");

        assertEquals("Name attribute matches", name, "James");

        String location = document.valueOf("/root/author/location");

        assertEquals("Location element matches", location, "Paris");
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        document = DocumentHelper.parseText(xmlText);
    }
}

