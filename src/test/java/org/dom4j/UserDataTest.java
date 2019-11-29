

package org.dom4j;

import org.dom4j.io.SAXReader;
import org.dom4j.util.UserDataAttribute;
import org.dom4j.util.UserDataDocumentFactory;
import org.dom4j.util.UserDataElement;
import org.testng.annotations.BeforeClass;

/**
 * Tests the UserDataDocumentFactory
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class UserDataTest extends AbstractTestCase {
    /** Input XML file to read */
    private static final String INPUT_XML_FILE = "/xml/web.xml";

    private Object userData = new Double(1.23456);


    public void testSetData()  {
        Element root = getRootElement();
        assertTrue(root instanceof UserDataElement);

        root.setData(userData);

        assertTrue("Stored user data!", root.getData() == userData);

        log("root: " + root);

        assertUserData(root, userData);

        Element cloned = (Element) root.clone();
        assertTrue("Cloned new instance", cloned != root);
        assertUserData(cloned, userData);

        cloned = root.createCopy();
        assertTrue("Cloned new instance", cloned != root);
        assertUserData(cloned, userData);
    }

    public void testCloneAttribute()  {
        Element root = getRootElement();
        root.addAttribute("name", "value");

        Attribute attribute = root.attribute("name");
        assertTrue(attribute instanceof UserDataAttribute);

        Element cloned = (Element) root.clone();
        Attribute clonedAttribute = cloned.attribute("name");
        assertTrue(clonedAttribute instanceof UserDataAttribute);
    }

    public void testNewAdditions()  {
        Element root = getRootElement();

        Element newElement = root.addElement("foo1234");
        assertTrue("New Element is a UserDataElement",
                newElement instanceof UserDataElement);

        root.addAttribute("bar456", "123");

        Attribute newAttribute = root.attribute("bar456");

        assertTrue("New Attribute is a UserDataAttribute",
                newAttribute instanceof UserDataAttribute);
    }

    public void testNewDocument()  {
        DocumentFactory factory = UserDataDocumentFactory.getInstance();
        Document document = factory.createDocument();

        Element root = document.addElement("root");
        assertTrue("Root Element is a UserDataElement",
                root instanceof UserDataElement);

        Element newElement = root.addElement("foo1234");
        assertTrue("New Element is a UserDataElement",
                newElement instanceof UserDataElement);

        root.addAttribute("bar456", "123");

        Attribute newAttribute = root.attribute("bar456");

        assertTrue("New Attribute is a UserDataAttribute",
                newAttribute instanceof UserDataAttribute);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void assertUserData(Element root, Object data)  {
        Object result = root.getData();
        assertTrue("No user data!", result != null);
        assertTrue("Stored user data correctly", data.equals(result));
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(UserDataDocumentFactory.getInstance());
        document = getDocument(INPUT_XML_FILE, reader);
    }
}

