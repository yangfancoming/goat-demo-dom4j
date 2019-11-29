

package org.dom4j;

import org.testng.annotations.Test;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A test harness to test the dom4j package in a threaded environment
 *
 * @author <a href="mailto:ddlucas@lse.com">David Lucas </a>
 * @version $Revision: 1.3 $
 */
@Test
public class ThreadingTest extends AbstractTestCase {
	private static final ThreadLocal FORMATTER_CACHE = new ThreadLocal();

	private static final String SEPERATOR = " - ";

	private static final FieldPosition FIELD_ZERO = new FieldPosition(0);

	private static void preformat(StringBuffer strBuf, String context) {
		long now = System.currentTimeMillis();
		Date currentTime = new Date(now);
		SimpleDateFormat formatter = (SimpleDateFormat) FORMATTER_CACHE.get();

		if (formatter == null) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz");
			FORMATTER_CACHE.set(formatter);
		}

		strBuf.append("[");
		formatter.format(currentTime, strBuf, FIELD_ZERO);
		strBuf.append(" (").append(now).append(") ]");

		strBuf.append(SEPERATOR);
		strBuf.append(getThreadId());
		strBuf.append(SEPERATOR);
		strBuf.append(context);
		strBuf.append(SEPERATOR);
	}

	private static String getThreadId() {
		return Thread.currentThread().getName();
	}

	/**
	 * This test combines many different types of operations on DOM4J in a
	 * threaded environment. If a problem occurs with threading, the tests will
	 * fail. This was used to help isolate an internal threading issue.
	 * Unfortunately it may not always create the condition necessary to break
	 * un-thread-safe code. This is due to the nature of the machine, JVM, and
	 * application and if the conditions are right. Typically the problems of
	 * multithreading occur due to an unprotected HashMap or ArrayList in a
	 * class being used by more than one thread. Also, most developers think
	 * that their class or object instance will only be used by one thread. But
	 * if a factory or singleton caches a class or instance, it can quickly
	 * become an unsafe environment. Hence this test to assist in locating
	 * threading issues.
	 */
	@Test(threadPoolSize = 5, invocationCount = 1000, timeOut = 100000L)
	public void testCombo() {
		int loop = 10;

		try {
			long begin = System.currentTimeMillis();
			String value = null;
			String expected = null;
			String xml = null;
			Document doc = null;
			Element root = null;
			Element item = null;
			Element newItem = null;
			QName qn = null;
			Namespace ns = null;
			long now = 0;

			xml = "<ROOT xmlns:t0=\"http://www.lse.com/t0\" >"
							+ "  <ctx><type>Context</type></ctx>"
							+ "  <A><B><C><D>This is a TEST</D></C></B></A>"
							+ "  <t0:Signon><A>xyz</A><t0:Cust>customer</t0:Cust>"
							+ "</t0:Signon></ROOT>";

			for (int i = 0; i < loop; i++) {
				doc = DocumentHelper.parseText(xml);

				root = doc.getRootElement();
				ns = Namespace.get("t0", "http://www.lse.com/t0");
				qn = QName.get("Signon", ns);
				item = root.element(qn);
				value = item.asXML();
				expected = "<t0:Signon xmlns:t0=\"http://www.lse.com/t0\">"
								+ "<A>xyz</A><t0:Cust>customer</t0:Cust></t0:Signon>";
				assertEquals("test t0:Signon ", expected, value);

				qn = root.getQName("Test");
				newItem = DocumentHelper.createElement(qn);
				now = System.currentTimeMillis();
				newItem.setText(String.valueOf(now));
				root.add(newItem);

				qn = root.getQName("Test2");
				newItem = DocumentHelper.createElement(qn);
				now = System.currentTimeMillis();
				newItem.setText(String.valueOf(now));
				root.add(newItem);

				item = root.element(qn);
				item.detach();
				item.setQName(qn);
				root.add(item);
				value = item.asXML();
				expected = "<Test2>" + now + "</Test2>";
				assertEquals("test Test2 ", expected, value);

				qn = root.getQName("Test3");
				newItem = DocumentHelper.createElement(qn);
				now = System.currentTimeMillis();
				newItem.setText(String.valueOf(now));
				root.add(newItem);

				item = root.element(qn);
				item.detach();
				item.setQName(qn);
				root.add(item);
				value = item.asXML();
				expected = "<Test3>" + now + "</Test3>";
				assertEquals("test Test3 ", expected, value);

				qn = item.getQName("Test4");
				newItem = DocumentHelper.createElement(qn);
				now = System.currentTimeMillis();
				newItem.setText(String.valueOf(now));
				root.add(newItem);

				item = root.element(qn);
				item.detach();
				item.setQName(qn);
				root.add(item);
				value = item.asXML();
				expected = "<Test4>" + now + "</Test4>";
				assertEquals("test Test4 ", expected, value);
			}

			double duration = System.currentTimeMillis() - begin;
			double avg = duration / loop;
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Exception in test: " + e.getMessage(), false);
		}
	}

	/**
	 * This test isolates QNameCache in a multithreaded environment.
	 */
	@Test(threadPoolSize = 5, invocationCount = 1000, timeOut = 100000L)
	public void testQNameCache() {
		int loop = 100;

		try {
			long begin = System.currentTimeMillis();
			String value = null;
			String expected = null;
			String xml = null;
			Document doc = null;
			Element root = null;
			Element item = null;
			Element newItem = null;
			QName qn = null;
			Namespace ns = null;
			long now = 0;

			xml = "<ROOT xmlns:t0=\"http://www.lse.com/t0\" >"
							+ "  <ctx><type>Context</type></ctx>"
							+ "  <A><B><C><D>This is a TEST</D></C></B></A>"
							+ "  <t0:Signon><A>xyz</A><t0:Cust>customer</t0:Cust>"
							+ "</t0:Signon></ROOT>";

			for (int i = 0; i < loop; i++) {
				doc = DocumentHelper.parseText(xml);
				root = doc.getRootElement();

				qn = DocumentHelper.createQName("test");
				value = fetchValue(qn);
				expected = "<test/>";
				assertEquals("test test ", expected, value);

				// creat it again
				qn = DocumentHelper.createQName("test");
				value = fetchValue(qn);
				expected = "<test/>";
				assertEquals("test test again ", expected, value);

				qn = root.getQName("t0:Signon");
				value = fetchValue(qn);
				expected = "<t0:Signon xmlns:t0=\"http://www.lse.com/t0\"/>";
				assertEquals("test t0:Signon ", expected, value);
			}

			double duration = System.currentTimeMillis() - begin;
			double avg = duration / loop;
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Exception in test: " + e.getMessage(), false);
		}
	}

	/**
	 * This method creates a value that can be expected during a test
	 *
	 * @param qn
	 * @return
	 */
	public String fetchValue(QName qn) {
		String value = null;

		StringBuffer sb = new StringBuffer();
		sb.append("<");

		String prefix = qn.getNamespacePrefix();

		if ((prefix != null) && (prefix.length() > 0)) {
			sb.append(prefix).append(":");
		}

		sb.append(qn.getName());

		String uri = qn.getNamespaceURI();

		if ((uri != null) && (uri.length() > 0)) {
			sb.append(" xmlns");

			if ((prefix != null) && (prefix.length() > 0)) {
				sb.append(":").append(prefix);
			}

			sb.append("=\"").append(uri).append("\"");
		}

		sb.append("/>");

		value = sb.toString();

		return value;
	}

}

