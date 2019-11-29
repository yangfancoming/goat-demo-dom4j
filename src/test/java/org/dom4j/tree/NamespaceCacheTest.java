

package org.dom4j.tree;

import org.dom4j.AbstractTestCase;
import org.dom4j.Namespace;

/**
 * A test harness to test the performance of the NamespaceCache
 * 
 * @author <a href="mailto:bfinnell@users.sourceforge.net">Brett Finnell </a>
 */
public class NamespaceCacheTest extends AbstractTestCase {
    private static final int THREADCOUNT = 50;

    private static final int ITERATIONCOUNT = 10000;


    public void testGetSameNamespaceSingleThread() {
        long start = System.currentTimeMillis();
        SameNSTest test = new SameNSTest();
        test.run();

        long end = System.currentTimeMillis();
        System.out.println("Same NS Single took " + (end - start) + " ms");
    }

    public void testGetSameNamespaceMultiThread() throws Exception {
        long start = System.currentTimeMillis();
        runMultiThreadedTest(new SameNSTest());

        long end = System.currentTimeMillis();
        System.out.println("Different NS Single took " + (end - start) + " ms");
    }

    public void testGetNewNamespaceSingleThread() {
        long start = System.currentTimeMillis();
        DifferentNSTest test = new DifferentNSTest();
        test.run();

        long end = System.currentTimeMillis();
        System.out.println("Same NS Multi took " + (end - start) + " ms");
    }

    public void testGetNewNamespaceMultiThread() throws Exception {
        long start = System.currentTimeMillis();
        runMultiThreadedTest(new DifferentNSTest());

        long end = System.currentTimeMillis();
        System.out.println("Different NS Multi took " + (end - start) + " ms");
    }

    private void runMultiThreadedTest(Runnable test) throws Exception {
        // Make the threads
        Thread[] threads = new Thread[THREADCOUNT];

        for (int i = 0; i < THREADCOUNT; i++) {
            threads[i] = new Thread(new SameNSTest());
        }

        // Start the threads
        for (int j = 0; j < THREADCOUNT; j++) {
            threads[j].start();
        }

        // Join with the threads
        for (int k = 0; k < THREADCOUNT; k++) {
            threads[k].join();
        }
    }

    private class SameNSTest implements Runnable {
        public void run() {
            NamespaceCache cache = new NamespaceCache();

            for (int i = 0; i < ITERATIONCOUNT; i++) {
                Namespace ns = cache.get("prefix", "uri");
            }
        }
    }

    private class DifferentNSTest implements Runnable {
        public void run() {
            NamespaceCache cache = new NamespaceCache();

            for (int i = 0; i < ITERATIONCOUNT; i++) {
                Namespace ns = cache.get("prefix", Integer.toString(i));
            }
        }
    }
}

