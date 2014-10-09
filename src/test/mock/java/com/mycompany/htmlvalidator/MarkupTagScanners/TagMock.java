/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.MarkupTagScanners;

import com.mycompany.htmlvalidator.errors.ErrorReporter;
import com.mycompany.htmlvalidator.errors.MarkupError;
import com.mycompany.htmlvalidator.errors.MarkupErrorReporter;

import java.util.*;

public class TagMock implements Tag{
    public ErrorReporter reporter;
    public boolean openingBracket;
    public boolean closingBracket;
    public List<Attribute> attrs;
    public boolean isSelfClosing;
    public boolean isClosing;
    public String name;
    public String toString;

    public TagMock() {
        this("");
    }

    public TagMock(String name) {
        this.reporter = new MarkupErrorReporter();
        this.openingBracket = false;
        this.closingBracket = false;
        this.attrs = new ArrayList<>();
        this.isSelfClosing = false;
        this.isClosing = false;
        this.name = name;
        this.toString = "MISSING STRING";
    }

    @Override
    public boolean hasOpeningBracket() {return openingBracket;}

    @Override
    public boolean hasClosingBracket() {return closingBracket;}

    @Override
    public String getName() {return name;}

    @Override
    public Iterable<Attribute> getAttributes() {return attrs;}

    @Override
    public boolean isClosing() {return isClosing;}

    @Override
    public boolean isSelfClosing() {return isSelfClosing;}

    @Override
    public ErrorReporter getErrorReporter() {return reporter;}

    @Override
    public String toString() {
        return toString;
    }

    public TagMock copy() {
        TagMock tag = new TagMock(name);
        tag.reporter = copyErrorReporter();
        tag.openingBracket = openingBracket;
        tag.closingBracket = closingBracket;
        tag.attrs = attrs;
        tag.isSelfClosing = isSelfClosing;
        tag.isClosing = isClosing;
        tag.toString = toString;
        return tag;
    }

    private ErrorReporter copyErrorReporter() {
        ErrorReporter result = new MarkupErrorReporter();

        for (MarkupError err : reporter.getErrors())
            result.addError(err);

        return result;
    }
}
