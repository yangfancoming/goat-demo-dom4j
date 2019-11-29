

package org.dom4j;

import org.dom4j.io.DOMReader;
import org.testng.annotations.BeforeClass;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.List;

/**
 * Test the use of namespaces
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class NamespacesTest extends AbstractTestCase {

    public void testNamespaces() throws Exception {
        testNamespaces(document);
        testNamespaces(saxRoundTrip(document));
        testNamespaces(domRoundTrip(document));
    }

    protected void testNamespaces(Document document) throws Exception {
        Document doc2 = (Document) document.clone();
        Element root = doc2.getRootElement();
        assertNamespace(root.getNamespace(), "","http://www.w3.org/2001/XMLSchema");
        assertEquals("xmlns=\"http://www.w3.org/2001/XMLSchema\"", root.getNamespace().asXML());
        assertEquals("namespace::*[name()='']", root.getNamespace().getPath());
        assertEquals("namespace::*[name()='']", root.getNamespace().getUniquePath());

        List<Namespace> additionalNS = root.additionalNamespaces();
        assertTrue("at least one additional namespace", (additionalNS != null)  && (additionalNS.size() > 0));

        Namespace ns = additionalNS.get(0);
        assertNamespace(ns, "t", "http://www.w3.org/namespace/");
        assertEquals("xmlns:t=\"http://www.w3.org/namespace/\"", ns.asXML());
        assertEquals("namespace::t", ns.getPath());
        assertEquals("namespace::t", ns.getUniquePath());

        Node node = root.node(0);
        assertTrue("First node is a namespace", node instanceof Namespace);

        // now lets try change the namespace
        root.remove(ns);
        root.addNamespace("t", "myNewURI");

        additionalNS = root.additionalNamespaces();
        assertTrue("at least one additional namespace", (additionalNS != null) && (additionalNS.size() > 0));

        ns = additionalNS.get(0);
        assertNamespace(ns, "t", "myNewURI");

        // lets test the list is backed
        additionalNS.remove(0);
        additionalNS.add(Namespace.get("t", "myNewURI-2"));

        additionalNS = root.additionalNamespaces();
        assertTrue("at least one additional namespace", (additionalNS != null)  && (additionalNS.size() > 0));

        ns = additionalNS.get(0);
        assertNamespace(ns, "t", "myNewURI-2");

        additionalNS.clear();
        root.addNamespace("t", "myNewURI");

        additionalNS = root.additionalNamespaces();
        assertTrue("at least one additional namespace", (additionalNS != null) && (additionalNS.size() > 0));

        ns = additionalNS.get(0);
        assertNamespace(ns, "t", "myNewURI");

        log("Namespaces: " + additionalNS);
        log("XML is now");
        log(root.asXML());
    }

    public void testNamespaceForPrefix() throws Exception {
        testNamespaceForPrefix(document);
        testNamespaceForPrefix(saxRoundTrip(document));
        testNamespaceForPrefix(domRoundTrip(document));
    }

    protected void testNamespaceForPrefix(Document document) throws Exception {
        Element root = document.getRootElement();
        Namespace ns = root.getNamespaceForPrefix("t");

        assertNamespace(ns, "t", "http://www.w3.org/namespace/");

        Element element = root.elements().get(0);
        Namespace ns2 = element.getNamespaceForPrefix("t");

        assertNamespace(ns2, "t", "http://www.w3.org/namespace/");
        assertTrue("Same namespace instance returned", ns == ns2);

        log("found: " + ns.asXML());
    }

    public void testNamespaceForDefaultPrefix() throws Exception {
        Document document = getDocument("/xml/test/defaultNamespace.xml");
        testNamespaceForDefaultPrefix(document);
        testNamespaceForDefaultPrefix(saxRoundTrip(document));
        testNamespaceForDefaultPrefix(domRoundTrip(document));
    }

    protected void testNamespaceForDefaultPrefix(Document document)  throws Exception {

        List<Node> list = document.selectNodes("//*");

        for (Node node : list) {
            Element element = (Element) node;
            Namespace ns = element.getNamespaceForPrefix("");
            assertNamespace(ns, "", "dummyNamespace");
            ns = element.getNamespaceForPrefix(null);
            assertNamespace(ns, "", "dummyNamespace");
            log("found: " + ns.asXML());
        }
    }

    public void testAttributeDefaultPrefix() throws Exception {
        Document document = getDocument("/xml/test/soap3.xml");
        testAttributeDefaultPrefix(document);
        testAttributeDefaultPrefix(saxRoundTrip(document));
        testAttributeDefaultPrefix(domRoundTrip(document));
    }

    protected void testAttributeDefaultPrefix(Document document) throws Exception {
        List<Node> list = document.selectNodes("//@*[local-name()='actor']");
        assertTrue("Matched at least one 'actor' attribute", list.size() > 0);

        for (Node node : list) {
            Attribute attribute = (Attribute) node;
            log("found: " + attribute.asXML());
            Element element = attribute.getParent();
            assertTrue("Attribute has a parent", element != null);
            Namespace ns = element.getNamespaceForPrefix("");
            String uri = "http://schemas.xmlsoap.org/soap/envelope/";
            assertNamespace(ns, "", uri);
            Namespace ns2 = attribute.getNamespace();
            // Note that namespaces do not inherit the default namespace!
            assertNamespace(ns2, "", "");
        }
    }

    public void testNamespaceForURI() throws Exception {
        testNamespaceForURI(document);
        testNamespaceForURI(saxRoundTrip(document));
        testNamespaceForURI(domRoundTrip(document));
    }

    protected void testNamespaceForURI(Document document) throws Exception {
        Element root = document.getRootElement();
        Namespace ns = root.getNamespaceForURI("http://www.w3.org/namespace/");
        assertNamespace(ns, "t", "http://www.w3.org/namespace/");
        Element element = root.elements().get(0);
        Namespace ns2 = element.getNamespaceForURI("http://www.w3.org/namespace/");
        assertNamespace(ns2, "t", "http://www.w3.org/namespace/");
        assertTrue("Same namespace instance returned", ns == ns2);
        log("found: " + ns.asXML());
    }

    public void testRedeclareNamespaces() throws Exception {
        Document document = getDocument("/xml/test/soap2.xml");
        testRedeclareNamespaces(document);
        testRedeclareNamespaces(saxRoundTrip(document));
        testRedeclareNamespaces(domRoundTrip(document));
    }

    protected void testRedeclareNamespaces(Document document) throws Exception {
        String uri = "http://schemas.xmlsoap.org/soap/envelope/";
        assertNamespaces(document.selectNodes("//*[local-name()='Envelope']"),"SOAP-ENV", uri);
        assertNamespaces(document.selectNodes("//*[local-name()='Body']"),"SOAP-ENV", uri);
        assertNamespaces(document.selectNodes("//*[local-name()='bar']"), "a","barURI");
        assertNamespaces(document.selectNodes("//*[local-name()='newBar']"),"a", "newBarURI");
        assertNamespaces(document.selectNodes("//*[local-name()='foo']"), "","fooURI");
        assertNamespaces(document.selectNodes("//*[local-name()='newFoo']"),"", "newFooURI");

    }

    public void testDefaultNamespaceIssue() throws Exception {
        Document document = getDocument("/xml/test/defaultNamespaceIssue.xsd");
        testDefaultNamespaceIssue(document);
        testDefaultNamespaceIssue(saxRoundTrip(document));
        testDefaultNamespaceIssue(domRoundTrip(document));
    }

    protected void testDefaultNamespaceIssue(Document document) throws Exception {
        // When writing documents using a default namespace with XMLWriter
        // a redeclaration of the default namespace to "" was dropped in the
        // output. Test that
        // <xsd:schema><xsd:element><xsd:annotation><xsd:documentation><text>
        // is in no namespace.
        String expr = "/xsd:schema/xsd:element/xsd:annotation/xsd:documentation/text";
        assertNotNull("default namespace redeclaration", document.selectSingleNode(expr));

        // The test document has a default namespace declaration on the root
        // element ("schema"), but the element itself is not in the default
        // namespace. Test that declaredNamespaces on the root element also
        // returns the default namespace declaration.

        for (Namespace ns : document.getRootElement().declaredNamespaces()) {
            if ("urn:wapforum:devicesheet".equals(ns.getURI()) && "".equals(ns.getPrefix())) {
                return;
            }
        }

        fail("Default namespace declaration not present on root element");
    }

    public void testDefaultNamespace()  {
        Document doc = DocumentHelper.createDocument();
        Element processDef = doc.addElement("process-definition","http://jbpm.org/statedefinition-2.0-beta3");

        Element startState = processDef.addElement("start-state");
        startState.addAttribute("name", "start");

        Element transition = startState.addElement("transition");
        transition.addAttribute("to", "first");

        assertEquals("http://jbpm.org/statedefinition-2.0-beta3", startState.getNamespace().getURI());
        assertEquals("", startState.getNamespace().getPrefix());
        System.out.println(doc.asXML());
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        document = getDocument("/xml/test/test_schema.xml");
    }

    protected Document saxRoundTrip(Document document) throws Exception {
        return DocumentHelper.parseText(document.asXML());
    }

    protected Document domRoundTrip(Document document) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document domDocument = builder.parse(new InputSource(
                new StringReader(document.asXML())));

        // now lets read it back as a DOM4J object
        DOMReader domReader = new DOMReader();

        return domReader.read(domDocument);
    }

    protected void assertNamespaces(List<? extends Node> nodes, String prefix, String uri)
            throws Exception {
        for (Node node : nodes) {
            Element element = (Element) node;
            assertNamespace(element.getNamespace(), prefix, uri);
        }
    }

    protected void assertNamespace(Namespace ns, String prefix, String uri)
            throws Exception {
        assertEquals("namespace prefix", prefix, ns.getPrefix());
        assertEquals("namespace URI", uri, ns.getURI());
    }
}

