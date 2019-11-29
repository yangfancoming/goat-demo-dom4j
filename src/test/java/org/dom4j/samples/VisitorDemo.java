/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: VisitorDemo.java,v 1.4 2005/01/29 14:52:57 maartenc Exp $
 */

package org.dom4j.samples;

import org.dom4j.*;

/**
 * A sample program to demonstrate the use of the Visitor Pattern in DOM4J
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class VisitorDemo extends SAXDemo {

    public static void main(String[] args) {
        run(new VisitorDemo(), args);
    }

    public VisitorDemo() {
    }

    protected void process(Document document) throws Exception {
        Visitor visitor = new VisitorSupport() {

            public void visit(Document document) {
                println(document.toString());
            }

            public void visit(DocumentType documentType) {
                println(documentType.toString());
            }

            public void visit(Element node) {
                println(node.toString());
            }

            public void visit(Attribute node) {
                println(node.toString());
            }

            public void visit(CDATA node) {
                println(node.toString());
            }

            public void visit(Comment node) {
                println(node.toString());
            }

            public void visit(Entity node) {
                println(node.toString());
            }

            public void visit(Namespace node) {
                println(node.toString());
            }

            public void visit(ProcessingInstruction node) {
                println(node.toString());
            }

            public void visit(Text node) {
                println(node.toString());
            }
        };

        document.accept(visitor);
    }
}


