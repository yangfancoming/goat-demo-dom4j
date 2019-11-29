/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: LargeDocumentDemo2.java,v 1.4 2005/01/29 14:52:57 maartenc Exp $
 */

package org.dom4j.samples;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

/**
 * A test harness to test the content API in DOM4J
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class LargeDocumentDemo2 extends SAXDemo {

    public static void main(String[] args) {
        run(new LargeDocumentDemo2(), args);
    }

    public LargeDocumentDemo2() {
    }

    public void run(String[] args) throws Exception {
        if (args.length < 1) {
            printUsage("<XML document URL>");
            return;
        }

        String xmlFile = args[0];

        Document document = parse(xmlFile);
        process(document);
    }

    protected Document parse(String url) throws Exception {
        SAXReader reader = new SAXReader();

        println("Parsing document:   " + url);

        // enable pruning to call me back as each Element is complete
        reader.addHandler("/PLAY/ACT", new playActHandler());

        println("##### starting parse");
        Document document = reader.read(url);
        println("##### finished parse");

        // the document will be complete but have the prunePath elements pruned
        println("Now lets dump what is left of the document after pruning...");

        return document;
    }

    class playActHandler implements ElementHandler {
        public void onStart(ElementPath path) {
            Element element = path.getCurrent();
            path.addHandler("SCENE/SPEECH", new actSceneSpeechHandler());
        }

        public void onEnd(ElementPath path) {
            Element element = path.getCurrent();
            println("Found Act: " + element.element("TITLE").getText());
            path.removeHandler("SCENE/SPEECH");
            element.detach();
        }
    }

    class actSceneSpeechHandler implements ElementHandler {
        public void onStart(ElementPath path) {
            Element element = path.getCurrent();
            println("Found Start of Speech");
        }

        public void onEnd(ElementPath path) {
            Element element = path.getCurrent();
            println("Found End of Speech by Speaker: "
                    + element.element("SPEAKER").getText());
            element.detach();
        }
    }
}

