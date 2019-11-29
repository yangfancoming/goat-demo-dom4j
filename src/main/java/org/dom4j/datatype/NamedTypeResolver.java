

package org.dom4j.datatype;

import com.sun.msv.datatype.xsd.XSDatatype;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <code>NamedTypeResolver</code> resolves named types for a given QName.
 * </p>
 * 
 * @author Yuxin Ruan
 * @version $Revision: 1.8 $
 */
class NamedTypeResolver {
    protected Map<QName, DocumentFactory> complexTypeMap = new HashMap<QName, DocumentFactory>();

    protected Map<QName, XSDatatype> simpleTypeMap = new HashMap<QName, XSDatatype>();

    protected Map<Element, QName> typedElementMap = new HashMap<Element, QName>();

    protected Map<Element, DocumentFactory> elementFactoryMap = new HashMap<Element, DocumentFactory>();

    protected DocumentFactory documentFactory;

    NamedTypeResolver(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    void registerComplexType(QName type, DocumentFactory factory) {
        complexTypeMap.put(type, factory);
    }

    void registerSimpleType(QName type, XSDatatype datatype) {
        simpleTypeMap.put(type, datatype);
    }

    void registerTypedElement(Element element, QName type,
                              DocumentFactory parentFactory) {
        typedElementMap.put(element, type);
        elementFactoryMap.put(element, parentFactory);
    }

    void resolveElementTypes() {
        for (Element element : typedElementMap.keySet()) {
            QName elementQName = getQNameOfSchemaElement(element);
            QName type = (QName) typedElementMap.get(element);

            if (complexTypeMap.containsKey(type)) {
                DocumentFactory factory = (DocumentFactory) complexTypeMap
                        .get(type);
                elementQName.setDocumentFactory(factory);
            } else if (simpleTypeMap.containsKey(type)) {
                XSDatatype datatype = (XSDatatype) simpleTypeMap.get(type);
                DocumentFactory factory = (DocumentFactory) elementFactoryMap
                        .get(element);

                if (factory instanceof DatatypeElementFactory) {
                    ((DatatypeElementFactory) factory)
                            .setChildElementXSDatatype(elementQName, datatype);
                }
            }
        }
    }

    void resolveNamedTypes() {
        resolveElementTypes();
    }

    private QName getQNameOfSchemaElement(Element element) {
        String name = element.attributeValue("name");

        return getQName(name);
    }

    private QName getQName(String name) {
        return documentFactory.createQName(name);
    }
}

