/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.errors;

public class InvalidMarkupError extends MarkupError {
    public static final String ERROR_MSG = "Invalid tag - %s";

    public InvalidMarkupError(String msg) {
        super(String.format(ERROR_MSG, msg));
    }
}
