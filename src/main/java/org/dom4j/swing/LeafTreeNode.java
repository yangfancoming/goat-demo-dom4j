

package org.dom4j.swing;

import org.dom4j.Node;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

/**
 * <p>
 * <code>LeafTreeNode</code> implements the Swing TreeNode interface to bind a
 * leaf XML nodes to a Swing TreeModel.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Jakob Jenkov
 * @version $Revision: 1.7 $
 */
public class LeafTreeNode implements TreeNode {
    protected static final Enumeration<? extends TreeNode> EMPTY_ENUMERATION = new Enumeration() {
        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            return null;
        }
    };

    /** The parent node of this TreeNode */
    private TreeNode parent;

    /** The dom4j Node which contains the */
    protected Node xmlNode;

    public LeafTreeNode() {
    }

    public LeafTreeNode(Node xmlNode) {
        this.xmlNode = xmlNode;
    }

    public LeafTreeNode(TreeNode parent, Node xmlNode) {
        this.parent = parent;
        this.xmlNode = xmlNode;
    }

    // TreeNode methods
    // -------------------------------------------------------------------------
    public Enumeration<? extends TreeNode> children() {
        return EMPTY_ENUMERATION;
    }

    public boolean getAllowsChildren() {
        return false;
    }

    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    public int getChildCount() {
        return 0;
    }

    public int getIndex(TreeNode node) {
        return -1;
    }

    public TreeNode getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return true;
    }

    public String toString() {
        // should maybe do things differently based on content?
        String text = xmlNode.getText();

        return (text != null) ? text.trim() : "";
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * Sets the parent of this node but doesn't change the parents children
     * 
     * @param parent
     *            DOCUMENT ME!
     */
    public void setParent(LeafTreeNode parent) {
        this.parent = parent;
    }

    public Node getXmlNode() {
        return xmlNode;
    }
}

