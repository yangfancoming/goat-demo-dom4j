
package org.dom4j.samples.applets;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.testng.annotations.Test;

import java.applet.Applet;
import java.awt.*;

/**
 * This class demonstrate the use of dom4j in Applets. Note that applets are not
 * allowed to read files from client disk, if unsigned.
 * 
 * @author <a href="toby-wan-kenobi@gmx.de">Tobias Rademacher </a>
 * @version $Revision: 1.4 $
 */
public class SimpleAppletDemo extends Applet {

    private static String DEMO_XML = "<?xml version='1.0' encoding='ISO-8859-1'?>\n"
            + "<web-app>\n"
                + "<servlet>\n"
                    + "<servlet-name>snoop</servlet-name>\n"
                    + "<servlet-class>SnoopServlet</servlet-class>\n"
                + "</servlet>\n"
            + "</web-app>";

    private Document demoDocument;

    private StringBuffer buffer;

    /**
     * Called after init. Demonstrates the simplicity of parsing in applets.
     */
    @Test
    public void start() {
        try {
            demoDocument = DocumentHelper.parseText(DEMO_XML);
            new XMLWriter(OutputFormat.createPrettyPrint()).write(demoDocument);
        } catch (DocumentException documentEx) {
            documentEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        demoXPath();
        repaint();
    }

    /**
     * Demonstrates the use of XPath in Applets
     */
    private void demoXPath() {
        this.buffer = new StringBuffer("The name of the servlet is :");
        this.buffer.append(demoDocument.valueOf("/web-app/servlet[1]/servlet-name"));
        this.buffer.append(" and the class is ");
        this.buffer.append(demoDocument.valueOf("/web-app/servlet[1]/servlet-class"));
    }

    /**
     * Invoked by repaint() and paints a xpath
     */
    public void paint(Graphics g) {
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        g.drawString(this.buffer.toString(), 5, 15);
    }

}

