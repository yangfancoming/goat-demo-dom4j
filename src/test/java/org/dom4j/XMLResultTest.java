

package org.dom4j;

import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLResult;
import org.dom4j.io.XMLWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.StringWriter;

/**
 * Test harness for the XMLResult which acts as a JAXP Result
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class XMLResultTest extends AbstractTestCase {
    protected static final boolean VERBOSE = false;


    public void testWriter() throws Exception {
        // load a default transformer
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        // use dom4j document as the source
        Source source = new DocumentSource(document);

        // use pretty print format and a buffer for the result
        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter buffer = new StringWriter();
        Result result = new XMLResult(buffer, format);

        // now lets transform
        transformer.transform(source, result);

        String text = buffer.toString();

        if (VERBOSE) {
            log("Using JAXP and XMLResult the document is:- ");
            log(text);
        }

        StringWriter out = new StringWriter();

        XMLWriter writer = new XMLWriter(out, format);
        writer.write(document);

        String text2 = out.toString();

        if (VERBOSE) {
            log("Using XMLWriter the text is:-");
            log(text2);
        }

        assertEquals("The text output should be identical", text2, text);
    }
}

