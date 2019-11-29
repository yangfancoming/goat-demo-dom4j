

package org.dom4j;

import java.util.List;

/**
 * A test harness to test XPath expression evaluation in DOM4J
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class XPathBugTest extends AbstractTestCase {

    public void testXPaths() throws Exception {
        Document document = getDocument("/xml/rabo1ae.xml");
        Element root = (Element) document
                .selectSingleNode("/m:Msg/m:Contents/m:Content");

        assertTrue("root is not null", root != null);

        Namespace ns = root.getNamespaceForPrefix("ab");

        assertTrue("Found namespace", ns != null);

        System.out.println("Found: " + ns.getURI());

        Element element = (Element) root
                .selectSingleNode("ab:RaboPayLoad[@id='1234123']");

        assertTrue("element is not null", element != null);

        String value = element.valueOf("ab:AccountingEntry/ab:RateType");

        assertEquals("RateType is correct", "CRRNT", value);
    }

    /**
     * A bug found by Rob Lebowitz
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testRobLebowitz() throws Exception {
        String text = "<ul>" + "    <ul>" + "        <li/>"
                + "            <ul>" + "                <li/>"
                + "            </ul>" + "        <li/>" + "    </ul>" + "</ul>";

        Document document = DocumentHelper.parseText(text);
        List lists = document.selectNodes("//ul | //ol");

        int count = 0;

        for (int i = 0; i < lists.size(); i++) {
            Element list = (Element) lists.get(i);
            List nodes = list.selectNodes("ancestor::ul");

            if ((nodes != null) && (nodes.size() > 0)) {
                continue;
            }

            nodes = list.selectNodes("ancestor::ol");

            if ((nodes != null) && (nodes.size() > 0)) {
                continue;
            }
        }
    }

    /**
     * A bug found by Stefan which results in IndexOutOfBoundsException for
     * empty results
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testStefan() throws Exception {
        String text = "<foo>hello</foo>";
        Document document = DocumentHelper.parseText(text);
        XPath xpath = DocumentHelper.createXPath("/x");
        Object value = xpath.evaluate(document);
    }

    /**
     * Test found by Mike Skells
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testMikeSkells() throws Exception {
        Document top = DocumentFactory.getInstance().createDocument();
        Element root = top.addElement("root");
        root.addElement("child1").addElement("child11");
        root.addElement("child2").addElement("child21");
        System.out.println(top.asXML());

        XPath test1 = top.createXPath("/root/child1/child11");
        XPath test2 = top.createXPath("/root/child2/child21");
        Node position1 = test1.selectSingleNode(root);
        Node position2 = test2.selectSingleNode(root);

        System.out.println("test1= " + test1);
        System.out.println("test2= " + test2);
        System.out.println("Position1 Xpath = " + position1.getUniquePath());
        System.out.println("Position2 Xpath = " + position2.getUniquePath());

        System.out.println("test2.matches(position1) : "
                + test2.matches(position1));

        assertTrue("test1.matches(position1)", test1.matches(position1));
        assertTrue("test2.matches(position2)", test2.matches(position2));

        assertTrue("test2.matches(position1) should be false", !test2
                .matches(position1));
    }
}

