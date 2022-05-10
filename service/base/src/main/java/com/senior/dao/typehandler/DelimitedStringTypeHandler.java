
package com.senior.dao.typehandler;

/**
 * @author senior
 */
public class DelimitedStringTypeHandler extends AbstractDelimitedTypeHandler<String> {
    @Override
    protected String convert(String s) {
        return s;
    }
}
