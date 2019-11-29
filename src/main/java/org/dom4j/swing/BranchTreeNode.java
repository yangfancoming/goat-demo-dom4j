

package org.dom4j.swing;

import org.dom4j.Branch;
import org.dom4j.CharacterData;
import org.dom4j.Node;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>
 * <code>BranchTreeNode</code> implements the Swing TreeNode interface to bind
 * dom4j XML Branch nodes (i.e. Document and Element nodes) to a Swing
 * TreeModel.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Jakob Jenkov
 * @version $Revision: 1.10 $
 */
public class BranchTreeNode extends LeafTreeNode {
    /** Stores the child tree nodes */
    protected List<TreeNode> children;

    public BranchTreeNode() {
    }

    public BranchTreeNode(Branch xmlNode) {
        super(xmlNode);
    }

    public BranchTreeNode(TreeNode parent, Branch xmlNode) {
        super(parent, xmlNode);
    }

    // TreeNode methods
    // -------------------------------------------------------------------------
    public Enumeration<TreeNode> children() {
        return new Enumeration<TreeNode>() {
            private int index = -1;

            public boolean hasMoreElements() {
                return (index + 1) < getChildCount();
            }

            public TreeNode nextElement() {
                return getChildAt(++index);
            }
        };
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public TreeNode getChildAt(int childIndex) {
        return (TreeNode) getChildList().get(childIndex);
    }

    public int getChildCount() {
        return getChildList().size();
    }

    public int getIndex(TreeNode node) {
        return getChildList().indexOf(node);
    }

    public boolean isLeaf() {
        return getXmlBranch().nodeCount() <= 0;
    }

    public String toString() {
        return xmlNode.getName();
    }

    // Implementation methods
    // -------------------------------------------------------------------------

    /**
     * Uses Lazy Initialization pattern to create a List of children
     * 
     * @return DOCUMENT ME!
     */
    protected List<TreeNode> getChildList() {
        // for now lets just create the children once, the first time they
        // are asked for.
        // XXXX - we may wish to detect inconsistencies here....
        if (children == null) {
            children = createChildList();
        }

        return children;
    }

    /**
     * Factory method to create List of children TreeNodes
     * 
     * @return DOCUMENT ME!
     */
    protected List<TreeNode> createChildList() {
        // add attributes and content as children?
        Branch branch = getXmlBranch();
        int size = branch.nodeCount();
        List<TreeNode> childList = new ArrayList<TreeNode>(size);

        for (int i = 0; i < size; i++) {
            Node node = branch.node(i);

            // ignore whitespace text nodes
            if (node instanceof CharacterData) {
                String text = node.getText();

                if (text == null) {
                    continue;
                }

                text = text.trim();

                if (text.length() <= 0) {
                    continue;
                }
            }

            childList.add(createChildTreeNode(node));
        }

        return childList;
    }

    /**
     * Factory method to create child tree nodes for a given XML node type
     * 
     * @param xmlNode
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected TreeNode createChildTreeNode(Node xmlNode) {
        if (xmlNode instanceof Branch) {
            return new BranchTreeNode(this, (Branch) xmlNode);
        } else {
            return new LeafTreeNode(this, xmlNode);
        }
    }

    protected Branch getXmlBranch() {
        return (Branch) xmlNode;
    }
}

