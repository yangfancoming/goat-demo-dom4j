

package org.dom4j.io;

import org.dom4j.AbstractTestCase;
import org.dom4j.ElementHandler;

/**
 * DOCUMENT ME!
 * 
 * @author Maarten Coene
 */
public class DispatchHandlerTest extends AbstractTestCase {

    public void testBug611445() throws Exception {
        MyHandler handler = new MyHandler();

        SAXReader reader = new SAXReader();
        reader.addHandler("/products/product/colour", handler);
        reader.read(getFile("/xml/test/sample.xml"));

        assertEquals(3, handler.getCount());

        reader.read(getFile("/xml/test/sample.xml"));
        assertEquals(6, handler.getCount());
    }

    private static class MyHandler implements ElementHandler {
        private int count = 0;

        public void onEnd(org.dom4j.ElementPath elementPath) {
        }

        public void onStart(org.dom4j.ElementPath elementPath) {
            count++;
        }

        int getCount() {
            return count;
        }
    }
}

