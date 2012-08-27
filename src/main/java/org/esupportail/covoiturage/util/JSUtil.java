package org.esupportail.covoiturage.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class JSUtil {

    public static String convertToArray(Collection<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            sb.append(convertToScalar(value));

            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public static String convertToHash(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        Iterator<?> it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = map.get(key);

            sb.append(convertToScalar(key));
            sb.append(": ");
            sb.append(convertToScalar(value));

            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public static String convertToScalar(Object value) {
        StringBuilder sb = new StringBuilder();

        if (value instanceof Number || value instanceof Boolean) {
            sb.append(value);
        } else {
            sb.append('"');
            sb.append(value);
            sb.append('"');
        }

        return sb.toString();
    }

}
