

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.List;

/**
 * Test harness for the sorting version of the selectNodes() function
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class SortByTest extends AbstractTestCase {

    public void testXPaths()  {
        List list = document.selectNodes("//SPEAKER", "NAME");
        log("Number of SPEAKER instances: " + list.size());
        List noDuplicates = document.selectNodes("//SPEAKER", ".", true);
        log("Number of distinct SPEAKER instances: " + noDuplicates.size());
        log("Number of distinct SPEAKER instances: " + noDuplicates.size());
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        document = new SAXReader().read(new File("xml/much_ado.xml"));
    }
}

