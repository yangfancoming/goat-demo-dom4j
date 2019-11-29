

package org.dom4j.util;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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
public class SimpleSingletonTest {

    private static SingletonStrategy<Map<String, String>> singleton;

    private static Map<String, String> reference;

    @BeforeClass
    public void setUp() throws Exception {
        if (singleton == null) {
            singleton = new PerThreadSingleton<Map<String, String>>();
            singleton.setSingletonClassName(HashMap.class.getName());
        }
    }

    public void testFirstInstance() throws Exception {
        Map<String, String> map = singleton.instance();
        Assert.assertNull(map.get("Test"), "testInstance");

        String expected = "new value";
        map.put("Test", expected);

        map = singleton.instance();
        reference = map;
        Assert.assertEquals(map.get("Test"), expected, "testFirstInstance");
    }

    @Test(dependsOnMethods = "testFirstInstance")
    public void testSecondInstance() throws Exception {
        Map<String, String> map = singleton.instance();
        Assert.assertEquals(map, reference, "testSecondInstance reference");
        Assert.assertEquals(map.get("Test"), "new value", "testInstance");
    }

}

