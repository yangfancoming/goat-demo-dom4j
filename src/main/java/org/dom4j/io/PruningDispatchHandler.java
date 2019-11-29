

package org.dom4j.io;

import org.dom4j.ElementPath;

/**
 * This extension on the {@link DispatchHandler}prunes the current {@link
 * org.dom4j.Element} when there are no {@link ElementHandler}objects active
 * the element.
 * 
 * @author Wonne keysers (Realsoftware)
 */
class PruningDispatchHandler extends DispatchHandler {
    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);

        if (getActiveHandlerCount() == 0) {
            elementPath.getCurrent().detach();
        }
    }
}

