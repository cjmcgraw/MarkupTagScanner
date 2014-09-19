package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mycompany.htmlvalidator.exceptions.ErrorReporter;
import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.tokens.*;

public class HtmlData implements Tag{
    private static final long serialVersionUID = -1778059172938272360L;
    private final static String COMMENT_START = MarkupTagNames.COMMENT_TAG.getBeginName();
    private final static String COMMENT_END = MarkupTagNames.COMMENT_TAG.getEndName();
    
    public final static String ELIPSIS = "[...]";
    public final static int MAX_NUM_ATTR_IN_STRING = 10;
    
    private boolean hasOpeningTag;
    private boolean hasClosingTag;
    private List<Attribute> attributes;
    private boolean selfClosing;
    private boolean isClosing;
    private StringBuilder name;
    
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
    }
    
    public String getName() {
        return this.name.toString();
    }
    
    public ErrorReporter getErrorReporter() {
        return null;
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
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + (hasClosingTag ? 1231 : 1237);
        result = prime * result + (hasOpeningTag ? 1231 : 1237);
        result = prime * result + (isClosing ? 1231 : 1237);
        result = prime * result + (selfClosing ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Tag)
            return equals((Tag) obj);
        else
            return super.equals(obj);
      
    }
    
    public boolean equals(Tag other) {
        boolean result = (
                this.getName().equals(other.getName()) &&
                this.getAttributes().equals(other.getAttributes()) &&
                this.isClosing == other.isClosing() && 
                this.hasClosingTag == other.hasClosingBracket() &&
                this.hasOpeningTag == other.hasOpeningBracket()
                );
        
        return result;
    }
}