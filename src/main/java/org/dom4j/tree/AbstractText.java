

package org.dom4j.tree;

import org.dom4j.Visitor;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * <code>AbstractText</code> is an abstract base class for tree implementors
 * to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public abstract class AbstractText extends AbstractCharacterData implements
        org.dom4j.Text {
    public AbstractText() {
    }

    public short getNodeType() {
        return TEXT_NODE;
    }

    public String toString() {
        return super.toString() + " [Text: \"" + getText() + "\"]";
    }

    public String asXML() {
        return getText();
    }

    public void write(Writer writer) throws IOException {
        writer.write(getText());
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

