

package org.dom4j.tree;

import org.dom4j.AbstractTestCase;

/**
 * DOCUMENT ME!
 * 
 * @author Maarten Coene
 */
public class CDataTest extends AbstractTestCase {
    public void testNullTest() {
        DefaultCDATA cdata = new DefaultCDATA(null);
        assertEquals("CData not correct", "<![CDATA[]]>", cdata.asXML());
    }

    public void testNormal() {
        DefaultCDATA cdata = new DefaultCDATA("sample");
        assertEquals("CData not correct", "<![CDATA[sample]]>", cdata.asXML());
    }
    
    public void testLongCData() throws Exception {
        getDocument("xml/test/longCDATA.xml");
    }
}

