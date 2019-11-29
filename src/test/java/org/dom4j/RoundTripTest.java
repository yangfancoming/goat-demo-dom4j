

package org.dom4j;

import org.dom4j.io.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * A test harness to test the the round trips of Documents.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class RoundTripTest extends AbstractTestCase {
    protected String[] testDocuments = {"/xml/test/encode.xml",
            "/xml/fibo.xml", "/xml/test/schema/personal-prefix.xsd",
            "/xml/test/soap2.xml", "/xml/test/test_schema.xml"};


    public void testTextRoundTrip() throws Exception {
        for (String testDocument : testDocuments) {
            Document doc = getDocument(testDocument);
            roundTripText(doc);
        }
    }

    public void testSAXRoundTrip() throws Exception {
        for (String testDocument : testDocuments) {
            Document doc = getDocument(testDocument);
            roundTripSAX(doc);
        }
    }

    public void testDOMRoundTrip() throws Exception {
        for (String testDocument : testDocuments) {
            Document doc = getDocument(testDocument);
            roundTripDOM(doc);
        }
    }

    public void testJAXPRoundTrip() throws Exception {
        for (String testDocument : testDocuments) {
            Document doc = getDocument(testDocument);
            roundTripJAXP(doc);
        }
    }

    public void testFullRoundTrip() throws Exception {
        for (String testDocument : testDocuments) {
            Document doc = getDocument(testDocument);
            roundTripFull(doc);
        }
    }

    public void testRoundTrip() throws Exception {
        Document document = getDocument("/xml/xmlspec.xml");

        // Document doc1 = roundTripText( document );
        Document doc1 = roundTripSAX(document);
        Document doc2 = roundTripDOM(doc1);
        Document doc3 = roundTripSAX(doc2);
        Document doc4 = roundTripText(doc3);
        Document doc5 = roundTripDOM(doc4);

        // Document doc5 = roundTripDOM( doc3 );
        assertDocumentsEqual(document, doc5);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected Document roundTripDOM(Document document) throws Exception {
        // now lets make a DOM object
        DOMWriter domWriter = new DOMWriter();
        org.w3c.dom.Document domDocument = domWriter.write(document);

        // now lets read it back as a DOM4J object
        DOMReader domReader = new DOMReader();
        Document newDocument = domReader.read(domDocument);

        // lets ensure names are same
        newDocument.setName(document.getName());

        assertDocumentsEqual(document, newDocument);

        return newDocument;
    }

    protected Document roundTripJAXP(Document document) throws Exception {
        // output the document to a text buffer via JAXP
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        StringWriter buffer = new StringWriter();
        StreamResult streamResult = new StreamResult(buffer);
        DocumentSource documentSource = new DocumentSource(document);

        transformer.transform(documentSource, streamResult);

        // now lets parse it back again via JAXP
        DocumentResult documentResult = new DocumentResult();
        StreamSource streamSource = new StreamSource(new StringReader(buffer
                .toString()));

        transformer.transform(streamSource, documentResult);

        Document newDocument = documentResult.getDocument();

        // lets ensure names are same
        newDocument.setName(document.getName());

        assertDocumentsEqual(document, newDocument);

        return newDocument;
    }

    protected Document roundTripSAX(Document document) throws Exception {
        // now lets write it back as SAX events to
        // a SAX ContentHandler which should build up a new document
        SAXContentHandler contentHandler = new SAXContentHandler();
        SAXWriter saxWriter = new SAXWriter(contentHandler, contentHandler,
                contentHandler);

        saxWriter.write(document);

        Document newDocument = contentHandler.getDocument();

        // lets ensure names are same
        newDocument.setName(document.getName());

        assertDocumentsEqual(document, newDocument);

        return newDocument;
    }

    protected Document roundTripText(Document document) throws Exception {
        StringWriter out = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(out);

        xmlWriter.write(document);

        // now lets read it back
        String xml = out.toString();

        StringReader in = new StringReader(xml);
        SAXReader reader = new SAXReader();
        Document newDocument = reader.read(in);

        // lets ensure names are same
        newDocument.setName(document.getName());

        assertDocumentsEqual(document, newDocument);

        return newDocument;
    }

    protected Document roundTripFull(Document document) throws Exception {
        Document doc2 = roundTripDOM(document);
        Document doc3 = roundTripSAX(doc2);
        Document doc4 = roundTripText(doc3);

        assertDocumentsEqual(document, doc4);

        return doc4;
    }
}

