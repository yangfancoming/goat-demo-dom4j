

package org.dom4j;

import java.util.Map;

/**
 * DOCUMENT ME!
 * 
 * @author kralik
 * @author Maarten Coene
 */
public class ProcessingInstructionTest extends AbstractTestCase {
    public void testParseValues() {
        String data = " abc='123' def=\"2!=3\" ghi=' 4 = '";
        ProcessingInstruction pi = DocumentHelper.createProcessingInstruction(
                "pi", data);

        Map values = pi.getValues();
        assertEquals(3, values.size());
        assertEquals("123", pi.getValue("abc"));
        assertEquals("2!=3", pi.getValue("def"));
        assertEquals(" 4 = ", pi.getValue("ghi"));
    }

    public void testBug787428() {
        String data = "xpath=\"/abc/cde[@id='qqq']\"";
        ProcessingInstruction pi = DocumentHelper.createProcessingInstruction(
                "merge", data);

        assertEquals("/abc/cde[@id='qqq']", pi.getValue("xpath"));
    }
}

