/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;

public final class ServicesUtils {

    public static void bindVariables(Map<String, Object> parameters, StatementBuilder statement) {
        if (parameters.size() > 0) {
            for (String key : parameters.keySet()) {
                if (parameters.get(key) instanceof List) {
                    Set setValues = new HashSet((List) parameters.get(key));
                    statement.withBindVariableValue(key, setValues);
                } else {
                    statement.withBindVariableValue(key, parameters.get(key));
                }
            }
        }
    }

}
