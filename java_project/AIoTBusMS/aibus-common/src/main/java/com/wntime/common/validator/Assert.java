

package com.wntime.common.validator;

import com.wntime.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }

    public static void isEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new RRException(message);
        }
    }

    public static void isEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new RRException(message);
        }
    }
}
