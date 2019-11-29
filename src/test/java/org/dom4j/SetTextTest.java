

package org.dom4j;

/**
 * Tests the setText method
 * 
 * @author <a href="mailto:maartenc@users.sourceforge.net">Maarten Coene </a>
 */
public class SetTextTest extends AbstractTestCase {
    /*
     * The structure of the test document is: [root] [author name="James"
     * location="UK"] James Strachem
     * [url]http://sourceforge.net/users/jstrachan/[/url] [/author] [author
     * name="Bob" location="Canada"] Bob McWhirter
     * [url]http://sourceforge.net/users/werken/[/url] [/author] [/root]
     */
    public void testSetText1() throws Exception {
        String newURL = "newURL";

        Node urlNode = document.selectSingleNode("//root/author[1]/url");
        urlNode.setText(newURL);

        assertEquals(newURL, urlNode.getText());
        assertTrue(urlNode instanceof Element);

        Element urlElement = (Element) urlNode;
        assertEquals(0, urlElement.elements().size());
    }

    public void testSetText2() throws Exception {
        String newName = "Strachem James";

        Node authorNode = document.selectSingleNode("//root/author[1]");
        authorNode.setText(newName);

        assertEquals(newName, authorNode.getText());
        assertTrue(authorNode instanceof Element);

        Element urlElement = (Element) authorNode;
        assertEquals(1, urlElement.elements().size());
    }
}

