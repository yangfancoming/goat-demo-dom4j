

package org.dom4j;

import java.util.Map;

/**
 * <code>ProcessingInstruction</code> defines an XML processing instruction.
 * The {@link Node#getName}method will return the target of the PI and the
 * {@link Node#getText}method will return the data from all of the
 * instructions.
 *
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
@SuppressWarnings("unused")
public interface ProcessingInstruction extends Node {
    /**
     * This method is the equivalent to the {@link #getName}method. It is added
     * for clarity.
     * 
     * @return the target of this PI
     */
    String getTarget();

    /**
     * This method is the equivalent to the {@link #setName}method. It is added
     * for clarity.
     * 
     * @param target
     *            DOCUMENT ME!
     */
    void setTarget(String target);

    /**
     * DOCUMENT ME!
     * 
     * @return the text for all the data associated with the processing
     *         instruction
     */
    String getText();

    /**
     * <p>
     * Returns the value of a specific name in the PI.
     * </p>
     * 
     * @param name
     *            is the name of the attribute to lookup.
     * 
     * @return the value of the named attribute
     */
    String getValue(String name);

    /**
     * DOCUMENT ME!
     * 
     * @return the values for this processing instruction as a Map
     */
    Map<String, String> getValues();

    void setValue(String name, String value);

    void setValues(Map<String, String> data);

    boolean removeValue(String name);
}

