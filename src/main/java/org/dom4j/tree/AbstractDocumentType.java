

package org.dom4j.tree;

import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Visitor;
import org.dom4j.dtd.Decl;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <code>AbstractDocumentType</code> is an abstract base class for tree
 * implementors to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.17 $
 */
public abstract class AbstractDocumentType extends AbstractNode implements DocumentType {
    public AbstractDocumentType() {
    }

    public short getNodeType() {
        return DOCUMENT_TYPE_NODE;
    }

    public String getName() {
        return getElementName();
    }

    public void setName(String name) {
        setElementName(name);
    }

    public String getPath(Element context) {
        // not available in XPath
        return "";
    }

    public String getUniquePath(Element context) {
        // not available in XPath
        return "";
    }

    /**
     * Returns the text format of the declarations if applicable, or the empty
     * String
     * 
     * @return DOCUMENT ME!
     */
    public String getText() {
        List<Decl> list = getInternalDeclarations();

        if ((list != null) && (list.size() > 0)) {
            StringBuilder buffer = new StringBuilder();
            Iterator<Decl> iter = list.iterator();

            if (iter.hasNext()) {
                Decl decl = iter.next();
                buffer.append(decl.toString());

                while (iter.hasNext()) {
                    decl = iter.next();
                    buffer.append("\n");
                    buffer.append(decl.toString());
                }
            }

            return buffer.toString();
        }

        return "";
    }

    public String toString() {
        return super.toString() + " [DocumentType: " + asXML() + "]";
    }

    public String asXML() {
        StringBuilder buffer = new StringBuilder("<!DOCTYPE ");
        buffer.append(getElementName());

        boolean hasPublicID = false;
        String publicID = getPublicID();

        if ((publicID != null) && (publicID.length() > 0)) {
            buffer.append(" PUBLIC \"");
            buffer.append(publicID);
            buffer.append("\"");
            hasPublicID = true;
        }

        String systemID = getSystemID();

        if ((systemID != null) && (systemID.length() > 0)) {
            if (!hasPublicID) {
                buffer.append(" SYSTEM");
            }

            buffer.append(" \"");
            buffer.append(systemID);
            buffer.append("\"");
        }

        buffer.append(">");

        return buffer.toString();
    }

    public void write(Writer writer) throws IOException {
        writer.write("<!DOCTYPE ");
        writer.write(getElementName());

        boolean hasPublicID = false;
        String publicID = getPublicID();

        if ((publicID != null) && (publicID.length() > 0)) {
            writer.write(" PUBLIC \"");
            writer.write(publicID);
            writer.write("\"");
            hasPublicID = true;
        }

        String systemID = getSystemID();

        if ((systemID != null) && (systemID.length() > 0)) {
            if (!hasPublicID) {
                writer.write(" SYSTEM");
            }

            writer.write(" \"");
            writer.write(systemID);
            writer.write("\"");
        }

        List<Decl> list = getInternalDeclarations();

        if ((list != null) && (list.size() > 0)) {
            writer.write(" [");

            for (Decl decl : list) {
                writer.write("\n  ");
                writer.write(decl.toString());
            }

            writer.write("\n]");
        }

        writer.write(">");
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

