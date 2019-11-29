

package org.dom4j.util;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;

/**
 * PerThreadSingleton Tester.
 *
 * @author ddlucas
 * @version 1.0
 * @since <pre>
 * 01 / 05 / 2005
 * </pre>
 */
@Test
public class PerThreadSingletonTest {

	private static SingletonStrategy<Map<String, String>> singleton;

	private static ThreadLocal<Map<String, String>> reference = new ThreadLocal<Map<String, String>>();

	static {
		singleton = new PerThreadSingleton<Map<String, String>>();
		singleton.setSingletonClassName(HashMap.class.getName());
	}

	@Test(threadPoolSize = 5, invocationCount = 1000, timeOut = 100000L)
	public void testPerThreadSingleton() throws Exception {
		String tid = Thread.currentThread().getName();
		Map<String, String> map = singleton.instance();

		String expected = "new value";
		if (!map.containsKey(tid) && reference.get() != null) {
			System.out.println("tid=" + tid + " map=" + map);
			System.out.println("reference=" + reference);
			System.out.println("singleton=" + singleton);
			fail("created singleton more than once");
		} else {
			map.put(tid, expected);
			reference.set(map);
		}

		String actual = map.get(tid);
		// System.out.println("tid="+tid+ " map="+map);
		assertEquals("testInstance", expected, actual);

		map = singleton.instance();
		expected = "new value";
		actual = map.get(tid);
		// System.out.println("tid="+tid+ " map="+map);
		// System.out.println("reference="+reference);
		// System.out.println("singleton="+singleton);
		assertEquals("testInstance", expected, actual);
		assertEquals("testInstance reference", reference.get(), map);

	}

}

