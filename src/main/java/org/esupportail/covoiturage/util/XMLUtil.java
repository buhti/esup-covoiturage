package org.esupportail.covoiturage.util;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * Cette classe permet de faciliter la manipulation de données XML.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public final class XMLUtil {

    /**
     * Retourne une représentation formattée du noeud XML spécifié.
     *
     * @param node
     * @return
     */
    public static String format(Node node) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(new DOMSource(node), result);
            return result.getWriter().toString();
        } catch (TransformerException e) {
            return "";
        }
    }

}
