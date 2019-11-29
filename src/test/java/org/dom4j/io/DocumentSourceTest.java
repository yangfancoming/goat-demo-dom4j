

package org.dom4j.io;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * DOCUMENT ME!
 * 
 * @author <a href="mailto:maartenc@sourceforge.net">Maarten Coene </a>
 */
public class DocumentSourceTest extends AbstractTestCase {


    public void testBug555549() throws Exception {
        // simulate <cr><lf>
        String xml = "<field id='Description' type='textarea'>line1"+ (char) 13 + (char) 10 + "line2</field>";
        Document doc = DocumentHelper.parseText(xml);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer txml = tf.newTransformer();
        StringWriter writer = new StringWriter();
        txml.transform(new DocumentSource(doc), new StreamResult(writer));
        System.out.println(writer.toString());
        assertTrue(writer.toString().indexOf("&#13") == -1);
    }
}

