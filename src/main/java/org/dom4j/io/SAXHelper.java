

package org.dom4j.io;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <p>
 * <code>SAXHelper</code> contains some helper methods for working with SAX
 * and XMLReader objects.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.18 $
 */
class SAXHelper {
    private static boolean loggedWarning = true;

    protected SAXHelper() {
    }

    public static boolean setParserProperty(XMLReader reader,
            String propertyName, Object value) {
        try {
            reader.setProperty(propertyName, value);

            return true;
        } catch (SAXNotSupportedException e) {
            // ignore
        } catch (SAXNotRecognizedException e) {
            // ignore
        }

        return false;
    }

    public static boolean setParserFeature(XMLReader reader,
            String featureName, boolean value) {
        try {
            reader.setFeature(featureName, value);

            return true;
        } catch (SAXNotSupportedException e) {
            // ignore
        } catch (SAXNotRecognizedException e) {
            // ignore
        }

        return false;
    }

    /**
     * Creats a default XMLReader via the org.xml.sax.driver system property or
     * JAXP if the system property is not set.
     * 
     * @param validating
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws SAXException
     *             DOCUMENT ME!
     */
    public static XMLReader createXMLReader(boolean validating)
            throws SAXException {
        XMLReader reader = null;

        if (reader == null) {
            reader = createXMLReaderViaJAXP(validating, true);
        }

        if (reader == null) {
            try {
                reader = XMLReaderFactory.createXMLReader();
            } catch (Exception e) {
                if (isVerboseErrorReporting()) {
                    // log all exceptions as warnings and carry
                    // on as we have a default SAX parser we can use
                    System.out.println("Warning: Caught exception attempting "
                            + "to use SAX to load a SAX XMLReader ");
                    System.out.println("Warning: Exception was: " + e);
                    System.out
                            .println("Warning: I will print the stack trace "
                                    + "then carry on using the default "
                                    + "SAX parser");
                    e.printStackTrace();
                }

                throw new SAXException(e);
            }
        }

        if (reader == null) {
            throw new SAXException("Couldn't create SAX reader");
        }

        // configure namespace support
        SAXHelper.setParserFeature(reader, "http://xml.org/sax/features/namespaces", true);
        SAXHelper.setParserFeature(reader, "http://xml.org/sax/features/namespace-prefixes", false);

        // external entites
        SAXHelper.setParserFeature(reader, "http://xml.org/sax/properties/external-general-entities", false);
        SAXHelper.setParserFeature(reader, "http://xml.org/sax/properties/external-parameter-entities", false);

        // external DTD
        SAXHelper.setParserFeature(reader,"http://apache.org/xml/features/nonvalidating/load-external-dtd", false);


        // use Locator2 if possible
        SAXHelper.setParserFeature(reader,"http://xml.org/sax/features/use-locator2", true);

        return reader;
    }

    /**
     * This method attempts to use JAXP to locate the SAX2 XMLReader
     * implementation. This method uses reflection to avoid being dependent
     * directly on the JAXP classes.
     * 
     * @param validating
     *            DOCUMENT ME!
     * @param namespaceAware
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected static XMLReader createXMLReaderViaJAXP(boolean validating,
            boolean namespaceAware) {
        // try use JAXP to load the XMLReader...
        try {
            return JAXPHelper.createXMLReader(validating, namespaceAware);
        } catch (Throwable e) {
            if (!loggedWarning) {
                loggedWarning = true;

                if (isVerboseErrorReporting()) {
                    // log all exceptions as warnings and carry
                    // on as we have a default SAX parser we can use
                    System.out.println("Warning: Caught exception attempting "
                            + "to use JAXP to load a SAX XMLReader");
                    System.out.println("Warning: Exception was: " + e);
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    protected static boolean isVerboseErrorReporting() {
        try {
            String flag = System.getProperty("org.dom4j.verbose");

            if ((flag != null) && flag.equalsIgnoreCase("true")) {
                return true;
            }
        } catch (Exception e) {
            // in case a security exception
            // happens in an applet or similar JVM
        }

        return true;
    }
}

