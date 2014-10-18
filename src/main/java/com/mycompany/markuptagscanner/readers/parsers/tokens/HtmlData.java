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
package com.mycompany.markuptagscanner.readers.parsers.tokens;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mycompany.markuptagscanner.*;
import com.mycompany.markuptagscanner.enums.*;
import com.mycompany.markuptagscanner.errors.*;

public class HtmlData implements Tag {
    private static final long serialVersionUID = -1778059172938272360L;
    private final static String COMMENT_START = MarkupTagNames.COMMENT_TAG.getBeginName();
    private final static String COMMENT_END = MarkupTagNames.COMMENT_TAG.getEndName();
    
    public final static String ELIPSIS = "[...]";
    public final static int MAX_NUM_ATTR_IN_STRING = 10;
    
    private ErrorReporter reporter;
    
    private boolean hasOpeningTag;
    private boolean hasClosingTag;
    private List<Attribute> attributes;
    private boolean selfClosing;
    private boolean isClosing;
    private StringBuilder name;
    private Point location;
    
    public HtmlData() {
        this("", false, false, false, false, new ArrayList<Attribute>());
    }
    
    public HtmlData(String name, boolean isClosing, boolean hasOpening,
                           boolean hasClosing, boolean selfClosing,
                           List<Attribute> defaultAttr) {
        this.name = new StringBuilder(name);
        this.attributes = defaultAttr;
        this.hasOpeningTag = hasOpening;
        this.hasClosingTag = hasClosing;
        this.selfClosing = selfClosing;
        this.isClosing = isClosing;
        this.location = new Point(0, 0);
        
        this.reporter = new MarkupErrorReporter();
    }
    
    public String getName() {
        return this.name.toString();
    }
    
    public ErrorReporter getErrorReporter() {
        return reporter;
    }
    
    public void confirmOpeningTag() {
        this.hasOpeningTag = true;
    }
    
    public boolean hasOpeningBracket() {
        return this.hasOpeningTag;
    }
    
    public void confirmClosingTag() {
        this.hasClosingTag = true;
    }
    
    public boolean hasClosingBracket() {
        return this.hasClosingTag;
    }
    
    public void setName(String name) {
        this.name = new StringBuilder(name.trim());
    }
    
    public void updateName(char c) {
        this.name.append(c);
    }
    
    public void updateName(String s) {
        this.name.append(s);
    }
    
    public List<Attribute> getAttributes() {
        return this.attributes;
    }
    
    public void setAttributes(Collection<Attribute> data) {
        this.attributes = new ArrayList<>();
        this.selfClosing = false;
        this.updateAttributes(data);
    }
    
    public void updateAttributes(Collection<Attribute> data) {
        for (Attribute attr : data)
            this.updateAttributes(attr);
    }
    
    public void updateAttributes(Attribute data) {
        if (this.isValidAttribute(data)) {
            this.selfClosing = this.selfClosing || this.isSelfClosingAttr(data);
            this.attributes.add(data);
        }
    }
    
    private boolean isValidAttribute(Attribute attr) {
        return attr != null && !attr.isEmpty();
    }
    
    private boolean isSelfClosingAttr(Attribute data) {
        return MarkupTag.CLOSING_ATTRIBUTE.equals(data.toString());
    }
    
    public boolean isClosing() {
        return this.isClosing;
    }
    
    public boolean isSelfClosing() {
        return this.selfClosing;
    }

    @Override
    public Point location() {
        return this.location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setIsClosing(boolean isClosing) {
        this.isClosing = isClosing;
    }
    
    private boolean isComment() {
        return this.name.toString().equals(COMMENT_START);
    }
    
    public String toString() {
        String open = MarkupTag.OPENING_TAG.toString();
        String close = MarkupTag.CLOSING_TAG.toString();
        String close_attr = MarkupTag.CLOSING_ATTRIBUTE.toString();
        
        String result = this.hasOpeningTag ? "" + open : "[" + open + "]";  
                
        result += (this.isClosing) ? close_attr : "";
        result += this.getName();
        result += " ";
        result += getAttributeString();
        result += (this.isComment()) ? COMMENT_END : "";
        result += (this.hasClosingTag) ? close : "[" + close + "]";
        
        return result;
    }
    
    private String getAttributeString() {
        if(this.attributes.size() > MAX_NUM_ATTR_IN_STRING)
            return ELIPSIS;
        
        String result = "";
        for (Attribute data : this.attributes)
            result += data.toString() + " ";
        return result.trim();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + (hasClosingTag ? 1231 : 1237);
        result = prime * result + (hasOpeningTag ? 1231 : 1237);
        result = prime * result + (isClosing ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
        result = prime * result + (selfClosing ? 1231 : 1237);
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HtmlData other = (HtmlData) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        if (hasClosingTag != other.hasClosingTag)
            return false;
        if (hasOpeningTag != other.hasOpeningTag)
            return false;
        if (isClosing != other.isClosing)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!getName().equals(other.getName()))
            return false;
        if (reporter == null) {
            if (other.reporter != null)
                return false;
        } else if (!reporter.equals(other.reporter))
            return false;
        if (selfClosing != other.selfClosing)
            return false;
        if (location == null)
            if (other.location != null)
                return false;
        if (!location.equals(other.location))
            return false;
        return true;
    }
}
