

package org.dom4j.tree;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * <code>FlyweightProcessingInstruction</code> is a Flyweight pattern
 * implementation of a singly linked, read-only XML Processing Instruction.
 * </p>
 * 
 * <p>
 * This node could be shared across documents and elements though it does not
 * support the parent relationship.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class FlyweightProcessingInstruction extends AbstractProcessingInstruction {
    /** The target of the PI */
    protected String target;

    /** The values for the PI as a String */
    protected String text;

    /** The values for the PI in name/value pairs */
    protected Map<String, String> values;

    /**
     * A default constructor for implementors to use.
     */
    public FlyweightProcessingInstruction() {
    }

    /**
     * <p>
     * This will create a new PI with the given target and values
     * </p>
     * 
     * @param target
     *            is the name of the PI
     * @param values
     *            is the <code>Map</code> of the values for the PI
     */
    public FlyweightProcessingInstruction(String target, Map<String, String> values) {
        this.target = target;
        this.values = values;
        this.text = toString(values);
    }

    /**
     * <p>
     * This will create a new PI with the given target and values
     * </p>
     * 
     * @param target
     *            is the name of the PI
     * @param text
     *            is the values for the PI as text
     */
    public FlyweightProcessingInstruction(String target, String text) {
        this.target = target;
        this.text = text;
        this.values = parseValues(text);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        throw new UnsupportedOperationException("This PI is read-only and "
                + "cannot be modified");
    }

    public String getText() {
        return text;
    }

    public String getValue(String name) {
        String answer = (String) values.get(name);

        if (answer == null) {
            return "";
        }

        return answer;
    }

    public Map<String, String> getValues() {
        return Collections.unmodifiableMap(values);
    }

    protected Node createXPathResult(Element parent) {
        return new DefaultProcessingInstruction(parent, getTarget(), getText());
    }
}

