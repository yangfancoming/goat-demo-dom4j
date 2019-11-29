

package org.dom4j.swing;

import org.dom4j.Document;

import javax.swing.tree.DefaultTreeModel;

/**
 * <p>
 * <code>DocumentTreeModel</code> implements a Swing TreeModel for a dom4j XML
 * Document.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Jakob Jenkov
 * @version $Revision: 1.7 $
 */
public class DocumentTreeModel extends DefaultTreeModel {
    /** The document for this model */
    protected Document document;

    public DocumentTreeModel(Document document) {
        super(new BranchTreeNode(document));
        this.document = document;
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the <code>Document</code> instance that this
     *         <code>TreeModel</code> is based on
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the <code>Document</code> instance that this
     * <code>TreeModel</code> is based on
     * 
     * @param document
     *            DOCUMENT ME!
     */
    public void setDocument(Document document) {
        this.document = document;
        setRoot(new BranchTreeNode(document));
    }
}

