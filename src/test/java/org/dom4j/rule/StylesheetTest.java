

package org.dom4j.rule;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.xpath.DefaultXPath;
import org.testng.annotations.BeforeClass;

/**
 * A test harness to test the use of the Stylesheet and the XSLT rule engine.
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class StylesheetTest extends AbstractTestCase {
    protected String[] templates = {
            "/",
            "*",
            "root",
            "author",
            "@name",
            "root/author",
            "author[@location='UK']",
            "root/author[@location='UK']",
            "root//author[@location='UK']"};

    protected String[] templates2 = {"/", "title", "para", "*"};

    protected Stylesheet stylesheet;


    public void testRules() throws Exception {
        for (int i = 0, size = templates.length; i < size; i++) {
            addTemplate(templates[i]);
        }

        log("");
        log("........................................");
        log("");
        log("Running stylesheet");

        stylesheet.run(document);

        log("Finished");
    }

    public void testLittleDoc() throws Exception {
        for (int i = 0, size = templates2.length; i < size; i++) {
            addTemplate(templates2[i]);
        }
        Document doc = getDocument("/xml/test/littledoc.xml");

        stylesheet = new Stylesheet();
        stylesheet.setValueOfAction(new Action() {
            public void run(Node node) {
                log("Default ValueOf action on node: " + node);
                log("........................................");
            }
        });

        stylesheet.run(doc);
    }

    public void testFireRuleForNode() throws Exception {
        final StringBuffer b = new StringBuffer();

        final Stylesheet s = new Stylesheet();
        Pattern pattern = DocumentHelper.createPattern("url");
        Action action = new Action() {
            public void run(Node node) throws Exception {
                b.append("url");
                s.applyTemplates(node);
            }
        };

        Rule r = new Rule(pattern, action);
        s.addRule(r);

        s.applyTemplates(document, new DefaultXPath("root/author/url"));

        assertEquals("Check url is processed twice", "urlurl", b.toString());
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        stylesheet = new Stylesheet();
        stylesheet.setValueOfAction(new Action() {
            public void run(Node node) {
                log("Default ValueOf action on node: " + node);
                log("........................................");
            }
        });
    }

    protected void addTemplate(final String match) {
        log("Adding template match: " + match);

        Pattern pattern = DocumentHelper.createPattern(match);

        log("Pattern: " + pattern);
        log("........................................");

        Action action = new Action() {
            public void run(Node node) throws Exception {
                log("Matched pattern: " + match);
                log("Node: " + node.asXML());
                log("........................................");

                // apply any child templates
                stylesheet.applyTemplates(node);
            }
        };

        Rule rule = new Rule(pattern, action);
        stylesheet.addRule(rule);
    }
}

