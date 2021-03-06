/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers.parsers.tokens;

import com.mycompany.markuptagscanner.Attribute;
import com.mycompany.markuptagscanner.enums.MarkupTag;
import com.mycompany.markuptagscanner.errors.*;

public class HtmlAttribute extends MarkupErrorReporter implements Attribute{
    private static final long serialVersionUID = 8687072175358271927L;
    public static final String DEFAULT_CLOSING_NAME = "self-closing";
    public static final String DEFAULT_EMPTY_VALUES = "";
    
    private String attributeName;
    private String attributeValue;
    
    public HtmlAttribute() {
        this(DEFAULT_EMPTY_VALUES);
    }
    
    public HtmlAttribute(String name) {
        this(name, DEFAULT_EMPTY_VALUES);
    }
    
    public HtmlAttribute(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }
    
    public String getName() {
        return this.attributeName;
    }
    
    public ErrorReporter getErrorReporter() {
        return this;
    }
    
    public void setName(String name) {
        if (MarkupTag.CLOSING_ATTRIBUTE.equals(name)) {
            this.attributeName = DEFAULT_CLOSING_NAME;
            this.setValue(DEFAULT_EMPTY_VALUES);
        }else
            this.attributeName = name.trim();
    }
    
    public String getValue() {
        return this.attributeValue;
    }
    
    public void setValue(String value) {
        if (this.attributeName.equals(DEFAULT_CLOSING_NAME))
            this.attributeValue = DEFAULT_EMPTY_VALUES;
        else
            this.attributeValue = value.trim();
    }
    
    public boolean isFlag() {
        return this.hasName() && !this.hasValue();
    }
    
    public boolean isClosingFlag() {
        return this.isFlag() && this.nameIs(DEFAULT_CLOSING_NAME);
    }
    
    public boolean isEmpty() {
        return this.nameIs(DEFAULT_EMPTY_VALUES) && this.valueIs(DEFAULT_EMPTY_VALUES);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
        result = prime * result + ((attributeValue == null) ? 0 : attributeValue.hashCode());
        result = prime * result + (super.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other instanceof HtmlAttribute)
            return equals((HtmlAttribute) other);
        else
            return super.equals(other);
    }
    
    public boolean equals(HtmlAttribute other) {
        boolean result = (this.nameIs(other.attributeName) &&
                          this.valueIs(other.attributeValue) &&
                          super.equals(other));
        return result;
    }
    
    public String toString() {
        String name = (this.isClosingFlag()) ? MarkupTag.CLOSING_ATTRIBUTE.toString() : this.attributeName;
        String value = (this.hasValue()) ? MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.toString() + this.attributeValue : "";
        return name + value;
    }
    
    private boolean hasValue() {
        return !this.valueIs(DEFAULT_EMPTY_VALUES);
    }
    
    private boolean hasName() {
        return !this.nameIs(DEFAULT_EMPTY_VALUES);
    }
    
    private boolean nameIs(String s) {
        return this.attributeName.equals(s);
    }
    
    private boolean valueIs(String s) {
        return this.attributeValue.equals(s);
    }
}
