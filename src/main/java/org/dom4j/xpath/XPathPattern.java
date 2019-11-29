

package org.dom4j.xpath;

import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.XPathException;
import org.jaxen.*;
import org.jaxen.dom4j.DocumentNavigator;
import org.jaxen.pattern.Pattern;
import org.jaxen.pattern.PatternParser;
import org.jaxen.saxpath.SAXPathException;

import java.util.Collections;

/**
 * <p>
 * <code>XPathPattern</code> is an implementation of Pattern which uses an
 * XPath xpath.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.18 $
 */
public class XPathPattern implements org.dom4j.rule.Pattern {
    private String text;

    private Pattern pattern;

    private Context context;

    public XPathPattern(Pattern pattern) {
        this.pattern = pattern;
        this.text = pattern.getText();
        this.context = new Context(getContextSupport());
    }

    public XPathPattern(String text) {
        this.text = text;
        this.context = new Context(getContextSupport());

        try {
            this.pattern = PatternParser.parse(text);
        } catch (SAXPathException e) {
            throw new InvalidXPathException(text, e.getMessage());
        } catch (RuntimeException e) {
            throw new InvalidXPathException(text);
        }
    }

    public boolean matches(Node node) {
        try {
            context.setNodeSet(Collections.singletonList(node));

            return pattern.matches(node, context);
        } catch (JaxenException e) {
            handleJaxenException(e);

            return false;
        }
    }

    public String getText() {
        return text;
    }

    public double getPriority() {
        return pattern.getPriority();
    }

    public org.dom4j.rule.Pattern[] getUnionPatterns() {
        Pattern[] patterns = pattern.getUnionPatterns();

        if (patterns != null) {
            int size = patterns.length;
            XPathPattern[] answer = new XPathPattern[size];

            for (int i = 0; i < size; i++) {
                answer[i] = new XPathPattern(patterns[i]);
            }

            return answer;
        }

        return null;
    }

    public short getMatchType() {
        return pattern.getMatchType();
    }

    public String getMatchesNodeName() {
        return pattern.getMatchesNodeName();
    }

    public void setVariableContext(VariableContext variableContext) {
        context.getContextSupport().setVariableContext(variableContext);
    }

    public String toString() {
        return "[XPathPattern: text: " + text + " Pattern: " + pattern + "]";
    }

    protected ContextSupport getContextSupport() {
        return new ContextSupport(new SimpleNamespaceContext(),
                XPathFunctionContext.getInstance(),
                new SimpleVariableContext(), DocumentNavigator.getInstance());
    }

    protected void handleJaxenException(JaxenException exception)
            throws XPathException {
        throw new XPathException(text, exception);
    }
}

