

package org.dom4j.datatype;

import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * Test harness to test the various data types supported in the XML Schema Data
 * Type integration.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class DataTypesTest extends AbstractDataTypeTestCase {

    public void testgMonthDay()  {
        testNodes("//gMonthDayTag", Calendar.class);
    }

    public void testgDay()  {
        testNodes("//gDayTag", Calendar.class);
    }

    public void testgMonth()  {
        testNodes("//gMonthTag", Calendar.class);
    }

    public void testDate()  {
        testNodes("//dateTag", Calendar.class);
    }

    public void testTime()  {
        testNodes("//timeTag", Calendar.class);
    }

    public void testDateTime()  {
        testNodes("//dateTimeTag", Calendar.class);
    }

    public void testgYearMonth()  {
        testNodes("//gYearMonthTag", Calendar.class);
    }

    public void testgYear()  {
        testNodes("//gYearTag", Calendar.class);
    }

    public void testBoolean()  {
        testNodes("//booleanTag", Boolean.class);
    }

    public void testBase64Binary()  {
        testNodes("//base64BinaryTag", byte[].class);
    }

    public void testHexBinary()  {
        testNodes("//hexBinaryTag", byte[].class);
    }

    // Number types
    public void testFloat()  {
        testNodes("//floatTag", Float.class);
    }

    public void testDouble()  {
        testNodes("//doubleTag", Double.class);
    }

    public void testDecimal()  {
        testNodes("//decimalTag", BigDecimal.class);
    }

    public void testInteger()  {
        testNodes("//integerTag", BigInteger.class);
    }

    public void testNonPositiveInteger()  {
        testNodes("//nonPositiveIntegerTag", BigInteger.class);
    }

    public void testNegativeInteger()  {
        testNodes("//negativeIntegerTag", BigInteger.class);
    }

    public void testLong()  {
        testNodes("//longTag", Long.class);
    }

    public void testInt()  {
        testNodes("//intTag", Integer.class);
    }

    public void testShort()  {
        testNodes("//shortTag", Short.class);
    }

    public void testByte()  {
        testNodes("//byteTag", Byte.class);
    }

    public void testNonNegativeInteger()  {
        testNodes("//nonNegativeIntegerTag", BigInteger.class);
    }

    public void testUnsignedLong()  {
        testNodes("//unsignedLongTag", BigInteger.class);
    }

    public void testUnsignedInt()  {
        testNodes("//unsignedIntTag", Long.class);
    }

    public void testUnsignedShort()  {
        testNodes("//unsignedShortTag", Integer.class);
    }

    public void testUnsignedByte()  {
        testNodes("//unsignedByteTag", Short.class);
    }

    public void testPositiveInteger()  {
        testNodes("//positiveIntegerTag", BigInteger.class);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception  {
        super.setUp();
        DocumentFactory factory = DatatypeDocumentFactory.getInstance();
        SAXReader reader = new SAXReader(factory);
        document = getDocument("/xml/test/schema/test.xml", reader);
    }
}

