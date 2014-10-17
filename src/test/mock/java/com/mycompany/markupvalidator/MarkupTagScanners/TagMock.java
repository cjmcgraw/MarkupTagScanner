/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */

package com.mycompany.markupvalidator.MarkupTagScanners;

import com.mycompany.markupvalidator.errors.ErrorReporter;
import com.mycompany.markupvalidator.errors.MarkupError;
import com.mycompany.markupvalidator.errors.MarkupErrorReporter;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TagMock implements Tag{
    public ErrorReporter reporter;
    public boolean openingBracket;
    public boolean closingBracket;
    public List<Attribute> attrs;
    public boolean isSelfClosing;
    public boolean isClosing;
    public String name;
    public String toString;
    public Point location;

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
    public Point location() {
        return this.location;
    }

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
