

package org.dom4j.jaxb;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.*;

/**
 * Writes {@link javax.xml.bind.Element}objects to an XML stream. {@link
 * javax.xml.bind.Element} instances can be created using the ObjectFactory that
 * is generated by the JAXB compiler.
 * 
 * @author Wonne Keysers (Realsoftware.be)
 * 
 * @see org.dom4j.io.XMLWriter
 * @see javax.xml.bind.JAXBContext
 */
public class JAXBWriter extends JAXBSupport {
    private XMLWriter xmlWriter;

    private OutputFormat outputFormat;

    /**
     * Creates a new JAXBWriter for the given JAXB context path. This is the
     * Java package where JAXB can find the generated XML classes. This package
     * MUST contain jaxb.properties!
     *
     * @param contextPath
     *            JAXB context path to be used
     *
     * @see javax.xml.bind.JAXBContext
     */
    public JAXBWriter(String contextPath) {
        super(contextPath);
        outputFormat = new OutputFormat();
    }

    /**
     * Creates a new JAXBWriter for the given JAXB context path. The specied
     * {@link org.dom4j.io.OutputFormat}will be used for writing the XML
     * stream.
     *
     * @param contextPath
     *            JAXB context path to be used
     * @param outputFormat
     *            the DOM4J {@link org.dom4j.io.OutputFormat}to be used
     *
     * @see javax.xml.bind.JAXBContext
     */
    public JAXBWriter(String contextPath, OutputFormat outputFormat) {
        super(contextPath);
        this.outputFormat = outputFormat;
    }

    /**
     * Creates a new JAXBWriter for the given JAXB context path, using the
     * specified {@link ClassLoader}. (This is the Java package where
     * JAXB can find the generated XML classes. This package MUST contain
     * jaxb.properties!)
     *
     * @param contextPath
     *            JAXB context path to be used
     * @param classloader
     *            the classloader to be used for loading JAXB
     *
     * @see javax.xml.bind.JAXBContext
     */
    public JAXBWriter(String contextPath, ClassLoader classloader) {
        super(contextPath, classloader);
    }

    /**
     * Creates a new JAXBWriter for the given JAXB context path, using the
     * specified {@link ClassLoader}. The specied {@link
     * org.dom4j.io.OutputFormat} will be used while writing the XML stream.
     *
     * @param contextPath
     *            JAXB context path to be used
     * @param classloader
     *            the class loader to be used to load JAXB
     * @param outputFormat
     *            the DOM4J {@link org.dom4j.io.OutputFormat}to be used
     *
     * @see javax.xml.bind.JAXBContext
     */
    public JAXBWriter(String contextPath, ClassLoader classloader,
            OutputFormat outputFormat) {
        super(contextPath, classloader);
        this.outputFormat = outputFormat;
    }

    /**
     * Returns the OutputFormat that will be used when writing the XML stream.
     *
     * @return Returns the output format.
     */
    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    /**
     * Defines to write the resulting output to the specified {@link
     * File}.
     *
     * @param file
     *            file to write to
     *
     * @throws IOException
     *             when the file cannot be found
     */
    public void setOutput(File file) throws IOException {
        getWriter().setOutputStream(new FileOutputStream(file));
    }

    /**
     * Defines to write the resulting output to the specified {@link
     * OutputStream}
     *
     * @param outputStream
     *            outputStream to write to.
     *
     * @throws IOException
     *             DOCUMENT ME!
     */
    public void setOutput(OutputStream outputStream) throws IOException {
        getWriter().setOutputStream(outputStream);
    }

    /**
     * Defines to write the resulting output to the specified {@link Writer}.
     *
     * @param writer
     *            writer to write to
     *
     * @throws IOException DOCUMENT ME!
     */
    public void setOutput(Writer writer) throws IOException {
        getWriter().setWriter(writer);
    }

    /**
     * Start a document by writing the initial XML declaration to the output.
     * This must be done prior to writing any other elements.
     *
     * @throws IOException
     *             if an error occured while writing the output
     * @throws SAXException
     *             thrown by the underlying SAX driver
     */
    public void startDocument() throws IOException, SAXException {
        getWriter().startDocument();
    }

    /**
     * Stop writing the document to the output. This must be done when all other
     * elements are finished.
     *
     * @throws IOException
     *             if an error occured while writing the output
     * @throws SAXException
     *             thrown by the underlying SAX driver
     */
    public void endDocument() throws IOException, SAXException {
        getWriter().endDocument();
    }

    /**
     * Writes the specified {@link javax.xml.bind.Element}to the document.
     * {@link javax.xml.bind.Element}instances can be created using the
     * ObjectFactory that is generated by the JAXB compiler.
     *
     * @param jaxbObject DOCUMENT ME!
     *
     * @throws IOException
     *             if an error occured while writing the output
     * @throws JAXBException
     *             when an error occured while marshalling the jaxbObject
     */
    public void write(javax.xml.bind.Element jaxbObject) throws IOException,
            JAXBException {
        getWriter().write(marshal(jaxbObject));
    }

    /**
     * Writes the closing tag of the specified {@link javax.xml.bind.Element}to
     * the document. This method can be used for writing {@link
     * javax.xml.bind.Element} instances can be created using the ObjectFactory
     * that is generated by the JAXB compiler.
     *
     * @param jaxbObject
     *            the JAXB element to write
     *
     * @throws IOException
     *             if an error occured while writing the output
     * @throws JAXBException
     *             when an error occured while marshalling the jaxbObject
     */
    public void writeClose(javax.xml.bind.Element jaxbObject)
            throws IOException, JAXBException {
        getWriter().writeClose(marshal(jaxbObject));
    }

    /**
     * Writes the opening tag of the specified {@link javax.xml.bind.Element}to
     * the document. {@link javax.xml.bind.Element}instances can be created
     * using the ObjectFactory that is generated by the JAXB compiler.
     *
     * @param jaxbObject
     *            the JAXB element to write
     *
     * @throws IOException
     *             if an error occured while writing the output
     * @throws JAXBException
     *             when an error occured while marshalling the jaxbObject
     */
    public void writeOpen(javax.xml.bind.Element jaxbObject)
            throws IOException, JAXBException {
        getWriter().writeOpen(marshal(jaxbObject));
    }

    /**
     * Writes the specified {@link org.dom4j.Element}to the document.
     *
     * @param element
     *            the {@link org.dom4j.Element}to write
     *
     * @throws IOException
     *             if an error occured while writing the output
     */
    public void writeElement(Element element) throws IOException {
        getWriter().write(element);
    }

    /**
     * Writes the closing tag of the specified {@link org.dom4j.Element}to the
     * document.
     *
     * @param element
     *            the {@link org.dom4j.Element}to write
     *
     * @throws IOException
     *             if an error occured while writing the output
     */
    public void writeCloseElement(Element element) throws IOException {
        getWriter().writeClose(element);
    }

    /**
     * Writes the opening tag of the specified {@link org.dom4j.Element}to the
     * document.
     *
     * @param element
     *            the {@link org.dom4j.Element}to write
     * 
     * @throws IOException
     *             if an error occured while writing the output
     */
    public void writeOpenElement(Element element) throws IOException {
        getWriter().writeOpen(element);
    }

    private XMLWriter getWriter() throws IOException {
        if (xmlWriter == null) {
            if (this.outputFormat != null) {
                xmlWriter = new XMLWriter(outputFormat);
            } else {
                xmlWriter = new XMLWriter();
            }
        }

        return xmlWriter;
    }
}

