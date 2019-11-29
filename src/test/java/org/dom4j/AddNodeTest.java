

package org.dom4j;

/**
 * A test harness to test the addNode() methods on node
 * 
 * @author Philippe Ombredanne
 */
public class AddNodeTest extends AbstractTestCase {

    public void testDom4jAddNodeClone() {
        Document maindoc = DocumentHelper.createDocument();
        Element docroot = maindoc.addElement("document");
        Element header = docroot.addElement("header").addText("Some Text");

        Document subdoc = DocumentHelper.createDocument();
        Element docroot2 = subdoc.addElement("document");

        docroot2.add((Element) maindoc.selectSingleNode("/document/header")
                .clone());
        assertEquals(subdoc.asXML(), maindoc.asXML());
    }

    public void testDom4jAddNodeCreateCopy() {
        Document maindoc = DocumentHelper.createDocument();
        Element docroot = maindoc.addElement("document");
        Element header = docroot.addElement("header").addText("Some Text");

        Document subdoc = DocumentHelper.createDocument();
        Element docroot2 = subdoc.addElement("document");

        docroot2.add(((Element) maindoc.selectSingleNode("/document/header"))
                .createCopy());
        assertEquals(subdoc.asXML(), maindoc.asXML());
    }

    public void testDom4jAddNodeBug() {
        Document maindoc = DocumentHelper.createDocument();
        Element docroot = maindoc.addElement("document");
        Element header = docroot.addElement("header").addText("Some Text");

        Document subdoc = DocumentHelper.createDocument();
        Element subroot = subdoc.addElement("document");

        try {
            // add the header node from maindoc without clone... or createCopy
            subroot.add((Element) maindoc.selectSingleNode("/document/header"));
            fail();
        } catch (IllegalAddException e) {
            // the header already has a parent, this exception is normal
        }
    }
}

