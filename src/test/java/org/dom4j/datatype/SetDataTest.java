

package org.dom4j.datatype;

import org.dom4j.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * Tests setting the value of datatype aware element or attribute value
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class SetDataTest extends AbstractTestCase {
	private DatatypeDocumentFactory factory = new DatatypeDocumentFactory();

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAttribute()  {
		QName personName = factory.createQName("person");
		QName ageName = factory.createQName("age");

		Element person = factory.createElement(personName);

		person.addAttribute(ageName, "10");

		Attribute age = person.attribute(ageName);

		assertTrue("Created DatatypeAttribute not correct", age instanceof DatatypeAttribute);

		log("Found attribute: " + age);

		Object data = age.getData();
		Object expected = new BigInteger("10");

		assertEquals("Data is correct type", BigInteger.class, data.getClass());

		assertEquals("Set age correctly", expected, data);

		age.setValue("32");
		data = age.getData();
		expected = new BigInteger("32");

		assertEquals("Set age correctly", expected, data);

		/**
		 * not sure if numeric types should be round tripped back to BigDecimal
		 * (say) age.setData( new Long( 21 ) ); data = age.getData(); expected =
		 * new BigInteger( "21" ); assertEquals( "Set age correctly", expected,
		 * data );
		 */

		// now lets set an invalid value
		age.setValue("abc");
		fail("Appeared to set an invalid value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAttributeWithNamespace()  {
		QName personName = factory.createQName("person", "t", "urn://testing");
		QName ageName = factory.createQName("age", "t", "urn://testing");

		Element person = factory.createElement(personName);

		person.addAttribute(ageName, "10");

		Attribute age = person.attribute(ageName);

		assertTrue("Created DatatypeAttribute not correct",
						age instanceof DatatypeAttribute);

		log("Found attribute: " + age);

		Object data = age.getData();
		Object expected = new BigInteger("10");

		assertEquals("Data is correct type", BigInteger.class, data.getClass());

		assertEquals("Set age correctly", expected, data);

		age.setValue("32");
		data = age.getData();
		expected = new BigInteger("32");

		assertEquals("Set age correctly", expected, data);

		age.setValue("abc");
		fail("Appeared to set an invalid value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testElement()  {
		QName personName = factory.createQName("person");
		QName numberOfCarsName = factory.createQName("numberOfCars");

		Element person = factory.createElement(personName);

		Element cars = person.addElement(numberOfCarsName);

		log("Found element: " + cars);

		short expected = 10;
		cars.setData(expected);

		Object data = cars.getData();

		assertEquals("Data is correct type", Short.class, data.getClass());
		assertEquals("Set cars correctly", expected, data);

		cars.setData((short) 32);
		data = cars.getData();
		expected = 32;

		assertEquals("Set cars correctly", expected, data);

		cars.setText("34");
		data = cars.getData();
		expected = 34;

		assertEquals("Set cars correctly", expected, data);

		// now lets set an invalid value
		cars.setText("abc");
		fail("Appeared to set an invalid value");
	}

	// Implementation methods
	// -------------------------------------------------------------------------
	@BeforeMethod
	public void setUp() throws Exception {
		super.setUp();

		Document schema = getDocument("/xml/test/schema/personal.xsd");
		factory.loadSchema(schema);

		Namespace ns = new Namespace("t", "urn://testing");
		factory.loadSchema(schema, ns);
	}
}

