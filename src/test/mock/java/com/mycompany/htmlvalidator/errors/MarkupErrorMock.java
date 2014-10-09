/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.errors;

public class MarkupErrorMock extends MarkupError {
    public String toString;

    public MarkupErrorMock(String toString) {
        super("");
        toString = toString;
    }

    public String toString() {
        return (toString != null) ? toString : super.toString();
    }
}
