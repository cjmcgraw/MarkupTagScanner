/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.utilities;

public class TagStackFactory {

    public TagStack create(String name) {
        switch (name.toLowerCase()) {
            case "html"     :       return new HtmlTagStack();
            default         :       throw new IllegalArgumentException();
        }
    }
}
