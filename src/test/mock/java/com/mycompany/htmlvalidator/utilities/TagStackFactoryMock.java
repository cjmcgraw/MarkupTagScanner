/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.utilities;

public class TagStackFactoryMock extends TagStackFactory {
    public TagStack tagStack;

    public TagStackFactoryMock(TagStackMock tagStack) {
        this.tagStack = tagStack;
    }

    @Override
    public TagStack create(String name) {
        return tagStack;
    }
}
