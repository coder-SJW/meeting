
package com.senior.dao.typehandler;

/**
 * @author senior
 */
public class DelimitedIntegerTypeHandler extends AbstractDelimitedTypeHandler<Integer> {
    @Override
    protected Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
