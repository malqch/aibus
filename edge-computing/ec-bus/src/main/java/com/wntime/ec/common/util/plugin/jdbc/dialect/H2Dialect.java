package com.wntime.ec.common.util.plugin.jdbc.dialect;


/**
 * A dialect compatible with the H2 database.
 *
 * @author Thomas Mueller
 */
public class H2Dialect extends Dialect {

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String sql, long offset, String offsetPlaceholder, long limit, String limitPlaceholder) {
        return new StringBuffer(sql.length() + 40).
                append(sql).
                append((offset > 0) ? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : " limit " + limitPlaceholder).
                toString();
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

//    public boolean bindLimitParametersInReverseOrder() {
//        return true;
//    }    
//
//    public boolean bindLimitParametersFirst() {
//        return false;
//    }


}